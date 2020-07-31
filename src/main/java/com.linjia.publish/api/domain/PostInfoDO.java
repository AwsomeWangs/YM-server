package com.linjia.publish.api.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "post_info")
public class PostInfoDO implements Serializable {
    private static final long serialVersionUID = -7938664687049908947L;
    /**
     * 帖子ID
     * 表字段 : post_info.id
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 帖子标题
     * 表字段 : post_info.title
     */
    private String title;

    /**
     * 帖子位置
     * 表字段 : post_info.location
     */
    private String location;

    /**
     * 帖子描述
     * 表字段 : post_info.description
     */
    private String description;

    /**
     * 帖子描述
     * 表字段 : post_info.price
     */
    private Double price;

    /**
     * 帖子描述
     * 表字段 : post_info.price_unit
     */
    private String priceUnit;

    /**
     * 列表主图
     * 表字段 : post_info.picture
     */
    private String picture;

    /**
     * 图片列表
     * 表字段 : post_info.picture_list
     */
    private String pictureList;

    /**
     * 帖子类型：（0：全部，1：二手，2：租借，3：活动，4：互助， 11：推荐）
     * 表字段 : post_info.post_type
     */
    private Integer postType;

    /**
     * 发布时间
     * 表字段 : post_info.post_date
     */
    private Date postDate;

    /**
     * 用户ID
     * 表字段 : post_info.user_id
     */
    private Long userId;

    /**
     * 表字段 : post_info.user_name
     */
    private String userName;

    /**
     * 用户手机
     * 表字段 : post_info.mobile
     */
    private String mobile;

    /**
     * 帖子状态：（0：正常，-1：失效）
     * 表字段 : post_info.status
     */
    private Integer status;

    /**
     * 表字段 : post_info.likeNum
     */
    private Integer likeNum;

    /**
     * 创建时间
     * 表字段 : post_info.gmt_create
     */
    private Date gmtCreate;

    /**
     * 更新时间
     * 表字段 : post_info.gmt_update
     */
    private Date gmtUpdate;

    /**
     * 经度
     * 表字段 : post_info.lon
     */
    private Double lon;

    /**
     * 纬度
     * 表字段 : post_info.lat
     */
    private Double lat;

    /**
     * 图片在服务器上的存储文件夹
     * 表字段 : post_info.picture_adress
     */
    private String pictureAdress;
    /**
     * 用户与帖子的距离
     */
    @Transient
    @Column(insertable = false, updatable = false)
    private Double distance;
}