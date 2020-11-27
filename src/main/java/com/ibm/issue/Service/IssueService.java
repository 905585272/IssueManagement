package com.ibm.issue.Service;

import com.github.pagehelper.PageInfo;
import com.ibm.issue.Entity.IssueWithBLOBs;

import java.util.List;

public interface IssueService {
    int issueInsert(IssueWithBLOBs issueWithBLOBs);
    int issueModify(IssueWithBLOBs issueWithBLOBs);

    int updateFinishtime(IssueWithBLOBs issueWithBLOBs);
    IssueWithBLOBs issueSelectbyno(Integer iNo);
    List<IssueWithBLOBs> issueSelectall();

    PageInfo issueSelectall(int a, int b);

    List<IssueWithBLOBs> selectallbyName(String iCreator);

    PageInfo selectallbyName(String iCreator, int a,int b);


    List<IssueWithBLOBs> issueSelectallSelective(IssueWithBLOBs issueWithBLOBs);

    int issuseCount();

   Integer selectallSelectiveInt(IssueWithBLOBs issueWithBLOBs);

    List<IssueWithBLOBs> selectallSelectivePage(IssueWithBLOBs issueWithBLOBs);

    PageInfo selectallSelectivePage(IssueWithBLOBs issueWithBLOBs,int a,int b);


}
