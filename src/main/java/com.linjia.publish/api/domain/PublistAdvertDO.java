package com.linjia.publish.api.domain;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "publish_advertisement")
public class PublistAdvertDO implements Serializable {
    private static final long serialVersionUID = -7938664687049908947L;
    /**
     * 帖子ID
     * 表字段 : publish_advertisement.id
     */
    @Id
    @GeneratedValue(generator ="JDBC")
    private Long id;

    /**
     * 广告标题
     * 表字段 : publish_advertisement.advert_title
     */
    private String advertTitle;

    /**
     * 广告内容
     * 表字段 : publish_advertisement.advert_content
     */
    private String advertContent;

    /**
     * 广告落地页
     * 表字段 : publish_advertisement.advert_url
     */
    private String advertUrl;

    /**
     * 广告图片
     * 表字段 : publish_advertisement.advert_img
     */
    private String advertImg;

    /**
     * 广告类型
     * 表字段 : publish_advertisement.advert_type
     */
    private int advertType;

    /**
     * 广告状态：（0：无效，1：有效）
     * 表字段 : publish_advertisement.status
     */
    private int status;

}