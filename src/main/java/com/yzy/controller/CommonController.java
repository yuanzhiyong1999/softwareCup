package com.yzy.controller;

import com.yzy.common.LoginContext;
import com.yzy.entity.Records;
import com.yzy.service.IRecordsService;
import com.yzy.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.lang.reflect.Array;
import java.util.Map;

@RestController
public class CommonController {

    @Autowired
    private Utils utils;

    //目标提取
    @PostMapping("/targetextraction")
    public ResultUtil target_extraction(@RequestParam("uploadFile") MultipartFile uploadFile) throws Exception {
        ResultUtil handle = RestTemplateUtil.targetExtraction(uploadFile);
        if (handle.getCode() != 200)
            return ResultUtil.fail(handle.getMsg());

        LinkedHashMap<String, Object> lhm = (LinkedHashMap<String, Object>) handle.getData();

        ArrayList<Object> al = new ArrayList<>();
        for (Map.Entry<String, Object> entry : lhm.entrySet()) {
            al.add(entry.getValue());
        }

        InputStream[] files = {uploadFile.getInputStream()};
        String username = LoginContext.getUser().getEmail();

        utils.handleOthers("target_extraction",files, al.get(0).toString(), username);

        return ResultUtil.succ(al.get(0), 1);
    }

    //    变化检测
    @PostMapping("/changedetection")
    public ResultUtil change_detection(@RequestParam("uploadFile") MultipartFile[] uploadFile) throws Exception {
//            处理
        ResultUtil handle = RestTemplateUtil.changeDetection(uploadFile);
        if (handle.getCode() != 200)
            return ResultUtil.fail(handle.getMsg());

        LinkedHashMap<String, Object> lhm = (LinkedHashMap<String, Object>) handle.getData();

        ArrayList<Object> al = new ArrayList<>();
        for (Map.Entry<String, Object> entry : lhm.entrySet()) {
            al.add(entry.getValue());
        }

        InputStream[] files = {uploadFile[0].getInputStream(), uploadFile[1].getInputStream()};
        String username = LoginContext.getUser().getEmail();

//            上传图片
//        String[] url = handle.getData().toString().split("[=}]");
        utils.handleOthers("change_detection",files, al.get(0).toString(), username);

        return ResultUtil.succ(al.get(0), 1);
    }

    //目标检测
    @PostMapping("/targetdetection")
    public ResultUtil target_detection(@RequestParam("uploadFile") MultipartFile uploadFile) throws Exception {
        ResultUtil handle = RestTemplateUtil.targetDetection(uploadFile);
        if (handle.getCode() != 200)
            return ResultUtil.fail(handle.getMsg());

//        LinkedHashMap<String, Object> lhm = (LinkedHashMap<String, Object>) handle.getData();
//
//        System.out.println(lhm);
//        ArrayList<Object> al = new ArrayList<>();
//        for (Map.Entry<String, Object> entry : lhm.entrySet()) {
//            al.add(entry.getValue());
//        }

        InputStream[] files = {uploadFile.getInputStream()};
        String username = LoginContext.getUser().getEmail();

        utils.handleOthers("target_detection",files,"result", username);

        return ResultUtil.succ(handle.getData(), 1);
    }

    //地形分类
    @PostMapping("/terrainclassification")
    public ResultUtil terrain_classification(@RequestParam("uploadFile") MultipartFile uploadFile) throws Exception {
        ResultUtil handle = RestTemplateUtil.terrainClassification(uploadFile);
        if (handle.getCode() != 200)
            return ResultUtil.fail(handle.getMsg());

        LinkedHashMap<String, Object> lhm = (LinkedHashMap<String, Object>) handle.getData();

        ArrayList<Object> al = new ArrayList<>();
        for (Map.Entry<String, Object> entry : lhm.entrySet()) {
            al.add(entry.getValue());
        }

        InputStream[] files = {uploadFile.getInputStream()};
        String username = LoginContext.getUser().getEmail();

        utils.handleOthers("terrain_classification",files, al.get(0).toString(), username);

        return ResultUtil.succ(al.get(0), 1);
    }
}
