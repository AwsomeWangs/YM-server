package com.linjia.publish.api.controller;

import com.linjia.common.base.enums.MsgEnum;
import com.linjia.common.base.model.ResultBase;
import com.linjia.common.publish.entity.query.FriendCircleQueryEntity;
import com.linjia.common.publish.entity.query.HistoryQueryEntity;
import com.linjia.common.publish.entity.query.PageQueryEntity;
import com.linjia.common.publish.entity.query.PostInfoDTO;
import com.linjia.common.publish.entity.result.DetailInfoEntity;
import com.linjia.common.publish.entity.result.ListInfoEntity;
import com.linjia.common.util.BeanValidator;
import com.linjia.publish.api.service.DiscoveryService;
import com.linjia.publish.api.service.PublishService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;
import java.util.List;

@Api(tags = {"发布帖子相关接口"})
@RequestMapping(value = "/publish")
@Slf4j
@RestController
public class PublishController {

    @Autowired
    PublishService publishService;

    @Autowired
    DiscoveryService discoveryService;
    /**
     * 帖子新增
     */
    @ApiOperation(value = "帖子新增")
    @RequestMapping(value = "/insertPostInfo", method = RequestMethod.POST)
    public ResultBase insertPostInfo(@RequestBody PostInfoDTO postInfoDTO) {
        ResultBase resultBase = new ResultBase();
        resultBase.setSuccess(true);
        if (postInfoDTO.getPostType() == 4){
            Double lon = postInfoDTO.getLon();
            Double lat = postInfoDTO.getLat();
            String userId = postInfoDTO.getUserId()+"";
            String description = postInfoDTO.getDescription();
            String pictureList = postInfoDTO.getPictureList();
            String picture = postInfoDTO.getPicture();
            String address = postInfoDTO.getLocation();
            if (lon == null || lat == null || StringUtils.isAnyBlank(userId, picture, pictureList)) {
                return ResultBase.fail("缺失参数");
            }

            int res = discoveryService.saveFriendCircle(lon, lat, userId, description, picture, pictureList, address);
            if (res == 1) {
                return ResultBase.success(1);
            } else {
                return ResultBase.fail("保存圈子失败");
            }
        }else {
            Long postId;
            try {
                BeanValidator.check(postInfoDTO);
                if (publishService.insertPostInfo(postInfoDTO)) {
                    resultBase.setMessage("ok");
                    return resultBase;
                }
            } catch (ValidationException e) {
                log.error("帖子新增，异常", e);
                return ResultBase.fail(e.getMessage());
            } catch (Exception e) {
                log.error("帖子新增，异常", e);
                return ResultBase.fail("帖子新增异常");
            }
        }
        return ResultBase.fail("帖子新增失败");
    }

    /**
     * 帖子修改
     */
    @ApiOperation(value = "帖子修改")
    @RequestMapping(value = "/updatePostInfo", method = RequestMethod.POST)
    public ResultBase updatePostInfo(@RequestBody PostInfoDTO postInfoDTO) {
        try {
            BeanValidator.check(postInfoDTO);
            boolean result = publishService.updatePostInfo(postInfoDTO);
            if (result) {
                return new ResultBase(MsgEnum.SUCCESS);
            }
            return new ResultBase(MsgEnum.FAIL);
        } catch (ValidationException e) {
            log.error("帖子修改，异常", e);
            return ResultBase.fail(e.getMessage());
        } catch (Exception e) {
            log.error("帖子修改，异常", e);
            return ResultBase.fail("帖子修改异常");
        }
    }

    /**
     * 帖子删除
     */
    @ApiOperation(value = "帖子删除")
    @RequestMapping(value = "/deletePostInfo", method = RequestMethod.POST)
    public ResultBase deletePostInfo(@RequestParam(value = "id") String id) {
        try {
            boolean result = publishService.deletePostInfo(id);
            if (result) {
                return new ResultBase(MsgEnum.SUCCESS);
            }
            return new ResultBase(MsgEnum.FAIL);
        } catch (Exception e) {
            log.error("帖子删除，异常", e);
            return ResultBase.fail("帖子删除异常");
        }
    }

    /**
     * @return
     */
    @ApiOperation(value = "获取帖子详情信息")
    @RequestMapping(value = "/detailinfo", method = RequestMethod.GET)
    public ResultBase<DetailInfoEntity> getDetailInfo(@RequestParam long infoId) {
        ResultBase<DetailInfoEntity> result = new ResultBase();
        try {
            result.setData(publishService.getDetailInfoEntity(infoId));
            result.setSuccess(true);
        } catch (Throwable e) {
            log.error("PublishController.getDetailInfo " + e);
        }
        return result;
    }

    /**
     * @return
     */
    @ApiOperation(value = "获取帖子列表信息")
    @RequestMapping(value = "/listinfo", method = RequestMethod.POST)
    public ResultBase<List<ListInfoEntity>> getListInfo(@RequestBody PageQueryEntity pageQueryEntity) {
        ResultBase<List<ListInfoEntity>> result = new ResultBase();
        try {
            result.setData(publishService.getListInfoEntitys(pageQueryEntity));
            result.setSuccess(true);
        } catch (Throwable e) {
            log.error("PublishController.getListInfo " + e);
        }
        return result;
    }

    /**
     * @return
     */
    @ApiOperation(value = "我的浏览记录帖子查询")
    @RequestMapping(value = "/historylist", method = RequestMethod.POST)
    public ResultBase<List<ListInfoEntity>> historyList(@RequestBody HistoryQueryEntity historyQueryEntity) {
        ResultBase<List<ListInfoEntity>> result = new ResultBase();
        try {
            result.setData(publishService.getListInfoEntitys(historyQueryEntity));
            result.setSuccess(true);
        } catch (Throwable e) {
            log.error("PublishController.getListInfo " + e);
        }
        return result;
    }
}
