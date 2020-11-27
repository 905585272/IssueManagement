package com.ibm.issue.Mapper;

import com.ibm.issue.Entity.Issue;
import com.ibm.issue.Entity.IssueWithBLOBs;

import java.util.List;

public interface IssueMapper {
    int deleteByPrimaryKey(Integer iNo);

    int insert(IssueWithBLOBs record);

    int insertSelective(IssueWithBLOBs record);

    int updatefinishtime(IssueWithBLOBs record);

    IssueWithBLOBs selectByPrimaryKey(Integer iNo);

    int updateByPrimaryKeySelective(IssueWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(IssueWithBLOBs record);

    int updateByPrimaryKey(Issue record);

    List<IssueWithBLOBs> selectallbyname(String iCreator);

    int selectcount();

    Integer selectallSelectiveInt(IssueWithBLOBs issueWithBLOBs);

    List<IssueWithBLOBs> selectall();


    List<IssueWithBLOBs> selectallSelective(IssueWithBLOBs issueWithBLOBs);

    List<IssueWithBLOBs> selectallSelectivePage(IssueWithBLOBs issueWithBLOBs);


}