package com.neusoft.mapper;

import com.neusoft.domain.PageInfo;
import com.neusoft.domain.Topic;
import com.neusoft.util.Page;

import java.util.List;
import java.util.Map;

public interface TopicMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Topic record);

    int insertSelective(Topic record);

    Topic selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Topic record);

    int updateByPrimaryKeyWithBLOBs(Topic record);

    int updateByPrimaryKey(Topic record);

    List<Topic> selectByUserid(Integer id);

    List<Map<String,Object>> getAllTopics();

    List<Map<String,Object>> getAllTopTopics();

    Map<String,Object> gettopic(int topicID);

    List<Map<String,Object>> getAllTopicsByCategoryId(Integer id);

    int count();
    int countByCategoryId(PageInfo pageInfo);
    int countByUserId(Integer userId);

    List<Map<String,Object>> getAllTopicsByPage(PageInfo pageInfo);

    List<Map<String,Object>> getAllTopicsByPageType(PageInfo pageInfo);

    List<Map<String,Object>> getAllTopicsHot();

}