package com.linjia.publish.api.dao;

import com.linjia.publish.api.model.UserFriendCircle;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface FriendCircleDao {
    String USER_FRIEND_CIRCLE = "user_friend_circle";
    String USER_PROFILE = "user_profile";
    String USER_FRIEND_CIRCLE_REPLY = "user_friend_circle_reply";

    //入参id是，销售线索主键id
    @Select(value = {"select a.id,description,picture,picture_list,user_id,b.avatar_url,nick_name,a.status,like_num,a.gmt_create,a.gmt_update,lon,lat,a.address,like_list from " + USER_FRIEND_CIRCLE + " a," + USER_PROFILE + " b where a.user_id = b.id and lon <= #{maxLon} and lon >= #{minLon} and lat <= #{maxLat} and lat >= #{minLat} order by gmt_create desc limit #{index},#{pageSize}"})
    List<UserFriendCircle> queryByCoordinateAndPage(@Param("maxLon") double maxLon, @Param("minLon") double minLon, @Param("maxLat") double maxLat, @Param("minLat") double minLat, @Param("index") int index, @Param("pageSize") int pageSize);

    @Select(value = {"select count(1) from " + USER_FRIEND_CIRCLE + " where lon <= #{maxLon} and lon >= #{minLon} and lat <= #{maxLat} and lat >= #{minLat}"})
    int countByPos(@Param("maxLon") double maxLon, @Param("minLon") double minLon, @Param("maxLat") double maxLat, @Param("minLat") double minLat);

    //入参id是，销售线索主键id
    @Select(value = {"select like_num,like_list from " + USER_FRIEND_CIRCLE + " where id=#{id}"})
    UserFriendCircle queryPostLikesById(@Param("id") int id);

    //入参id是，销售线索主键id
    @Select(value = {"select a.id,description,picture,picture_list,user_id,nick_name,a.status,like_num,a.gmt_create,a.gmt_update,lon,lat,a.address,like_list from " + USER_FRIEND_CIRCLE + " a," + USER_PROFILE + " b where a.id=#{id} and a.user_id=b.id"})
    List<UserFriendCircle> queryById(@Param("id") int id);

    @Update(value = {"update " + USER_FRIEND_CIRCLE + " set like_num = #{likeNum} ,like_list = #{likeList} where id=#{id} "})
    int updateLikeState(@Param("id") int id, @Param("likeNum") int likeNum, @Param("likeList") String likeList);

    @Select(value = {"select count(1) from " + USER_FRIEND_CIRCLE_REPLY + " where post_id=#{id}"})
    int countReplyByPostId(int id);

    @Select(value = {"select count(1) from " + USER_FRIEND_CIRCLE + " where lon <= #{maxLon} and lon >= #{minLon} and lat <= #{maxLat} and lat >= #{minLat} and gmt_create > #{time}"})
    int countByPosAndDate(@Param("maxLon") double maxLon, @Param("minLon") double minLon, @Param("maxLat") double maxLat, @Param("minLat") double minLat, @Param("time") String time);

    @Update("insert into " + USER_FRIEND_CIRCLE_REPLY + "(user_id,post_id,reply_id,content) values (#{userId},#{postId},#{replyId},#{content})")
    int insertReplyMessage(@Param("userId") String userId, @Param("postId") int postId, @Param("replyId") int replyId, @Param("content") String content);
}
