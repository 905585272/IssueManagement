package com.ibm.issue.Service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ibm.issue.Entity.IssueWithBLOBs;
import com.ibm.issue.Entity.User;
import com.ibm.issue.Mapper.IssueMapper;
import com.ibm.issue.Service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IssueServiceimpl implements IssueService {

    @Autowired
    private IssueMapper issueMapper;
    @Override
    public int issueInsert(IssueWithBLOBs issueWithBLOBs) {
       int nums= issueMapper.insertSelective(issueWithBLOBs);
        return nums;
    }

    @Override
    public int issueModify(IssueWithBLOBs issueWithBLOBs) {

        return issueMapper.updateByPrimaryKeySelective(issueWithBLOBs);
    }

    @Override
    public int updateFinishtime(IssueWithBLOBs issueWithBLOBs) {

        return  issueMapper.updatefinishtime(issueWithBLOBs);
    }

    @Override
    public IssueWithBLOBs issueSelectbyno(Integer iNo) {
      IssueWithBLOBs issueWithBLOBs=  issueMapper.selectByPrimaryKey(iNo);
        return issueWithBLOBs;
    }

    @Override
    public List<IssueWithBLOBs> issueSelectall() {
    List<IssueWithBLOBs> list = issueMapper.selectall();
        return list;
    }

    @Override
    public PageInfo issueSelectall(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<IssueWithBLOBs> list=issueMapper.selectall();
        PageInfo page=new PageInfo(list);
        return page;
    }

    @Override
    public List<IssueWithBLOBs> selectallbyName(String iName) {

        return issueMapper.selectallbyname(iName);
    }

    @Override
    public PageInfo selectallbyName(String iCreator, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<IssueWithBLOBs> list=issueMapper.selectallbyname(iCreator);
        PageInfo page=new PageInfo(list);
        return page;

    }


    @Override
    public List<IssueWithBLOBs> issueSelectallSelective(IssueWithBLOBs issueWithBLOBs) {
        List<IssueWithBLOBs> list = issueMapper.selectallSelective(issueWithBLOBs);
        return list;
    }



    @Override
    public int issuseCount() {

        return issueMapper.selectcount();
    }

    @Override
    public Integer selectallSelectiveInt(IssueWithBLOBs issueWithBLOBs) {
        return issueMapper.selectallSelectiveInt(issueWithBLOBs);

    }

    @Override
    public List<IssueWithBLOBs> selectallSelectivePage(IssueWithBLOBs issueWithBLOBs) {

        return issueMapper.selectallSelectivePage(issueWithBLOBs);
    }

    @Override
    public PageInfo selectallSelectivePage(IssueWithBLOBs issueWithBLOBs, int pageNum, int pageSize) {

        PageHelper.startPage(pageNum,pageSize);
        List<IssueWithBLOBs> list=issueMapper.selectallSelectivePage(issueWithBLOBs);
        PageInfo page=new PageInfo(list);
        return page;

    }


}
