package com.yzy.common.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.interfaces.Claim;
import com.yzy.common.LoginContext;
import com.yzy.entity.User;
import com.yzy.mapper.UserMapper;
import com.yzy.utils.TokenUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Resource
    private UserMapper userMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler)throws Exception{
        if(request.getMethod().equals("OPTIONS")){
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }
        response.setCharacterEncoding("utf-8");
        String token = request.getHeader("token"); //前端vue将token添加在请求头中
        if(token != null){
            Map<String, Claim> result = TokenUtil.verify(token);
            if(result.size()!=0){
//                System.out.println("通过拦截器");
                String email = result.get("email").asString();
                User user = userMapper.selectById(email);
                LoginContext.add(user);
                return true;
            }
        }
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try{
            JSONObject json = new JSONObject();
            json.put("msg","凭证失效，请重新登录");
            json.put("code","50000");
            response.getWriter().append(json.toJSONString());
//            System.out.println("认证失败，未通过拦截器");

        }catch (Exception e){
            e.printStackTrace();
            response.sendError(500);
            return false;
        }
        return false;
    }
}
