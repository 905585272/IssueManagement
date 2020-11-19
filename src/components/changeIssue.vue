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
                        <el-option label="最高" value="highest" name="iLevel"></el-option>
                        <el-option label="较高" value="high" name="iLevel"></el-option>
                        <el-option label="一般" value="common" name="iLevel"></el-option>
                        <el-option label="低" value="low" name="iLevel"></el-option>
                    </el-select>
                </el-form-item>
            
                <el-form-item label="影响版本" class="col-md-3" prop="iVerson">
                    <el-input v-model="issueform.iVerson"></el-input>
                </el-form-item>
                <el-form-item label="计划修改时间" class="col-md-3" prop="iPlantime">
                    <el-date-picker type="date" placeholder="选择日期" v-model="issueform.iPlantime" :picker-options="pickerOptions0"></el-date-picker>
                </el-form-item>
                <el-form-item label="实际完成时间" class="col-md-3">
                    <el-input v-model="issueform.iFinishtime" ReadOnly></el-input>
                </el-form-item>
            
                <el-form-item label="重现步骤" class="col-md-12" prop="iReappear">
                    <el-input type="textarea" v-model="issueform.iReappear" class="col-md-10" ></el-input>
                </el-form-item>

                <el-form-item label="解决方案" class="col-md-12" prop="iHandlemethod">
                    <el-input type="textarea" v-model="issueform.iHandlemethod" class="col-md-10" v-if="show_hide"></el-input>
                </el-form-item>

                <el-form-item label="指派修改人" class="col-md-4">
                    <el-input v-model="issueform.iChangeperson" ReadOnly></el-input>
                </el-form-item>
                <el-form-item class="col-md-12">
                    <el-button type="primary" @click="submitForm('issueform')" v-if="show_hide">提交验证</el-button>
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
      return {
        pickerOptions0:{ 
            disabledDate(time) {
                return time.getTime() < Date.now() - 8.64e7;//如果没有后面的-8.64e7就是不可以选择今天的 
            }
        },
        show_flg:true,
        rNname:'',
        issueform: {
            iCreator:'',
            iTitle:'',
            iNo: '',
            //Math.floor(Math.random() * 10000)
            iCdate: '',
            iType: '',
            iLevel: '',
            iVerson:'',
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
            iVerson: [
                { required: true,message: '请输入影响版本',trigger: 'blur' },
                { min: 1, max: 30, message: '长度在 1 到 30 个字符', trigger: 'blur' },
            ],
            iPlantime: [
                { type: 'date', required: true, message: '请选择计划时间', trigger: 'change' }
            ],
            iHandlemethod:[
                { min: 1, max: 200, message: '长度在 1 到 2000 个字符', trigger: 'blur' },
            ],
        }
      }
    },
    methods: {
        submitForm(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    alert('submit!');
                    var date=new Date().getFullYear()+"/"+new Date().getMonth()+"/"+new Date().getDate()
                    this.issueform.iFinishtime=date;
                    this.issueform.iIssuestate='待验证';
                } else {
                    alert('error submit!!');
                }
            });
        },
        show_hide(){
            if(this.rNname==this.issueform.iChangeperson){
                this.show_flg=true;
            }else if(this.rNname==this.issueform.iCreator){
                this.show_flg=false;
            }
            return this.show_flg;
        }
    }
  };
</script>