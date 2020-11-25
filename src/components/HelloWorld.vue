<template>
  <div class="container col-md-12 big_body">
   <el-container>
      <el-aside  class="aside_button col-md-3">
      <template>
        <div class="block">
        <div style="height:7vh"></div>
          <el-carousel trigger="click" height="30vh">
            <el-carousel-item>
              <img src="../../ibm.jpg" alt="IBM">
            </el-carousel-item>
            <el-carousel-item>
              <img src="../../intel.jpg" alt="intel">
            </el-carousel-item>
            <el-carousel-item>
              <img src="../../microsoft.jpg" alt="microsoft">
            </el-carousel-item>
          </el-carousel>
        </div>
      </template>
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
        <div class="title col-md-12">
          <h1 class="col-md-12 blue_text"><i class="el-icon-s-help white_text"></i>Issue管理系统</h1>
        </div>
        <!-- 时间 -->
        <h5 id="current-time">当前时间:{{time}}</h5>
        <div class="content col-md-8 container">
          <div class="welcome col-md-12">
            <h1 v-if="helloTitle" class="white_text">欢迎使用Issue管理系统！</h1>
            <h2 v-if="entersuccess" class="white_text"><i class="el-icon-user-solid white_text"></i>欢迎， {{rName}}</h2>
          </div>
          <div class="img col-md-12">
            <el-avatar shape="square" :size="100" :fit="fits" :src="squareUrl" v-if="iconable"></el-avatar>
            <el-avatar shape="square" :size="100" :fit="fits" :src="squareUrlTwo" v-if="iconableTwo"></el-avatar>
            <el-avatar shape="square" :size="100" :fit="fits" :src="squareUrlThree" v-if="iconableThree"></el-avatar>
          </div>
          <div class="registerPage col-md-6" v-if="registerable">
            <!-- <router-link to="/registerpage" class="white_text"> -->
              <el-button type="primary" @click="register = true" class="white_text" round>
                <h5><i class="el-icon-s-help white_text"></i>注册</h5>
              </el-button>
            <!-- </router-link> -->
          </div>
          <div class="enterPage col-md-6" v-if="enterable">
            <el-button @click="drawer = true" type="primary" class="white_text" round>
              <h5><i class="el-icon-user-solid white_text"></i>登录</h5>
            </el-button>
          </div>
          <div class="quitbtn col-md-6" v-show="quitable">
          <el-button type="primary" class="white_text" size="small" @click="quit" round>
              <h5><i class="el-icon-right white_text"></i>退出当前账号</h5>
          </el-button>
          </div>
          <div class="changePage col-md-6" v-if="changeable">
            <router-link to="/changepage" class="white_text">
              <el-button type="primary" class="white_text" size="small" round>
                <h5><i class="el-icon-s-tools white_text"></i>修改个人信息</h5>
              </el-button>
            </router-link>
          </div>
          <div class="createIssue col-md-6" v-if="createissue">
            <router-link to="/createissue" class="white_text">
              <el-button type="primary" class="white_text" size="small" round>
                <h5><i class="el-icon-circle-plus white_text"></i>创建新Issue</h5>
              </el-button>
            </router-link>
          </div>
          <div class="issueList col-md-6" v-if="issuelist">
            <router-link to="/issueList" class="white_text">
              <el-button type="primary" class="white_text" size="small" round>
                <h5><i class="el-icon-star-on white_text"></i>Issue列表</h5>
              </el-button>
            </router-link>
          </div>
          <div class="issueReport col-md-6" v-if="issuereport">
            <router-link to="/issueReport" class="white_text">
              <el-button type="primary" class="white_text" size="small" round>
                <h5><i class="el-icon-star-on white_text"></i>Issue报表</h5>
              </el-button>
            </router-link>
          </div>
          <div class="userManage col-md-6" v-if="admin_flg">
            <router-link to="/userManage" class="white_text">
              <el-button type="danger" class="white_text" size="small" round>
                <h5><i class="el-icon-loading"></i>账号管理</h5>
              </el-button>
            </router-link>
          </div>
        </div>
      </div>
    </el-container>
    <el-drawer id="enter_body"
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
    <el-drawer id="enter_body"
      :visible.sync="register"
      :with-header="false"
      direction="ltr"
      :before-close="handleCloseRegister">
      >
      <div class="container">
        <div >
            <div id="change_info_title">
                <h5 class="text-left"><b>用户注册</b></h5>
                <hr>
            </div>
            <div id="change_body">
              <el-form :model="ruleForm" :rules="rules" ref="ruleForm" class="demo-ruleForm">
                  <el-form-item label="登录ID：" prop="rId" class="col-md-8">
                      <el-input v-model="ruleForm.rId"></el-input>
                  </el-form-item>
                  <el-form-item label="姓名：" prop="rName" class="col-md-8">
                      <el-input v-model="ruleForm.rName" ></el-input>
                  </el-form-item>
                  <el-form-item label="邮箱：" prop="rEmail" class="col-md-8">
                      <el-input v-model="ruleForm.rEmail" type="email"></el-input>
                  </el-form-item>
                  <el-form-item label="输入密码：" prop="rPwd2" class="col-md-8">
                      <el-input v-model="ruleForm.rPwd" show-password></el-input>
                  </el-form-item>
                  <el-form-item label="确认密码：" prop="final_passwd" class="col-md-8">
                      <el-input v-model="ruleForm.final_passwd" show-password></el-input>
                  </el-form-item>
                  <el-form-item>
                  <el-button type="primary" @click="registerForm('ruleForm')">确认</el-button>
                  </el-form-item>
              </el-form>
            </div>
        </div>   
      </div>
    </el-drawer>
  </div>
