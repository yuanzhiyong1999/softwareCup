package com.yzy.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yzy.entity.News;
import com.yzy.entity.Records;
import com.yzy.mapper.RecordsMapper;
import com.yzy.service.IRecordsService;
import com.yzy.utils.ResultUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yuanzhiyong
 * @since 2022-05-24
 */
@RestController
@RequestMapping("/records")
public class RecordsController {

    @Resource
    private RecordsMapper recordsMapper;

    @GetMapping("/get")
    public ResultUtil getRecords(long pageNum, long pageSize,String username){

        Page<Records> recordsPage = new Page<>(pageNum,pageSize);
        QueryWrapper<Records> recordsQueryWrapper = new QueryWrapper<>();
        recordsQueryWrapper.eq("user",username).orderByDesc("last_time");
        recordsMapper.selectPage(recordsPage,recordsQueryWrapper);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("records", recordsPage.getRecords());
        jsonObject.put("total", recordsPage.getTotal());
        jsonObject.put("pages",recordsPage.getPages());

        return ResultUtil.succ(jsonObject,recordsPage.getRecords().size());
    }

}

