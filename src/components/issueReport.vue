<template>
    <div class="container">
        <div class="query_title col-md-12">
            <h5 class="text-left"><b>查询条件</b></h5>
            <el-divider></el-divider>
        </div>
        <div class="query_content col-md-10">
            <el-form :model="issueform" :rules="rules" ref="issueform" class="row">
                <el-form-item label="用户 ID" class="col-md-5" >
                    <el-input v-model="issueform.rId"></el-input>
                </el-form-item>
                <el-form-item label="用户姓名" class="col-md-5" >
                    <el-input v-model="issueform.rName"></el-input>
                </el-form-item>
                <el-form-item class="issue_button">
                    <el-button type="primary" @click="submitForm()">查询</el-button>
                    <el-button type="button" @click="reset()">清空</el-button>
                    <el-button type="button" @click="goback()">返回</el-button>
                </el-form-item>
            </el-form>
        </div>
        <div class="report_title col-md-12"> 
            <h5 class="text-left"><b>统计报表</b></h5>
            <el-divider></el-divider>
        </div>
        <div class="report_content col-md-12">
            <el-table :row-class-name="tableRowClassName" :data="tableData" stripe border style="width:100%" :default-sort = "{prop: 'create_date', order: 'descending'}">
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
            @current-change="handleCurrentChange">
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
                this.total_data_num = res.data.length;
                for (let index = 0; index < 4; index++) {
                    const element = res.body[index];
                    if(element.rUserid != 'admin'){
                        this.tableData.push(element);
                    }
                }
            }).catch(function(error){
                console.log(error);
            });
    },
    methods:{
        handleCurrentChange(currentPage){
            this.$http.get('http://localhost:8080/user/selectallPage/'+currentPage).
                then(function(res){
                    // console.log(res.data.list);
                    this.tableData.splice(0,this.tableData.length);
                    res.data.list.forEach(element=>{
                        // console.log(element);
                        if (element.rId !== 'admin') {
                            this.tableData.push(element); 
                        }
                    })
            });
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
                        if(element.rUserid != 'admin'){
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

</style>