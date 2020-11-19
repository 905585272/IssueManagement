<template>
  <div class="container text-center">
    <div class="titleContent">
      <img src="../assets/titleicon.png" alt="TitleIcon">
      <h1>Issue管理系统</h1>
   </div>
   <div class="row">
      <div class="registerPage col-md-4">
        <router-link to="/registerpage"><h1>注册</h1></router-link>
      </div>
      <div class="enterPage col-md-4">
        <router-link to="/enterpage" v-if="enterable" ><h1>登录</h1></router-link>
        <h1 v-if="entersuccess" >欢迎， {{username}}</h1>
      </div>
      <div class="registerPage col-md-4">
        <router-link to="/changepage" v-if="registerable"><h1>修改个人信息</h1></router-link>
      </div>
      <div class="createIssue col-md-4">
        <router-link to="/createissue" v-if="createissue"><h1>创建新Issue</h1></router-link>
      </div>
      <div class="issueReport col-md-4">
        <router-link to="/issueReport" v-if="issuereport"><h1>Issue报表</h1></router-link>
      </div>
  </div>
  </div>
</template>

<script>
export default {
  data(){
    return{
      ruleForm:{
        ID:'',
        name: '',
      },
    }
  },
  computed:{
    username(){
      return this.$store.state.username;
    },
    enterable(){
      return this.$store.state.enterable;
    },
    entersuccess(){
      return this.$store.state.entersuccess;
    },
    registerable(){
      return this.$store.state.registerable;
    },
    createissue(){
      return this.$store.state.createissue;
    },
    issuereport(){
      return this.$store.state.issuereport;
    }
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  .titleContent{
    margin-bottom: 100px;
  }
  .titleContent img{
    border-radius: 50px;
  }
  a {
  text-decoration: none;
  }
 
  .router-link-active {
    text-decoration: none;
  }
</style>
