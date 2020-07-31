package com.linjia.publish.api.dao;

import com.linjia.common.publish.entity.query.PublistAdvertVO;
import com.linjia.publish.api.domain.PublistAdvertDO;
import com.linjia.publish.api.domain.UserFriendCircleDO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiscoveryDao {
    String USER_FRIEND_CIRCLE = "user_friend_circle";
    String USER_PROFILE = "user_profile";
    String USER_FRIEND_CIRCLE_REPLY = "user_friend_circle_reply";
    String PUBLISH_ADVERTISEMENT = "publish_advertisement";

    //入参id是，销售线索主键id
    @Select(value = {"select a.id,description,picture,picture_list,user_id,b.avatar_url,nick_name,a.status,like_num,a.gmt_create,a.gmt_update,a.lon,a.lat,a.address,like_list from " + USER_FRIEND_CIRCLE + " a," + USER_PROFILE + " b where a.user_id = b.id and a.lon <= #{maxLon} and a.lon >= #{minLon} and a.lat <= #{maxLat} and a.lat >= #{minLat} order by gmt_create desc limit #{index},#{pageSize}"})
    List<UserFriendCircleDO> queryByCoordinateAndPage(@Param("maxLon") double maxLon, @Param("minLon") double minLon, @Param("maxLat") double maxLat, @Param("minLat") double minLat, @Param("index") int index, @Param("pageSize") int pageSize);

    @Select(value = {"select count(1) from " + USER_FRIEND_CIRCLE + " where lon <= #{maxLon} and lon >= #{minLon} and lat <= #{maxLat} and lat >= #{minLat}"})
    int countByPos(@Param("maxLon") double maxLon, @Param("minLon") double minLon, @Param("maxLat") double maxLat, @Param("minLat") double minLat);

    //入参id是，销售线索主键id
    @Select(value = {"select like_num,like_list from " + USER_FRIEND_CIRCLE + " where id=#{id}"})
    UserFriendCircleDO queryPostLikesById(@Param("id") int id);

    //入参id是，销售线索主键id
    @Select(value = {"select a.id,description,picture,picture_list,user_id,nick_name,avatar_url,a.status,like_num,a.gmt_create,a.gmt_update,a.lon,a.lat,a.address,like_list from " + USER_FRIEND_CIRCLE + " a," + USER_PROFILE + " b where a.id=#{id} and a.user_id=b.id"})
    List<UserFriendCircleDO> queryById(@Param("id") int id);

    @Update(value = {"update " + USER_FRIEND_CIRCLE + " set like_num = #{likeNum} ,like_list = #{likeList} where id=#{id} "})
    int updateLikeState(@Param("id") int id, @Param("likeNum") int likeNum, @Param("likeList") String likeList);

    @Select(value = {"select count(1) from " + USER_FRIEND_CIRCLE_REPLY + " where info_id=#{id} and status = 0"})
    int countReplyByPostId(int id);

    @Select(value = {"select count(1) from " + USER_FRIEND_CIRCLE + " where lon <= #{maxLon} and lon >= #{minLon} and lat <= #{maxLat} and lat >= #{minLat} and gmt_create > #{time}"})
    int countByPosAndDate(@Param("maxLon") double maxLon, @Param("minLon") double minLon, @Param("maxLat") double maxLat, @Param("minLat") double minLat, @Param("time") String time);

    @Update("insert into " + USER_FRIEND_CIRCLE_REPLY + "(user_id,info_id,reply_id,content) values (#{userId},#{postId},#{replyId},#{content})")
    int insertReplyMessage(@Param("userId") String userId, @Param("postId") int postId, @Param("replyId") int replyId, @Param("content") String content);

    @Update("insert into " + USER_FRIEND_CIRCLE + "(description,picture,picture_list,user_id,status,like_num,gmt_create,gmt_update,lon,lat,address) values (#{description},#{picture},#{pictureList},#{userId},#{status},#{likeNum},#{gmtCreate},#{gmtUpdate},#{lon},#{lat},#{address})")
    int insertPost(@Param("description")String description, @Param("picture")String picture, @Param("pictureList")String pictureList, @Param("userId")String userId, @Param("status")int status, @Param("likeNum")int likeNum, @Param("gmtCreate")String gmtCreate, @Param("gmtUpdate")String gmtUpdate,@Param("lon") Double lon, @Param("lat")Double lat,@Param("address") String address);

    @Select(value = {"select id,advert_title,advert_content,advert_url,advert_img,advert_type,status from " + PUBLISH_ADVERTISEMENT + " where advert_type=#{adType} and status=1"})
    List<PublistAdvertDO> queryAdByType(@Param("adType") int adType);
}
