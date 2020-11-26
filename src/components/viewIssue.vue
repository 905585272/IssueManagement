<template>
<div class="img_body">
    <div class="container">
        <div class="col-md-12 page_title">
            <el-form class="form-horizontal row" role="form" :model="issueform" ref="issueform">
                <label for="title" class="col-md-2 control-label"><h4>Issue题目</h4></label>
                <el-form-item class="col-md-8" prop="iTitle" >
                    <el-input  v-model="issueform.iTitle" id="title" placeholder="请输入题目" ReadOnly></el-input>
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
                    <el-input type="textarea" v-model="issueform.iReappear" class="col-md-10" ReadOnly :rows="10"></el-input>
                </el-form-item>

                <el-form-item label="解决方案" class="col-md-12">
                    <el-input type="textarea" v-model="issueform.iHandlemethod" class="col-md-10" ReadOnly :rows="8"></el-input>
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
    background-image: url(https://img.ivsky.com/img/tupian/pic/202005/03/hupo_daoying-002.jpg);
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