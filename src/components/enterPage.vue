<template>
  <div class="container text-center">
    <h1>登录界面</h1>
    <form class="form-group">
      <div class="col-md-6">
        <label for="systemId">系统ID：</label>
        <input type="text" required maxlength="30" id="systemId" />
      </div>
      <div class="col-md-6">
        <label for="password">输入密码：</label>
        <input type="password" required maxlength="30" id="password" />
      </div>
      <div class="col-md-6">
        <input type="submit" value="登录" />
      </div>
    </form>
  </div>
</template>

<script>
export default {
  data() {
    return {};
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>