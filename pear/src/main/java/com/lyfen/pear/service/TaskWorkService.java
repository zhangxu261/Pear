package com.lyfen.pear.service;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lyfen.pear.common.utils.SecurityUtils;
import com.lyfen.pear.domain.Task;
import com.lyfen.pear.domain.TaskWork;
import com.lyfen.pear.mapper.TaskMapper;
import com.lyfen.pear.mapper.TaskWorkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class TaskWorkService {

    @Autowired
    private TaskWorkMapper taskWorkMapper;
    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private ProjectService projectService;

    public int insert(TaskWork taskWork) {
        if (taskWork.getWorkDate() == null) {
            taskWork.setWorkDate(LocalDateTime.now());
        }
        taskWork.setUserId(SecurityUtils.getLoginUser().getUser().getUserId());
        int row = taskWorkMapper.insert(taskWork);
        if (row > 0) {
            //更新对应任务的进度
            updateTaskSchedule(taskWork.getTaskId(), taskWork.getWorkTime());
            // 更新项目的进度,查询项目下面的一级任务做统计
            Long projectId = taskMapper.selectById(taskWork.getTaskId()).getProjectId();
            projectService.updateProjectSchedule(projectId);
        }
        return row;
    }

    private void updateTaskSchedule(Long taskId, Integer workTime) {
        Task task = taskMapper.selectById(taskId);
        task.setActualTime(task.getActualTime() + workTime);
        task.setSchedule(NumberUtil.decimalFormat("#.##", NumberUtil.div(task.getActualTime(), task.getEstimateTime()).floatValue() * 100));
        taskMapper.updateById(task);
        if (task.getParentId() != 0L) {
            // 如果存在父任务继续更新进度
            updateTaskSchedule(task.getParentId(), workTime);
        }
    }


    public List<TaskWork> listUserTaskWork() {
        LambdaQueryWrapper<TaskWork> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TaskWork::getUserId, SecurityUtils.getLoginUser().getUser().getUserId());
        Date current = new Date();
        DateTime begin = DateUtil.beginOfMonth(current);
        DateTime end = DateUtil.endOfMonth(current);
        queryWrapper.between(TaskWork::getWorkDate, begin.toString(), end.toString());
        return taskWorkMapper.selectList(queryWrapper);
    }
}
