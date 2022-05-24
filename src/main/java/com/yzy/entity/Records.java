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
 * @since 2022-05-24
 */
@TableName("tb_records")
public class Records implements Serializable {


    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 第一张图片
     */
    private String firstPic;

    /**
     * 第二张图片
     */
    private String secondPic;

    /**
     * 处理结果
     */
    private String result;

    /**
     * 处理类型
     */
    private String type;

    /**
     * 用户
     */
    private String user;

    /**
     * 最后操作时间
     */
    private Date lastTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstPic() {
        return firstPic;
    }

    public void setFirstPic(String firstPic) {
        this.firstPic = firstPic;
    }

    public String getSecondPic() {
        return secondPic;
    }

    public void setSecondPic(String secondPic) {
        this.secondPic = secondPic;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }

    @Override
    public String toString() {
        return "Records{" +
        "id=" + id +
        ", firstPic=" + firstPic +
        ", secondPic=" + secondPic +
        ", result=" + result +
        ", type=" + type +
        ", user=" + user +
        ", lastTime=" + lastTime +
        "}";
    }
}
