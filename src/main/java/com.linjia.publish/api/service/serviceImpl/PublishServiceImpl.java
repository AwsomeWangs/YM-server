package com.linjia.publish.api.service.serviceImpl;

import com.alibaba.fastjson.JSONArray;
import com.linjia.common.publish.entity.query.HistoryQueryEntity;
import com.linjia.common.publish.entity.query.PageQueryEntity;
import com.linjia.common.publish.entity.query.PostInfoDTO;
import com.linjia.common.publish.entity.result.DetailInfoEntity;
import com.linjia.common.publish.entity.result.ListInfoEntity;
import com.linjia.common.util.DateUtils;
import com.linjia.publish.api.dao.PublishDao;
import com.linjia.publish.api.domain.PostInfoDO;
import com.linjia.publish.api.helper.InfoHelper;
import com.linjia.publish.api.service.PublishService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class PublishServiceImpl implements PublishService {
    @Autowired
    PublishDao publishDao;

    @Override
    public boolean updatePostInfo(PostInfoDTO postInfoDTO) {
        PostInfoDO postInfoDO = new PostInfoDO();
        BeanUtils.copyProperties(postInfoDTO, postInfoDO);
        long result = publishDao.updateByPrimaryKeySelective(postInfoDO);
        if (result >= 1) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean updatePostInfo(PostInfoDO postInfoDO) {
        long result = publishDao.updateByPrimaryKeySelective(postInfoDO);
        if (result >= 1) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean deletePostInfo(String id) {
        PostInfoDO postInfoDO = new PostInfoDO();
        postInfoDO.setId(Long.valueOf(id));
        postInfoDO.setStatus(-2);
        long result = publishDao.updateByPrimaryKeySelective(postInfoDO);
        if (result >= 1) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean insertPostInfo(PostInfoDTO postInfoDTO) {
        PostInfoDO postInfoDO = new PostInfoDO();
        BeanUtils.copyProperties(postInfoDTO, postInfoDO);
        BeanUtils.copyProperties(postInfoDTO, postInfoDO);
        Date nowDate = new Date();
        postInfoDO.setGmtCreate(nowDate);
        postInfoDO.setPostDate(nowDate);
        postInfoDO.setStatus(0);
        long result = publishDao.insert(postInfoDO);
        if (result >= 1) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public DetailInfoEntity getDetailInfoEntity(long infoId) {
        try {
            PostInfoDO postInfoDO = publishDao.selectByPrimaryKey(infoId);
            if (postInfoDO != null) {
                DetailInfoEntity detailInfoEntity = new DetailInfoEntity();
                detailInfoEntity.setPostType(postInfoDO.getPostType());
                detailInfoEntity.setInfoID(postInfoDO.getId());
                detailInfoEntity.setTitle(postInfoDO.getTitle());
                detailInfoEntity.setDesc(postInfoDO.getDescription());
                if (postInfoDO.getPrice() != null) {
                    DecimalFormat decimalFormat = new DecimalFormat("##############.##");
                    detailInfoEntity.setPrice(decimalFormat.format(postInfoDO.getPrice()));
                }
                detailInfoEntity.setPriceUnit(postInfoDO.getPriceUnit());
                String pictureSource = postInfoDO.getPictureList();
                if (StringUtils.isNotBlank(pictureSource)) {
                    pictureSource = URLDecoder.decode(pictureSource, "UTF-8");
                    String[] pics = pictureSource.split(",");
                    JSONArray picArray = new JSONArray();
                    for (String pic : pics) {
                        picArray.add(pic);
                    }
                    detailInfoEntity.setImages(picArray);
                }
                int num = DateUtils.getDaysNumBetweenTwoDates(postInfoDO.getPostDate(), new Date());
                String postDesc = num + "天前发布";
                if (num <= 0) {
                    postDesc = "今天发布";
                }
                detailInfoEntity.setPostDate(postDesc);
                detailInfoEntity.setStatus(postInfoDO.getStatus());
                detailInfoEntity.setLon(postInfoDO.getLon());
                detailInfoEntity.setLat(postInfoDO.getLat());
                detailInfoEntity.setUserId(postInfoDO.getUserId());
                detailInfoEntity.setUserName(postInfoDO.getUserName());
                detailInfoEntity.setLocation(postInfoDO.getLocation());
                detailInfoEntity.setMobile(postInfoDO.getMobile());
                return detailInfoEntity;
            }
        } catch (Exception e) {
            log.error("PublishServiceImpl.getDetailInfoEntity", e);
        }
        return null;
    }

    @Override
    public List<ListInfoEntity> getListInfoEntitys(PageQueryEntity pageQueryEntity) {
        List<ListInfoEntity> listInfoEntities = new ArrayList<>();
        List<PostInfoDO> postInfos;
        try {
            if (pageQueryEntity.getLat() != null && pageQueryEntity.getLat() != null) {
                postInfos = publishDao.selectListSortByDistance(pageQueryEntity);
            } else {
                postInfos = publishDao.selectList(pageQueryEntity);
            }
            InfoHelper.listTransfer(postInfos, listInfoEntities, pageQueryEntity.getLon(), pageQueryEntity.getLat());
        } catch (Exception e) {
            log.error("PublishServiceImpl.getListInfoEntitys", e);
        }
        return listInfoEntities;
    }

    @Override
    public List<ListInfoEntity> getListInfoEntitys(HistoryQueryEntity historyQueryEntity) {
        List<ListInfoEntity> listInfoEntities = new ArrayList<>();
        List<String> infoIdList = new ArrayList<>();
        String[] infoIdArray = historyQueryEntity.getInfoIds().split(",");
        for (int i = 0; i < infoIdArray.length; i++) {
            infoIdList.add(infoIdArray[i]);
        }
        if (infoIdList.size() > 0) {
            Example example = new Example(PostInfoDO.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andIn("id", infoIdList);
            List<PostInfoDO> postInfos = publishDao.selectByExample(example);
            InfoHelper.listTransfer(postInfos, listInfoEntities, historyQueryEntity.getLon(), historyQueryEntity.getLat());
        }
        return listInfoEntities;
    }
}
