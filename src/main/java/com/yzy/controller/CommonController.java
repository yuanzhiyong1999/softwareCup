package com.yzy.controller;

import com.yzy.common.LoginContext;
import com.yzy.entity.Records;
import com.yzy.service.IRecordsService;
import com.yzy.utils.QiniuCloudUtil;
import com.yzy.utils.RestTemplateUtil;
import com.yzy.utils.ResultUtil;
import com.yzy.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Array;

@RestController
public class CommonController {

    @Resource
    private IRecordsService recordsService;


    @PostMapping("/targetextraction")
    public ResultUtil target_extraction(HttpServletRequest request) {
        String code = request.getRequestURI().split("/")[2];
        return ResultUtil.succ(code, 1);
    }


    //    变化检测
    @PostMapping("/changedetection")
    public ResultUtil change_detection(@RequestParam("uploadFile") MultipartFile[] uploadFile) throws Exception {

//            处理
        ResultUtil handle = RestTemplateUtil.changeDetection(uploadFile);
        if (handle.getCode() != 200)
            return ResultUtil.fail(handle.getMsg());
//            上传图片

        ResultUtil upload = QiniuCloudUtil.uploadTwo(uploadFile, "change_detection");
        if (upload.getCode() != 200)
            return ResultUtil.fail(upload.getMsg());

//        解析数据
        String[] url = handle.getData().toString().split("[=}]");
        ArrayList<String> sites = (ArrayList<String>) upload.getData();
//        System.out.println(url[1]);
//        System.out.println(sites.get(1));

//      插入数据库
        Records record = new Records();
        record.setFirstPic(sites.get(1));
        record.setSecondPic(sites.get(1));
        record.setResult(url[1]);
        record.setType("change_detection");
        record.setUser(LoginContext.getUser().getEmail());
        record.setLastTime(Utils.getTime());
        recordsService.save(record);

        return ResultUtil.succ(url[1],1);
    }


    @PostMapping("/targetdetection")
    public ResultUtil target_detection(HttpServletRequest request) {
        String code = request.getRequestURI().split("/")[2];
        return ResultUtil.succ(code, 1);
    }


    @PostMapping("/terrainclassification")
    public ResultUtil terrain_classification(HttpServletRequest request) {
        String code = request.getRequestURI().split("/")[2];
        return ResultUtil.succ(code, 1);
    }
}
