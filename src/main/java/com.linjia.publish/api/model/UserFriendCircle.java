package com.linjia.publish.api.model;

import lombok.Data;

@Data
public class UserFriendCircle {
    private int id;
    private String description;// '用户id',
    private String picture;// '用户id',
    private String picture_list;// '用户id',
    private String user_id;// '用户id',
    private String nick_name;// '用户id',
    private String avatar_url;// '用户id',
    private int status;// '是否有效',
    private int like_num;//'点赞数',
    private int liked;//'是否已点赞 0.未 1.已',
    private String like_list;// '点赞者用户id',
    private String gmt_create;// '内容',
    private String gmt_update;//'图片',
    private double lon;//'经度',
    private double lat;//'纬度',
    private String address;//'地址',


}
