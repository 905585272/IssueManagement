<template>
  <div class="container">
        <div id="change_info_title">
            <h5 class="text-left"><b>登录页面</b></h5>
            <hr>
        </div>
        <div id="change_body">
            <el-form :model="ruleForm" :rules="rules" ref="ruleForm" class="demo-ruleForm">
                <el-form-item label="系统ID：" prop="rId" class="col-md-8" id="user_id">
                    <el-input v-model="ruleForm.rId"></el-input>
                </el-form-item>
                <el-form-item label="输入密码：" prop="rPwd" class="col-md-8">
                    <el-input v-model="ruleForm.rPwd" show-password></el-input>
                </el-form-item>
                <el-form-item>
                <el-button type="primary" @click="submitForm('ruleForm')">确认</el-button>
                <el-button type="button" @click="goback()">返回</el-button>
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
          submitForm(ruleForm) {
                this.$refs[ruleForm].validate((valid) => {
                    if (valid) {
                        console.log(this.ruleForm.rId); 
                        this.$http.get('http://localhost:8080/user/selectbyid/'+this.ruleForm.rId).
                        then(function(res){
                            console.log(res.data);
                            if (res.data.rUserid == '经理') {
                                this.$store.state.createissue = false;
                                console.log('是经理');
                            }else{
                            this.$store.state.createissue = true;
                            console.log('不是经理');
                        }
                            if(res.data===1){ 
                                this.$alert('用户不存在!', {
                                    confirmButtonText: '确定',
                                    })
                            }else{
                                if(this.ruleForm.rPwd==res.data.rPwd){
                                this.$store.state.rName = this.ruleForm.rName;
                                this.$alert('欢迎使用', {
                                    confirmButtonText: '确定',
                                }).then(() => {
                                        this.$message({
                                            type: 'success',
                                            message: '欢迎!'+res.data.rName,
                                        },
                                        this.$store.state.enterable = false,
                                        this.$store.state.entersuccess = true,
                                        this.$store.state.registerable = false,
                                        this.$store.state.issuereport = true,
                                        this.$store.state.changeable = true,
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
                                        this.$router.go(-1));
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
                        alert('error submit!!');
                        }
                    })    
                },
                goback(){
                    this.$router.go(-1);
                }, 
            },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
