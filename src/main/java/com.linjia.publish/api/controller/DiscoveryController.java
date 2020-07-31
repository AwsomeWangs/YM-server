package com.linjia.publish.api.controller;

import com.linjia.common.base.model.ResultBase;
import com.linjia.common.discovery.FriendCircleReplyDTO;
import com.linjia.common.publish.entity.query.AdQueryEntity;
import com.linjia.common.publish.entity.query.FriendCircleQueryEntity;
import com.linjia.common.publish.entity.query.PublistAdvertVO;
import com.linjia.common.publish.entity.query.UserFriendCircleVO;
import com.linjia.publish.api.service.DiscoveryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户朋友圈（发现模块）相关接口
 */
@Api(tags = {"demo"})
@RequestMapping(value = "/publish")
@Slf4j
@RestController
public class DiscoveryController {
    @Autowired
    DiscoveryService discoveryService;


    /**
     * 测试案例
     */
    @ApiOperation(value = "测试案例")
    @RequestMapping(value = "/getPost", method = RequestMethod.POST)
    ResultBase queryById(@RequestBody FriendCircleQueryEntity queryEntity) {
        Double lon = queryEntity.getLon();
        Double lat = queryEntity.getLat();
        String userId = queryEntity.getUserId();
        Integer pageIndex = queryEntity.getPageIndex();
        Integer pageSize = queryEntity.getPageSize();
        if (lon == null || lat == null || userId == null || pageIndex == null || pageSize == null) {
            return ResultBase.fail("缺失参数");
        }

        List<UserFriendCircleVO> userFriendCircle = discoveryService.queryFriendCircleList(lon, lat, userId, pageIndex, pageSize);
        if (userFriendCircle.size() >= 0) {
            return ResultBase.success(userFriendCircle);
        }
        return ResultBase.fail("查询失败");
    }

    /**
     * 测试案例
     */
    @ApiOperation(value = "测试案例")
    @RequestMapping(value = "/savePost", method = RequestMethod.POST)
    ResultBase savePost(@RequestBody FriendCircleQueryEntity queryEntity) {
        Double lon = queryEntity.getLon();
        Double lat = queryEntity.getLat();
        String userId = queryEntity.getUserId();
        String description = queryEntity.getContent();
        String pictureList = queryEntity.getPicList();
        String picture = queryEntity.getPicList().split(",")[0];
        String address = queryEntity.getAddress();
        if (lon == null || lat == null || StringUtils.isAnyBlank(userId, picture, pictureList)) {
            return ResultBase.fail("缺失参数");
        }
        int res = discoveryService.saveFriendCircle(lon, lat, userId, description, picture, pictureList, address);
        if (res == 1) {
            return ResultBase.success(1);
        } else {
            return ResultBase.fail("保存失败");
        }
    }

    /**
     * 测试案例
     */
    @ApiOperation(value = "测试案例")
    @RequestMapping(value = "/clickLike", method = RequestMethod.POST)
    ResultBase clickLike(@RequestBody FriendCircleQueryEntity queryEntity) {
        String userId = queryEntity.getUserId();
        Integer postId = queryEntity.getPostId();
        if (StringUtils.isBlank(userId) || postId == null) {
            return ResultBase.fail("参数缺失");
        }
        int res = discoveryService.likePost(userId, postId);
        if (res == 1) {
            return ResultBase.success(1);
        } else {
            return ResultBase.success(0);
        }
    }


    /**
     * 测试案例
     */
    @ApiOperation(value = "测试案例")
    @RequestMapping(value = "/checkNew", method = RequestMethod.POST)
    ResultBase checkNewPost(@RequestBody FriendCircleQueryEntity queryEntity) {
        Double lon = queryEntity.getLon();
        Double lat = queryEntity.getLat();
        String userId = queryEntity.getUserId();
        String timeStamp = queryEntity.getTimeStamp();
        if (StringUtils.isAnyBlank(userId, timeStamp) || lat == null || lon == null) {
            return ResultBase.fail("参数缺失");
        }
        int result = discoveryService.checkNewPost(lon, lat, userId, timeStamp);
        return ResultBase.success(result);
    }

    /**
     * 回复朋友圈
     */

    @ApiOperation(value = "回复朋友圈")
    @RequestMapping(value = "/reply", method = RequestMethod.POST)
    ResultBase reply(@RequestBody FriendCircleReplyDTO friendCircleReplyDTO) {
        Assert.notNull(friendCircleReplyDTO, "参数异常为null");
        boolean res = discoveryService.reply(friendCircleReplyDTO);
        if (res) {
            return ResultBase.success();
        } else {
            return ResultBase.fail("回复失败");
        }
    }

    /**
     * 删除回复
     */

    @ApiOperation(value = "删除评论")
    @RequestMapping(value = "/delReply", method = RequestMethod.GET)
    ResultBase delReply(@RequestParam long id) {
        boolean res = discoveryService.delReply(id);
        if (res) {
            return ResultBase.success();
        } else {
            return ResultBase.fail("回复失败");
        }
    }

    /**
     * 查看朋友圈评论
     */

    @ApiOperation(value = "查看朋友圈评论")
    @RequestMapping(value = "/getReply", method = RequestMethod.GET)
    ResultBase getReplyByInfoId(@RequestParam long infoId, @RequestParam int pn) {
        List<FriendCircleReplyDTO> dtos = new ArrayList<>();
        try {
            dtos = discoveryService.getReplyByInfoId(infoId, pn);
        } catch (Exception e) {
            ResultBase.fail("服务异常");
        }
        return ResultBase.success(dtos);
    }

    /**
     * 测试案例
     */
    @ApiOperation(value = "测试案例")
    @RequestMapping(value = "/getPostDetail", method = RequestMethod.POST)
    ResultBase getPostDetail(@RequestBody String postId) {
        if (StringUtils.isBlank(postId)) {
            return ResultBase.fail("参数缺失");
        }
        try {
            List<UserFriendCircleVO> friendCircle = discoveryService.getFriendCircleDetail(postId);
            return ResultBase.success(friendCircle);
        } catch (Exception e) {
            log.error("查询失败" + e);
            return ResultBase.fail("查询失败");
        }
    }

    /**
     * 测试案例
     */
    @ApiOperation(value = "测试案例")
    @RequestMapping(value = "/getDisAdBanner", method = RequestMethod.POST)
    ResultBase getDiscoveryAdBanner(@RequestBody AdQueryEntity adQueryEntity) {
        int adType = adQueryEntity.getAdType();
        try {
            List<PublistAdvertVO> adList = discoveryService.getDiscoveryAdBanner(adType);
            return ResultBase.success(adList);
        } catch (Exception e) {
            log.error("查询失败" + e);
            return ResultBase.fail("查询失败");
        }
    }
}
