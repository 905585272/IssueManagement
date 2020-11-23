<template>
    <div class="container">
        <div class="query_title col-md-12">
            <h5 class="text-left"><b>查询条件</b></h5>
            <el-divider></el-divider>
        </div>
        <div class="query_content col-md-9">
            <el-form :model="issueform" :rules="rules" ref="issueform" class="row">
                <el-form-item label="用户 ID" class="col-md-3" >
                    <el-input v-model="issueform.rId"></el-input>
                </el-form-item>
                <el-form-item label="用户姓名" class="col-md-3" >
                    <el-input v-model="issueform.rName"></el-input>
                </el-form-item>
                <el-form-item class="issue_button">
                    <el-button type="primary" @click="submitForm()">查询</el-button>
                    <el-button type="button" @click="reset()">清空</el-button>
                    <el-button type="button" @click="goback()">返回</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script>
export default {
    data(){
        return{
            issueform:{
                rId:'',
            }
        }
    }
}
</script>

<style scoped>

</style>