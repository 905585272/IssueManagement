package com.ibm.issue.Service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ibm.issue.Entity.User;
import com.ibm.issue.Mapper.UserMapper;
import com.ibm.issue.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceimpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private User user;


    @Override
    public int insert(User user) {
        System.out.println(user);
       int nums= userMapper.insertuser(user);
        return nums;
    }



    @Override
    public User selectbyid(String id) {
        return  userMapper.selectByPrimaryKey(id);
    }



    @Override
    public List<User> selectall() {

        return userMapper.selectAll();
    }

    @Override
    public PageInfo selectall(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<User> list=userMapper.selectAll();
        PageInfo page=new PageInfo(list);
        return page;
    }

    @Override
    public int modify(User user) {

        return userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public List<User> selectallSelective(User user) {

        return userMapper.selectAllSelective(user);
    }

    @Override
    public List<User> selectAllSelectivepage(User user) {

        return userMapper.selectAllSelectivePage(user);
    }

    @Override
    public PageInfo selectAllSelectivepage(User user, int pageNum, int pageSize) {

        PageHelper.startPage(pageNum,pageSize);
        List<User> list=userMapper.selectAllSelectivePage(user);
        PageInfo page=new PageInfo(list);
        return page;

    }

    @Override
    public int selectIssuecount() {
        return userMapper.selectissuecount();

    }
}
