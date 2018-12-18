package com.neusoft.mapper;

import com.neusoft.domain.Collect;

public interface CollectMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Collect record);

    int insertSelective(Collect record);

    Collect selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Collect record);

    int updateByPrimaryKey(Collect record);

    Collect findByUserTopic(Collect record);

    int deleteByUserTopic(Collect record);
}