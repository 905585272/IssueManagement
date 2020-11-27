package com.ibm.issue.Controller;

import com.github.pagehelper.PageInfo;
import com.ibm.issue.Entity.IssueWithBLOBs;
import com.ibm.issue.Entity.User;
import com.ibm.issue.Service.IssueService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("issue")
public class Issuehandle {


    @Autowired
    private IssueService issueService;


//issue插入数据
    @Transactional
    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/insert",produces = "application/json")
    public int issueInsert(@RequestBody IssueWithBLOBs issueWithBLOBs){
       int nums= issueService.issueInsert(issueWithBLOBs);
       return nums;

    }


//issue的更新
@Transactional
    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/update",produces = "application/json")
    public int updatehandle(@RequestBody IssueWithBLOBs issueWithBLOBs){
        int nums= issueService.issueModify(issueWithBLOBs);
        return nums;

    }



//更新issue的Finishtime，置空
    @Transactional
    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/updateFinishtime",produces = "application/json")
    public int updatefinishtime(@RequestBody IssueWithBLOBs issueWithBLOBs){
        int nums= issueService.updateFinishtime(issueWithBLOBs);
        return nums;

    }



//根据no进行查询
    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/selectbyno/{iNo}",produces = "application/json")
    public Object selectbyno(@RequestBody @PathVariable("iNo") Integer iNo){
        IssueWithBLOBs nums= issueService.issueSelectbyno(iNo);
        if (nums==null){
            return 1;
        }
        return nums;

    }


//查询所有
    @CrossOrigin
    @ResponseBody
    @RequestMapping("/selectall")
    public List<IssueWithBLOBs> Selectall(){
        return issueService.issueSelectall();
    }



//模糊查询
    @CrossOrigin
    @ResponseBody
    @RequestMapping("/selectallSelective")
    public List<IssueWithBLOBs> SelectallSelective(@RequestBody IssueWithBLOBs issueWithBLOBs){

        if(issueWithBLOBs.getiCreator().equals("")){
             issueWithBLOBs.setiCreator(null);
               }
        if(issueWithBLOBs.getiIssuestate().equals("")){
                   issueWithBLOBs.setiIssuestate(null);
          }
        if(issueWithBLOBs.getiChangeperson().equals("")){
            issueWithBLOBs.setiChangeperson(null);
        }
        return issueService.issueSelectallSelective(issueWithBLOBs);
    }




//issue的数据条数
    @CrossOrigin
    @ResponseBody
    @RequestMapping("/issuecount")
    public int Issuecount(){
        return issueService.issuseCount();
    }




//issue分页
    @CrossOrigin
    @ResponseBody
    @RequestMapping("/selectallPage/{pageNum}")
    public PageInfo SelectallPage(@RequestBody @PathVariable("pageNum") Integer pageNum){
        return issueService.issueSelectall(pageNum,4);
    }





    //模糊查询后的issue的数据条数
    @CrossOrigin
    @ResponseBody
    @RequestMapping("/selectallselectiveint")
    public Integer selectallselectiveint(@RequestBody IssueWithBLOBs issueWithBLOBs){
        if(issueWithBLOBs.getiCreator().equals("")){
            issueWithBLOBs.setiCreator(null);
        }
        if(issueWithBLOBs.getiIssuestate().equals("")){
            issueWithBLOBs.setiIssuestate(null);
        }
        if(issueWithBLOBs.getiChangeperson().equals("")){
            issueWithBLOBs.setiChangeperson(null);
        }
        return issueService.selectallSelectiveInt(issueWithBLOBs);
    }





//根据创建人或者修改人进行查询数据并分页
    @CrossOrigin
    @ResponseBody
    @RequestMapping("/selectallbynamePage/{iCreator}/{pageNum}")
    public PageInfo SelectallbynamePage(@RequestBody @PathVariable("iCreator") @Param("iCreator") String iCreator, @PathVariable("pageNum") Integer pageNum){
        return issueService.selectallbyName(iCreator,pageNum,4);
    }





//模糊查询的分页操作
    @CrossOrigin
    @ResponseBody
    @RequestMapping("/selectallSelectivePage/{pageNum}")
    public PageInfo selectallSelectivePage(@RequestBody  IssueWithBLOBs issueWithBLOBs, @PathVariable("pageNum") Integer pageNum){
        return issueService.selectallSelectivePage(issueWithBLOBs,pageNum,4);
    }






}
