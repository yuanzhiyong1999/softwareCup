package com.yzy.service.impl;

import com.yzy.entity.EmailCode;
import com.yzy.mapper.EmailCodeMapper;
import com.yzy.service.IEmailCodeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yzy.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yuanzhiyong
 * @since 2022-05-21
 */
@Service
public class EmailCodeServiceImpl extends ServiceImpl<EmailCodeMapper, EmailCode> implements IEmailCodeService {

    @Autowired
    private IEmailCodeService emailCodeService;

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Value("${others.base_url}")
    private String baseurl;

    @Value("${server.port}")
    private String port;


    public String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }


    /**
     * @param email_to 接收邮件地址
     * @param type     邮件类型：注册/找回密码
     * @return true/false
     */
    public boolean sendEmail(String email_to, String type) {
        try {
            EmailCode emailCode = new EmailCode();
            String code = getUUID();
            emailCode.setCode(code);
            emailCode.setEmail(email_to);
            emailCode.setSendType(type);
            emailCode.setSendTime(Utils.getTime());
            emailCodeService.save(emailCode);

            SimpleMailMessage message = new SimpleMailMessage();

            if (type == "register") {
                message.setSubject("注册激活");
                message.setText(String.format("请点击下方的链接激活你的账号：%s:%s/active/%s", baseurl, port, code));
            } else {
                message.setSubject("找回密码");
                message.setText(String.format("请点击下方的链接找回密码：%s:%s/active/%s", baseurl, port, code));
            }

            message.setTo(email_to);
            message.setFrom(from);
            mailSender.send(message);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
}
