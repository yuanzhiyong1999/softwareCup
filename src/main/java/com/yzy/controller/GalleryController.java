package com.yzy.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yzy.common.LoginContext;
import com.yzy.entity.Gallery;
import com.yzy.mapper.GalleryMapper;
import com.yzy.service.IGalleryService;
import com.yzy.utils.ResultUtil;
import com.yzy.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.xml.bind.DatatypeConverter;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

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

    @Resource
    private GalleryMapper galleryMapper;

    @Autowired
    private Utils utils;

    @PostMapping("/uploadimg")
    public ResultUtil uploadImgList(@RequestParam(value = "image") MultipartFile[] file) throws Exception {
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
            if (res.getCode() == 200)
                return ResultUtil.succ(res.getData(), res.getCount());
            else
                return ResultUtil.fail(res.getMsg());
        }
//        图片为空
        return ResultUtil.fail("图片为空");
    }

    //获取用户上传图片
    @GetMapping("/getallimage")
    public ResultUtil getallImage(String username) {
        QueryWrapper<Gallery> galleryQueryWrapper = new QueryWrapper<>();
        galleryQueryWrapper.eq("user_name", username).select("id", "img_name", "img_url", "upload_time");
        List<Gallery> list = galleryService.list(galleryQueryWrapper);
        return ResultUtil.succ(list, list.size());

    }
    //获取用户上传图片
    @GetMapping("/getimage")
    public ResultUtil getImage(long pageNum,long pageSize,String username) {
        Page<Gallery> galleryPage = new Page<>(pageNum,pageSize);
        QueryWrapper<Gallery> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id", "img_name", "img_url", "upload_time")
                .eq("user_name",username)
                .orderByDesc("upload_time");
        galleryMapper.selectPage(galleryPage,queryWrapper);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("records", galleryPage.getRecords());
        jsonObject.put("total", galleryPage.getTotal());
        jsonObject.put("pages",galleryPage.getPages());
        return ResultUtil.succ(jsonObject, galleryPage.getRecords().size());
    }

    @PostMapping("/deleteimg")
    public ResultUtil deleteImg(String id) {
        UpdateWrapper<Gallery> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", id)
                .set("is_deleted", 1);
        if (galleryService.update(null, updateWrapper))
            return ResultUtil.succ(null, 1);
        return ResultUtil.fail("删除失败");

    }

    @PostMapping("/save2gallery")
    public ResultUtil save2Gallery(@RequestBody Map<String,Object> map) throws Exception {

//        Base64.Decoder decoder = Base64.getDecoder();

        String username = LoginContext.getUser().getEmail();

        Map<String, Integer> params= (Map<String, Integer>) map.get("params");

        String filename = "";
        for (Map.Entry<String, Integer> entry : params.entrySet()) {
            if (entry.getValue()==0)
                continue;
            filename+="-";
            filename+= entry.getKey();
            filename+=entry.getValue();
        }
        String base64img =map.get("base64Data").toString();
        byte[] bytes = DatatypeConverter.parseBase64Binary(base64img);

        ResultUtil result = utils.uploadAndSave(bytes, username, filename);
        if (result.getCode() == 200)
            return ResultUtil.succ(result.getData(), result.getCount());
        return ResultUtil.fail(result.getMsg());
    }

}

