package com.linjia.publish.api.model;

import lombok.Data;

@Data
public class UserFriendCircleReply {
    private int id;
    private int user_id;
    private int post_id;
    private int reply_id;
    private String content;
}
