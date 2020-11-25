<template>
    <div class="container">
        <div>
            <div class="titlea col-md-12">
                <h5 class="text-left"><b>Issue查询</b></h5>
                <el-divider></el-divider>
            </div>
            <div class="issue_menu col-md-9">
                <el-form :model="issueform" :rules="rules" ref="issueform" class="row">
                    <el-form-item label="Issue ID" prop="iNo" class="col-md-3" >
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
                push_index:0,
                data_list:[],
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
                    iFinishtime: [],
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
            //确定进入列表的操作模式
            console.log("temporary name:"+this.$store.state.temporary_name);
            console.log("temporary identity:"+this.$store.state.identity);
            if (this.$store.state.temporary_name!==undefined&&this.$store.state.identity!==undefined) {
                console.log("name:"+this.$store.temporary_name);
                this.$http.post('http://localhost:8080/issue/selectallSelective',{
                    iCreator:this.$store.temporary_name,
                    iChangeperson:'',
                    iNo:'',
                    iIssuestate:'',
                }).then(function (res) {
                    res.body.forEach(element=>{
                        this.data_list.push(element)
                    })
                }).then(
                    function () {
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
                    }
                )
            }else{
                if(this.$store.state.rUserid=='经理'){
                    this.rUserid_flg=false;
                    this.$http.get('http://localhost:8080/issue/selectall').
                    then(function(res){
                        this.msg = res.body;
                        for (let index = 0; index < 4; index++) {
                            const element = this.msg[index];
                            this.tableData.push(element);
                        }
                        this.msg.forEach(item=>{
                            this.data_list.push(item);
                        })
                        this.total_data_num = res.body.length;
                        console.log("!!length:"+this.data_list.length);
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
                                this.data_list.push(element);
                            }
                        };
                        console.log("!!length:"+this.data_list.length);
                        this.total_data_num = num2;
                    }).catch(function(error){
                        console.log(error);
                    })
                }
            }
        },
        methods: {
            handleCurrentChange(currentPage){
            this.push_index=(currentPage-1)*4;
            // this.currentPage = currentPage;
            // console.log(currentPage);
                if (this.$store.state.rUserid=='普通用户') {
                    console.log("length:"+this.data_list.length);
                    console.log("page num:"+currentPage);
                    console.log("long:"+this.data_list.length%4);
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
                }else{
                    console.log("length:"+this.data_list.length);
                    console.log("page num:"+currentPage);
                    console.log("long:"+this.data_list.length%4);
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
                return index = index + 1;
            },
            submitForm() {
                // console.log("data list:"+this.data_list);
                this.data_list.splice(0,this.data_list.length);
                this.page_num=1;
                // console.log("date1："+this.issueform.iFinishtime[0].getMonth());
                // console.log("date2："+this.issueform.iFinishtime[1].getMonth());
                // console.log("state:"+new Date(this.issueform.iFinishtime).getFullYear());
                this.$http.post('http://localhost:8080/issue/selectallSelective',{
                    iCreator:this.issueform.iCreator,
                    iChangeperson:this.issueform.iChangeperson,
                    iNo:this.issueform.iNo,
                    iIssuestate:this.issueform.iIssuestate,
                }).then(function(res){
                        this.tableData.splice(0,this.tableData.length);
                        // console.log(res.body);
                        if (this.$store.state.rUserid=='普通用户') {
                            //数据过滤
                            res.body.forEach(element=>{
                                if (element.iCreator==this.$store.state.rId+this.$store.state.rName||element.iChangeperson==this.$store.state.rName) { 
                                    if ((this.issueform.iCdate[0]!==undefined&&this.issueform.iCdate[1]!==undefined)&&(this.issueform.iFinishtime[0]==undefined&&this.issueform.iFinishtime[1]==undefined)) {
                                        console.log("error!");
                                        //先判断年份
                                        if (new Date(element.iCdate).getFullYear()>=this.issueform.iCdate[0].getFullYear()&&new Date(element.iCdate).getFullYear()<=this.issueform.iCdate[1].getFullYear()) {
                                            //再判断月份
                                            if (new Date(element.iCdate).getMonth()>=this.issueform.iCdate[0].getMonth()&&new Date(element.iCdate).getMonth()<=this.issueform.iCdate[1].getMonth()) {
                                                //最后判断日期
                                                if (new Date(element.iCdate).getDate()>=this.issueform.iCdate[0].getDate()&&new Date(element.iCdate).getDate()<=this.issueform.iCdate[1].getDate()) {
                                                    this.data_list.push(element);
                                                }
                                            }
                                        }
                                    }
                                    else if ((this.issueform.iCdate[0]==undefined&&this.issueform.iCdate[1]==undefined)&&(this.issueform.iFinishtime[0]!==undefined&&this.issueform.iFinishtime[1]!==undefined)&&element.iFinishtime!==''){
                                        console.log("error!:"+element.iNo);
                                        if (new Date(element.iFinishtime).getFullYear()>=this.issueform.iFinishtime[0].getFullYear()&&new Date(element.iFinishtime).getFullYear()<=this.issueform.iFinishtime[1].getFullYear()) {
                                            //再判断月份
                                            if (new Date(element.iFinishtime).getMonth()>=this.issueform.iFinishtime[0].getMonth()&&new Date(element.iFinishtime).getMonth()<=this.issueform.iFinishtime[1].getMonth()) {
                                                //最后判断日期
                                                if (new Date(element.iFinishtime).getDate()>=this.issueform.iFinishtime[0].getDate()&&new Date(element.iFinishtime).getDate()<=this.issueform.iFinishtime[1].getDate()) {
                                                    this.data_list.push(element);
                                                }
                                            }
                                        }
                                    }
                                    else if ((this.issueform.iFinishtime[0]!==undefined&&this.issueform.iFinishtime[1]!==undefined)&&((this.issueform.iCdate[0]!==undefined&&this.issueform.iCdate[1]!==undefined)&&element.iFinishtime!=='')) {
                                        if (new Date(element.iCdate).getFullYear()>=this.issueform.iCdate[0].getFullYear()&&new Date(element.iCdate).getFullYear()<=this.issueform.iCdate[1].getFullYear()
                                        &&new Date(element.iFinishtime).getFullYear()>=this.issueform.iFinishtime[0].getFullYear()&&new Date(element.iFinishtime).getFullYear()<=this.issueform.iFinishtime[1].getFullYear()) {
                                            //再判断月份
                                            if (new Date(element.iCdate).getMonth()>=this.issueform.iCdate[0].getMonth()&&new Date(element.iCdate).getMonth()<=this.issueform.iCdate[1].getMonth()
                                            &&new Date(element.iFinishtime).getMonth()>=this.issueform.iFinishtime[0].getMonth()&&new Date(element.iFinishtime).getMonth()<=this.issueform.iFinishtime[1].getMonth()) {
                                                //最后判断日期
                                                if (new Date(element.iCdate).getDate()>=this.issueform.iCdate[0].getDate()&&new Date(element.iCdate).getDate()<=this.issueform.iCdate[1].getDate()
                                                &&new Date(element.iFinishtime).getDate()>=this.issueform.iFinishtime[0].getDate()&&new Date(element.iFinishtime).getDate()<=this.issueform.iFinishtime[1].getDate()) {
                                                    this.data_list.push(element);
                                                }
                                            }
                                        }
                                    }
                                    else{
                                        this.data_list.push(element);
                                    }
                                }
                            })
                        }else{
                            res.body.forEach(element=>{
                                if ((this.issueform.iCdate[0]!==undefined&&this.issueform.iCdate[1]!==undefined)&&(this.issueform.iFinishtime[0]==undefined&&this.issueform.iFinishtime[1]==undefined)) {
                                        console.log("error!");
                                        //先判断年份
                                        if (new Date(element.iCdate).getFullYear()>=this.issueform.iCdate[0].getFullYear()&&new Date(element.iCdate).getFullYear()<=this.issueform.iCdate[1].getFullYear()) {
                                            // console.log(element.getFullYear());
                                            //再判断月份
                                            if (new Date(element.iCdate).getMonth()>=this.issueform.iCdate[0].getMonth()&&new Date(element.iCdate).getMonth()<=this.issueform.iCdate[1].getMonth()) {
                                                // console.log(element.getMonth());
                                                //最后判断日期
                                                if (new Date(element.iCdate).getDate()>=this.issueform.iCdate[0].getDate()&&new Date(element.iCdate).getDate()<=this.issueform.iCdate[1].getDate()) {
                                                    // console.log(element.getDate());
                                                    this.data_list.push(element);
                                                }
                                            }
                                        }
                                    }
                                    else if ((this.issueform.iCdate[0]==undefined&&this.issueform.iCdate[1]==undefined)&&(this.issueform.iFinishtime[0]!==undefined&&this.issueform.iFinishtime[1]!==undefined)&&element.iFinishtime!==''){
                                        console.log("error!:"+element.iNo);
                                        if (new Date(element.iFinishtime).getFullYear()>=this.issueform.iFinishtime[0].getFullYear()&&new Date(element.iFinishtime).getFullYear()<=this.issueform.iFinishtime[1].getFullYear()) {
                                            //再判断月份
                                            if (new Date(element.iFinishtime).getMonth()>=this.issueform.iFinishtime[0].getMonth()&&new Date(element.iFinishtime).getMonth()<=this.issueform.iFinishtime[1].getMonth()) {
                                                //最后判断日期
                                                if (new Date(element.iFinishtime).getDate()>=this.issueform.iFinishtime[0].getDate()&&new Date(element.iFinishtime).getDate()<=this.issueform.iFinishtime[1].getDate()) {
                                                    this.data_list.push(element);
                                                }
                                            }
                                        }
                                    }
                                    else if ((this.issueform.iFinishtime[0]!==undefined&&this.issueform.iFinishtime[1]!==undefined)&&((this.issueform.iCdate[0]!==undefined&&this.issueform.iCdate[1]!==undefined)&&element.iFinishtime!=='')) {
                                        if (new Date(element.iCdate).getFullYear()>=this.issueform.iCdate[0].getFullYear()&&new Date(element.iCdate).getFullYear()<=this.issueform.iCdate[1].getFullYear()
                                        &&new Date(element.iFinishtime).getFullYear()>=this.issueform.iFinishtime[0].getFullYear()&&new Date(element.iFinishtime).getFullYear()<=this.issueform.iFinishtime[1].getFullYear()) {
                                            //再判断月份
                                            if (new Date(element.iCdate).getMonth()>=this.issueform.iCdate[0].getMonth()&&new Date(element.iCdate).getMonth()<=this.issueform.iCdate[1].getMonth()
                                            &&new Date(element.iFinishtime).getMonth()>=this.issueform.iFinishtime[0].getMonth()&&new Date(element.iFinishtime).getMonth()<=this.issueform.iFinishtime[1].getMonth()) {
                                                //最后判断日期
                                                if (new Date(element.iCdate).getDate()>=this.issueform.iCdate[0].getDate()&&new Date(element.iCdate).getDate()<=this.issueform.iCdate[1].getDate()
                                                &&new Date(element.iFinishtime).getDate()>=this.issueform.iFinishtime[0].getDate()&&new Date(element.iFinishtime).getDate()<=this.issueform.iFinishtime[1].getDate()) {
                                                    this.data_list.push(element);
                                                }
                                            }
                                        }
                                    }
                                    else{
                                        this.data_list.push(element);
                                    }
                            })
                        }
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
                        // console.log("data list length:"+this.data_list.length);
                        this.total_data_num=this.data_list.length;
                });
            },
            reset(){
                this.page_num=1;
                this.tableData.splice(0,this.tableData.length);
                this.issueform.iCreator='';
                this.issueform.iNo='';
                this.issueform.iCdate= '';
                this.issueform.iFinishtime='',
                this.issueform.iChangeperson='',
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
                    this.msg.forEach(item=>{
                        this.data_list.push(item);
                    })
                    this.total_data_num = res.body.length;
                    console.log("!!length:"+this.data_list.length);
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
                            this.data_list.push(element);
                        }
                    };
                    console.log("!!length:"+this.data_list.length);
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