package com.yzy.utils;

import com.yzy.common.LoginContext;
import com.yzy.entity.Records;
import com.yzy.service.IRecordsService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.ArrayList;

@Service
public class ChangeDetectionUtil {

    @Resource
    private IRecordsService recordsService;

    @Async
    public void handleOthers(InputStream[] files, String result, String username) throws Exception {
        ResultUtil upload = QiniuCloudUtil.uploadTwo(files, "change_detection");

//        解析数据
        ArrayList<String> sites = (ArrayList<String>) upload.getData();
//        System.out.println(url[1]);
//        System.out.println(sites.get(1));

//      插入数据库
        Records record = new Records();
        record.setFirstPic(sites.get(1));
        record.setSecondPic(sites.get(1));
        record.setResult(result);
        record.setType("change_detection");
        record.setUser(username);
        record.setLastTime(Utils.getTime());
        recordsService.save(record);
    }
}
