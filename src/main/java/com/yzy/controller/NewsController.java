package com.yzy.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yzy.entity.News;
import com.yzy.entity.User;
import com.yzy.mapper.NewsMapper;
import com.yzy.service.INewsService;
import com.yzy.utils.ResultUtil;
import com.yzy.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author yuanzhiyong
 * @since 2022-07-07
 */
@RestController
@RequestMapping("/news")
public class NewsController {


    @Resource
    private INewsService newsService;

    @Resource
    private NewsMapper newsMapper;

//    @PostMapping("/add")
//    public ResultUtil addNews(String title, String url) {
//
//        News news = new News();
//        news.setTitle(title);
//        news.setUrl(url);
//        news.setReleaseTime(Utils.getTime());
//        if (newsService.save(news))
//            return ResultUtil.succ(null,1);
//        return ResultUtil.fail("添加失败");
//    }

    @GetMapping("/get")
    public ResultUtil getNews(long pageNum,long pageSize){

        Page<News> news = new Page<>(pageNum,pageSize);
        QueryWrapper<News> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("release_time");
        newsMapper.selectPage(news,queryWrapper);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("records", news.getRecords());
        jsonObject.put("total", news.getTotal());
        jsonObject.put("pages",news.getPages());

        return ResultUtil.succ(jsonObject,news.getRecords().size());
    }

}

