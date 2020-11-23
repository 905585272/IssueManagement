<template>
<div>
    <div class="container">
        <div class="col-md-12 page_title">
            <el-form class="form-horizontal row" role="form" :model="issueform" ref="issueform">
                <label for="title" class="col-md-2 control-label"><h4>Issue题目</h4></label>
                <el-form-item class="col-md-8" prop="iTitle" >
                    <el-input  v-model="issueform.iTitle" id="title" placeholder="请输入题目"></el-input>
                </el-form-item>
            </el-form>
        </div>
    </div>
    <div class="container body">
        <div class="col-md-12">
            <el-form ref="issueform" :model="issueform" class="col-md-12 row">
                <el-form-item label="Issue No" class="col-md-3" >
                    <el-input v-model="issueform.iNo" ReadOnly></el-input>
                </el-form-item>
                <el-form-item label="创建时间" class="col-md-3">
                    <el-input v-model="issueform.iCdate" ReadOnly></el-input>
                </el-form-item>
                <el-form-item label="Issue类型" class="col-md-3">
                    <el-input v-model="issueform.iType" ReadOnly></el-input>
                </el-form-item>
                <el-form-item label="Issue等级" class="col-md-3">
                    <el-input v-model="issueform.iLevel" ReadOnly>
                    </el-input>
                </el-form-item>
            
                <el-form-item label="影响版本" class="col-md-3">
                    <el-input v-model="issueform.iVesion" ReadOnly></el-input>
                </el-form-item>
                <el-form-item label="计划修改时间" class="col-md-3">
                    <el-input  v-model="issueform.iPlantime" ReadOnly></el-input>
                </el-form-item>
                <el-form-item label="实际完成时间" class="col-md-3">
                    <el-input v-model="issueform.iFinishtime" ReadOnly></el-input>
                </el-form-item>
            
                <el-form-item label="重现步骤" class="col-md-12">
                    <el-input type="textarea" v-model="issueform.iReappear" class="col-md-10" ReadOnly></el-input>
                </el-form-item>

                <el-form-item label="解决方案" class="col-md-12" v-if="show_flg">
                    <el-input type="textarea" v-model="issueform.iHandlemethod" class="col-md-10" ReadOnly></el-input>
                </el-form-item>

                <el-form-item label="指派修改人" class="col-md-4">
                    <el-input v-model="issueform.iChangeperson" ReadOnly></el-input>
                </el-form-item>
                <el-form-item class="col-md-12">
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
      return {
        pickerOptions0:{ 
            disabledDate(time) {
                return time.getTime() < Date.now() - 8.64e7;//如果没有后面的-8.64e7就是不可以选择今天的 
            }
        },
        show_flg:'',
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
      }
    },
    mounted(){
        this.issueform.default_date=new Date();
        console.log("!"+this.issueform.default_date)
        this.issueform.iCreator=this.$store.state.iCreator;
        this.issueform.iTitle=this.$store.state.iTitle;
        this.issueform.iNo=this.$store.state.iNo;
        this.issueform.iCdate=this.$store.state.iCdate;
        this.issueform.iType=this.$store.state.iType;
        this.issueform.iLevel=this.$store.state.iLevel;
        this.issueform.iVesion=this.$store.state.iVesion;
        this.issueform.iFinishtime=this.$store.state.iFinishtime;
        this.issueform.iReappear=this.$store.state.iReappear;
        this.issueform.iChangeperson=this.$store.state.iChangeperson;
        this.issueform.iHandlemethod=this.$store.state.iHandlemethod;
        this.issueform.iIssuestate=this.$store.state.iIssuestate;
        this.issueform.iPlantime=this.$store.state.iPlantime;
        if(this.$store.state.rName==this.issueform.iChangeperson){
                this.show_flg=true;
            }else if(this.$store.state.rName==this.issueform.iCreator){
                this.show_flg=false;
            }
    },
    methods: {
        getDate(strDate){
            var date = eval('new Date(' + strDate.replace(/\d+(?=-[^-]+$)/, 
            function (a) { return parseInt(a, 10) - 1; }).match(/\d+/g) + ')');
            return date;
            },
        goback(){
                this.$router.go(-1);
        },
    }
  };
</script>