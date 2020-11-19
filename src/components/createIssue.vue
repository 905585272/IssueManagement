<template>
<div>
    <div class="container">
        <div class="col-md-10 center-block page_title">
            <form class="bs-example bs-example-form form-horizontal row" role="form">
                <label for="title" class="col-sm-2 control-label"><h4>Issue题目</h4></label>
                <div class="input-group input-group-lg col-md-8">
                    <input type="text" class="form-control" id="title" placeholder="请输入题目">
                </div>
            </form>
        </div>
    </div>
    <div class="container body">
        <div class="col-md-12">
            <el-form ref="issue_form" :model="issue_form" class="col-md-12 row">
                <el-form-item label="Issue No" class="col-md-3">
                    <el-input v-model="issue_form.no" ReadOnly></el-input>
                </el-form-item>
                <el-form-item label="创建时间" class="col-md-3">
                    <el-input v-model="issue_form.create_date" ReadOnly></el-input>
                </el-form-item>
                <el-form-item label="Issue类型" class="col-md-3">
                    <el-input v-model="issue_form.issue_type"></el-input>
                </el-form-item>
                <el-form-item label="Issue等级" class="col-md-3">
                    <el-select v-model="issue_form.level" placeholder="请选择Issue等级">
                        <el-option label="最高" value="highest" name="level"></el-option>
                        <el-option label="较高" value="high" name="level"></el-option>
                        <el-option label="一般" value="common" name="level"></el-option>
                        <el-option label="低" value="low" name="level"></el-option>
                    </el-select>
                </el-form-item>
            </el-form>
            <el-form ref="issue_form" :model="issue_form" class="col-md-12 row">
                <el-form-item label="影响版本" class="col-md-3">
                    <el-input v-model="issue_form.verson"></el-input>
                </el-form-item>
                <el-form-item label="计划修改时间" class="col-md-3">
                    <el-date-picker type="date" placeholder="选择日期" v-model="issue_form.change_date"></el-date-picker>
                </el-form-item>
                <el-form-item label="实际完成时间" class="col-md-3">
                    <el-input v-model="issue_form.finish_date" ReadOnly></el-input>
                </el-form-item>
            </el-form>
            <el-form ref="issue_form" :model="issue_form" class="col-md-12 step">
                <el-form-item label="重现步骤" >
                    <el-input type="textarea" v-model="issue_form.repro_steps" class="col-md-10"></el-input>
                </el-form-item>
            </el-form>
             <el-form ref="issue_form" :model="issue_form" class="col-md-12 row">
                <el-form-item label="指派修改人" class="col-md-4">
                    <el-input v-model="issue_form.chaneg_man"></el-input>
                </el-form-item>
            </el-form>
            <el-form ref="issue_form" :model="issue_form" class="col-md-12 row">
                <el-form-item class="col-md-12">
                    <el-button type="primary" @click="submitForm()">提交</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</div>
</template>

<style>
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
        issue_form: {
            no: '',
            //Math.floor(Math.random() * 10000)
            create_date: new Date().getFullYear()+"/"+new Date().getMonth()+"/"+new Date().getDate(),
            issue_type: '',
            level: [],
            verson:'',
            change_date:'',
            finish_date: '',
            repro_steps: '',
            chaneg_man:''
        }
      }
    },
    methods: {
      submitForm() {
        console.log('submit!');
        var date=new Date().getFullYear()+"/"+new Date().getMonth()+"/"+new Date().getDate()
        this.issue_form.finish_date=date;
      }
    }
  }
</script>