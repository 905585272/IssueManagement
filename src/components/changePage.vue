<template>
    <div class="container">
        <div id="change_info_title">
            <h5 class="text-left"><b>修改信息</b></h5>
            <hr>
        </div>
        <div id="change_body">
            <el-form :model="ruleForm" :rules="rules" ref="ruleForm" class="demo-ruleForm">
                <el-form-item label="登录ID：" prop="rId" class="col-md-8">
                    <el-input v-model="ruleForm.rId" disabled="disabled"></el-input>
                </el-form-item>
                <el-form-item label="姓名：" prop="rName" class="col-md-8">
                    <el-input v-model="ruleForm.rName" ></el-input>
                </el-form-item>
                <el-form-item label="邮箱：" prop="rEmail" class="col-md-8">
                    <el-input v-model="ruleForm.rEmail" type="email"></el-input>
                </el-form-item>
                <el-form-item label="修改密码：" prop="rPwd" class="col-md-8">
                    <el-input v-model="ruleForm.rPwd" show-password></el-input>
                </el-form-item>
                <el-form-item label="确认密码：" prop="final_passwd" class="col-md-8">
                    <el-input v-model="ruleForm.final_passwd" show-password></el-input>
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
        margin:20px auto;
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
                    if (!/^(?=.*[A-Z].*)(?=.*[a-z].*)(?=.*?[_\-@&=!])[a-zA-Z_\-@&=!0-9]+.{7,30}$/.test(value)){
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
            };
            return {
                ruleForm:{
                    rId:'',
                    rName: '',
                    rEmail:'',
                    rPwd:'',
                    final_passwd:''
                },
                rules: {
                    rName: [
                        { required: true, message: '请输入名字', trigger: 'blur' },
                        { min: 1, max: 30, message: '长度在 1 到 30 个字符', trigger: 'blur' },
                        { validator: checkChinese, trigger: 'blur' }
                    ],
                    rEmail: [
                        { required: true, message: '请输入邮箱', trigger: 'blur' },
                        { min: 1, max: 30, message: '长度在 1 到 30 个字符', trigger: 'blur' },
                        { validator: checkEmail, trigger: 'blur' }
                    ],
                    rPwd: [
                        { required: true,message: '请输入密码',trigger: 'blur' },
                        { min: 8, max: 30, message: '长度在 8 到 30 个字符', trigger: 'blur' },
                        { validator: validatePass1, trigger: 'blur' }
                    ],
                    final_passwd: [
                        { required: true,message: '请再次输入密码',trigger: 'blur' },
                        { validator: validatePass2, trigger: 'blur' }
                    ]
                }
            }
        },
        mounted(){
            this.ruleForm.rId=this.$store.state.rId;
            this.ruleForm.rName=this.$store.state.rName;
            this.ruleForm.rEmail=this.$store.state.rEmail;
        },
        methods: {
            submitForm(forMname) {
                this.$refs[forMname].validate((valid) => {
                    console.log("data is posting!");
                    if (valid) {
                        console.log(this.ruleForm.rId);
                        this.$http.get('http://localhost:8080/user/selectall').
                        then(function(res){
                            this.msg = res.body;
                            console.log(this.msg);
                            var flag=true;
                            // this.msg.forEach(item=>{
                            //     console.log(this.ruleForm.rName);
                            //    if(item.rName==this.ruleForm.rName){
                            //        flag = false;
                            //        this.$alert('姓名与ID不同！',{
                            //            confirmButtonText: '确定',
                            //        })
                            //    }
                            // });
                               if (flag) {
                                this.$http.post('http://localhost:8080/user/update',{
                                rId:this.ruleForm.rId,
                                rName:this.ruleForm.rName,
                                rEmail:this.ruleForm.rEmail,
                                rPwd:this.ruleForm.rPwd,
                            }).then(function(resp){
                                console.log(resp);
                                // this.$store.state.username = this.ruleForm.rName;
                                // this.$store.state.enterable = false;
                                // this.$store.state.entersuccess = true;
                                // this.$store.state.registerable = false;
                                // this.$store.state.createissue = true;
                                // this.$store.state.issuereport = true;
                                // this.$store.state.changeable = true;
                                this.$store.state.rId=this.ruleForm.rId;
                                this.$store.state.rName=this.ruleForm.rName;
                                this.$store.state.rEmail=this.ruleForm.rEmail;
                                this.$store.state.rPwd=this.ruleForm.rPwd;
                                this.$alert('用户信息修改成功！', {
                                    confirmButtonText: '确定',
                                }).then(() => {
                                        this.$router.go(-1);
                                    })
                            }).catch(function(error){
                                console.log(error);
                            })
                               }
                        }).catch(function(error){
                            console.log(error);
                        })
                       
                    } else {
                        alert('error submit!!');
                    }
                        
                });
            },
            goback(){
                this.$router.go(-1);
            },
        }
    };
</script>