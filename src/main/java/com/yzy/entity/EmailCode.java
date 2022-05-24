package com.yzy.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author yuanzhiyong
 * @since 2022-05-21
 */
@TableName("tb_email_code")
public class EmailCode implements Serializable {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Integer id;

    private String code;

    private String email;

    private String sendType;

    private Date sendTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSendType() {
        return sendType;
    }

    public void setSendType(String sendType) {
        this.sendType = sendType;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    @Override
    public String toString() {
        return "EmailCode{" +
        "id=" + id +
        ", code=" + code +
        ", email=" + email +
        ", sendType=" + sendType +
        ", sendTime=" + sendTime +
        "}";
    }
}
