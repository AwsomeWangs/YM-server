package com.linjia.publish.api.service.serviceImpl;

import com.linjia.common.discovery.FriendCircleReplyDTO;
import com.linjia.common.publish.entity.query.PublistAdvertVO;
import com.linjia.common.publish.entity.query.UserFriendCircleVO;
import com.linjia.common.util.LocationUtils;
import com.linjia.publish.api.dao.DiscoveryDao;
import com.linjia.publish.api.dao.DiscoveryReplyDao;
import com.linjia.publish.api.domain.PublistAdvertDO;
import com.linjia.publish.api.domain.UserFriendCircleDO;
import com.linjia.publish.api.domain.UserFriendCircleReplyDO;
import com.linjia.publish.api.service.DiscoveryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class DiscoveryServiceImpl implements DiscoveryService {
    @Autowired
    DiscoveryDao discoveryDao;
    @Autowired
    DiscoveryReplyDao discoveryReplyDao;

    SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");

    @Override
    public List<UserFriendCircleVO> queryFriendCircleList(double lon, double lat, String userId, int pageIndex, int pageSize) {
        double distance = 200;
        int startIndex = (pageIndex - 1) * pageSize;
        List<UserFriendCircleDO> userFriendCircleList = getFriendCircleList(lon, lat, distance, startIndex, pageSize);
        //补足策略待完善
        if (userFriendCircleList.size() < pageSize) {
            userFriendCircleList = getFriendCircleList(lon, lat, distance * 2, startIndex, pageSize);
        }

        List<UserFriendCircleVO> result = new ArrayList<>();
        for (int i = 0; i < userFriendCircleList.size(); i++) {
            double latB = userFriendCircleList.get(i).getLat();
            double lonB = userFriendCircleList.get(i).getLon();
            String range = LocationUtils.getDistance(lon, lat, lonB, latB);
            String rangeStr = range + "km";
            String likeList = userFriendCircleList.get(i).getLikeList();
            int liked = 0;
            if (StringUtils.isNotBlank(likeList) && likeList.contains("<" + userId + ">")) {
                liked = 1;
            }
            int id = userFriendCircleList.get(i).getId();
            int replyNum = discoveryDao.countReplyByPostId(id);
            UserFriendCircleVO userFriendCircleVO = new UserFriendCircleVO();
            setUserFriendCircleVO(userFriendCircleVO, userFriendCircleList.get(i));
            userFriendCircleVO.setLiked(liked);
            userFriendCircleVO.setDistance(rangeStr);
            userFriendCircleVO.setReplyNum(replyNum);
            result.add(userFriendCircleVO);
        }
        return result;
    }

    public void setUserFriendCircleVO(UserFriendCircleVO userFriendCircleVO, UserFriendCircleDO userFriendCircle) {
        userFriendCircleVO.setId(userFriendCircle.getId());
        userFriendCircleVO.setDescription(userFriendCircle.getDescription());
        userFriendCircleVO.setPicture(userFriendCircle.getPicture());
        userFriendCircleVO.setUserId(userFriendCircle.getUserId() + "");
        userFriendCircleVO.setNickName(userFriendCircle.getNickName());
        userFriendCircleVO.setStatus(userFriendCircle.getStatus());
        userFriendCircleVO.setLiked(userFriendCircle.getLiked());
        userFriendCircleVO.setLikeNum(userFriendCircle.getLikeNum());
        userFriendCircleVO.setGmtCreate(userFriendCircle.getGmtCreate());
        userFriendCircleVO.setGmtUpdate(userFriendCircle.getGmtUpdate());
        userFriendCircleVO.setLon(userFriendCircle.getLon());
        userFriendCircleVO.setLat(userFriendCircle.getLat());
        userFriendCircleVO.setAddress(userFriendCircle.getAddress());
        userFriendCircleVO.setHeadImg(userFriendCircle.getAvatarUrl());
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
        String time = f.format(date);
        int count = discoveryDao.countByPosAndDate(maxLon, minLon, maxLat, minLat, time);
        return count;
    }

    @Override
    public int likePost(String userId, int postId) {
        int likeNum = 0;
        String likeList = null;
        UserFriendCircleDO friendCircle = discoveryDao.queryPostLikesById(postId);
        if (friendCircle != null) {
            likeNum = friendCircle.getLikeNum() + 1;
            likeList = friendCircle.getLikeList();
            if (StringUtils.isNotBlank(likeList) && likeList.contains("<" + userId + ">")) {
                return 0;
            }
            if (StringUtils.isNotBlank(likeList)) {
                likeList = likeList + "<" + userId + ">";
            } else {
                likeList = "<" + userId + ">";
            }
        }
        int res = discoveryDao.updateLikeState(postId, likeNum, likeList);
        return res;
    }

    @Override
    public boolean reply(FriendCircleReplyDTO friendCircleReplyDTO) {
        UserFriendCircleReplyDO replyDO = new UserFriendCircleReplyDO();
        BeanUtils.copyProperties(friendCircleReplyDTO, replyDO);
        int res = discoveryReplyDao.insert(replyDO);
        return res == 1;
    }

    @Override
    public boolean delReply(long id) {
        int res = discoveryReplyDao.updateStatus((short) -1, id);
        return res == 1;
    }

    @Override
    public List<FriendCircleReplyDTO> getReplyByInfoId(long infoId, int pn) {
        int pageSize = 5;
        int limitStart = (pn - 1) * pageSize;
        int limitEnd = limitStart + pageSize;
        List<UserFriendCircleReplyDO> friendCircleReplyDOS = discoveryReplyDao.queryReplyByInfoId(infoId, limitStart, limitEnd);
        List<FriendCircleReplyDTO> resDTO = new ArrayList<>();
        for (UserFriendCircleReplyDO friendCircleReplyDO : friendCircleReplyDOS) {
            FriendCircleReplyDTO tempDTO = new FriendCircleReplyDTO();
            BeanUtils.copyProperties(friendCircleReplyDO, tempDTO);
            resDTO.add(tempDTO);
        }
        return resDTO;
    }


    @Override
    public List<UserFriendCircleVO> getFriendCircleDetail(String postId) {
        int id = Integer.parseInt(postId);
        List<UserFriendCircleDO> friendCircleList = discoveryDao.queryById(id);
        if (friendCircleList != null && friendCircleList.size() > 0) {
            UserFriendCircleVO friendCircleVO = new UserFriendCircleVO();
            setUserFriendCircleVO(friendCircleVO, friendCircleList.get(0));
            String picListStr = friendCircleList.get(0).getPictureList();
            String[] picList = new String[0];
            if (StringUtils.isNotBlank(picListStr)) {
                picList = picListStr.split("\\,");
            }
            friendCircleVO.setPictureList(picList);
            int replyCount = discoveryDao.countReplyByPostId(id);
            friendCircleVO.setReplyNum(replyCount);
            List<UserFriendCircleVO> result = new ArrayList<>();
            result.add(friendCircleVO);
            return result;
        } else {
            return null;
        }
    }

    @Override
    public int saveFriendCircle(Double lon, Double lat, String userId, String description, String picture, String pictureList, String address) {
        int status = 0;
        int likeNum = 0;
        Date date = new Date();
        String gmtCreate = f.format(date);
        String gmtUpdate = gmtCreate;
        int res = 0;
        try {
            res = discoveryDao.insertPost(description, picture, pictureList, userId, status, likeNum, gmtCreate, gmtUpdate, lon, lat, address);
            return res;
        } catch (Exception e) {
            log.error("帖子新增，异常", e);
        }
        return res;
    }

    @Override
    public List<PublistAdvertVO> getDiscoveryAdBanner(int adType) {
        List<PublistAdvertVO> result = null;
        try {
            List<PublistAdvertDO> adList = discoveryDao.queryAdByType(adType);
            if (adList != null && adList.size() > 0) {
                result = new ArrayList<>();
                for (PublistAdvertDO ad : adList) {
                    PublistAdvertVO advertVO = new PublistAdvertVO();
                    advertVO.setId(ad.getId());
                    advertVO.setAdvertTitle(ad.getAdvertTitle());
                    advertVO.setAdvertContent(ad.getAdvertContent());
                    advertVO.setAdvertImg(ad.getAdvertImg());
                    advertVO.setAdvertUrl(ad.getAdvertUrl());
                    advertVO.setAdvertType(adType);
                    advertVO.setStatus(ad.getStatus());
                    result.add(advertVO);
                }
            }
        } catch (Exception e) {

        }
        return result;
    }

    private List<UserFriendCircleDO> getFriendCircleList(double lon, double lat, double distance, int index, int pageSize) {
        //以用户位置为中心边长10公里矩形
        //纬度1度=111公里，经度一度=111公里*COS纬度
        double maxLon = lon + distance / (111 * Math.cos(Math.toRadians(lat)));
        double minLon = lon - distance / (111 * Math.cos(Math.toRadians(lat)));
        double maxLat = lat + distance / 111;
        double minLat = lat - distance / 111;
        List<UserFriendCircleDO> userFriendCircle = discoveryDao.queryByCoordinateAndPage(maxLon, minLon, maxLat, minLat, index, pageSize);
        return userFriendCircle;
    }
}
