package com.yzy.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yzy.common.LoginContext;
import com.yzy.entity.Gallery;
import com.yzy.service.IGalleryService;
import com.yzy.utils.ResultUtil;
import com.yzy.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author yuanzhiyong
 * @since 2022-07-01
 */
@RestController
@RequestMapping("/gallery")
public class GalleryController {


    @Autowired
    private IGalleryService galleryService;

    @Autowired
    private Utils utils;

    @PostMapping("/uploadpic")
    public ResultUtil uploadPicList(@RequestParam(value = "image") MultipartFile[] file) throws Exception {
        if (file != null && file.length > 0) {
            ArrayList<String> filenames = new ArrayList<>();
            InputStream[] files = new InputStream[file.length];
            for (int i = 0; i < file.length; i++) {
                String fileName = file[i].getOriginalFilename();//获取文件名加后缀
                filenames.add(fileName);
                files[i] = file[i].getInputStream();
            }
//            InputStream[] files = {file[0].getInputStream(), file[1].getInputStream()};
            String username = LoginContext.getUser().getEmail();

            ResultUtil res = utils.uploadAndSave(files, username, filenames);
            if (res.getCode() ==200)
                return ResultUtil.succ(res.getData(),res.getCount());
            else
                return ResultUtil.fail(res.getMsg());
        }
//        图片为空
        return ResultUtil.fail("图片为空");
    }

    //获取用户上传图片
    @GetMapping("/getimage")
    public ResultUtil getImage(String username){

        QueryWrapper<Gallery> galleryQueryWrapper = new QueryWrapper<>();
        galleryQueryWrapper.eq("user_name",username).select("img_name","img_url","upload_time");
        List<Gallery> list =  galleryService.list(galleryQueryWrapper);
        return ResultUtil.succ(list,list.size());
    }
}
