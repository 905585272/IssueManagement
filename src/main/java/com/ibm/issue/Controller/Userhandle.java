package com.ibm.issue.Controller;

import com.github.pagehelper.PageInfo;
import com.ibm.issue.Entity.User;
import com.ibm.issue.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("user")
public class Userhandle {
    @Autowired
    private UserService userService;




//注册保存信息接口
@Transactional
@CrossOrigin
@ResponseBody
@PostMapping(value = "/insert" ,produces = "application/json")
    public int insert2(@RequestBody User user ){
        int nums=userService.insert(user);
        return nums;
    }




    //根据id进行查询，进行修改时使用的接口
    @CrossOrigin
    @ResponseBody
    @RequestMapping("/selectbyid/{rId}")
    public Object Selectbyid(@RequestBody @PathVariable("rId") String id){
        User user=  userService.selectbyid(id);
        if (user==null){
            return 1;
        }
        return user;
    }



    //修改个人信息接口
    @Transactional
    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/update" ,produces = "application/json")
    public int update(@RequestBody User user ){

//        if (user.getrName().equals("")){
//            user.setrName(null);
//        }
//        if (user.getrPwd().equals("")){
//            user.setrPwd(null);
//        }
//        if (user.getrEmail().equals("")){
//            user.setrEmail(null);
//        }
//        if (user.getrUserid().equals("")){
//            user.setrUserid(null);
//        }
//        if (user.getrState().equals("")){
//            user.setrState(null);
//        }

        int nums=userService.modify(user);
        return nums;
    }


//返回所有用户数据

    @CrossOrigin
    @ResponseBody
    @RequestMapping("/selectall")
    public List<User> Selectall(){
        return userService.selectall();
    }


//可查询可选择性
    @CrossOrigin
    @ResponseBody
    @RequestMapping("/selectallSelective")
    public List<User> SelectallSelective(@RequestBody User user){
        if(user.getrId().equals("")){
            user.setrId(null);
        }
        if(user.getrName().equals("")){
            user.setrName(null);
        }
        return userService.selectallSelective(user);
    }




//    分页查询
@CrossOrigin
@ResponseBody
@RequestMapping("/selectallPage/{pageNum}")
public PageInfo SelectallPage(@RequestBody @PathVariable("pageNum") Integer pageNum){
    return userService.selectall(pageNum,4);
}



//用户的数据条数
    @CrossOrigin
    @ResponseBody
    @RequestMapping("/usercount")
    public int Usercount(){
        return userService.selectIssuecount();
    }




//模糊查询user分页
    @CrossOrigin
    @ResponseBody
    @RequestMapping("/selectAllSelectivepage/{pageNum}")
    public PageInfo selectAllSelectivepage(@RequestBody User user, @PathVariable("pageNum") Integer pageNum){
        return userService.selectAllSelectivepage(user,pageNum,4);
    }



}
