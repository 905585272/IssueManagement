<template>
  <div class="container">
        <div id="change_info_title">
            <h5 class="text-left"><b>登录页面</b></h5>
            <hr>
        </div>
        <div id="change_body">
            <el-form :model="ruleForm" :rules="rules" ref="ruleForm" class="demo-ruleForm">
                <el-form-item label="系统ID：" prop="rId" class="col-md-8">
                    <el-input v-model="ruleForm.rId"></el-input>
                </el-form-item>
                <el-form-item label="输入密码：" prop="rPwd" class="col-md-8">
                    <el-input v-model="ruleForm.rPwd"></el-input>
                </el-form-item>
                <el-form-item>
                <el-button type="primary" @click="login()">确认</el-button>
                </el-form-item>
            </el-form>
        </div>   
    </div>
</template>

<style scoped>
    .el-form-item{
        margin: 20px auto;
        text-align: center;
    }
    #change_body{
        margin:15% auto;
    }
</style>

<script>
export default {
  data() {
    return {
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
            }
        }
    }, 
    methods:{
      login:function(){
        this.$store.state.username = this.ruleForm.rName;
        this.$store.state.enterable = false;
        this.$store.state.entersuccess = true;
        this.$store.state.registerable = false;
        this.$store.state.createissue = true;
        this.$store.state.issuereport = true;
        this.$store.state.changeable = true;
        this.$store.state.changeissue = true;
        this.$router.go(-1);
      }
    }
};
</script>