package com.linjia.publish.api.service;

import com.linjia.common.publish.entity.query.HistoryQueryEntity;
import com.linjia.common.publish.entity.query.PageQueryEntity;
import com.linjia.common.publish.entity.query.PostInfoDTO;
import com.linjia.common.publish.entity.result.DetailInfoEntity;
import com.linjia.common.publish.entity.result.ListInfoEntity;
import com.linjia.publish.api.domain.PostInfoDO;

import java.util.List;

public interface PublishService {

    boolean updatePostInfo(PostInfoDTO postInfoDTO);

    boolean updatePostInfo(PostInfoDO postInfoDO);

    boolean deletePostInfo(String id);

    boolean insertPostInfo(PostInfoDTO postInfoDTO);

    DetailInfoEntity getDetailInfoEntity(long infoId);
    //首页列表、我的发布
    List<ListInfoEntity> getListInfoEntitys(PageQueryEntity pageQueryEntity);
    //浏览记录、我的收藏
    List<ListInfoEntity> getListInfoEntitys(HistoryQueryEntity historyQueryEntity);
}
