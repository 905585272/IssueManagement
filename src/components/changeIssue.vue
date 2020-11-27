<template>
<div class="img_body">
    <div class="container">
        <div class="col-md-12 page_title">
            <el-form class="form-horizontal row" role="form" :rules="rules" :model="issueform" ref="issueform">
                <label for="title" class="col-md-2 control-label"><h4>Issue题目</h4></label>
                <el-form-item class="col-md-8" prop="iTitle" >
                    <el-input  v-model="issueform.iTitle" id="title" placeholder="请输入题目" :readonly="readonly"></el-input>
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
                    <el-input v-model="issueform.iType" id="issue_type" :readonly="readonly"></el-input>
                </el-form-item>
                <el-form-item label="Issue等级" class="col-md-3" prop="iLevel">
                    <el-select v-model="issueform.iLevel" placeholder="请选择Issue等级" :disabled="disabled">
                        <el-option label="最高" value="最高" name="iLevel"></el-option>
                        <el-option label="较高" value="较高" name="iLevel"></el-option>
                        <el-option label="一般" value="一般" name="iLevel"></el-option>
                        <el-option label="低" value="低" name="iLevel"></el-option>
                    </el-select>
                </el-form-item>
            
                <el-form-item label="影响版本" class="col-md-3" prop="iVesion">
                    <el-input v-model="issueform.iVesion" id="issue_version" :readonly="readonly"></el-input>
                </el-form-item>
                <el-form-item label="计划修改时间" class="col-md-4" prop="iPlantime">
                    <el-date-picker
                    placeholder="选择日期" v-model="issueform.iPlantime" :picker-options="pickerOptions0" :disabled="disabled"></el-date-picker>
                </el-form-item>
                <el-form-item label="实际完成时间" class="col-md-3">
                    <el-input v-model="issueform.iFinishtime" :readonly="readonly"></el-input>
                </el-form-item>
            
                <el-form-item label="重现步骤" class="col-md-12" prop="iReappear">
                    <el-input type="textarea" v-model="issueform.iReappear" class="col-md-10" :rows="10" :readonly="readonly"></el-input>
                </el-form-item>

                <el-form-item label="解决方案" class="col-md-12" prop="iHandlemethod">
                    <el-input type="textarea" v-model="issueform.iHandlemethod" class="col-md-10" :rows="8" :readonly="creator_readonly"></el-input>
                </el-form-item>

                <el-form-item label="指派修改人" class="col-md-4">
                    <el-input v-model="issueform.iChangeperson" ReadOnly></el-input>
                </el-form-item>
                <el-form-item class="col-md-12">
                    <el-button type="primary" @click="submitForm('issueform')" v-if="show_flg">提交验证</el-button>
                    <el-button type="primary" @click="return_issue()" v-if="show_flg2&&this.issueform.iIssuestate!=='已关闭'">退回修改</el-button>
                    <el-button type="primary" @click="agree_issue()" v-if="show_flg2&&this.issueform.iIssuestate!=='已关闭'">接受修改</el-button>
                    <el-button type="primary" @click="reopen()" v-if="show_flg2&&this.issueform.iIssuestate=='已关闭'">重新打开</el-button>
                    <el-button type="button" @click="goback()">返回</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</div>
</template>

<style scoped>
    .page_title{
        margin-bottom: 20px;
    }
    .el-textarea__inner{
        height:100px;
    }
    .body{
        margin:20px auto;
        box-shadow: 0 5px 5px rgba(0, 0, 0, .5), 0 0 6px rgba(0, 0, 0, .5);
        border-radius: 15px;
        background: rgba(255, 255, 255, 0.3);
    }
    .img_body{
    position: relative;
    background-image: url(~@/assets/hupo_daoying-002.jpg);
    background-size:cover;
    height: 100vh;
    }
</style>

