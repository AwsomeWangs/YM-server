package com.linjia.publish.api.service;

import com.linjia.common.discovery.FriendCircleReplyDTO;
import com.linjia.common.publish.entity.query.PublistAdvertVO;
import com.linjia.common.publish.entity.query.UserFriendCircleVO;

import java.util.List;

public interface DiscoveryService {
    List<UserFriendCircleVO> queryFriendCircleList(double lon, double lat, String userId, int pageIndex, int pageSize);

    int checkNewPost(double lon, double lat, String userId, String timeStamp);

    int likePost(String userId, int postId);

    boolean reply(FriendCircleReplyDTO friendCircleReplyDTO);

    boolean delReply(long id);

    List<FriendCircleReplyDTO> getReplyByInfoId(long infoId, int pn);

    List<UserFriendCircleVO> getFriendCircleDetail(String postId);

    int saveFriendCircle(Double lon, Double lat, String userId, String description, String picture, String pictureList, String address);

    List<PublistAdvertVO> getDiscoveryAdBanner(int adType);
}
