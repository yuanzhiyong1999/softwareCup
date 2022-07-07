package com.yzy.controller;

import com.alibaba.fastjson.JSONObject;
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
    public ResultUtil target_extraction(String image) throws Exception {
        ResultUtil handle = RestTemplateUtil.targetExtraction(image);
        if (handle.getCode() != 200)
            return ResultUtil.fail(handle.getMsg());

        LinkedHashMap<String, Object> lhm = (LinkedHashMap<String, Object>) handle.getData();

        String attention_img = (String) lhm.get("attention_img");
        String binary_img = (String) lhm.get("binary_img");

        String username = LoginContext.getUser().getEmail();

        utils.handleOthers("target_extraction", image, null, binary_img, username);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("binary_img", binary_img);
        jsonObject.put("attention_img", attention_img);

        return ResultUtil.succ(jsonObject, 1);
    }

    //    变化检测
    @PostMapping("/changedetection")
    public ResultUtil change_detection(String before, String after) throws Exception {
        ResultUtil handle = RestTemplateUtil.changeDetection(before, after);
        if (handle.getCode() != 200)
            return ResultUtil.fail(handle.getMsg());

        LinkedHashMap<String, Object> lhm = (LinkedHashMap<String, Object>) handle.getData();

        String attention_img = (String) lhm.get("attention_img");
        String binary_img = (String) lhm.get("binary_img");

        String username = LoginContext.getUser().getEmail();

        utils.handleOthers("change_detection", before, after, binary_img, username);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("binary_img", binary_img);
        jsonObject.put("attention_img", attention_img);
        return ResultUtil.succ(jsonObject,1);
    }

    //目标检测
    @PostMapping("/targetdetection")
    public ResultUtil target_detection(String image) throws Exception {
        ResultUtil handle = RestTemplateUtil.targetDetection(image);
        if (handle.getCode() != 200)
            return ResultUtil.fail(handle.getMsg());

//        LinkedHashMap<String, Object> lhm = (LinkedHashMap<String, Object>) handle.getData();
//
//        System.out.println(lhm);
//        ArrayList<Object> al = new ArrayList<>();
//        for (Map.Entry<String, Object> entry : lhm.entrySet()) {
//            al.add(entry.getValue());
//        }

//        InputStream[] files = {uploadFile.getInputStream()};
        String username = LoginContext.getUser().getEmail();
        String result = (String) handle.getData();

        utils.handleOthers("target_detection", image, null, result, username);

        return ResultUtil.succ(result, 1);
    }

    //地形分类
    @PostMapping("/terrainclassification")
    public ResultUtil terrain_classification(String image) throws Exception {
        ResultUtil handle = RestTemplateUtil.terrainClassification(image);
        if (handle.getCode() != 200)
            return ResultUtil.fail(handle.getMsg());

        LinkedHashMap<String, Object> lhm = (LinkedHashMap<String, Object>) handle.getData();

        String attention_img = (String) lhm.get("attention_img");
        String binary_img = (String) lhm.get("binary_img");

        String username = LoginContext.getUser().getEmail();

        utils.handleOthers("terrain_classification", image, null, binary_img, username);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("binary_img", binary_img);
        jsonObject.put("attention_img", attention_img);

        return ResultUtil.succ(jsonObject, 1);
    }
}
