<template>
    <div class="container col-md-12" id="big_body">
        <div class="query_title col-md-8">
            <h5 class="text-left"><b>查询条件</b></h5>
            <el-divider></el-divider>
        </div>
        <div class="query_content col-md-6">
            <el-form :model="issueform" :rules="rules" ref="issueform" class="row">
                <el-form-item label="用户 ID" prop="rId" class="col-md-6" >
                    <el-input v-model="issueform.rId" class="col-md-11"></el-input>
                </el-form-item>
                <el-form-item label="用户姓名" prop="rName" class="col-md-5" >
                    <el-input v-model="issueform.rName"></el-input>
                </el-form-item>
                <el-form-item class="issue_button">
                    <el-button type="primary" @click="submitForm()">查询</el-button>
                    <el-button type="button" @click="reset()">清空</el-button>
                    <el-button type="button" @click="goback()">返回</el-button>
                </el-form-item>
            </el-form>
        </div>
        <div class="col-md-12" style="height:5vh"></div>
        <div class="report_title col-md-8"> 
            <h5 class="text-left"><b>统计报表</b></h5>
            <el-divider></el-divider>
        </div>
        <div class="col-md-12" style="height:5vh"></div>
        <div class="report_content col-md-8">
            <el-table class="issue_table" :row-class-name="tableRowClassName" :data="tableData" stripe border style="width:100%" :default-sort="{prop: 'create_date', order: 'descending'}">
                <el-table-column type="index" label="序号" :index="indexMethod"></el-table-column>
                <el-table-column prop="rId" label="用户ID"></el-table-column>
                <el-table-column prop="rName" label="用户姓名"></el-table-column>
                <el-table-column prop="rCissue" label="创建Issue数">
                    <template slot-scope="scope" type="text" >
                        <router-link @click.native="creator_turnto_issueList(scope.row)" to="">
                            {{scope.row.rCissue}}
                        </router-link>
                    </template>
                </el-table-column>
                <el-table-column prop="rRissue" label="收到Issue数">
                    <template slot-scope="scope">
                        <router-link @click.native="receive_turnto_issueList(scope.row)" to="">
                            {{scope.row.rRissue}}
                        </router-link>
                    </template>
                </el-table-column>
                <el-table-column prop="rMissue" label="修改Issue数">
                    <template slot-scope="scope">
                        <router-link @click.native="change_turnto_issueList(scope.row)" to="">
                            {{scope.row.rMissue}}
                        </router-link>
                    </template>
                </el-table-column>
                <el-table-column prop="iSuccess" label="完成率"></el-table-column>
            </el-table>
            <el-pagination
            background :page-size="4" :pager-count="11" layout="prev, pager, next" :total="total_data_num"
            @current-change="handleCurrentChange" :current-page.sync="page_num">
            </el-pagination>
        </div>
    </div>
</template>

<script>
export default {
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
            issueform:{
                rId:'',
                rName:''
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
    mounted() {
            this.$http.get('http://localhost:8080/user/selectall').
            then(function(res){
                res.data.forEach(element=>{
                    if(element.rUserid != 'Admin'){
                       this.data_list.push(element);
                    }
                })
                this.total_data_num = this.data_list.length;
                for (let index = 0; index < 4; index++) {
                    const element = this.data_list[index];
                    this.tableData.push(element);
                }
            }).catch(function(error){
                console.log(error);
            });
    },
    methods:{
        handleCurrentChange(currentPage){
            this.push_index=(currentPage-1)*4;
            this.tableData.splice(0,this.tableData.length);
            for (let index = 0; index < 4; index++) {
                // console.log("page:"+index);
                if (this.push_index<this.data_list.length) {
                    const element = this.data_list[this.push_index++];
                    // console.log("page:"+element);
                    this.tableData.push(element);
                }else{
                    break;
                }
            }
        },
        // 序号生成
        indexMethod(index) {
            return index = index + 1;
        },
        //返回功能
        goback(){
                this.$router.go(-1);
        },
        //清空设置
        reset(){
            this.page_num=1;
            this.data_list.splice(0,this.data_list.length);
            this.tableData.splice(0,this.tableData.length);
            this.issueform.rId='';
            this.issueform.rName='';
            this.$http.get('http://localhost:8080/user/selectall').
            then(function(res){
                this.total_data_num = res.data.length;
                for (let index = 0; index < 4; index++) {
                    const element = res.body[index];
                    console.log("element:"+element.rName);
                    this.tableData.push(element);
                }
                res.body.forEach(element=>{
                    if(element.rUserid != 'Admin'){
                        this.data_list.push(element);
                    }
                })
            }).catch(function(error){
                console.log(error);
            });
        },
        tableRowClassName({row}) {
                if(row.rMissue === 0 || row.rRissue === 0){
                    return row.iSuccess = 0 + '%';
                }else{
                    // console.log('row.rMissue:' + row.rMissue);
                    // console.log('row.rRissue:' + row.rRissue);
                    // console.log('row.iSuccess:' + (row.rMissue / row.rRissue));
                    return row.iSuccess = ((row.rMissue / row.rRissue)*100).toFixed(0) + '%';
                }
        },
        //报表的跳转方法
        creator_turnto_issueList(row){
            this.$store.state.temporary_name = row.rName,
            this.$store.state.identity='creator',
            this.$router.push({path:'/issueList'})
        },
        receive_turnto_issueList(row){
            this.$store.state.temporary_name = row.rName,
            this.$store.state.identity='receiver',
            this.$router.push({path:'/issueList'})
        },
        change_turnto_issueList(row){
            console.log('change!');
            this.$store.state.temporary_name = row.rName,
            this.$store.state.identity='changer',
            this.$router.push({path:'/issueList'})
        },
        submitForm() {
            this.data_list.splice(0,this.data_list.length);
            this.page_num=1;
            this.$http.post('http://localhost:8080/user/selectallSelective',{
            rId:this.issueform.rId,
            rName:this.issueform.rName,
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
    }
}
</script>

<style scoped>
    #big_body{
        height: 100vh;
        background-image: url(https://img.ivsky.com/img/tupian/pic/202004/20/luori-006.jpg);
        background-size:cover;
        -o-background-size: cover;
        background-repeat: no-repeat;
    }
    .query_content{
        background-color: rgba(255, 255, 255, 0.6);
        box-shadow: 0 5px 5px rgba(0, 0, 0, .5), 0 0 6px rgba(0, 0, 0, .5);
        margin: 20px auto;
        border-radius: 10px;
    }
    .report_content{
        margin: 20px auto;
    }
    .el-table{
        border-radius: 15px;
        opacity: 0.8;
    }
    .query_title{
        color: #d3d3d3;
        margin: 0 auto;
    }
    .report_title{
        color: #d3d3d3;
        margin: 20px auto;
    }
    .issue_table{
        margin: 20px auto;
        border-radius: 15px;
    }
</style>