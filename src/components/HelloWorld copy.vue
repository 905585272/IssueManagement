<template>
  <div class="container col-md-12 big_body">
   <el-container>
      <el-aside  class="aside_button col-md-3">
        <div class="title col-md-12">
          <h3 class="col-md-12 blue_text"><i class="el-icon-s-help white_text"></i>Issue管理系统</h3>
        </div>
        <div class="registerPage col-md-12">
          <el-button type="primary" class="white_text">
            <router-link to="/registerpage" v-if="registerable" class="white_text"><h5><i class="el-icon-s-help white_text"></i>注册</h5></router-link>
          </el-button>
          <div class="block"><el-avatar shape="square" :size="70" :src="squareUrl" v-if="changeable"></el-avatar></div>
        </div>
        <div class="enterPage col-md-12">
          <el-button @click="drawer = true" type="primary" class="white_text">
            <h5><i class="el-icon-user-solid white_text"></i>登录</h5>
          </el-button>
          
        </div>
        <div class="changePage col-md-12">
          <router-link to="/changepage" v-if="changeable" class="white_text"><h5><i class="el-icon-s-tools white_text"></i>修改个人信息</h5></router-link>
        </div>
        <div class="createIssue col-md-12">
          <router-link to="/createissue" v-if="createissue" class="white_text"><h5><i class="el-icon-circle-plus white_text"></i>创建新Issue</h5></router-link>
        </div>
        <div class="issueReport col-md-12">
          <router-link to="/issueReport" v-if="issuereport" class="white_text"><h5><i class="el-icon-star-on white_text"></i>Issue报表</h5></router-link>
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
    <el-drawer
      title="我是标题"
      :visible.sync="drawer"
      :direction="direction"
      :before-close="handleClose">
      <span>我来啦!</span>
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
        ID:'',
        name: '',
      },
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
    issuereport(){
      return this.$store.state.issuereport;
    },
    // changeissue(){
    //   return this.$store.state.changeissue;
    // }
  },
  mounted(){
    this.timer=setInterval(()=>{
      var hour,minute,second;
      hour = new Date().getHours();
      minute = new Date().getMinutes();
      second = new Date().getSeconds();
      this.time = hour+":"+minute+":"+second;
    },1000);
  },
  methods:{
    handleClose(done) {
        this.$confirm('确认关闭？')
          .then(() => {
            done();
          })
          .catch(() => {});
    }
  },
  beforeDestroy() {
    clearTimeout(this.timer);
  }
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
    height: 13vh;
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
