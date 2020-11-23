<template>
  <div class="container col-md-12 big_body">
   <el-container>
      <el-aside  class="aside_button col-md-3">
        <div class="title col-md-12">
          <h3 class="col-md-12 blue_text"><i class="el-icon-s-help white_text"></i>Issue管理系统</h3>
        </div>
        <div class="registerPage col-md-12">
          <router-link to="/registerpage" class="white_text">
            <el-button type="primary" class="white_text" v-if="registerable">
              <h5><i class="el-icon-s-help white_text"></i>注册</h5>
            </el-button>
          </router-link>
          <div class="block"><el-avatar shape="square" :size="70" :src="squareUrl" v-if="iconable"></el-avatar></div>
        </div>
        <div class="enterPage col-md-12" v-if="enterable">
          <el-button @click="drawer = true" type="primary" class="white_text" >
            <h5><i class="el-icon-user-solid white_text"></i>登录</h5>
          </el-button>
        </div>
        <div class="quitbtn col-md-12" v-if="quitable">
        <el-button type="info" class="white_text" size="small">
            <h5><i class="el-icon-right white_text"></i>注销</h5>
        </el-button>
        </div>
        <div class="changePage col-md-12" v-if="changeable">
          <router-link to="/changepage" class="white_text"><h5><i class="el-icon-s-tools white_text"></i>修改个人信息</h5></router-link>
        </div>
        <div class="createIssue col-md-12" v-if="createissue">
          <router-link to="/createissue" class="white_text"><h5><i class="el-icon-circle-plus white_text"></i>创建新Issue</h5></router-link>
        </div>
        <div class="issueList col-md-12" v-if="issuelist">
          <router-link to="/issueList" class="white_text"><h5><i class="el-icon-star-on white_text"></i>Issue列表</h5></router-link>
        </div>
        <div class="issueReport col-md-12" v-if="issuereport">
          <router-link to="/issueReport" class="white_text"><h5><i class="el-icon-star-on white_text"></i>Issue报表</h5></router-link>
        </div>
        <div class="issueReport col-md-12" v-if="admin_flg">
          <router-link to="/userManage" style="color:#F56C6C"><h5><i class="el-icon-loading"></i>账号管理</h5></router-link>
        </div>
        <div class=" col-md-10" id="producer_list">
          <h4 class="white_text col-md-12">--------关于我们--------</h4>
          <ul class="col-md-8 center-block">
            <li class="white_text col-md-12">PM</li>
              <ul class="white_text ">
                <li class=" col-md-auto">陈炜锐</li>
              </ul>
            <li class="white_text col-md-12">测试人员</li>
              <ul class="white_text">
                <li class="col-md-12">黄大新</li>
                <li class="col-md-12">黄镇东</li>
                <li class="col-md-12">黄鹏腾</li>
              </ul>
            <li class="white_text col-md-12">前端人员</li>
              <ul class="white_text">
                <li class="col-md-12">傅镇赋</li>
                <li class="col-md-12">陈炜锐</li>
                <li class="col-md-12">马鸿昌</li>
                <li class="col-md-12">胡铭谦</li>
              </ul>
            <li class="white_text col-md-12">后端人员</li>
              <ul class="white_text">
                <li class="col-md-12">蔡富荣</li>
                <li class="col-md-12">黄钢墩</li>
                <li class="col-md-12">陈锦全</li>
              </ul>
          </ul>
        </div>
      </el-aside>
      <div class="img_body col-md-9">
        <!-- 时间 -->
        <h5 id="current-time">当前时间:{{time}}</h5>
        <div class="welcome col-md-11">
          <h1 class="col-md-12">欢迎使用！</h1>
          <h2 v-if="entersuccess" class="col-12 white_text"><i class="el-icon-user-solid white_text"></i>欢迎， {{rName}}</h2>
        </div>
        <!-- 日历 -->
        <div class="left-wrap col-md-11" id="calendar">
          <el-calendar v-model="calendar_data">
          </el-calendar>
        </div>
      </div>
    </el-container>
    <el-drawer id="enter_body"
      title="用户登陆"
      :visible.sync="drawer"
      :with-header="false"
      :direction="direction"
      :before-close="handleClose">
      >
      <div class="container">
        <div>
            <div id="change_info_title">
                <h5 class="text-left"><b>用户登陆</b></h5>
                <hr>
            </div>
            <el-form :model="ruleForm" :rules="rules" ref="ruleForm" class="demo-ruleForm">
                <el-form-item label="系统ID：" prop="rId" class="col-md-8" id="user_id">
                    <el-input v-model="ruleForm.rId"></el-input>
                </el-form-item>
                <el-form-item label="输入密码：" prop="rPwd" class="col-md-8">
                    <el-input v-model="ruleForm.rPwd" show-password></el-input>
                </el-form-item>
                <el-form-item>
                <el-button type="primary" @click="submitForm('ruleForm')">确认</el-button>
                </el-form-item>
            </el-form>
        </div>   
      </div>
    </el-drawer>

  </div>
