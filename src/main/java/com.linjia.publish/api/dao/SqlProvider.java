package com.linjia.publish.api.dao;

import com.linjia.common.publish.entity.query.PageQueryEntity;
import org.apache.commons.lang.StringUtils;

public class SqlProvider {

    String POST_INFO = "post_info";

    public String selectListSortByDistance(PageQueryEntity pageQueryEntity) {
        StringBuilder selectList = new StringBuilder("select *," +
                " case when lat is null or lon is null then 999" +
                " else (ACOS(SIN(("+pageQueryEntity.getLat()+" * PI()) / 180) * SIN((lat * PI()) / 180)+COS(("+pageQueryEntity.getLat()+" * PI()) / 180) * COS((lat * PI()) / 180) * COS( ("+pageQueryEntity.getLon()+" * PI()) / 180 - (lon * PI()) / 180 ) ) * 6378.138)" +
                " end as distance  from " + POST_INFO);
        if (pageQueryEntity.getStatus() != 1) {
            selectList.append(" where status=0");
        } else {
            selectList.append(" where status in (0,-1)");
        }
        if (pageQueryEntity.getPostType() != null && pageQueryEntity.getPostType() >= 0) {
            selectList.append(" and post_type=" + pageQueryEntity.getPostType());
        }
        if (pageQueryEntity.getUserId() != null && pageQueryEntity.getUserId() > 0L) {
            selectList.append(" and user_id=" + pageQueryEntity.getUserId());
        }
        if (StringUtils.isNotBlank(pageQueryEntity.getKey())) {
            selectList.append(" and title like '%" + pageQueryEntity.getKey() + "%'");
        }
        selectList.append(" order by distance");
        if (pageQueryEntity.getRowSrt() >= 0 && pageQueryEntity.getPageSize() > 0) {
            selectList.append(" limit " + pageQueryEntity.getRowSrt() + "," + pageQueryEntity.getPageSize());
        }
        return selectList.toString();
    }

    public String selectList(PageQueryEntity pageQueryEntity) {
        StringBuilder selectList = new StringBuilder("select * from "+ POST_INFO);
        if (pageQueryEntity.getStatus() != 1) {
            selectList.append(" where status=0");
        } else {
            selectList.append(" where status in (0,-1)");
        }
        if (pageQueryEntity.getPostType() != null && pageQueryEntity.getPostType() >= 0) {
            selectList.append(" and post_type=" + pageQueryEntity.getPostType());
        }
        if (pageQueryEntity.getUserId() != null && pageQueryEntity.getUserId() > 0L) {
            selectList.append(" and user_id=" + pageQueryEntity.getUserId());
        }
        if (StringUtils.isNotBlank(pageQueryEntity.getKey())) {
            selectList.append(" and title like '%" + pageQueryEntity.getKey() + "%'");
        }
        if (pageQueryEntity.getRowSrt() >= 0 && pageQueryEntity.getPageSize() > 0) {
            selectList.append(" limit " + pageQueryEntity.getRowSrt() + "," + pageQueryEntity.getPageSize());
        }
        return selectList.toString();
    }

}