<script>
export default {
    data() {
      return {
        pickerOptions0:{ 
            disabledDate(time) {
                return time.getTime() < Date.now() - 8.64e7;//如果没有后面的-8.64e7就是不可以选择今天的 
            }
        },
        creator_readonly:false,
        disabled:false,
        readonly:false,
        show_flg:'',
        show_flg2:'',
        rNname:'',
        issueform: {
            iCreator:'',
            iTitle:'',
            iNo: '',
            iCdate: '',
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
            iHandlemethod:[
                { required: true,message: '请输入解决方案',trigger: 'blur' },
                { min: 1, max: 200, message: '长度在 1 到 2000 个字符', trigger: 'blur' },
            ],
        }
      }
    },
    mounted(){
        this.$http.get('http://localhost:8080/issue/selectbyno/'+parseInt(this.$store.state.iNo)).
        then(function(res){
            console.log(res);
            this.issueform.iNo=this.$store.state.iNo;
            this.issueform.iCreator=res.data.iCreator;
            this.issueform.iTitle=res.data.iTitle;
            this.issueform.iCdate=res.data.iCdate;
            this.issueform.iType=res.data.iType;
            this.issueform.iLevel=res.data.iLevel;
            this.issueform.iVesion=res.data.iVesion;
            this.issueform.iFinishtime=res.data.iFinishtime;
            this.issueform.iReappear=res.data.iReappear;
            this.issueform.iChangeperson=res.data.iChangeperson;
            this.issueform.iHandlemethod=res.data.iHandlemethod;
            this.issueform.iIssuestate=res.data.iIssuestate;
            this.issueform.iPlantime=new Date(res.data.iPlantime);
            if(this.$store.state.rName==res.data.iChangeperson){
                this.show_flg=true;
                this.show_flg2=false;
                document.getElementById("issue_type").readOnly=false;
                document.getElementById("issue_version").readOnly=false;
                document.getElementById("title").readOnly = false;
                this.readonly =true;
                this.disabled =true;
                this.creator_readonly=false;
            }else if(this.$store.state.rId+this.$store.state.rName==res.data.iCreator){
                this.show_flg=false;
                this.show_flg2=true;
                document.getElementById("issue_type").readOnly=true;
                document.getElementById("issue_version").readOnly=true;
                document.getElementById("title").readOnly = true;
                this.readonly = true;
                this.disabled = true;
                this.creator_readonly=true;
            }
            // console.log("user:"+this.$store.state.rName);
            // console.log("c:"+this.issueform.iCreator);
            // console.log("show_flg:"+this.show_flg);
            // console.log("show_flg2:"+this.show_flg2);
        }).catch(function(error){
            console.log(error);
        })
    },
    methods: {
        submitForm(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    if (this.issueform.iIssuestate == '已关闭') {
                            this.$alert('该Issue已关闭！', {
                            confirmButtonText: '确定',
                        })
                    }else{
                        this.issueform.iIssuestate='待验证';
                        this.$alert('Issue修改完成！', {
                            confirmButtonText: '确定',
                        }).then(() => {
                            console.log("！！！finishtime:"+this.issueform.iFinishtime);
                            this.$http.post('http://localhost:8080/issue/update',{
                            iNo:this.issueform.iNo,
                            iCreator:this.issueform.iCreator,
                            iTitle:this.issueform.iTitle,
                            iCdate:this.issueform.iCdate,
                            iType:this.issueform.iType,
                            iLevel:this.issueform.iLevel,
                            iVesion:this.issueform.iVesion,
                            iFinishtime:this.issueform.iFinishtime,
                            iReappear:this.issueform.iReappear,
                            iChangeperson:this.issueform.iChangeperson,
                            iHandlemethod:this.issueform.iHandlemethod,
                            iIssuestate:this.issueform.iIssuestate,
                            iPlantime:this.issueform.iPlantime,
                            // 更新用户对应的issue数量
                        }).then(
                            this.$router.go(-1),
                            )
                        })
                    }
                    // this.issueform.iFinishtime=new Date().getFullYear()+"-"+(new Date().getMonth()+1)+"-"+new Date().getDate();
                    
                } else {
                    alert('error submit!!');
                }
            });
        },
        reopen(){
            this.$http.post('http://localhost:8080/user/selectallSelective',{
                rId:"",
                rName:this.issueform.iChangeperson,
            }).then(function(res){
                this.msg=res.body;
                this.msg.forEach(item=>{
                    if (item.rName==this.issueform.iChangeperson && this.issueform.iIssuestate=='已关闭') {
                        this.$message({
                            type: 'success',
                            message: '重新打开完成！',
                        }),
                        this.issueform.iIssuestate='待解决';
                        console.log("rId:"+item.rId);
                        this.$http.post('http://localhost:8080/user/update',{
                            rId:item.rId,
                            rMissue:parseInt(item.rMissue-1),
                            }).then(
                                this.$http.post('http://localhost:8080/issue/update',{
                                iNo:this.issueform.iNo,
                                iIssuestate:this.issueform.iIssuestate,
                                }).then(
                                    // updateFinishtime
                                    this.$http.post('http://localhost:8080/issue/updateFinishtime',{
                                        iNo:this.issueform.iNo
                                    }).then(
                                        this.$router.go(-1),
                                    )
                                ) 
                            )  
                    }
                });
            });
        },
        agree_issue(){
            this.$http.post('http://localhost:8080/user/selectallSelective',{
                rId:"",
                rName:this.issueform.iChangeperson,
            }).then(function(res){
                this.msg=res.body;
                this.msg.forEach(item=>{
                    if (item.rName==this.issueform.iChangeperson && (this.issueform.iIssuestate!=='已关闭'&& this.issueform.iIssuestate!=='待解决')) {
                        this.$message({
                            type: 'success',
                            message: '接受修改完成！',
                        }),
                        this.issueform.iIssuestate='已关闭';
                        console.log("rId:"+item.rId);
                        this.$http.post('http://localhost:8080/user/update',{
                            rId:item.rId,
                            rMissue:parseInt(item.rMissue+1),
                            }).then(
                                this.$http.post('http://localhost:8080/issue/update',{
                                iNo:this.issueform.iNo,
                                iIssuestate:this.issueform.iIssuestate,
                                iFinishtime:new Date().getFullYear()+"-"+(new Date().getMonth()+1)+"-"+new Date().getDate(),
                                }).then(
                                this.$router.go(-1),
                                ) 
                            )  
                    }
                    else if (this.issueform.iIssuestate =='已关闭') {
                        this.$alert('该报表已经是已关闭状态！', {
                            confirmButtonText: '确定',
                        })
                    }else if (this.issueform.iIssuestate=='待解决') {
                        this.$alert('该报表还未修改！', {
                            confirmButtonText: '确定',
                        })
                    }
                });
            });
        },
        return_issue(){
            this.issueform.iFinishtime=new Date().getFullYear()+"-"+(new Date().getMonth()+1)+"-"+new Date().getDate();
            if (this.issueform.iIssuestate=='退回') {
                this.$alert('该报表已经是退回状态！', {
                    confirmButtonText: '确定',
                })
            }else if (this.issueform.iIssuestate =='待解决') {
                this.$alert('该报表还未修改！', {
                    confirmButtonText: '确定',
                })
            }else if (this.issueform.iIssuestate=='已关闭') {
                this.$alert('该报表已经是已关闭状态！', {
                    confirmButtonText: '确定',
                })
            }else{
                this.$alert('Issue退回成功！', {
                    confirmButtonText: '确定',
                }).then(() => {
                    this.issueform.iIssuestate='退回';
                    this.$http.post('http://localhost:8080/issue/update',{
                    iNo:this.issueform.iNo,
                    iIssuestate:this.issueform.iIssuestate,
                    }).then(
                    this.$router.go(-1),
                    )
                })
            };
            
        },
        goback(){
                this.$router.go(-1);
        },
    }
  };
</script>