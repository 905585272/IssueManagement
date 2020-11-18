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
        <router-link to="/enterpage" v-if="enterable"><h1>登录</h1></router-link>
        <h1 v-if="entersuccess">Welcome {{username}}</h1>
      </div>
      <div class="registerPage col-md-4">
        <router-link to="/changepage"><h1>修改个人信息</h1></router-link>
      </div>
  </div>
  </div>
</template>

<script>
export default {
  data(){
    return{
      enterable:true,
      entersuccess:false,
      ruleForm:{
        ID:'',
        name: '',
      },
    }
  },
  computed:{
    username(){
      return this.$store.state.username;
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
