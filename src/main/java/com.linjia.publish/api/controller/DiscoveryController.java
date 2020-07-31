package com.linjia.publish.api.controller;

import com.linjia.common.base.model.ResultBase;
import com.linjia.common.publish.entity.query.FriendCircleQueryEntity;
import com.linjia.common.publish.entity.query.UserFriendCircleVO;
import com.linjia.publish.api.service.DiscoveryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
    @ApiImplicitParam(name = "demo", value = "demo ", dataType = "String", paramType = "query")
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
        if (userFriendCircle.size() == 0) {
            return ResultBase.success(userFriendCircle, true);
        }
        return ResultBase.success(userFriendCircle, false);
    }

    /**
     * 测试案例
     */
    @ApiOperation(value = "测试案例")
    @ApiImplicitParam(name = "demo", value = "demo ", dataType = "String", paramType = "query")
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
    @ApiImplicitParam(name = "demo", value = "demo ", dataType = "String", paramType = "query")
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

    @ApiOperation(value = "回复朋友圈", notes = "根据帖子id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "String"),
            @ApiImplicitParam(name = "postId", value = "动态id", required = true, dataType = "Int"),
            @ApiImplicitParam(name = "replyId", value = "回复id", required = true, dataType = "Int"),
            @ApiImplicitParam(name = "content", value = "回复内容", required = true, dataType = "String")
    })
    @RequestMapping(value = "/reply", method = RequestMethod.POST)
    ResultBase reply(@RequestParam("userId") String userId, @RequestParam("postId") int postId, @RequestParam("replyId") int replyId, @RequestParam("content") String content) {
        if (StringUtils.isAnyBlank(userId, content, postId + "", replyId + "")) {
            return ResultBase.fail("参数缺失");
        }
        // todo 校验userId、postId、replyId是否合法
        boolean res = discoveryService.reply(userId, postId, replyId, content);
        if (res) {
            return ResultBase.success();
        } else {
            return ResultBase.fail("回复失败");
        }
    }

    /**
     * 测试案例
     */
    @ApiOperation(value = "测试案例")
    @ApiImplicitParam(name = "demo", value = "demo ", dataType = "String", paramType = "query")
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
}
