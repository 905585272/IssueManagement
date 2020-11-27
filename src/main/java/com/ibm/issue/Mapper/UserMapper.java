package com.ibm.issue.Mapper;

import com.ibm.issue.Entity.User;

import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(String rId);

    int insert(User record);

    int insertSelective(User record);

    int insertuser(User record);

    List<User> selectAll();

    List<User> selectAllSelective(User record);

    List<User> selectAllSelectivePage(User record);

    User selectByPrimaryKey(String rId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int selectissuecount();

}