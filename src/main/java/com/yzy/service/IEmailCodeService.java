package com.yzy.service;

import com.yzy.entity.EmailCode;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yuanzhiyong
 * @since 2022-05-21
 */
public interface IEmailCodeService extends IService<EmailCode> {

    boolean sendEmail(String email_to, String type);

}
