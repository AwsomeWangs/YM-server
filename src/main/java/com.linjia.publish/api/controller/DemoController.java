package com.linjia.publish.api.controller;

import com.alibaba.fastjson.JSON;
import com.linjia.publish.api.model.AreaVillage;
import com.linjia.publish.api.service.DemoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"demo"})
@RequestMapping(value = "/publish")
@Slf4j
@RestController
public class DemoController {

    @Autowired
    DemoService demoService;

    /**
     * 测试案例
     */
    @ApiOperation(value = "测试案例")
    @ApiImplicitParam(name = "demo", value = "demo ", dataType = "String", paramType = "query")
    @RequestMapping(value = "/demo", method = RequestMethod.GET)
    String queryById(@RequestParam("id") String id) {
        AreaVillage areaVillage =  demoService.queryById(id);
        String str = JSON.toJSONString(areaVillage);
        return str;
    }
}
