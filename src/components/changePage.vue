<template>
    <div class="container">
        <div id="change_info_title">
            <h5 class="text-left"><b>修改信息</b></h5>
            <hr>
        </div>
        <div id="change_body">
            <el-form :model="ruleForm" :rules="rules" ref="ruleForm" class="demo-ruleForm">
                <el-form-item label="登录ID：" prop="ID" class="col-md-8" id="user_id">
                    <el-input v-model="ruleForm.ID" disabled="disabled"></el-input>
                </el-form-item>
                <el-form-item label="姓名：" prop="name" class="col-md-8">
                    <el-input v-model="ruleForm.name" ></el-input>
                    <!-- onkeyup="value=value.replace(/[^\u4E00-\u9FA5]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\u4E00-\u9FA5]/g,''))"  -->
                </el-form-item>
                <el-form-item label="邮箱：" prop="email" class="col-md-8">
                    <el-input v-model="ruleForm.email" type="email"></el-input>
                </el-form-item>
                <el-form-item label="修改密码：" prop="change_passwd" class="col-md-8">
                    <el-input v-model="ruleForm.change_passwd" show-password></el-input>
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
            var validatePass2 = (rule, value, callback) => {
                if (value !== this.ruleForm.change_passwd) {
                callback(new Error('两次输入密码不一致!'));
                } else {
                callback();
                }
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
                    ID:'',
                    name: '',
                    email:'',
                    change_passwd:'',
                    final_passwd:''
                },
                rules: {
                    name: [
                        { required: true, message: '请输入名字', trigger: 'blur' },
                        { min: 1, max: 20, message: '长度在 1 到 20 个汉字', trigger: 'blur' },
                        { validator: checkChinese, trigger: 'blur' }
                    ],
                    email: [
                        { required: true, message: '请输入邮箱', trigger: 'blur' },
                        { min: 1, max: 30, message: '长度在 1 到 30 个字符', trigger: 'blur' },
                        { validator: checkEmail, trigger: 'blur' }
                    ],
                    change_passwd: [
                        { required: true,message: '请输入密码',trigger: 'blur' },
                        { min: 1, max: 30, message: '长度在 1 到 30 个字符', trigger: 'blur' }
                    ],
                    final_passwd: [
                        { required: true,message: '请再次输入密码',trigger: 'blur' },
                        { validator: validatePass2, trigger: 'blur' }
                    ]
                }
            }
        }, 
        methods: {
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        console.log(this.change_passwd);
                        console.log(this.final_passwd);
                        alert('submit!');
                    } else {
                        alert('error submit!!');
                    }
                });
            }
        }
    };
</script>