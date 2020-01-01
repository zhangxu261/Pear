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
          <el-table-column label="预估/实际工时" width="120">
            <template slot-scope="scope">
              <span>{{scope.row.estimateTime}} / {{scope.row.actualTime}}</span>
            </template>
          </el-table-column>
          <el-table-column label="进度" align="left" width="160">
            <template slot-scope="scope">
              <el-progress :percentage="scope.row.schedule" />
            </template>
          </el-table-column>
          <el-table-column label="分派给" align="left">
            <template slot-scope="scope">
              <div>{{scope.row.realName}}</div>
            </template>
          </el-table-column>
          <el-table-column label="创建" align="left">
            <template slot-scope="scope">
              <div>{{scope.row.createdBy}}</div>
              <div>{{parseTime(scope.row.createdTime, '{y}-{m}-{d}')}}</div>
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
                icon="el-icon-edit-outline"
                @click="handleAddTaskWork(scope.row)"
              >工作日志</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-col>
    </el-row>

    <!-- 登记工时对话框 -->
    <el-dialog :title="title" :visible.sync="taskWorkOpen">
      <el-form ref="taskWorkForm" :model="taskWorkForm" :rules="taskWorkRules">
        <el-form-item label="工作耗时" prop="workTime">
          <el-input type="number" v-model="taskWorkForm.workTime" placeholder="单位是小时" />
        </el-form-item>
        <el-form-item label="登记日期" prop="workDate">
          <el-date-picker type="date" v-model="taskWorkForm.workDate" />
        </el-form-item>
        <el-form-item label="工作内容" prop="workContent">
          <el-input type="textarea" :rows="5" v-model="taskWorkForm.workContent" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitTaskWorkForm">保 存</el-button>
        <!-- <el-button @click="cancel">取 消</el-button> -->
      </div>
    </el-dialog>

    <el-dialog :title="title" :visible.sync="taskOpen">
      <el-form ref="taskForm" :model="taskForm" :rules="taskRules">
        <el-form-item label="任务编号" prop="code">
          <el-input v-model="taskForm.code" placeholder="大写英文字符和数字，如T100" />
        </el-form-item>
        <el-form-item label="任务主题" prop="subject">
          <el-input v-model="taskForm.subject" />
        </el-form-item>
        <el-form-item label="任务描述" prop="description">
          <el-input type="textarea" :rows="2" v-model="taskForm.description" />
        </el-form-item>
        <el-form-item label="预估工时" prop="estimateTime">
          <el-input type="number" v-model="taskForm.estimateTime" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitTaskForm">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listProject, listTask, addTaskWork, addTask } from "@/api/pear/task";

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
      taskOptions: undefined,
      taskWorkOpen: false,
      // 表单参数
      taskWorkForm: {},
      // 表单校验
      taskWorkRules: {
        workTime: [
          { required: true, message: "工作耗时不能为空", trigger: "blur" }
        ],
        workContent: [
          { required: true, message: "工作内容不能为空", trigger: "blur" },
          { max: 255, message: "最多255个字符", trigger: "blur" }
        ]
      },
      taskForm: {},
      taskWorkRules: {}
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
    handleAddTask() {
      this.title = "添加任务";
      this.taskOpen = true;
      this.taskFormReset();
      this.taskForm.projectId = this.taskQueryParams.projectId;
    },
    handleAddSubTask(row) {
      this.title = "添加子任务 - " + row.subject;
      this.taskOpen = true;
      this.taskFormReset();
      this.taskForm.projectId = this.taskQueryParams.projectId;
      this.taskForm.parentId = row.id;
    },
    handleAddTaskWork(row) {
      this.title = "登记工时 - " + row.subject;
      this.taskWorkOpen = true;
      this.taskWorkFormReset();
      this.taskWorkForm.taskId = row.id;
      this.taskWorkForm.workDate = new Date();
    },
    taskFormReset() {},
    taskWorkFormReset() {
      this.taskWorkForm = {
        workTime: undefined,
        workDate: undefined,
        workContent: undefined
      };
      this.resetForm("taskWorkForm");
    },
    submitTaskWorkForm() {
      this.$refs["taskWorkForm"].validate(valid => {
        if (valid) {
          addTaskWork(this.taskWorkForm).then(response => {
            if (response.code === 200) {
              this.msgSuccess("登记成功");
              this.taskWorkOpen = false;
            } else {
              this.msgError(response.msg);
            }
          });
        }
      });
    },
    submitTaskForm() {
      this.$refs["taskForm"].validate(valid => {
        if (valid) {
          addTask(this.taskForm).then(response => {
            if (response.code === 200) {
              this.msgSuccess("添加成功");
              this.taskOpen = false;
            } else {
              this.msgError(response.msg);
            }
          });
        }
      });
    }
  }
};
</script>