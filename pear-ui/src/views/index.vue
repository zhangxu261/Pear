<template>
  <div class="dashboard-editor-container">
    <el-calendar :range="currentMonth">
      <template slot="dateCell" slot-scope="{date, data}">
        <div>
          <div>{{ data.day.split('-').slice(2).join('-') }}</div>
          <div v-for="item in userTaskWorkList">
            <div v-if="(item.workDate).indexOf(data.day) != -1">{{item.workContent}}</div>
            <div v-else></div>
          </div>
        </div>
      </template>
    </el-calendar>
  </div>
</template>

<script>
import { listUserTaskWork } from "@/api/pear/task";

export default {
  data() {
    return {
      currentMonth: undefined,
      userTaskWorkList: []
    };
  },
  created() {
    let date = new Date();
    var firstDay = new Date(date.getFullYear(), date.getMonth(), 1);
    var lastDay = new Date(date.getFullYear(), date.getMonth() + 1, 0);
    this.currentMonth = [firstDay, lastDay];
    this.getUserTaskWorkList();
  },
  methods: {
    getUserTaskWorkList() {
      listUserTaskWork().then(response => {
        this.userTaskWorkList = response.data;
      });
    }
  }
};
</script>
