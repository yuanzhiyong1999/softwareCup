package com.yzy.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
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


    public static ResultUtil changeDetection(String before, String after){
        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<String, Object>();
        paramMap.add("before", before);
        paramMap.add("after", after);
        ResultUtil respBody = restTemplate.postForObject(python_url + "/change_detection", paramMap, ResultUtil.class);

        if (respBody.getCode() == 200) {
            return ResultUtil.succ(respBody.getData(), respBody.getCount());
        }
        return ResultUtil.fail(respBody.getMsg());

}

    public static ResultUtil targetExtraction(String image){
        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<String, Object>();
        paramMap.add("image", image);
        ResultUtil respBody = restTemplate.postForObject(python_url + "/target_extraction", paramMap, ResultUtil.class);

        if (respBody.getCode() == 200) {
            return ResultUtil.succ(respBody.getData(), respBody.getCount());
        }
        return ResultUtil.fail(respBody.getMsg());
    }

    public static ResultUtil targetDetection(String image){
        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<String, Object>();
        paramMap.add("image", image);
        ResultUtil respBody = restTemplate.postForObject(python_url + "/target_detection", paramMap, ResultUtil.class);

        if (respBody.getCode() == 200) {
            return ResultUtil.succ(respBody.getData(), respBody.getCount());
        }
        return ResultUtil.fail(respBody.getMsg());
    }

    public static ResultUtil terrainClassification(String image) {
        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<String, Object>();
        paramMap.add("image", image);
        ResultUtil respBody = restTemplate.postForObject(python_url + "/terrain_classification", paramMap, ResultUtil.class);

        if (respBody.getCode() == 200) {
            return ResultUtil.succ(respBody.getData(), respBody.getCount());
        }
        return ResultUtil.fail(respBody.getMsg());
    }
}
