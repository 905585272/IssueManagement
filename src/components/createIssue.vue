<template>
<div>
    <div class="container">
        <div class="col-md-12 page_title">
            <el-form class="form-horizontal row" role="form" :rules="rules" :model="issueform" ref="issueform">
                <label for="title" class="col-md-2 control-label"><h4>Issue题目</h4></label>
                <el-form-item class="col-md-8" prop="iTitle" >
                    <el-input  v-model="issueform.iTitle" id="title" placeholder="请输入题目"></el-input>
                </el-form-item>
            </el-form>
        </div>
    </div>
    <div class="container body">
        <div class="col-md-12">
            <el-form ref="issueform" :model="issueform" class="col-md-12 row" :rules="rules">
                <el-form-item label="Issue No" class="col-md-3" >
                    <el-input v-model="issueform.iNo" ReadOnly></el-input>
                </el-form-item>
                <el-form-item label="创建时间" class="col-md-3">
                    <el-input v-model="issueform.iCdate" ReadOnly></el-input>
                </el-form-item>
                <el-form-item label="Issue类型" class="col-md-3" prop="iType">
                    <el-input v-model="issueform.iType"></el-input>
                </el-form-item>
                <el-form-item label="Issue等级" class="col-md-3" prop="iLevel">
                    <el-select v-model="issueform.iLevel" placeholder="请选择Issue等级">
                        <el-option label="最高" value="最高" name="iLevel"></el-option>
                        <el-option label="较高" value="较高" name="iLevel"></el-option>
                        <el-option label="一般" value="一般" name="iLevel"></el-option>
                        <el-option label="低" value="低" name="iLevel"></el-option>
                    </el-select>
                </el-form-item>
            
                <el-form-item label="影响版本" class="col-md-3" prop="iVesion">
                    <el-input v-model="issueform.iVesion"></el-input>
                </el-form-item>
                <el-form-item label="计划修改时间" class="col-md-3" prop="iPlantime">
                    <el-date-picker type="date" placeholder="选择日期" v-model="issueform.iPlantime" :picker-options="pickerOptions0"></el-date-picker>
                </el-form-item>

                <el-form-item label="重现步骤" class="col-md-12" prop="iReappear">
                    <el-input type="textarea" v-model="issueform.iReappear" class="col-md-10"></el-input>
                </el-form-item>
                <el-form-item label="指派修改人" class="col-md-4" prop="iChangeperson">
                    <el-input v-model="issueform.iChangeperson"></el-input>
                </el-form-item>
                <el-form-item class="col-md-12">
                    <el-button type="primary" @click="submitForm('issueform')">提交</el-button>
                    <el-button type="button" @click="goback()">返回</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</div>
</template>

<style scoped>
    .page_title{
        margin:3% auto;
    }
    .el-textarea__inner{
        height:100px;
    }
    .body{
        margin:10% auto;
    }
</style>

