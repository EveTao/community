package com.neusoft.mapper;

import com.neusoft.domain.Agree;

public interface AgreeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Agree record);

    int insertSelective(Agree record);

    Agree selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Agree record);

    int updateByPrimaryKey(Agree record);
}