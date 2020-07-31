package com.linjia.publish.api.service.serviceImpl;

import com.linjia.common.publish.entity.query.UserFriendCircleVO;
import com.linjia.common.util.LocationUtils;
import com.linjia.publish.api.dao.FriendCircleDao;
import com.linjia.publish.api.model.UserFriendCircle;
import com.linjia.publish.api.service.DiscoveryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class DiscoveryServiceImpl implements DiscoveryService {
    @Autowired
    FriendCircleDao friendCircleDao;

    @Override
    public List<UserFriendCircleVO> queryFriendCircleList(double lon, double lat, String userId, int pageIndex, int pageSize) {
        double distance = 200;
        int startIndex = (pageIndex - 1) * pageSize;
        List<UserFriendCircle> userFriendCircleList = getFriendCircleList(lon, lat, distance, startIndex, pageSize);
        //补足策略待完善
        if (userFriendCircleList.size() < pageSize) {
            userFriendCircleList = getFriendCircleList(lon, lat, distance * 2, startIndex, pageSize);
        }

        List<UserFriendCircleVO> result = new ArrayList<>();
        for (int i = 0; i < userFriendCircleList.size(); i++) {
            double latB = userFriendCircleList.get(i).getLat();
            double lonB = userFriendCircleList.get(i).getLon();
            double range = LocationUtils.getDistance(lat, lon, latB, lonB);
            NumberFormat nf = NumberFormat.getInstance();
            // 设置精确到小数点后n位
            nf.setMaximumFractionDigits(1);
            String rangeStr = nf.format(range / 1000) + "km";
            String likeList = userFriendCircleList.get(i).getLike_list();
            int liked = 0;
            if (StringUtils.isNotBlank(likeList) && likeList.contains("<" + userId + ">")) {
                liked = 1;
            }
            int id = userFriendCircleList.get(i).getId();
            int replyNum = friendCircleDao.countReplyByPostId(id);
            UserFriendCircleVO userFriendCircleVO = new UserFriendCircleVO();
            setUserFriendCircleVO(userFriendCircleVO,userFriendCircleList.get(i));
            userFriendCircleVO.setLiked(liked);
            userFriendCircleVO.setDistance(rangeStr);
            userFriendCircleVO.setReplyNum(replyNum);
            result.add(userFriendCircleVO);
        }
        return result;
    }

    public void setUserFriendCircleVO(UserFriendCircleVO userFriendCircleVO,UserFriendCircle userFriendCircle) {
        userFriendCircleVO.setId(userFriendCircle.getId());
        userFriendCircleVO.setDescription(userFriendCircle.getDescription());
        userFriendCircleVO.setPicture(userFriendCircle.getPicture());
        userFriendCircleVO.setUserId(userFriendCircle.getUser_id());
        userFriendCircleVO.setNickName(userFriendCircle.getNick_name());
        userFriendCircleVO.setStatus(userFriendCircle.getStatus());
        userFriendCircleVO.setLiked(userFriendCircle.getLiked());
        userFriendCircleVO.setLikeNum(userFriendCircle.getLike_num());
        userFriendCircleVO.setGmtCreate(userFriendCircle.getGmt_create());
        userFriendCircleVO.setGmtUpdate(userFriendCircle.getGmt_update());
        userFriendCircleVO.setLon(userFriendCircle.getLon());
        userFriendCircleVO.setLat(userFriendCircle.getLat());
        userFriendCircleVO.setAddress(userFriendCircle.getAddress());
        userFriendCircleVO.setHeadImg(userFriendCircle.getAvatar_url());
    }

    @Override
    public int checkNewPost(double lon, double lat, String userId, String timeStamp) {
        //以用户位置为中心边长10公里矩形
        //纬度1度=111公里，经度一度=111公里*COS纬度
        double distance = 10;
        double maxLon = lon + distance / (111 * Math.cos(Math.toRadians(lat)));
        double minLon = lon - distance / (111 * Math.cos(Math.toRadians(lat)));
        double maxLat = lat + distance / 111;
        double minLat = lat - distance / 111;
        Date date = new Date(Long.valueOf(timeStamp));
        SimpleDateFormat k = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        String time = k.format(date);
        int count = friendCircleDao.countByPosAndDate(maxLon, minLon, maxLat, minLat, time);
        return count;
    }

    @Override
    public int likePost(String userId, int postId) {
        int likeNum = 0;
        String likeList = null;
        UserFriendCircle friendCircle = friendCircleDao.queryPostLikesById(postId);
        if (friendCircle != null) {
            likeNum = friendCircle.getLike_num() + 1;
            likeList = friendCircle.getLike_list();
            if (likeList.contains("<" + userId + ">")) {
                return 0;
            }
            if (StringUtils.isNotBlank(likeList)) {
                likeList = likeList + "<" + userId + ">";
            } else {
                likeList = "<" + userId + ">";
            }
        }
        int res = friendCircleDao.updateLikeState(postId, likeNum, likeList);
        return res;
    }

    @Override
    public boolean reply(String userId, int postId, int replyId, String content) {
        int res = friendCircleDao.insertReplyMessage(userId, postId, replyId, content);
        if (res == 1) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<UserFriendCircleVO> getFriendCircleDetail(String postId) {
        int id = Integer.parseInt(postId);
        List<UserFriendCircle> friendCircleList = friendCircleDao.queryById(id);
        if (friendCircleList != null && friendCircleList.size()>0){
            UserFriendCircleVO friendCircleVO = new UserFriendCircleVO();
            setUserFriendCircleVO(friendCircleVO,friendCircleList.get(0));
            String picListStr = friendCircleList.get(0).getPicture_list();
            String[] picList = new String[0];
            if (StringUtils.isNotBlank(picListStr)) {
                picList = picListStr.split("\\,");
            }
            friendCircleVO.setPictureList(picList);
            int replyCount = friendCircleDao.countReplyByPostId(id);
            friendCircleVO.setReplyNum(replyCount);
            List<UserFriendCircleVO> result = new ArrayList<>();
            result.add(friendCircleVO);
            return result;
        }else {
            return null;
        }
    }

    private List<UserFriendCircle> getFriendCircleList(double lon, double lat, double distance, int index, int pageSize) {
        //以用户位置为中心边长10公里矩形
        //纬度1度=111公里，经度一度=111公里*COS纬度
        double maxLon = lon + distance / (111 * Math.cos(Math.toRadians(lat)));
        double minLon = lon - distance / (111 * Math.cos(Math.toRadians(lat)));
        double maxLat = lat + distance / 111;
        double minLat = lat - distance / 111;
        List<UserFriendCircle> userFriendCircle = friendCircleDao.queryByCoordinateAndPage(maxLon, minLon, maxLat, minLat, index, pageSize);
        return userFriendCircle;
    }
}
