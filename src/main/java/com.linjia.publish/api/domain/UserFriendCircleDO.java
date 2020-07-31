package com.linjia.publish.api.domain;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
public class UserFriendCircleDO {
    private int id;
    private String description;// '用户id',
    private String picture;// '用户id',
    private String pictureList;// '用户id',
    private int userId;// '用户id',
    private String nickName;// '用户id',
    private String avatarUrl;// '用户id',
    private int status;// '是否有效',
    private int likeNum;//'点赞数',
    private int liked;//'是否已点赞 0.未 1.已',
    private String likeList;// '点赞者用户id',
    private String gmtCreate;// '内容',
    private String gmtUpdate;//'图片',
    private double lon;//'经度',
    private double lat;//'纬度',
    private String address;//'地址',


}
