package com.linjia.publish.api.service;

import com.linjia.common.publish.entity.query.UserFriendCircleVO;

import java.util.List;

public interface DiscoveryService {
    List<UserFriendCircleVO> queryFriendCircleList(double lon, double lat, String userId, int pageIndex, int pageSize);

    int checkNewPost(double lon, double lat, String userId, String timeStamp);

    int likePost(String userId, int postId);

    boolean reply(String userId, int postId, int replyId, String content);

    List<UserFriendCircleVO> getFriendCircleDetail(String postId);
}