</template>

<script>
export default {
  data(){
    var validatePass1 = (rule, value, callback) => {
          if (value !== '') { 
              if (!/^(?=.*[A-Z].*)(?=.*[a-z].*)(?=.*?[_\-@&=!])[a-zA-Z_\-@&=!0-9]+.{7,30}$/.test(value)){
                  callback(new Error('密码必须包含大小写和特殊字符(_-@&=!)，且在8-30位之间'));
                  }
              }
              else {
              callback();
              }
          callback();
      };
      var validatePass2 = (rule, value, callback) => {
          if (value !== this.ruleForm.rPwd) {
          callback(new Error('两次输入密码不一致!'));
          }
            else {
          callback();
          }
          callback();
      };
      var checkEmail = (rule, value, callback) => {
            if (value === '') {
          callback(new Error('请正确填写邮箱'));
          } else {
              if (value !== '') { 
              var reg=/^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
                  if(!reg.test(value)){
                  callback(new Error('请输入有效的邮箱'));
                  }
              }
          callback();
          }
      };
      var checkChinese = (rule, value, callback) => {
          if (value) {
              if (!/^[\u2E80-\u9FFF]+$/.test(value)) {
              callback(new Error('请输入汉字!'));
              } else {
              callback();
              }
          }
          callback();
      }
    return{
      msg:[],
      fits:'fill',
      squareUrl: "https://wx2.sinaimg.cn/mw690/0061Qq8Vly1gh7qredom7j30jg0jg0ur.jpg",
      squareUrlTwo:"https://wx4.sinaimg.cn/mw690/0061Qq8Vly1gh7qrf1b2bj30jg0jg75z.jpg",
      squareUrlThree:"https://wx1.sinaimg.cn/mw690/0061Qq8Vly1gh7qrdspvsj30jg0jg0uo.jpg",
      timer:'',
      time:'',
      ruleForm:{
        rId:'',
        rPwd:'',
        rName:'',
        rEmail:'',
        final_passwd:'',
        rUserid:'普通用户',
        rState:'激活',
        rCissue: 0,
        rRissue: 0,
        rMissue: 0
      },
      rules: {
          rId: [
              { required: true, message: '请输入ID', trigger: 'blur' },
              { min: 1, max: 30, message: '长度在 1 到 30 个字符', trigger: 'blur' },
          ],
          rName: [
              { required: false, message: '请输入名字', trigger: 'blur' },
              { min: 1, max: 20, message: '长度在 1 到 20 个汉字', trigger: 'blur' },
              { validator: checkChinese, trigger: 'blur' }
          ],
          rEmail: [
              { required: true, message: '请输入邮箱', trigger: 'blur' },
              { min: 1, max: 30, message: '长度在 1 到 30 个字符', trigger: 'blur' },
              { validator: checkEmail, trigger: 'blur' }
          ],
          rPwd: [
              { required: true,message: '请输入密码',trigger: 'blur' },
              { min: 1, max: 30, message: '长度在 1 到 30 个字符', trigger: 'blur' },
          ],
          rPwd2: [
              { required: true,message: '请输入密码',trigger: 'blur' },
              { min: 1, max: 30, message: '长度在 1 到 30 个字符', trigger: 'blur' },
              { validator: validatePass1, trigger: 'blur' }
          ],
          final_passwd: [
              { required: true,message: '请再次输入密码',trigger: 'blur' },
              { validator: validatePass2, trigger: 'blur' }
          ]
      },
      admin_flg:false,
      register:false,
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
    iconableTwo(){
      return this.$store.state.iconableTwo;
    },
    iconableThree(){
      return this.$store.state.iconableThree;
    },
    quitable(){
      return this.$store.state.quitable;
    },
    helloTitle(){
      return this.$store.state.helloTitle;
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
    // if (this.$store.state.rUserid == '经理') {
    //   this.$store.state.changeable = true;
    //   this.$store.state.createissue = false;
    //   this.$store.state.issuereport = true;
    //   this.$store.state.issuelist = true;
    //   this.$store.state.quitable = true;
    //   // console.log('是经理');
    // }else if(this.$store.state.rUserid == '普通用户'){
    //   this.$store.state.changeable = true;
    //   this.$store.state.createissue = true;
    //   this.$store.state.issuereport = false;
    //   this.$store.state.issuelist = true;
    //   this.$store.state.quitable = true;
    //   // console.log('不是经理');
    // }else if (this.$store.state.rUserid == 'Admin') {
    //   this.admin_flg=true;
    //   this.$store.state.changeable = false;
    //   this.$store.state.createissue = false;
    //   this.$store.state.issuelist = false;
    //   this.$store.state.issuereport =false;
    //   this.$store.state.quitable = true;  
    // }
  },
  methods:{
    quit(){
      this.$store.state.rName = '';
      // 全局用户数据同步
      this.$store.state.rId = '',
      this.$store.state.rName = '',
      this.$store.state.rEmail = '',
      this.$store.state.rPwd = '',
      this.$store.state.rUserid = '',
      this.$store.state.rState = '',
      this.$store.state.rCissue = '',
      this.$store.state.rRissue = '',
      this.$store.state.rMissue = '',
      this.$store.state.enterable = true,
      this.$store.state.entersuccess = false,
      this.$store.state.registerable = true,
      this.$store.state.issuereport = false,
      this.$store.state.changeable = false,
      this.$store.state.issuelist = false,
      this.$store.state.iconable = false,
      this.$store.state.iconableTwo = false,
      this.$store.state.iconableThree = false,
      this.$store.state.quitable = false,
      this.$store.state.createissue = false,
      this.$store.state.helloTitle = true,
      this.admin_flg=false;
    },
    handleClose(done) {
      this.$confirm('确认取消登陆？')
        .then(() => {
          done();
        })
        .catch(() => {});
    },
    handleCloseRegister(done) {
      this.$confirm('确认取消注册？')
        .then(() => {
          done();
        })
        .catch(() => {});
    },
    registerForm(formName) {
      this.$refs[formName].validate((valid) => {
          console.log("data is posting!");
          if (valid) {
              console.log(this.ruleForm.rId); 
              this.$http.get('http://localhost:8080/user/selectall').
              then(function(res){
                  this.msg = res.body;
                  console.log(this.msg.length);
                  var flag=true;
                  this.msg.forEach(item=>{
                      if (item.rId==this.ruleForm.rId) {
                          flag=false;
                          this.$alert('ID重复!', {
                          confirmButtonText: '确定',
                          })
                      }
                  });
                    if (flag) {
                      this.$http.post('http://localhost:8080/user/insert',{
                      rId:this.ruleForm.rId,
                      rName:this.ruleForm.rName,
                      rEmail:this.ruleForm.rEmail,
                      rPwd:this.ruleForm.rPwd,
                      rUserid:this.ruleForm.rUserid,
                      rState:this.ruleForm.rState,
                      rCissue: this.ruleForm.rCissue,
                      rRissue: this.ruleForm.rRissue,
                      rMissue: this.ruleForm.rMissue
                  }).then(function(){
                      // console.log("!"+resp);
                      this.$alert('用户注册成功！', {
                          confirmButtonText: '确定',
                      }).then(() => {
                        this.$store.state.enterable = false,
                        this.$store.state.entersuccess = true,
                        this.$store.state.registerable = false,
                        this.$store.state.issuereport = true,
                        this.$store.state.changeable = true,
                        this.$store.state.helloTitle = false,
                        // 全局用户数据同步
                        this.$store.state.rId = this.ruleForm.rId,
                        this.$store.state.rName = this.ruleForm.rName,
                        this.$store.state.rEmail = this.ruleForm.rEmail,
                        this.$store.state.rPwd = this.ruleForm.rPwd,
                        this.$store.state.rUserid = '普通用户',
                        this.$store.state.rState = this.ruleForm.rState,
                        this.$store.state.rCissue = this.ruleForm.rCissue,
                        this.$store.state.rRissue = this.ruleForm.rRissue,
                        this.$store.state.rMissue = this.ruleForm.rMissue,
                        this.register=false;
                        this.$store.state.changeable = true;
                        this.$store.state.createissue = true;
                        this.$store.state.issuereport = false;
                        this.$store.state.issuelist = true;
                        this.$store.state.iconableTwo = true;
                        this.$store.state.helloTitle = false;
                        this.$store.state.quitable = true,
                        this.$message({
                            type: 'success',
                            message: '欢迎!'+this.$store.state.rName,
                        },
                        );
                          })
                  }).catch(function(error){
                      console.log(error);
                  })
                      }
              }).catch(function(error){
                  console.log(error);
              })
              
          } else {
              alert('error submit!!');
          }
      });
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
                }else if(res.data.rState === '注销'){
                    this.$alert('该用户已注销!', {
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
                      this.$store.state.quitable = true,
                      this.$store.state.helloTitle = false,
                      this.drawer=false;
                      if (this.$store.state.rUserid == '经理') {
                        this.$store.state.changeable = true;
                        this.$store.state.createissue = false;
                        this.$store.state.issuereport = true;
                        this.$store.state.issuelist = true;
                        this.$store.state.iconable = true;
                        this.$store.state.helloTitle = false;
                        // console.log('是经理');
                      }else if(this.$store.state.rUserid == '普通用户'){
                        this.$store.state.changeable = true;
                        this.$store.state.createissue = true;
                        this.$store.state.issuereport = false;
                        this.$store.state.issuelist = true;
                        this.$store.state.iconableTwo = true;
                        this.$store.state.helloTitle = false;
                        // console.log('不是经理');
                      }else if (this.$store.state.rUserid == 'Admin') {
                        this.admin_flg=true;
                        this.$store.state.changeable = false;
                        this.$store.state.createissue = false;
                        this.$store.state.issuelist = false;
                        this.$store.state.issuereport =false;
                        this.$store.state.iconableThree = true;
                        this.$store.state.helloTitle = false;
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
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style>
  .el-carousel__item h3 {
    color: #475669;
    font-size: 14px;
    opacity: 0.75;
    line-height: 150px;
    margin: 0;
  }
  .el-carousel__item:nth-child(2n) {
     background-color: #99a9bf;
  }
  .el-carousel__item:nth-child(2n+1) {
     background-color: #d3dce6;
  }
  img{
  width:100%;
  height:100%;
  }
  #change_body{
        margin:15% auto;
    }
  .big_body{
    height: 100vh;
    position: relative;
    bottom: 0vh;
  }
  .img_body{
    position: relative;
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
    background: -webkit-linear-gradient(top,black,#454647);
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
    height: 10vh;
    text-align: center;
    line-height: 70px;
  }
  .createIssue{
    height: 10vh;
    text-align: center;
    line-height: 70px;
  }
  .issueReport{
    height: 10vh;
    text-align: center;
    line-height: 70px;
  }
  .issueList{
    height: 10vh;
    text-align: center;
    line-height: 70px;
  }
  .userManage{
    height: 10vh;
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
    position: relative;
    margin: 0 auto;
    text-align: center;
    top: 5vh;
  }
  .welcome h1{
    font-size: 5vh;
    text-align: center;
    line-height: 200px;
    height: 20vh;
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
    position: relative;
    bottom: 4vh;
    height: 50vh;
  }
  
  .left-wrap /deep/ .el-calendar-table .el-calendar-day{
    height: 5vh;
  }
  .el-calendar{
    opacity:0.8;
  }
  .content{
    display: flex;
    flex-wrap: wrap;
    background: rgba(61, 52, 52, 0.5);
    height: 600px;
    box-sizing:border-box;
    box-shadow: 0px 15px 25px rgba(0,0,0,.5);
    border-radius:15px;
  }
  .img{
    top: 10px;
    height: 10vh;
    text-align: center;
    line-height: 70px;
  }
</style>
