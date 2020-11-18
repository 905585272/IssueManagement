<template>
  <div class="container">
        <div id="change_info_title">
            <h5 class="text-left"><b>登录页面</b></h5>
            <hr>
        </div>
        <div id="change_body">
            <el-form :model="ruleForm" :rules="rules" ref="ruleForm" class="demo-ruleForm">
                <el-form-item label="系统ID：" prop="ID" class="col-md-8" id="user_id">
                    <el-input v-model="ruleForm.ID"></el-input>
                </el-form-item>
                <el-form-item label="输入密码：" prop="password" class="col-md-8">
                    <el-input v-model="ruleForm.password"></el-input>
                </el-form-item>
                <el-form-item>
                <el-button type="primary">确认</el-button>
                </el-form-item>
            </el-form>
        </div>   
    </div>
</template>

<style scoped>
    .el-form-item{
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
                ID:'',
                password:''
            },
            rules: {
                ID: [
                    { required: true, message: '请输入ID', trigger: 'blur' },
                    { min: 1, max: 30, message: '长度在 1 到 30 个字符', trigger: 'blur' },
                ],
                password: [
                    { required: true, message: '请输入密码',trigger: 'blur' },
                    { min: 1, max: 30, message: '长度在 1 到 30 个字符', trigger: 'blur' }
                ]
            }
        }
    }, 
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>