</template>

<script>
export default {
  data(){
    return{
      squareUrl: "https://cube.elemecdn.com/9/c2/f0ee8a3c7c9638a54940382568c9dpng.png",
      timer:'',
      time:'',
      ruleForm:{
        rId:'',
        rPwd:'',
        rName:'',
      },
      rules: {
          rId: [
              { required: true, message: '请输入ID', trigger: 'blur' },
              { min: 1, max: 30, message: '长度在 1 到 30 个字符', trigger: 'blur' },
          ],
          rPwd: [
              { required: true, message: '请输入密码',trigger: 'blur' },
              { min: 1, max: 30, message: '长度在 1 到 30 个字符', trigger: 'blur' }
          ]
      },
      admin_flg:false,
      drawer: false,
      direction: 'rtl',
      calendar_data: new Date(),
    }
  },
  computed:{
    rName(){
      return this.$store.state.rName;
    },
    enterable(){
      return this.$store.state.enterable;
    },
    entersuccess(){
      return this.$store.state.entersuccess;
    },
    changeable(){
      return this.$store.state.changeable;
    },
    registerable(){
      return this.$store.state.registerable;
    },
    createissue(){
      return this.$store.state.createissue;
    },
    issuelist(){
      return this.$store.state.issuelist;
    },
    issuereport(){
      return this.$store.state.issuereport;
    },
    iconable(){
      return this.$store.state.iconable;
    },
    quitable(){
      return this.$store.state.quitable;
    }
  },
  mounted(){
    this.timer=setInterval(()=>{
      var hour,minute,second;
      hour = new Date().getHours();
      minute = new Date().getMinutes();
      second = new Date().getSeconds();
      this.time = hour+":"+minute+":"+second;
    },1000);
    console.log("!"+this.$store.state.rUserid);
    if (this.$store.state.rUserid == '经理') {
      this.$store.state.createissue = false;
      // console.log('是经理');
    }else if(this.$store.state.rUserid == '普通用户'){
      this.$store.state.createissue = true;
      // console.log('不是经理');
    }else if (this.$store.state.rUserid == 'admin') {
      this.$store.state.createissue = false;
      this.admin_flg=true;
    }
  },
  methods:{
    handleClose(done) {
      this.$confirm('确认取消登陆？')
        .then(() => {
          done();
        })
        .catch(() => {});
    },
    closeDrawer(){
    },
    submitForm(ruleForm) {
    this.$refs[ruleForm].validate((valid) => {
        if (valid) {
            this.$http.get('http://localhost:8080/user/selectbyid/'+this.ruleForm.rId).
            then(function(res){
                console.log(res.data);
                if(res.data===1){ 
                    this.$alert('用户不存在!', {
                        confirmButtonText: '确定',
                        })
                }else{
                    if(this.ruleForm.rPwd==res.data.rPwd){
                    this.$store.state.rName = this.ruleForm.rName;
                      // 全局用户数据同步
                      this.$store.state.rId = res.data.rId,
                      this.$store.state.rName = res.data.rName,
                      this.$store.state.rEmail = res.data.rEmail,
                      this.$store.state.rPwd = res.data.rPwd,
                      this.$store.state.rUserid = res.data.rUserid,
                      this.$store.state.rState = res.data.rState,
                      this.$store.state.rCissue = res.data.rCissue,
                      this.$store.state.rRissue = res.data.rRissue,
                      this.$store.state.rMissue = res.data.rMissue,
                      this.$store.state.enterable = false,
                      this.$store.state.entersuccess = true,
                      this.$store.state.registerable = false,
                      this.$store.state.issuereport = true,
                      this.$store.state.changeable = true,
                      this.$store.state.issuelist = true,
                      this.$store.state.iconable = true,
                      this.$store.state.quitable = true,
                      this.drawer=false;
                      if (this.$store.state.rUserid == '经理') {
                        this.$store.state.createissue = false;
                        // console.log('是经理');
                      }else if(this.$store.state.rUserid == '普通用户'){
                        this.$store.state.createissue = true;
                        // console.log('不是经理');
                      }else if (this.$store.state.rUserid == 'admin') {
                        this.admin_flg=true;
                        this.$store.state.changeable = false;
                        this.$store.state.createissue = false;
                        this.$store.state.issuelist = false;
                        this.$store.state.issuereport =false;
                      }
                    this.$alert('欢迎使用', {
                        confirmButtonText: '确定',
                    }).then(() => {
                            this.$message({
                                type: 'success',
                                message: '欢迎!'+res.data.rName,
                            },
                            );
                        });
                    }
                    else{
                        this.$alert('密码错误!', {
                        confirmButtonText: '确定',
                        })
                    }
                }
                })
            }
            else {
            this.$alert('非法操作!', {
              confirmButtonText: '确定',
              });
            }
        })    
    },
  },
  beforeDestroy() {
    clearTimeout(this.timer);
  },
  // quit(){

  // }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style>
  .big_body{
    height: 100vh;
    position: relative;
    bottom: 0vh;
  }
  .img_body{
    background-image: url(~@/assets/widthPic.jpg);
    background-size:cover;
    height: 100vh;
  }
  .aside_button{
    position: absolute;
    bottom: 0vh;
    height: 100vh;
    border-radius: 2px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
    background: -webkit-linear-gradient(top,black,#303133);
  }
  .blue_text{
    margin: 0 auto;
    text-align: center;
    color: skyblue;
    height: 13vh;
    line-height: 70px;
  }
  .white_text{
    color: snow;
  }
  .registerPage{
    height: 10vh;
    text-align: center;
    line-height: 70px;
  }
  .enterPage{
    height: 10vh;
    text-align: center;
    line-height: 70px;
    font-family: "Helvetica Neue";
  }
  .quitbtn{
    height: 10vh;
    text-align: center;
    line-height: 70px;
  }
  .changePage{
    height: 5vh;
    text-align: center;
    line-height: 70px;
  }
  .createIssue{
    height: 5vh;
    text-align: center;
    line-height: 70px;
  }
  .issueReport{
    height: 5vh;
    text-align: center;
    line-height: 70px;
  }
  .issueList{
    height: 5vh;
    text-align: center;
    line-height: 70px;
  }
  #producer_list{
    position: absolute;
    bottom: 5vh;
    height: 45vh;
    margin: 0px auto;
  }
  #producer_list ul{
    margin: 0px auto;
  }
  #producer_list h4{
    text-align: center;
  }
  .welcome{
    position: absolute;
    top: 10vh;
  }
  .welcome h1{
    font-size: 10vh;
    text-align: center;
    margin:0px;
    padding:0px;
    font-family:"YouYuan";
    font-weight:bold;
    background: linear-gradient(to right, black, skyblue);
    -webkit-background-clip: text;
    color: transparent;
    -webkit-text-fill-color: transparent;
    text-fill-color: transparent;

  }
  .welcome h2{
    font-size: 5vh;
    text-align: center;
    line-height: 200px;
    height: 20vh;
  }
  #current-time{
    position: absolute;
    top: 5px;
    font-size: 3vh;
  }
  #calendar{
    position: absolute;
    bottom: 4vh;
    height: 50vh;
  }
  
  .left-wrap /deep/ .el-calendar-table .el-calendar-day{
    height: 5vh;
  }
  .el-calendar{
    opacity:0.8;
  }
</style>
