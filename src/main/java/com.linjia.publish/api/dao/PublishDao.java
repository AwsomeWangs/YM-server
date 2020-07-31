package com.linjia.publish.api.dao;

import com.linjia.common.publish.entity.query.PageQueryEntity;
import com.linjia.publish.api.domain.PostInfoDO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface PublishDao extends Mapper<PostInfoDO> {


    @SelectProvider(type = SqlProvider.class, method = "selectListSortByDistance")
    List<PostInfoDO> selectListSortByDistance(PageQueryEntity pageQueryEntity);

    @SelectProvider(type = SqlProvider.class, method = "selectList")
    List<PostInfoDO> selectList(PageQueryEntity pageQueryEntity);
}