<script>
export default {
    data() {
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
        msg:[],
        pickerOptions0:{ 
            disabledDate(time) {
                return time.getTime() < Date.now() - 8.64e7;//如果没有后面的-8.64e7就是不可以选择今天的 
            }
        },
        userform:{
            rId:'',
            rCissue:'',
        },
        issueform: {
            iCreator:'',
            iTitle:'',
            iNo: '',
            iCdate: new Date().getFullYear()+"-"+(new Date().getMonth()+1)+"-"+new Date().getDate(),
            iType: '',
            iLevel: '',
            iVesion:'',
            iPlantime:'',
            iFinishtime: '',
            iReappear: '',
            iChangeperson:'',
            iHandlemethod:'',
            iIssuestate:'',
        },
        rules: {
            iTitle: [
                { min: 0, max: 80, message: '长度在 0 到 80 个字符', trigger: 'blur' },
            ],
            iLevel:[
                { required: true,message: '请选择计划时间',trigger: 'change' },
            ],
            iType: [
                { required: true, message: '请输入issue类型', trigger: 'blur' },
                { min: 1, max: 30, message: '长度在 1 到 30 个字符', trigger: 'blur' },
            ],
            iVesion: [
                { required: true,message: '请输入影响版本',trigger: 'blur' },
                { min: 1, max: 30, message: '长度在 1 到 30 个字符', trigger: 'blur' },
            ],
            iPlantime: [
                { type: 'date', required: true, message: '请选择计划时间', trigger: 'change' }
            ],
            iChangeperson:[
                { required: true, message: '请输入指派修改人', trigger: 'blur' },
                { validator: checkChinese, trigger: 'blur' }
            ],
            iReappear:[
                { min: 1, max: 2000, message: '长度在 1 到 2000 个字符', trigger: 'blur' },
            ]
        }
      }
    },
    mounted(){
        this.$http.get('http://localhost:8080/issue/issuecount').
        then(function(res){
            this.issueform.iNo = res.body+1;
        }).catch(function(error){
            console.log(error);
        })
        
    },
    methods: {
        getDate(strDate){
            var date = eval('new Date(' + strDate.replace(/\d+(?=-[^-]+$)/, 
            function (a) { return parseInt(a, 10) - 1; }).match(/\d+/g) + ')');
            return date;
            },
        submitForm(formName) {
            this.$refs[formName].validate((valid) => {
                    this.issueform.iCreator=this.$store.state.rName;
                    if (valid) {
                        this.$http.post('http://localhost:8080/user/selectallSelective',{
                            rName:this.issueform.iChangeperson,
                        }).then(function(res){
                            this.msg=res.body;
                            // console.log("length:"+this.msg.length);
                            // console.log(this.issueform.iChangeperson),
                            var flg = false;
                            this.msg.forEach(item=>{
                                if (item.rName==this.issueform.iChangeperson) {
                                    flg = true;
                                    // console.log("!res:"+item.rId);
                                    // console.log("!num:"+parseInt(item.rRissue+1));
                                }
                            });
                            if(flg){
                                this.msg.forEach(item=>{
                                if (item.rName==this.issueform.iChangeperson) {
                                    flg = true;
                                        this.$http.post('http://localhost:8080/user/update',{
                                        rId:item.rId,
                                        rRissue:parseInt(item.rRissue+1),
                                        }).then(
                                            this.$http.get('http://localhost:8080/user/selectbyid/'+this.$store.state.rId).
                                            then(function(res){
                                                // console.log("res:"+this.$store.state.rId);
                                                // console.log("num:"+parseInt(res.data.rCissue+1));
                                                this.$http.post('http://localhost:8080/user/update',{
                                                rId:res.data.rId,
                                                rCissue:parseInt(res.data.rCissue+1),
                                                }).catch(function(error){
                                                    console.log(error);
                                                })
                                            })
                                        ).then(
                                            this.$http.post('http://localhost:8080/issue/insert',{
                                            iCreator:this.$store.state.rId+this.issueform.iCreator,
                                            iTitle:this.issueform.iTitle,
                                            iNo:this.issueform.iNo,
                                            iCdate:this.issueform.iCdate,
                                            // this.getDate(this.issueform.iCdate),
                                            iType:this.issueform.iType,
                                            iLevel:this.issueform.iLevel,
                                            iVesion:this.issueform.iVesion,
                                            iPlantime:this.issueform.iPlantime,
                                            iFinishtime:this.issueform.iFinishtime,
                                            iReappear:this.issueform.iReappear,
                                            iChangeperson:this.issueform.iChangeperson,
                                            iHandlemethod:this.issueform.iHandlemethod, 
                                            }).then(function(resp){
                                                console.log(resp);
                                                this.$alert('Issue创建成功！', {
                                                    confirmButtonText: '确定',
                                                }).then(() => {
                                                        
                                                    })
                                            }).catch(function(error){
                                                console.log(error);
                                            }),
                                            this.$router.go(-1),
                                        );
                                    }
                                });
                                
                            }else{
                                this.$message.error('你指定的修改人不存在!');
                                console.log("error")
                            } 
                        });
                    } else {
                        this.$alert('非法操作!', {
                            confirmButtonText: '确定',
                        });
                    }
                });
        },
        goback(){
                this.$router.go(-1);
        }, 
    }
  };
</script>