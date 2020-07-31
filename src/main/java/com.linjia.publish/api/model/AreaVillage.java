package com.linjia.publish.api.model;

import lombok.Data;

@Data
public class AreaVillage {
    private String name;// '小区名称',
    private String longitude;// '经度',
    private String latitude;//'纬度',
    private String address;//'详细位置',
    private String province;//'省份',
    private String city;// '城市',
    private String region;//'区域',
}
