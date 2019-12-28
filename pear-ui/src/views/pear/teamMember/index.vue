<template>
  <div class="app-container">
    <el-row :gutter="10">
      <!-- 团队 -->
      <el-col :span="5" :xs="24">
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAddTeam">新建团队</el-button>
          </el-col>
        </el-row>
        <el-table
          v-loading="loading"
          :data="teamList"
          highlight-current-row
          @current-change="handleSelectRow"
        >
          <el-table-column label="团队" align="left" prop="name" />
        </el-table>
        <pagination
          :hide-on-single-page="true"
          v-show="teamTotal>0"
          :total="teamTotal"
          :page.sync="teamQueryParms.pageNum"
          :limit.sync="teamQueryParms.pageSize"
          @pagination="getTeamList"
        />
      </el-col>

      <!-- 成员 -->
      <el-col :span="19" :xs="24">
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAddMember">添加成员</el-button>
          </el-col>
        </el-row>

        <el-table v-loading="loading" :data="memberList" stripe>
          <el-table-column label="工号 " align="left" prop="code" />
          <el-table-column label="姓名" align="left">
            <template slot-scope="scope">
              <span>{{scope.row.name}}</span>
              <span v-if="scope.row.isLeader">(组长)</span>
            </template>
          </el-table-column>
          <el-table-column label="邮箱" align="left" prop="email" />
          <el-table-column label="状态" align="left" prop="status" />
          <el-table-column label="创建日期" align="left" prop="createdTime" width="160">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.createdTime, '{y}-{m}-{d}') }}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="操作"
            align="center"
            width="180"
            class-name="small-padding fixed-width"
          >
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="handleUpdate(scope.row)"
              >修改</el-button>
              <el-button
                v-if="scope.row.userId !== 1"
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="handleDelete(scope.row)"
              >删除</el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-key"
                @click="handleResetPwd(scope.row)"
              >重置</el-button>
            </template>
          </el-table-column>
        </el-table>

        <pagination
          v-show="memberTotal>0"
          :total="memberTotal"
          :page.sync="memberQueryParams.pageNum"
          :limit.sync="memberQueryParams.pageSize"
          @pagination="getMemberList"
        />
      </el-col>
    </el-row>

    <el-dialog :title="title" :visible.sync="teamOpen" width="30%">
      <el-form ref="teamForm" :model="teamForm" :rules="teamRules">
        <el-form-item label="团队名称" prop="name">
          <el-input v-model="teamForm.name" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitTeamForm">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog :title="title" :visible.sync="memberOpen">
      <el-form ref="memberForm" :model="memberForm" :rules="memberRules"></el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitMemberForm">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listTeam, listMember, addTeam } from "@/api/pear/teamMember";

export default {
  data() {
    return {
      // 遮罩层
      loading: true,
      // 总条数
      teamTotal: 0,
      memberTotal: 0,
      teamList: [],
      memberList: [],
      // 查询参数
      teamQueryParms: {
        pageNum: 1,
        pageSize: 20
      },
      memberQueryParams: {
        pageNum: 1,
        pageSize: 10,
        teamId: null
      },
      title: "",
      teamOpen: false,
      memberOpen: false,
      teamForm: {},
      memberForm: {},
      teamRules: {
        name: [{ required: true, message: "团队名称不能为空", trigger: "blur" }]
      },
      memberRules: {}
    };
  },
  created() {
    this.getTeamList();
  },
  methods: {
    getTeamList() {
      this.loading = true;
      listTeam(this.teamQueryParms).then(response => {
        this.teamList = response.rows;
        this.teamTotal = response.total;
        this.loading = false;
      });
    },
    handleSelectRow(row) {
      this.memberQueryParams.teamId = row.id;
      this.getMemberList();
    },
    getMemberList() {
      this.loading = true;
      listMember(this.memberQueryParams).then(response => {
        this.memberList = response.rows;
        this.memberTotal = response.total;
        this.loading = false;
      });
    },
    handleAddMember() {
      this.memberOpen = true;
      this.title = "添加成员";
    },
    submitTeamForm() {
      this.$refs["teamForm"].validate(valid => {
        if (valid) {
          if (this.teamForm.id != undefined) {
            // updateUser(this.form).then(response => {
            //   if (response.code === 200) {
            //     this.msgSuccess("修改成功");
            //     this.open = false;
            //     this.getList();
            //   } else {
            //     this.msgError(response.msg);
            //   }
            // });
          } else {
            addTeam(this.teamForm).then(response => {
              if (response.code === 200) {
                this.msgSuccess("创建成功");
                this.teamOpen = false;
                this.getTeamList();
              } else {
                this.msgError(response.msg);
              }
            });
          }
        }
      });
    },
    submitMemberForm() {},
    handleAddTeam() {
      this.teamOpen = true;
      this.title = "新建团队";
    }
  }
};
</script>