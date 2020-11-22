<template>
  <div class="container">
    <div>
      <div class="titlea col-md-12">
        <h5 class="text-left"><b>Issue查询</b></h5>
        <el-divider></el-divider>
      </div>
      <div class="issue_menu col-md-9">
        <el-form :model="issueform" :rules="rules" ref="issueform" class="row">
          <el-form-item label="Issue No" prop="iNo" class="col-md-3">
            <el-input v-model="issueform.iNo"></el-input>
          </el-form-item>
          <el-form-item label="Issue状态" class="col-md-3">
            <el-select v-model="issueform.iLevel" placeholder="请选择Issue状态">
              <el-option
                label="待验证"
                value="待验证"
                name="iIssuestate"
              ></el-option>
              <el-option
                label="关闭"
                value="关闭"
                name="iIssuestate"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="创建时间" class="col-md-6">
            <el-date-picker
              v-model="issueform.iCdate"
              type="daterange"
              unlink-panels
              range-separator="-"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
            >
            </el-date-picker>
          </el-form-item>
          <el-form-item label="创建人" class="col-md-3">
            <el-input v-model="issueform.iCreator"></el-input>
          </el-form-item>
          <el-form-item label="修改人" prop="iCreator" class="col-md-3">
            <el-input
              id="change_person"
              v-model="issueform.iChangeperson"
            ></el-input>
          </el-form-item>
          <el-form-item label="修改时间" class="col-md-6">
            <el-date-picker
              v-model="issueform.iCdate"
              type="daterange"
              unlink-panels
              range-separator="-"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
            >
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
    <div>
      <div class="titlea col-md-12">
        <h5 class="text-left"><b>Issue列表</b></h5>
        <el-divider></el-divider>
      </div>
      <el-table
        class="col-md-11 issue_table"
        :data="tableData"
        style="width: 100%"
        :row-class-name="tableRowClassName"
        :default-sort="{ prop: 'create_date', order: 'descending' }"
      >
        <!-- border -->
        <!-- stripe -->
        <el-table-column type="index" label="序号" :index="indexMethod">
        </el-table-column>
        <el-table-column prop="iNo" label="IssueID"> </el-table-column>
        <el-table-column prop="iTitle" label="Issue标题"> </el-table-column>
        <el-table-column prop="iCreator" label="创建人"> </el-table-column>
        <el-table-column prop="iCdate" label="创建时间"> </el-table-column>
        <el-table-column prop="iChangeperson" label="修改人"> </el-table-column>
        <el-table-column prop="iIssuestate" label="Issue状态">
        </el-table-column>
        <el-table-column prop="iPlantime" label="计划完成时间">
        </el-table-column>
        <el-table-column prop="iFinishtime" label="实际完成时间">
        </el-table-column>
        <el-table-column prop="action" label="操作">
          <template>
            <el-button type="text" size="small"> 详情 </el-button>
            <el-button type="text" size="small" @click="linkToChangeIssue">
              修改
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    var checkChinese = (rule, value, callback) => {
      if (value) {
        if (!/^[\u2E80-\u9FFF]+$/.test(value)) {
          callback(new Error("请输入汉字!"));
        } else {
          callback();
        }
      }
      callback();
    };
    return {
      issueform: {
        iCreator: "",
        iTitle: "",
        iNo: "",
        iCdate: "",
        iType: "",
        iLevel: "",
        iVesion: "",
        iPlantime: "",
        iFinishtime: "",
        iReappear: "",
        iChangeperson: this.$store.state.rName,
        iHandlemethod: "",
        iIssuestate: "",
      },
      tableData: [],
      rules: {
        iNo: [
          {
            min: 1,
            max: 30,
            message: "长度在 1 到 30 个字符",
            trigger: "blur",
          },
        ],
        iCreator: [
          {
            min: 1,
            max: 30,
            message: "长度在 1 到 30 个字符",
            trigger: "blur",
          },
          { validator: checkChinese, trigger: "blur" },
        ],
        iChangeperson: [
          {
            min: 1,
            max: 30,
            message: "长度在 1 到 30 个字符",
            trigger: "blur",
          },
          { validator: checkChinese, trigger: "blur" },
        ],
      },
    };
  },
  mounted() {
    if (this.$store.state.rUserid == "经理") {
      this.$http
        .get("http://localhost:8080/issue/selectall")
        .then(function (res) {
          this.msg = res.body;
          this.msg.forEach((item) => {
            this.tableData.push(item);
            console.log(item);
          });
        })
        .catch(function (error) {
          console.log(error);
        });
    } else if (this.$store.state.rUserid == "普通用户") {
      this.$http
        .get("http://localhost:8080/issue/selectall")
        .then(function (res) {
          // 更改修改人输入框是否只读
          document.getElementById("change_person").readOnly = true;

          this.msg = res.body;
          console.log(this.msg.length);
          this.msg = res.body;
          this.msg.forEach((item) => {
            if (item.iChangeperson == this.$store.state.rName) {
              this.tableData.push(item);
            }
          });
        })
        .catch(function (error) {
          console.log(error);
        });
    }
  },
  methods: {
    // 标记状态
    tableRowClassName({ row }) {
      // console.log(row.issue_state);
      if (row.issue_state == "待验证") {
        return "warning-row";
      } else if (row.issue_state == "关闭") {
        return "success-row";
      }
      return "";
    },
    // 序号生成
    indexMethod(index) {
      return index++;
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
    reset() {
      this.issueform.iCreator = "";
      this.issueform.iNo = "";
      this.issueform.iCdate = "";
      this.issueform.iFinishtime = "";
      this.issueform.iIssuestate = "";
    },
    goback() {
      this.$router.go(-1);
    },
    linkToChangeIssue() {
      this.$router.go("/changeissue");
    },
  },
};
</script>

<style>
.issue_menu {
  /* border-radius: 2px; */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
  margin: 20px auto;
}
.issue_button {
  margin: 20px auto;
}
.el-table .warning-row {
  background: oldlace;
}
.el-table .success-row {
  background: #f0f9eb;
}
.issue_table {
  margin: 20px auto;
}
</style>