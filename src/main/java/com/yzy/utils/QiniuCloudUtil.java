package com.yzy.utils;

import com.qiniu.util.Auth;
import com.qiniu.util.Base64;
import com.qiniu.util.StringMap;
import com.qiniu.util.UrlSafeBase64;

import com.yzy.common.LoginContext;
import com.yzy.entity.Records;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 七牛云上传文件工具类
 */
@Service
public class QiniuCloudUtil {

    // 设置需要操作的账号的AK和SK
    private static final String ACCESS_KEY = "ZNW9hy971YuWSQOFHusyTQwGgRIJhY9JbRO6iuqf";
    private static final String SECRET_KEY = "S1B26pdzPlsCoAqtwFxPjmvteOuSRBfJrhAh6AuX";

    // 要上传的空间
    private static final String bucketname = "softwarecup";

    // 密钥
    private static final Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);

    private static final String DOMAIN = "http://cup.lijx.cloud/";

    private static final String style = "自定义的图片样式";

    public static String getUpToken() {
        return auth.uploadToken(bucketname, null, 3600, new StringMap().put("insertOnly", 1));
    }

    // 普通上传
//    public static String upload(String filePath, String fileName) throws IOException {
//        // 创建上传对象
//        Configuration config = new Configuration(regionGroup);
//        UploadManager uploadManager = new UploadManager();
//        try {
//            // 调用put方法上传
//            String token = auth.uploadToken(bucketname);
//            if(StringUtils.isEmpty(token)) {
//                System.out.println("未获取到token，请重试！");
//                return null;
//            }
//            Response res = uploadManager.put(filePath, fileName, token);
//            // 打印返回的信息
//            System.out.println(res.bodyString());
//            if (res.isOK()) {
//                Ret ret = res.jsonToObject(Ret.class);
//                //如果不需要对图片进行样式处理，则使用以下方式即可
//                //return DOMAIN + ret.key;
//                return DOMAIN + ret.key + "?" + style;
//            }
//        } catch (QiniuException e) {
//            Response r = e.response;
//            // 请求失败时打印的异常的信息
//            System.out.println(r.toString());
//            try {
//                // 响应的文本信息
//                System.out.println(r.bodyString());
//            } catch (QiniuException e1) {
//                // ignore
//            }
//        }
//        return null;
//    }

    //base64方式上传
    public static String put64image(byte[] base64, String key) throws Exception{
        String file64 = Base64.encodeToString(base64, 0);
        Integer len = base64.length;

        //华北空间使用 upload-z1.qiniu.com，华南空间使用 upload-z2.qiniu.com，北美空间使用 upload-na0.qiniu.com
        String url = "http://upload-z1.qiniu.com/putb64/" + len + "/key/"+ UrlSafeBase64.encodeToString(key);

        RequestBody rb = RequestBody.create(null, file64);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Content-Type", "application/octet-stream")
                .addHeader("Authorization", "UpToken " + getUpToken())
                .post(rb).build();
        //System.out.println(request.headers());
        OkHttpClient client = new OkHttpClient();
        okhttp3.Response response = client.newCall(request).execute();
        response.close();
//        System.out.println(response);
        //如果不需要添加图片样式，使用以下方式
        return DOMAIN + key;
//        return DOMAIN + key + "?" + style;
    }

    public static byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
        return output.toByteArray();
    }

    public static ResultUtil uploadTwo(InputStream[] uploadFile, String task_name) throws Exception {
        try {
            List<String> urls = new ArrayList<>();
            for (InputStream file : uploadFile) {
                byte[] bytes = toByteArray(file);
                String imageName = Utils.get_path_name(task_name);

                //使用base64方式上传到七牛云
                String url = put64image(bytes, imageName);
                urls.add(url);
            }
            return ResultUtil.succ(urls,1);
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtil.fail("保存记录异常");
        }
    }

//    // 普通删除(暂未使用以下方法，未测试)
//    public static void delete(String key) throws IOException {
//        // 实例化一个BucketManager对象
//        BucketManager bucketManager = new BucketManager(auth);
//        // 此处的33是去掉：http://ongsua0j7.bkt.clouddn.com/,剩下的key就是图片在七牛云的名称
//        key = key.substring(33);
//        try {
//            // 调用delete方法移动文件
//            bucketManager.delete(bucketname, key);
//        } catch (QiniuException e) {
//            // 捕获异常信息
//            Response r = e.response;
//            System.out.println(r.toString());
//        }
//    }
//
//    class Ret {
//        public long fsize;
//        public String key;
//        public String hash;
//        public int width;
//        public int height;
//    }
}