package com.linjia.publish.api.helper;

import com.linjia.common.publish.entity.result.ListInfoEntity;
import com.linjia.common.util.DateUtils;
import com.linjia.common.util.LocationUtils;
import com.linjia.publish.api.domain.PostInfoDO;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

public class InfoHelper {


    public static void listTransfer(List<PostInfoDO> postInfos, List<ListInfoEntity> listInfoEntities, Double lon, Double lat) {
        for (PostInfoDO postInfoDO : postInfos) {
            ListInfoEntity listInfoEntity = new ListInfoEntity();
            listInfoEntity.setPostType(postInfoDO.getPostType());
            listInfoEntity.setInfoID(postInfoDO.getId());
            listInfoEntity.setTitle(postInfoDO.getTitle());
            if (postInfoDO.getPrice() != null) {
                DecimalFormat decimalFormat = new DecimalFormat("##############.##");
                listInfoEntity.setPrice(decimalFormat.format(postInfoDO.getPrice()));
            }
            listInfoEntity.setPriceUnit(postInfoDO.getPriceUnit());
            listInfoEntity.setImage(postInfoDO.getPicture());
            listInfoEntity.setStatus(postInfoDO.getStatus());
            if (postInfoDO.getDistance() != null) {
                DecimalFormat df = new DecimalFormat("0.0");
                String distance = df.format(postInfoDO.getDistance());
                if ("0.0".equals(distance)) {
                    distance = "0.1";
                }
                listInfoEntity.setDistance(distance + "公里以内");
            } else if (lon != null && lat != null && postInfoDO.getLon() != null && postInfoDO.getLat() != null) {
                String distance = LocationUtils.getDistance(lon, lat, postInfoDO.getLon(), postInfoDO.getLat());
                listInfoEntity.setDistance(distance + "公里以内");
            }
            int num = DateUtils.getDaysNumBetweenTwoDates(postInfoDO.getPostDate(), new Date());
            String postDesc = num + "天前发布";
            if (num <= 0) {
                postDesc = "今天发布";
            }
            listInfoEntity.setPostDate(postDesc);
            listInfoEntities.add(listInfoEntity);
        }

    }
}
