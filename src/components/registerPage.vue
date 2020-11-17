<template>
<div class="container">
  <div class="text-center jumbotron">
      <h1>注册界面</h1>
  </div>
  <div class="content text-center">
      <form class="form-group">
          <div class="col-md-6">
          <label for="id">登录ID：</label>
          <input type="text" v-model="id" maxlength="30">
          </div>
          <div class="col-md-6">
          <label for="name">*姓名：</label>
          <input  type="text" v-model="name" maxlength="20">
          </div>
          <div class="col-md-6">
          <label for="email">*邮箱：</label>
          <input type="text" v-model="email" maxlength="30">
          </div>
          <div class="col-md-6">
          <label for="password">*输入密码：</label>
          <input type="text" v-model="passwordOne" maxlength="30">
          </div>
          <div class="col-md-6">
          <label for="password">*确认密码：</label>
          <input type="text" v-model="passwordTwo" maxlength="30">
          </div>
          <input type="submit" class="form-control" @click="submit">
      </form>
  </div>
  </div>
</template>

<script>
export default {
  data(){
    return{
        id:'',
        name:'',
        email:'',
        passwordOne:'',
        passwordTwo:''
    }
  },
  methods: {
      submit:function(){
          if(this.passwordOne == this.passwordTwo){
              alert('密码正确！');
          }
          else if(this.passwordOne != this.passwordTwo){
              alert('密码错误！');
          }
      }
  },
//    mounted () {
//     this.$axios
//       .get('')
//       .then()
//       .catch(function (error) { // 请求失败处理
//         console.log(error);
//       });
//   }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
 .content{
     width: 500px;
     
 }
</style>