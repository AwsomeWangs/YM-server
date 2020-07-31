package com.linjia.publish.api.service.serviceImpl;

import com.linjia.publish.api.dao.AraeVillageDao;
import com.linjia.publish.api.model.AreaVillage;
import com.linjia.publish.api.service.DemoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DemoServiceImpl implements DemoService {
    @Autowired
    AraeVillageDao araeVillageDao;
    @Override
    public AreaVillage queryById(String id) {
        AreaVillage areaVillage = araeVillageDao.queryById(id);
        return areaVillage;
    }
}
