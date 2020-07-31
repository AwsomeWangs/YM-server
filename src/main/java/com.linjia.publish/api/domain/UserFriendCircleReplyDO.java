package com.linjia.publish.api.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "user_friend_circle_reply")
public class UserFriendCircleReplyDO implements Serializable {
    /**
     * 主键id
     * 表字段 : id
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 帖子动态ID
     * 表字段 : info_id
     */
    private Long infoId;
    /**
     * 朋友圈评论用户ID
     * 表字段 : f_uid
     */
    private Long fUid;
    /**
     * 朋友圈评论用户昵称
     * 表字段 : f_name
     */
    private String fName;
    /**
     * 要回复用户id
     * 表字段 : to_uid
     */
    private Long toUid;
    /**
     * 要回复用户昵称
     * 表字段 : to_name
     */
    private String toName;
    /**
     * 回复的内容
     * 表字段 : content
     */
    private String content;
    /**
     * 回复的内容状态
     * 表字段 : status
     */
    private Short status = 0;
    /**
     * 创建时间
     * 表字段 : createtime
     */
    @Column(insertable = false)
    private Date createtime;
    /**
     * 修改时间
     * 表字段 : updatetime
     */
    @Column(insertable = false)
    private Date updatetime;
}
