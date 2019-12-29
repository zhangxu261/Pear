<template>
  <div class="app-container">
    <el-row :gutter="10">
      <!-- 团队 -->
      <el-col :span="5" :xs="24">
        <el-table
          v-loading="loading"
          :data="projectList"
          highlight-current-row
          @current-change="handleSelectRow"
        >
          <el-table-column label="项目" align="left">
            <template slot-scope="scope">
              <div>
                <b>{{ scope.row.code }} - {{ scope.row.name }}</b>
              </div>
              <div>
                <el-progress :percentage="scope.row.schedule" />
              </div>
            </template>
          </el-table-column>
        </el-table>
        <pagination
          :hide-on-single-page="true"
          v-show="total>0"
          :total="total"
          :page.sync="projectQueryParms.pageNum"
          :limit.sync="projectQueryParms.pageSize"
          @pagination="getProjectList"
        />
      </el-col>

      <!-- 任务 -->
      <el-col :span="19" :xs="24">
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button
              type="primary"
              icon="el-icon-plus"
              size="mini"
              v-show="taskQueryParams.projectId"
              @click="handleAddTask"
            >添加任务</el-button>
          </el-col>
        </el-row>

        <el-table
          v-loading="loading"
          :data="taskList"
          row-key="id"
          default-expand-all
          :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
        >
          <el-table-column label="任务主题" width="300">
            <template slot-scope="scope">
              <span>{{ scope.row.code }} - {{ scope.row.subject }}</span>
            </template>
          </el-table-column>
          <el-table-column label="预估/实际工时">
           <template slot-scope="scope">
               <sapn>{{scope.row.estimateTime}} / {{scope.row.actualTime}}</sapn>
           </template>
          </el-table-column>
          <el-table-column label="进度" align="left" width="150">
            <template slot-scope="scope">
              <el-progress :percentage="scope.row.schedule" />
            </template>
          </el-table-column>
          <el-table-column label="分派给" align="left">
              <template slot-scope="scope">
              <div>{{scope.row.realName}}</div>
              </template>
          </el-table-column>
          <el-table-column label="创建日期" align="left">
            <template slot-scope="scope">
              <div>{{parseTime(scope.row.createdTime, '{y}-{m}-{d}')}}</div>
            </template>
          </el-table-column>
          <el-table-column label="创建人" align="left">
            <template slot-scope="scope">
              <div>{{scope.row.createdBy}}</div>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-plus"
                @click="handleAddSubTask(scope.row)"
              >添加子任务</el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="handleUpdate(scope.row)"
              >修改</el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="handleAddTaskWork(scope.row)"
              >工作日志</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { listProject, listTask } from "@/api/pear/task";

export default {
  data() {
    return {
      // 遮罩层
      loading: true,
      // 总条数
      total: 0,
      projectList: [],
      // 查询参数
      projectQueryParms: {
        pageNum: 1,
        pageSize: 20
      },
      taskQueryParams: {
        projectId: null
      },
      title: "",
      taskOpen: false,
      // 表格树数据
      taskList: [],
      // 部门部门树选项
      taskOptions: undefined
    };
  },
  created() {
    this.getProjectList();
  },
  methods: {
    getProjectList() {
      this.loading = true;
      listProject(this.projectQueryParms).then(response => {
        this.projectList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    getTaskList() {
      this.loading = true;
      listTask(this.taskQueryParams).then(response => {
        this.taskList = response.data;
        this.loading = false;
      });
    },
    handleSelectRow(row) {
      this.taskQueryParams.projectId = row.id;
      this.getTaskList();
    },
    handleAddSubTask() {

    },
    handleAddTaskWork() {

    }
  }
};
</script>