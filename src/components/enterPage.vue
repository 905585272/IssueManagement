<template>
  <div class="container text-center">
      <h1>登录界面</h1>
      <form class="form-group">
            <label for="">&nbsp;&nbsp;&nbsp;<span class="xinghao">*</span>系统ID：</label><input type="text" required class="input" maxlength="30"> <br/>
            <label for=""><span class="xinghao">*</span>输入密码：</label><input type="password" required class="input" maxlength="30"> <br/>
            <input type="submit" value="登录">
        </form>
  </div>
</template>

<script>
export default {
  data(){
    return{

    }
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
 
</style>