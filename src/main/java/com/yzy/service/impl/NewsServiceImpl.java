package com.yzy.service.impl;

import com.yzy.entity.News;
import com.yzy.mapper.NewsMapper;
import com.yzy.service.INewsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yuanzhiyong
 * @since 2022-07-07
 */
@Service
public class NewsServiceImpl extends ServiceImpl<NewsMapper, News> implements INewsService {

}
