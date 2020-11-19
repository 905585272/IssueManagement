<template>
    <div class="container">
        <div id="change_info_title">
            <h5 class="text-left"><b>账号注册</b></h5>
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
                <el-form-item label="输入密码：" prop="rPwd" class="col-md-8">
                    <el-input v-model="ruleForm.rPwd" show-password></el-input>
                </el-form-item>
                <el-form-item label="确认密码：" prop="final_passwd" class="col-md-8">
                    <el-input v-model="ruleForm.final_passwd" show-password></el-input>
                </el-form-item>
                <el-form-item>
                <el-button type="primary" @click="submitForm('ruleForm')">确认</el-button>
                </el-form-item>
            </el-form>
        </div>   
    </div>
</template>

<style>
    .el-form-item{
        margin:0 auto;
        text-align: center;
    }
    #change_body{
        margin:15% auto;
    }
</style>

<script>
    export default {
        data() {
            var validatePass1 = (rule, value, callback) => {
                if (value !== '') { 
                    if (!/^(?=.*[A-Z].*)(?=.*[a-z].*)(?=.*?[_\-@&=])[a-zA-Z_\-@&=]+.{8,30}$/.test(value)){
                        callback(new Error('密码必须包含大小写和特殊字符(_-@&=)，且在8-30位之间'));
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
            return {
                ruleForm:{
                    rId:'',
                    rName: '',
                    rEmail:'',
                    rPwd:'',
                    final_passwd:''
                },
                rules: {
                    rId: [
                        { required: true, message: '请输入ID', trigger: 'blur' },
                        { min: 1, max: 30, message: '长度在 1 到 30 个字符', trigger: 'blur' },
                    ],
                    rName: [
                        { required: true, message: '请输入名字', trigger: 'blur' },
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
                        { validator: validatePass1, trigger: 'blur' }
                    ],
                    final_passwd: [
                        { required: true,message: '请再次输入密码',trigger: 'blur' },
                        { validator: validatePass2, trigger: 'blur' }
                    ]
                }
            }
        }, 
        methods: {
            submitForm(ruleForm) {
                this.$refs[ruleForm].validate((valid) => {
                    console.log("data is posting!");
                    if (valid) {
                        this.$http.post("http://127.0.0.1:9090/user/insert2",{
                            rId:this.ruleForm.rId,
                            rName:this.ruleForm.rName,
                            rEmail:this.ruleForm.rEmail,
                            rPwd:this.ruleForm.rPwd,
                        }).then(function(resp){
                            console.log(resp)
                        }).catch(function(error){
                            console.log(error);
                        })
                    } else {
                        alert('error submit!!');
                    }
                });
            }
        }
    };
</script>