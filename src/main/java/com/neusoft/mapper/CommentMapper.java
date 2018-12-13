package com.neusoft.mapper;

import com.neusoft.domain.Comment;
import com.neusoft.domain.PageInfo;
import com.neusoft.domain.User;

import java.util.List;
import java.util.Map;

public interface CommentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Comment record);

    int insertSelective(Comment record);

    Comment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKey(Comment record);

    List<Map<String,Object>> selectByTopicid(Integer topicid);

    List<Map<String,Object>> selectByUserid(Integer userid);

    int getCount();

    List<Map<String,Object>> selectByUseridPage(PageInfo pageInfo);

    List<Map<String,Object>> selectByUseridPart(Integer userid);

    List<Map<String,Object>> selectTop();

    List<Map<String,Object>> selectByUseridNew(User user);

}