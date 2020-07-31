package com.linjia.publish.api.dao;

import com.linjia.publish.api.domain.UserFriendCircleReplyDO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface DiscoveryReplyDao extends Mapper<UserFriendCircleReplyDO> {
    String TABLE_NAME = "user_friend_circle_reply";

    @Update(value = {"update " + TABLE_NAME + " set status = #{status} where id = #{id}"})
    int updateStatus(@Param("status") short status, @Param("id") long id);


    @Select(value = {"select id ,f_uid,f_name,to_uid,to_name,content,createtime from " + TABLE_NAME + " where status =0 and info_id = #{infoId} limit #{limitStart}, #{limitEnd}"})
    List<UserFriendCircleReplyDO> queryReplyByInfoId(@Param("infoId") long infoId, @Param("limitStart") int limitStart, @Param("limitEnd") int limitEnd);


}
