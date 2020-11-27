package com.ibm.issue.Service;

import com.github.pagehelper.PageInfo;
import com.ibm.issue.Entity.User;

import java.util.List;

public interface UserService {
    int insert(User user);
    User selectbyid(String id);
    List<User> selectall();
    PageInfo selectall(int a,int b);

    int modify(User user);


    List<User> selectallSelective(User user);


    List<User> selectAllSelectivepage(User user);

    PageInfo selectAllSelectivepage(User user,int a,int b);



    int selectIssuecount();
}
