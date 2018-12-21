package com.neusoft.mapper;

import com.neusoft.domain.Collect;

import java.util.List;
import java.util.Map;

public interface CollectMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Collect record);

    int insertSelective(Collect record);

    Collect selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Collect record);

    int updateByPrimaryKey(Collect record);

    Collect findByUserTopic(Collect record);

    int deleteByUserTopic(Collect record);

    List<Map<String,Object>>  findCollectTopic(Integer userid);

    int countCollectTopic(Integer userid);
}