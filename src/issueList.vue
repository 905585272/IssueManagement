<template>
    <div class="container">
        <div>
            <div class="titlea col-md-12">
                <h5 class="text-left"><b>Issue查询</b></h5>
                <el-divider></el-divider>
            </div>
            <div class="issue_menu col-md-9">
                <el-form :model="issueform" :rules="rules" ref="issueform" class="row">
                    <el-form-item label="Issue No" prop="iNo" class="col-md-3" >
                        <el-input v-model="issueform.iNo"></el-input>
                    </el-form-item>
                    <el-form-item label="Issue状态" class="col-md-3" >
                        <el-select v-model="issueform.iIssuestate" placeholder="请选择Issue状态">
                            <el-option label="待验证" value="待验证" name="iIssuestate"></el-option>
                            <el-option label="关闭" value="关闭" name="iIssuestate"></el-option>
                            <el-option label="待修改" value="待修改" name="iIssuestate"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="创建时间" class="col-md-6">
                        <el-date-picker 
                            v-model="issueform.iCdate"
                            type="daterange"
                            unlink-panels
                            range-separator="-"
                            start-placeholder="开始日期"
                            end-placeholder="结束日期">
                        </el-date-picker>
                    </el-form-item>
                    <el-form-item label="创建人" class="col-md-3" >
                        <el-input v-model="issueform.iCreator"></el-input>
                    </el-form-item>
                    <el-form-item label="修改人" prop="iCreator" class="col-md-3" >
                        <el-input id="change_person" v-model="issueform.iChangeperson"></el-input>
                    </el-form-item>
                    <el-form-item label="修改时间" class="col-md-6">
                        <el-date-picker 
                            v-model="issueform.iFinishtime"
                            type="daterange"
                            unlink-panels
                            range-separator="-"
                            start-placeholder="开始日期"
                            end-placeholder="结束日期">
                        </el-date-picker>
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
                <h5 class="text-left"><b>Issue列表</b></h5>
                <el-divider></el-divider>
            </div>
            <el-table class="col-md-12 issue_table"
                :data="tableData"
                style="width: 100%"
                :row-class-name="tableRowClassName"
                :default-sort="{prop: 'date', order: 'descending'}"
                @row-click="turnto_changeIssue">
                <!-- border -->
                <!-- stripe -->
                <el-table-column
                type="index"
                label="序号"
                :index="indexMethod">
                </el-table-column>
                <el-table-column
                prop="iNo"
                label="IssueID">
                </el-table-column>
                <el-table-column
                prop="iTitle"
                label="Issue标题">
                </el-table-column>
                <el-table-column
                prop="iCreator"
                label="创建人">
                </el-table-column>
                <el-table-column
                prop="iCdate"
                label="创建时间">
                </el-table-column>
                <el-table-column
                prop="iChangeperson"
                label="修改人">
                </el-table-column>
                <el-table-column
                prop="iIssuestate"
                label="Issue状态">
                </el-table-column>
                <el-table-column
                prop="iPlantime"
                label="计划完成时间">
                </el-table-column>
                <el-table-column
                prop="iFinishtime"
                label="实际完成时间">
                </el-table-column>
                <el-table-column
                prop="action"
                label="操作">
                <template slot-scope="scope">
                    <router-link to="/viewIssue">
                        <el-button
                        icon="el-icon-view"
                        @click="turnto_changeIssue(scope.row)">
                        </el-button>
                    </router-link>
                    <router-link to="/changeIssue">
                        <el-button
                        type="primary"
                        icon="el-icon-edit"
                        @click="turnto_changeIssue(scope.row)"
                        v-if="rUserid_flg">
                        </el-button>
                    </router-link>
                </template>
                </el-table-column>
            </el-table>
            <el-pagination
            background :page-size="4" :pager-count="11" layout="prev, pager, next" :total="total_data_num"
            @current-change="handleCurrentChange" :current-page.sync="page_num">
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
                page_num:1,
                hide_flag:true,
                total_data_num:1,
                rUserid_flg:'',
                issueform:{
                    iCreator:'',
                    iTitle:'',
                    iNo: '',
                    iCdate: [],
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
                tableData: [
                ],
                rules:{
                    iNo: [
                        { min: 1, max: 30, message: '长度在 1 到 30 个字符', trigger: 'blur' },
                    ], 
                    iCreator: [
                        { min: 1, max: 30, message: '长度在 1 到 30 个字符', trigger: 'blur' },
                        { validator: checkChinese, trigger: 'blur' }
                    ],
                    iChangeperson: [
                        { min: 1, max: 30, message: '长度在 1 到 30 个字符', trigger: 'blur' },
                        { validator: checkChinese, trigger: 'blur' }
                    ],
                }
            }
        },
        mounted(){
            if(this.$store.state.rUserid=='经理'){
                this.rUserid_flg=false;
                this.$http.get('http://localhost:8080/issue/selectall').
                then(function(res){
                    this.msg = res.body;
                    for (let index = 0; index < 4; index++) {
                        const element = this.msg[index];
                        this.tableData.push(element);
                    }
                    this.total_data_num = res.body.length;
                }).catch(function(error){
                    console.log(error);
                })
            }else if (this.$store.state.rUserid=='普通用户') {
                this.rUserid_flg=true;
                this.$http.get('http://localhost:8080/issue/selectall').
                then(function(res){
                    // console.log(this.msg.length);
                    this.msg = res.body;
                    var num = 0;
                    for (let index = 0; index < this.msg.length; index++) {
                        const element = this.msg[index];
                        if (element.iChangeperson==this.$store.state.rName || element.iCreator==(this.$store.state.rId+this.$store.state.rName)){
                            this.tableData.push(element);
                            num++;
                        }if (num >= 4) {
                            break;
                        }
                    };
                    var num2=0;
                    for (let index = 0; index < this.msg.length; index++) {
                        const element = this.msg[index];
                        if (element.iChangeperson==this.$store.state.rName || element.iCreator==(this.$store.state.rId+this.$store.state.rName)){
                            num2++;
                        }
                    };
                    this.total_data_num = num2;
                }).catch(function(error){
                    console.log(error);
                })
            }
        },
        methods: {
            handleCurrentChange(currentPage){
            // this.currentPage = currentPage;
            // console.log(currentPage);
                if (this.$store.state.rUserid=='普通用户') {
                    if (this.issueform.iCreator==''&&this.issueform.iChangeperson==''&&this.issueform.iNo==''&&this.issueform.iIssuestate=='') {
                        this.$http.post('http://localhost:8080/issue/selectallbynamePage/'+this.$store.state.rName+'/'+currentPage).
                            then(function(res){
                                // console.log(res.data.list);
                                this.tableData.splice(0,this.tableData.length);
                                res.data.list.forEach(element=>{
                                    // console.log(element);
                                    this.tableData.push(element);
                                })
                        });
                    }else if (this.issueform.iCreator==''&&this.issueform.iChangeperson=='') {
                        this.$http.post('http://localhost:8080/issue/selectallSelectivePage/'+currentPage,{
                            iCreator:this.$store.state.rName,
                            iChangeperson:this.$store.state.rName,
                            iNo:this.issueform.iNo,
                            iIssuestate:this.issueform.iIssuestate,
                        }).then(function(res){
                                // console.log(res.data.list);
                                this.tableData.splice(0,this.tableData.length);
                                res.data.list.forEach(element=>{
                                    // console.log(element);
                                    this.tableData.push(element);
                                })
                        });
                    }else if (this.issueform.iCreator!==''||this.issueform.iChangeperson!=='') {
                        console.log("search icreator:"+this.issueform.iCreator);
                        console.log("search ichanger:"+this.issueform.iChangeperson);
                        this.$http.post('http://localhost:8080/issue/selectallSelectivePage/'+currentPage,{
                            iCreator:this.issueform.iCreator,
                            iChangeperson:this.issueform.iChangeperson,
                            iNo:this.issueform.iNo,
                            iIssuestate:this.issueform.iIssuestate,
                        }).then(function(res){
                                // console.log(res.data.list);
                                this.tableData.splice(0,this.tableData.length);
                                res.data.list.forEach(element=>{
                                    // console.log(element);
                                    this.tableData.push(element);
                                })
                        });
                    }
                }else{
                    this.$http.post('http://localhost:8080/issue/selectallPage/'+currentPage).
                        then(function(res){
                            // console.log(res.data.list);
                            this.tableData.splice(0,this.tableData.length);
                            res.data.list.forEach(element=>{
                                // console.log(element);
                                this.tableData.push(element);
                            })
                    });
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
            // 标记状态
            tableRowClassName({row}) {
                // console.log(row.issue_state);
                if (row.iIssuestate == '待验证') {
                return 'warning-row';
                } else if (row.iIssuestate == '关闭') {
                return 'success-row';
                } else if (row.iIssuestate == '退回') {
                return 'danger-row';
                }else if(row.iIssuestate == '待修改'){
                    return 'changeable-row'
                }
                return '';
            },
            // 序号生成
            indexMethod(index) {
                return index ++;
            },
            submitForm() {
                this.page_num=1;
                console.log("date1"+this.issueform.iCdate[0]);
                console.log("date1"+this.issueform.iCdate[1]);
                this.tableData.splice(0,this.tableData.length);
                if (this.$store.state.rUserid=='普通用户') {
                    if (this.issueform.iCreator==''&&this.issueform.iChangeperson==''&&this.issueform.iNo==''&&this.issueform.iIssuestate=='') {
                        this.$http.post('http://localhost:8080/issue/selectallbynamePage/'+this.$store.state.rName+'/'+1).
                            then(function(res){
                                // console.log(res.data.list);
                                this.tableData.splice(0,this.tableData.length);
                                res.data.list.forEach(element=>{
                                    // console.log(element);
                                    this.tableData.push(element);
                                })
                        });
                        this.$http.post('http://localhost:8080/issue/selectallselectiveint',{
                            iCreator:this.$store.state.rName,
                            iChangeperson:this.$store.state.rName,
                        }).then(function (resp) {
                            console.log("body:"+resp.body);
                            console.log("lenght:"+resp.body.length);
                            this.total_data_num = resp.body;
                        })
                    }else if (this.issueform.iCreator==''&&this.issueform.iChangeperson=='') {
                        this.$http.post('http://localhost:8080/issue/selectallSelectivePage/'+1,{
                            iCreator:this.$store.state.rName,
                            iChangeperson:this.$store.state.rName,
                            iNo:this.issueform.iNo,
                            iIssuestate:this.issueform.iIssuestate,
                        }).then(function(res){
                                // console.log(res.data.list);
                                this.tableData.splice(0,this.tableData.length);
                                res.data.list.forEach(element=>{
                                    // console.log(element);
                                    this.tableData.push(element);
                                })
                        });
                        this.$http.post('http://localhost:8080/issue/selectallselectiveint',{
                            iCreator:this.$store.state.rName,
                            iChangeperson:this.$store.state.rName,
                            iNo:this.issueform.iNo,
                            iIssuestate:this.issueform.iIssuestate,
                        }).then(function (resp) {
                            console.log("body:"+resp.body);
                            console.log("lenght:"+resp.body.length);
                            this.total_data_num = resp.body;
                        })
                    }else if (this.issueform.iCreator!==''&&this.issueform.iChangeperson=='') {
                        console.log("search icreator:"+this.issueform.iCreator);
                        console.log("search ichanger:"+this.issueform.iChangeperson);
                        this.$http.post('http://localhost:8080/issue/selectallSelectivePage/'+1,{
                            iCreator:this.issueform.iCreator,
                            iChangeperson:'',
                            iNo:this.issueform.iNo,
                            iIssuestate:this.issueform.iIssuestate,
                        }).then(function(res){
                                // console.log(res.data.list);
                                this.tableData.splice(0,this.tableData.length);
                                res.data.list.forEach(element=>{
                                    // console.log(element);
                                    this.tableData.push(element);
                                })
                        });
                        this.$http.post('http://localhost:8080/issue/selectallselectiveint',{
                            iCreator:this.issueform.iCreator,
                            iChangeperson:'',
                            iNo:this.issueform.iNo,
                            iIssuestate:this.issueform.iIssuestate,
                        }).then(function (resp) {
                            console.log("body:"+resp.body);
                            console.log("lenght:"+resp.body.length);
                            this.total_data_num = resp.body;
                        })
                    }else if (this.issueform.iCreator==''&&this.issueform.iChangeperson!=='') {
                        console.log("search icreator:"+this.issueform.iCreator);
                        console.log("search ichanger:"+this.issueform.iChangeperson);
                        this.$http.post('http://localhost:8080/issue/selectallSelectivePage/'+1,{
                            iCreator:'',
                            iChangeperson:this.issueform.iChangeperson,
                            iNo:this.issueform.iNo,
                            iIssuestate:this.issueform.iIssuestate,
                        }).then(function(res){
                                // console.log(res.data.list);
                                this.tableData.splice(0,this.tableData.length);
                                res.data.list.forEach(element=>{
                                    // console.log(element);
                                    this.tableData.push(element);
                                })
                        });
                        this.$http.post('http://localhost:8080/issue/selectallSelective',{
                                    iCreator:this.issueform.iCreator,
                                    iNo:this.issueform.iNo,
                                    iIssuestate:this.issueform.iIssuestate,
                                    iChangeperson:this.issueform.iChangeperson,
                                }).then(function (res) {
                                    this.total_data_num = res.body.length;
                                })
                    }else{
                        this.$http.post('http://localhost:8080/issue/selectallSelectivePage/'+1,{
                            iCreator:this.issueform.iCreator,
                            iChangeperson:this.issueform.iChangeperson,
                            iNo:this.issueform.iNo,
                            iIssuestate:this.issueform.iIssuestate,
                        }).then(function(res){
                                // console.log(res.data.list);
                                this.tableData.splice(0,this.tableData.length);
                                res.data.list.forEach(element=>{
                                    // console.log(element);
                                    this.tableData.push(element);
                                })
                        });
                        this.$http.post('http://localhost:8080/issue/selectallSelective',{
                            iCreator:this.issueform.iCreator,
                            iNo:this.issueform.iNo,
                            iIssuestate:this.issueform.iIssuestate,
                            iChangeperson:this.issueform.iChangeperson,
                        }).then(function (res) {
                            this.total_data_num = res.body.length;
                        })
                    }
                }
            },
            reset(){
                this.tableData.splice(0,this.tableData.length);
                this.issueform.iCreator='';
                this.issueform.iChangeperson='';
                this.issueform.iNo='';
                this.issueform.iCdate= '';
                this.issueform.iFinishtime='',
                this.issueform.iIssuestate='';
                if(this.$store.state.rUserid=='经理'){
                    this.rUserid_flg=false;
                    this.$http.get('http://localhost:8080/issue/selectall').
                    then(function(res){
                        this.msg = res.body;
                        for (let index = 0; index < 4; index++) {
                            const element = this.msg[index];
                            this.tableData.push(element);
                        }
                        this.total_data_num = res.body.length;
                    }).catch(function(error){
                        console.log(error);
                    })
                }else if (this.$store.state.rUserid=='普通用户') {
                    this.rUserid_flg=true;
                    this.$http.get('http://localhost:8080/issue/selectall').
                    then(function(res){
                        // console.log(this.msg.length);
                        this.msg = res.body;
                        var num = 0;
                        for (let index = 0; index < this.msg.length; index++) {
                            const element = this.msg[index];
                            if (element.iChangeperson==this.$store.state.rName || element.iCreator==(this.$store.state.rId+this.$store.state.rName)){
                                this.tableData.push(element);
                                num++;
                            }if (num >= 4) {
                                break;
                            }
                        };
                        var num2=0;
                        for (let index = 0; index < this.msg.length; index++) {
                            const element = this.msg[index];
                            if (element.iChangeperson==this.$store.state.rName || element.iCreator==(this.$store.state.rId+this.$store.state.rName)){
                                num2++;
                            }
                        };
                        this.total_data_num = num2;
                    }).catch(function(error){
                        console.log(error);
                    })
                }
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
    .el-table .changeable-row{
        background: #cfe7ff;
    }
    .issue_table{
        margin: 20px auto;
    }
</style>