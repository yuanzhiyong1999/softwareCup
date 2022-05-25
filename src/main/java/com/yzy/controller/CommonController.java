package com.yzy.controller;

import com.yzy.common.LoginContext;
import com.yzy.entity.Records;
import com.yzy.service.IRecordsService;
import com.yzy.utils.*;
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
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.lang.reflect.Array;
import java.util.Map;

@RestController
public class CommonController {

    @Autowired
    private ChangeDetectionUtil changeDetectionUtil;

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

        LinkedHashMap<String,Object> lhm = (LinkedHashMap<String, Object>) handle.getData();

        ArrayList<Object> al = new ArrayList<>();
        for (Map.Entry<String,Object> entry: lhm.entrySet()){
            al.add(entry.getValue());
        }

        InputStream[] files = {uploadFile[0].getInputStream(),uploadFile[1].getInputStream()};
        String username =  LoginContext.getUser().getEmail();

//            上传图片
//        String[] url = handle.getData().toString().split("[=}]");
        changeDetectionUtil.handleOthers(files,al.get(0).toString(),username);

        return ResultUtil.succ(al.get(0),1);
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
