package com.linjia.publish.api.dao;

import com.linjia.publish.api.model.AreaVillage;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface AraeVillageDao {
    String AREA_VILLAGE = "area_village";

    //入参id是，销售线索主键id
    @Select(value={"select * from "+ AREA_VILLAGE + " where  id = #{id}"})
    AreaVillage queryById(@Param("id") String id);
}
