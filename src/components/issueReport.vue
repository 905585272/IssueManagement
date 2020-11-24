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
                    <template slot-scope="scope">
                        <router-link to="/issueList" @click="turnto_issueList(scope.row)">
                            {{scope.row.rCissue}}
                        </router-link>
                    </template>
                </el-table-column>
                <el-table-column prop="rRissue" label="收到Issue数">
                    <template slot-scope="scope">
                        <router-link to="/issueList" @click="turnto_issueList(scope.row)">
                            {{scope.row.rRissue}}
                        </router-link>
                    </template>
                </el-table-column>
                <el-table-column prop="rMissue" label="修改Issue数">
                    <template slot-scope="scope">
                        <router-link to="/issueList" @click="turnto_issueList(scope.row)">
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
            // this.$http.get('http://localhost:8080/user/selectall').
            // then(function(res){
            //     this.msg = res.body;
            //     console.log(this.msg);
            //     this.msg.forEach(item=>{
            //         if(item.rUserid != 'admin'){
            //             this.tableData.push(item);
            //         }
            //     });
            // }).catch(function(error){
            //     console.log(error);
            // })
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
        goback(){
                this.$router.go(-1);
        },
        reset(){
                this.tableData.splice(0,this.tableData.length);
                this.issueform.rId='';
                this.issueform.rName='';
                this.$http.get('http://localhost:8080/user/selectall').
                then(function(res){
                    this.msg = res.body;
                    console.log(this.msg);
                    this.msg.forEach(item=>{
                        if(item.rUserid != 'admin'){
                            this.tableData.push(item);
                        }
                    });
                }).catch(function(error){
                    console.log(error);
                })  
        },
        tableRowClassName({row}) {
                if(row.rMissue === 0 || row.rRissue === 0){
                    // console.log('row.rMissue:' + row.rMissue);
                    // console.log('row.rRissue:' + row.rRissue);
                    return row.iSuccess = 0 + '%';
                }else{
                    console.log('row.rMissue:' + row.rMissue);
                    console.log('row.rRissue:' + row.rRissue);
                    console.log('row.iSuccess:' + (row.rMissue / row.rRissue));
                    return row.iSuccess = ((row.rMissue / row.rRissue)*100).toFixed(0) + '%';
                }
        },
        turnto_issueList(row){
                this.$store.state.rCissue=row.rCissue;
                this.$store.state.rRissue=row.rRissue;
                this.$store.state.rMissue=row.rMissue;
                this.$store.state.rId = row.rId;
                this.$store.state.rName = row.rName;
                // this.$store.state.iCdate=row.iCdate;
                // this.$store.state.iType=row.iType;
                // this.$store.state.iLevel=row.iLevel;
                // this.$store.state.iVesion=row.iVesion;
                // this.$store.state.iPlantime=row.iPlantime;
                // this.$store.state.iFinishtime=row.iFinishtime;
                // this.$store.state.iReappear=row.iReappear;
                // this.$store.state.iChangeperson=row.iChangeperson;
                // this.$store.state.iHandlemethod=row.iHandlemethod;
                // this.$store.state.iIssuestate=row.iIssuestate;
        },
        submitForm() {
            this.$http.post('http://localhost:8080/user/selectAllSelectivepage/1',{
            rId:this.issueform.rId,
            rName:this.issueform.rName,
            }).then(function (userdata) {
                this.tableData.splice(0,this.tableData.length);
                console.log(userdata.body.list);
                userdata.body.list.forEach(element=>{
                    this.tableData.push(element);
                })
            })
        },
    }
}
</script>

<style scoped>

</style>