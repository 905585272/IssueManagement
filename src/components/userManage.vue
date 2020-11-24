<template>
    <div class="container">
        <div>
            <div class="titlea col-md-12">
                <h5 class="text-left"><b>账号管理</b></h5>
                <el-divider></el-divider>
            </div>
            <div class="issue_menu col-md-9">
                <el-form :model="userform" :rules="rules" ref="userform" class="row">
                    <el-form-item label="用户ID" class="col-md-6" >
                        <el-input v-model="userform.iCreator"></el-input>
                    </el-form-item>
                    <el-form-item label="用户姓名" prop="iCreator" class="col-md-6" >
                        <el-input v-model="userform.iChangeperson"></el-input>
                    </el-form-item>
                    <el-form-item class="issue_button">
                    <el-button type="primary" @click="submitForm()">查询</el-button>
                    <el-button type="button" @click="reset()">清空</el-button>
                    <el-button type="button" @click="goback()">返回</el-button>
                    </el-form-item>
                </el-form>
            </div>
        </div>
        <div class="col-md-12">
            <div class="titlea col-md-12">
                <h5 class="text-left"><b>账号列表</b></h5>
                <el-divider></el-divider>
            </div>
            <el-table class="col-md-12 issue_table"
                :data="tableData"
                style="width: 100%"
                :row-class-name="tableRowClassName"
                :default-sort = "{prop: 'create_date', order: 'descending'}"
                @row-click="turnto_changeIssue">
                <!-- border -->
                <!-- stripe -->
                <el-table-column
                type="index"
                label="序号"
                :index="indexMethod">
                </el-table-column>
                <el-table-column
                prop="rId"
                label="用户ID">
                </el-table-column>
                <el-table-column
                prop="rName"
                label="用户姓名">
                </el-table-column>
                <el-table-column
                prop="rEmail"
                label="邮箱">
                </el-table-column>
                <el-table-column
                prop="rUserid"
                label="用户身份">
                </el-table-column>
                <!-- <el-table-column prop="r">
                    
                </el-table-column> -->
                <el-table-column
                prop="rState"
                label="账号状态">
                </el-table-column>
                <el-table-column
                prop="action"
                label="操作">
                <template slot-scope="scope">
                    <el-button
                    size="small"
                    @click="turnto_changeIssue(scope.row)">
                    注销
                    </el-button>
                    <el-button
                    type="primary"
                    size="small"
                    v-if="promotion"
                    @click="turnto_changeIssue(scope.row)">
                    经理
                    </el-button>
                </template>
                </el-table-column>
            </el-table>
        </div>
    </div>
</template>

<script>
    export default{
        data(){
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
            return{
                cancellation:true,
                promotion:true,
                userform:{
                    rId:'',
                    rName:'',
                },
                tableData: [
                ],
                rules:{
                    rId: [
                        { min: 1, max: 30, message: '长度在 1 到 30 个字符', trigger: 'blur' },
                    ], 
                    rName: [
                        { min: 1, max: 30, message: '长度在 1 到 30 个字符', trigger: 'blur' },
                        { validator: checkChinese, trigger: 'blur' }
                    ],
                }
            }
        },
        mounted(){
            this.$http.get('http://localhost:8080/user/selectall').
            then(function(res){
                this.msg = res.body;
                this.msg.forEach(item=>{
                    this.tableData.push(item);
                });
            }).catch(function(error){
                console.log(error);
            });
        },
        methods: {
            turnto_changeIssue(row){
                this.$store.state.iCreator=row.iCreator;
                this.$store.state.iTitle=row.iTitle;
                this.$store.state.iNo=row.iNo;
                this.$store.state.iCdate=row.iCdate;
                this.$store.state.iType=row.iType;
                this.$store.state.iLevel=row.iLevel;
                this.$store.state.iVesion=row.iVesion;
                this.$store.state.iPlantime=row.iPlantime;
                this.$store.state.iFinishtime=row.iFinishtime;
                this.$store.state.iReappear=row.iReappear;
                this.$store.state.iChangeperson=row.iChangeperson;
                this.$store.state.iHandlemethod=row.iHandlemethod;
                this.$store.state.iIssuestate=row.iIssuestate;
            },
            // 标记状态
            tableRowClassName({row}) {
                if (row.rUserid == '经理') {
                return this.promotion = false;
                } else if (row.rUserid == 'admin') {
                return this.promotion = false;
                } else if(row.rUserid == '普通用户'){
                    return this.promotion = true;
                }
            },
            // 序号生成
            indexMethod(index) {
                return index ++;
            },
            submitForm() {
                // this.$refs[formName].validate((valid) => {
            //         console.log("data is posting!");
            //         if (valid) {
            //             console.log(this.ruleForm.rId); 
            //             this.$http.get('http://localhost:8080/user/selectall').
            //             then(function(res){
            //                 this.msg = res.body;
            //                 console.log(this.msg.length);
            //                 var flag=true;
            //                 this.msg.forEach(item=>{
            //                     if (item.rId==this.ruleForm.rId) {
            //                         flag=false;
            //                         this.$alert('ID重复!', {
            //                         confirmButtonText: '确定',
            //                         })
            //                    }
            //                 });
            //                  if (flag) {
            //                     this.$http.post('http://localhost:8080/user/insert',{
            //                 }).then(function(resp){
            //                     console.log(resp);
            //                     this.$alert('用户注册成功！', {
            //                         confirmButtonText: '确定',
            //                     }).then(() => {
            //                             this.$router.go(-1);
            //                         })
            //                 }).catch(function(error){
            //                     console.log(error);
            //                 })
            //                    }
            //             }).catch(function(error){
            //                 console.log(error);
            //             })
                       
            //         } else {
            //             alert('error submit!!');
            //         }
                // });
            },
            reset(){
                this.userform.rId='';
                this.userform.rName='';
            },
            goback(){
                this.$router.go(-1);
            },  
        }
    }
</script>

<style>
    .issue_menu{
        /* border-radius: 2px; */
        box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
        margin: 20px auto;
    }
    .issue_button{
        margin: 20px auto;
    }
    .el-table .warning-row {
        background: oldlace;
    }
    .el-table .success-row {
        background: #f0f9eb;
    }
    .el-table .danger-row {
        background: #F56C6C;
    }
    .issue_table{
        margin: 20px auto;
    }
</style>