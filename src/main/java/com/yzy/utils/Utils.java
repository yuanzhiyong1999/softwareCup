package com.yzy.utils;

import com.yzy.entity.Gallery;
import com.yzy.entity.Records;
import com.yzy.service.IGalleryService;
import com.yzy.service.IRecordsService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class Utils {
    @Resource
    private IRecordsService recordsService;

    @Resource
    private IGalleryService galleryService;

    public static String MD5(String input) {
        //获取MD5机密实例
        return DigestUtils.md5DigestAsHex(input.getBytes());
    }

    public static Timestamp getTime(){
        Date date = new Date();//获得系统时间.

        String nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);//将时间格式转换成符合Timestamp要求的格式.

        return Timestamp.valueOf(nowTime);

    }

    public static String get_path_name(String task_name){
        Calendar ca = Calendar.getInstance();
        String year = String.valueOf(ca.get(Calendar.YEAR));
        String month = String.valueOf(ca.get(Calendar.MONTH) + 1);
        String day = String.valueOf(ca.get(Calendar.DATE));
        String xxx = year + '/' + month + '/' + day;
        return String.format("img/%s/%s/%s", task_name, xxx, UUID.randomUUID().toString());
    }

    @Async
    public void handleOthers(String type,String before,String after, String result, String username) throws Exception {
//        ResultUtil upload = QiniuCloudUtil.upload(files, "change_detection");

//        解析数据
//        ArrayList<String> sites = (ArrayList<String>) upload.getData();
//        System.out.println(url[1]);
//        System.out.println(sites.get(1));

//      插入数据库
        Records record = new Records();
        record.setFirstPic(before);
        if (Objects.equals(type, "change_detection"))
            record.setSecondPic(after);
        record.setResult(result);
        record.setType(type);
        record.setUser(username);
        record.setLastTime(Utils.getTime());
        recordsService.save(record);
    }

    public ResultUtil uploadAndSave (InputStream[] files, String username, ArrayList<String> filesname) throws Exception {
        ResultUtil upload = QiniuCloudUtil.upload(files, "gallery");
        if (upload.getCode() != 200)
            return ResultUtil.fail("上传云盘失败");
        ArrayList<String> sites = (ArrayList<String>) upload.getData();
//        System.out.println(sites);
//        System.out.println(sites.get(1));

//      插入数据库
        for (int i=0;i<sites.size();i++){
            Gallery gallery = new Gallery();
            gallery.setImgName(filesname.get(i));
            gallery.setImgUrl(sites.get(i));
            gallery.setUserName(username);
            gallery.setUploadTime(getTime());
            gallery.setIsDeleted(0);
            galleryService.save(gallery);
        }
    return ResultUtil.succ(sites,sites.size());
    }
    public ResultUtil uploadAndSave (byte[] base64Data, String username, String condition) throws Exception {
        String imageName = Utils.get_path_name("enhancement");
        String url = null;
        try {
            url = QiniuCloudUtil.put64image(base64Data, imageName);
        }catch (Exception e){
            return ResultUtil.fail("上传异常");
        }

        try {
            Gallery gallery = new Gallery();
            gallery.setImgName(condition);
            gallery.setImgUrl(url);
            gallery.setUserName(username);
            gallery.setUploadTime(getTime());
            gallery.setIsDeleted(0);
            galleryService.save(gallery);
        }catch (Exception b){
            return ResultUtil.fail("保存异常");
        }

        return ResultUtil.succ(url,1);
    }
}