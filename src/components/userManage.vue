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
                        <el-input v-model="userform.rId"></el-input>
                    </el-form-item>
                    <el-form-item label="用户姓名" prop="iCreator" class="col-md-6" >
                        <el-input v-model="userform.rName"></el-input>
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
                :default-sort = "{prop: 'create_date', order: 'descending'}"
                @row-click="turnto_changeIssue"
                >
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
                    type="danger"
                    v-if="scope.row.rState === '激活'"
                    @click="usingCancellation(scope.row)">
                    注销
                    </el-button>
                    <el-button
                    size="small"
                    type="success"
                    v-if="scope.row.rState === '注销'"
                    @click="usingAction(scope.row)">
                    激活
                    </el-button>
                    <el-button
                    type="primary"
                    size="small"
                    v-if="scope.row.rUserid === '普通用户' && scope.row.rState === '激活'"
                    @click="updateToManager(scope.row)">
                    经理
                    </el-button>
                </template>
                </el-table-column>
            </el-table>
            <el-pagination
            background :page-size="4" :pager-count="11" layout="prev, pager, next" :total="total_data_num"
            @current-change="handleCurrentChange">
            </el-pagination>
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
                push_index:0,
                data_list:[],
                total_data_num:1,
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
                this.total_data_num = res.data.length;
                res.body.forEach(element=>{
                    if(element.rUserid !== 'Admin'){
                        this.data_list.push(element);
                    }
                })
                for (let index = 0; index < 4; index++) {
                    const element = this.data_list[index];
                    this.tableData.push(element);
                }  
                this.total_data_num = res.body.length;
            }).catch(function(error){
                console.log(error);
            });
        },
        methods: {
            handleCurrentChange(currentPage){
                this.push_index=(currentPage-1)*4;
                this.tableData.splice(0,this.tableData.length);
                    for (let index = 0; index < 4; index++) {
                        // console.log("page:"+index);
                        if (this.push_index<this.data_list.length) {
                            const element = this.data_list[this.push_index++];
                            console.log("page:"+element);
                            this.tableData.push(element);
                        }else{
                            break;
                        }
                    }
            },
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
            // 序号生成
            indexMethod(index) {
                return index = index + 1 ;
            },
            submitForm() {
                this.data_list.splice(0,this.data_list.length);
                this.page_num=1;
                this.$http.post('http://localhost:8080/user/selectallSelective',{
                rId:this.userform.rId,
                rName:this.userform.rName,
                }).then(function (userdata) {
                    this.tableData.splice(0,this.tableData.length);
                    console.log(userdata.body);
                    userdata.body.forEach(element=>{
                    //  this.tableData.push(element);
                        console.log("element:"+element.rName);
                        this.data_list.push(element);
                    })
                }).then(function () {
                    console.log("data list:"+this.data_list);
                    if (this.data_list.length>=4) {
                        for (let index = 0; index < 4; index++) {
                            this.push_index = index;
                            const element = this.data_list[index];
                            
                            this.tableData.push(element);
                        }
                    }
                    else{
                        for (let index = 0; index < this.data_list.length; index++) {
                            this.push_index = index;
                            const element = this.data_list[index];
                            this.tableData.push(element);
                        }
                    }   
                    this.total_data_num=this.data_list.length;
                })
            },
            reset(){
                this.data_list.splice(0,this.data_list.length);
                this.tableData.splice(0,this.tableData.length);
                this.userform.rId='';
                this.userform.rName='';
                this.$http.get('http://localhost:8080/user/selectall').
                then(function(res){
                    this.total_data_num = res.data.length;
                    res.body.forEach(element=>{
                        if(element.rUserid != 'Admin'){
                            this.data_list.push(element);
                        }
                    })
                    for (let index = 0; index < 4; index++) {
                        const element = this.data_list[index];
                        this.tableData.push(element);
                    }
                }).catch(function(error){
                    console.log(error);
                });
                this.total_data_num=this.data_list.length;
            },
            goback(){
                this.$router.go(-1);
            },
            //
            usingCancellation(row){
                row.rState = '注销';
                this.$http.post('http://localhost:8080/user/update',{
                    rId:row.rId,
                    rState:row.rState,
                }).then(function(res){
                    console.log(res.data);
                    // this.cancellation = true;
                })
            },
            //
            updateToManager(row){
                if(row.rUserid == '普通用户'){
                    row.rUserid = '经理';
                    this.$http.post('http://localhost:8080/user/update',{
                    rId:row.rId,
                    rUserid:row.rUserid,
                    }).then(function(resp){
                        console.log(resp.data);
                    })
                }
            },
            //
            usingAction(row){
                row.rState = '激活';
                this.$http.post('http://localhost:8080/user/update',{
                    rId:row.rId,
                    rState:row.rState,
                }).then(function(res){
                    console.log(res.data);
                    // this.cancellation = true;
                })
            }
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