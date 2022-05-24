package com.yzy.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.yzy.entity.EmailCode;
import com.yzy.service.IEmailCodeService;
import com.yzy.utils.ResultUtil;
import com.yzy.utils.TokenUtil;
import com.yzy.utils.Utils;
import com.yzy.entity.User;
import com.yzy.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author yuanzhiyong
 * @since 2022-05-21
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IEmailCodeService emailCodeService;

    @Autowired
    private IUserService userService;


    @PostMapping("/login")
    public ResultUtil login(String username, String password) {

        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        queryWrapper.eq("email", username).eq("is_active", true);
        User user = userService.getOne(queryWrapper);
        if (user != null) {
            if (user.getPassword().equals(Utils.MD5(password))) {
                user.setPassword(null);
//                获取token并返回用户信息和toekn
                String token = TokenUtil.sign(user);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("userinfo", user);
                jsonObject.put("token", token);

                return ResultUtil.succ(jsonObject, 1);
            } else {
                return ResultUtil.fail("用户名或密码错误");
            }

        } else {
            return ResultUtil.fail("账号未注册或未激活");
        }
    }

    @PostMapping("/register")
    public ResultUtil register(String username, String password) {
        User user = userService.getById(username);
        if (user != null)
            return ResultUtil.fail("邮箱已注册");

        User user1 = new User();
        user1.setEmail(username);
        user1.setPassword(Utils.MD5(password));
        user1.setIsActive(false);

        user1.setLastLogin(Utils.getTime());

        userService.save(user1);

        if (emailCodeService.sendEmail(username, "register"))
            return ResultUtil.succ(null, 1);
        else
            return ResultUtil.fail("注册异常");
    }

    @PostMapping("/active")
    public ResultUtil active(String code){
        QueryWrapper<EmailCode> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code",code);
        try {
            EmailCode emailCode = emailCodeService.getOne(queryWrapper);
            if (emailCode != null) {

                QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
                userQueryWrapper.eq("email",emailCode.getEmail());
                User user = userService.getOne(userQueryWrapper);

                UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
                userUpdateWrapper.eq("email",user.getEmail())
                        .set("is_active",true);
                userService.update(null,userUpdateWrapper);

                return ResultUtil.succ(null, 1);
            } else {
                return ResultUtil.fail("激活失败");
            }
        } catch (Exception e) {
            return ResultUtil.fail("激活异常");
        }

    }

    //更新用户个人信息
    @PostMapping("/updateuser")
    public ResultUtil updateInfo(User request){

        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("email",request.getEmail())
                .set("username",request.getUsername())
                .set("gender",request.getGender());
        try {
            if (userService.update(null, updateWrapper)){
                return ResultUtil.succ(null,1);
            }else {
                return ResultUtil.fail("更新个人信息失败");
            }
        }catch (Exception e){
            return ResultUtil.fail("更新个人信息异常");
        }
    }

}


