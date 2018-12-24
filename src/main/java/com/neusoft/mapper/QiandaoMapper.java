package com.neusoft.mapper;

import com.neusoft.domain.Qiandao;

import java.util.List;
import java.util.Map;

public interface QiandaoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Qiandao record);

    int insertSelective(Qiandao record);

    Qiandao selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Qiandao record);

    int updateByPrimaryKey(Qiandao record);

    int countToday(Integer userid);

    Qiandao selectByUserId(Integer userid);

    List<Map<String,Object>> selectNew();

    List<Map<String,Object>> selectFast();

    List<Map<String,Object>> selectTop();

}