package com.yzy.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.util.Objects;

@Service
public class RestTemplateUtil {

    private static RestTemplate restTemplate;

    @Autowired(required = true)
    public void setRestTemplate(RestTemplate restTemplate) {
        RestTemplateUtil.restTemplate = restTemplate;
    }

    private static String python_url;

    @Value("${others.python_url}")
    public void setPython_url(String python_url) {
        this.python_url = python_url;
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


    public static ResultUtil changeDetection(MultipartFile[] uploadFile) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        //设置提交方式都是表单提交
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> paramsMap = new LinkedMultiValueMap<>();

        try {
            String[] arr = {"before", "after"};
            for (int i = 0; i < 2; i++) {
//                byte[] buffer = toByteArray(uploadFile[i]);
                ByteArrayResource temp = new ByteArrayResource(uploadFile[i].getBytes()) {
                    @Override
                    public String getFilename() throws IllegalStateException {
                        return "y";
                    }
                };
                paramsMap.add(arr[i], temp);
            }


            // 构造请求的实体。包含body和headers的内容

            HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(paramsMap, headers);

            ResultUtil respBody = restTemplate.postForObject(python_url + "/change_detection", request, ResultUtil.class);

            if (respBody.getCode() == 200) {
                return ResultUtil.succ(respBody.getData(), 1);
            }
            return ResultUtil.fail("处理失败");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.fail("处理异常");
        }
    }

    public static ResultUtil targetExtraction(MultipartFile uploadFile) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        //设置提交方式都是表单提交
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> paramsMap = new LinkedMultiValueMap<>();

        try {
            ByteArrayResource temp = new ByteArrayResource(uploadFile.getBytes()) {
                @Override
                public String getFilename() throws IllegalStateException {
                    return "y";
                }
            };
            paramsMap.add("image", temp);
            HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(paramsMap, headers);
            ResultUtil respBody = restTemplate.postForObject(python_url + "/target_extraction", request, ResultUtil.class);

            if (respBody.getCode() == 200) {
                return ResultUtil.succ(respBody.getData(), 1);
            }
            return ResultUtil.fail("处理失败");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.fail("处理异常");
        }
    }

    public static ResultUtil targetDetection(MultipartFile uploadFile) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        //设置提交方式都是表单提交
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> paramsMap = new LinkedMultiValueMap<>();

        try {
            ByteArrayResource temp = new ByteArrayResource(uploadFile.getBytes()) {
                @Override
                public String getFilename() throws IllegalStateException {
                    return "y";
                }
            };
            paramsMap.add("image", temp);
            HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(paramsMap, headers);
            ResultUtil respBody = restTemplate.postForObject(python_url + "/target_detection", request, ResultUtil.class);

            if (respBody.getCode() == 200) {
                return ResultUtil.succ(respBody.getData(), 1);
            }
            return ResultUtil.fail("处理失败");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.fail("处理异常");
        }
    }

    public static ResultUtil terrainClassification(MultipartFile uploadFile) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        //设置提交方式都是表单提交
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> paramsMap = new LinkedMultiValueMap<>();

        try {
            ByteArrayResource temp = new ByteArrayResource(uploadFile.getBytes()) {
                @Override
                public String getFilename() throws IllegalStateException {
                    return "y";
                }
            };
            paramsMap.add("image", temp);
            HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(paramsMap, headers);
            ResultUtil respBody = restTemplate.postForObject(python_url + "/terrain_classification", request, ResultUtil.class);

            if (respBody.getCode() == 200) {
                return ResultUtil.succ(respBody.getData(), 1);
            }
            return ResultUtil.fail("处理失败");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.fail("处理异常");
        }
    }
}
