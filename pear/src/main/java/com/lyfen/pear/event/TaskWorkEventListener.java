package com.lyfen.pear.event;

import cn.hutool.core.util.NumberUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lyfen.pear.domain.Project;
import com.lyfen.pear.domain.Task;
import com.lyfen.pear.mapper.ProjectMapper;
import com.lyfen.pear.mapper.TaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TaskWorkEventListener {

    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private ProjectMapper projectMapper;

    /**
     * 登记工时被创建时触发的事件
     */
    @EventListener
    public void taskWorkCreatedEvent(TaskWorkEvent event) {
        //更新对应任务的进度
        updateTaskSchedule(event.getTaskId(), event.getWorkTime());
        // 更新项目的进度,查询项目下面的一级任务做统计
        Long projectId = taskMapper.selectById(event.getTaskId()).getProjectId();
        LambdaQueryWrapper<Task> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Task::getProjectId, projectId);
        queryWrapper.eq(Task::getParentId, 0);
        List<Task> taskList = taskMapper.selectList(queryWrapper);
        Integer totalActualTime = 0;
        Integer totalEstimateTime = 0;
        for (Task task : taskList) {
            totalActualTime += task.getActualTime();
            totalEstimateTime += task.getEstimateTime();
        }
        String projectSchedule = NumberUtil.decimalFormat("#.##", NumberUtil.div(totalActualTime, totalEstimateTime).doubleValue());
        Project project = new Project();
        project.setId(projectId);
        project.setSchedule(projectSchedule);
        projectMapper.updateById(project);
    }

    private void updateTaskSchedule(Long taskId, Integer workTime) {
        Task task = taskMapper.selectById(taskId);
        task.setActualTime(task.getActualTime() + workTime);
        task.setSchedule(NumberUtil.decimalFormat("#.##", NumberUtil.div(task.getActualTime(), task.getEstimateTime()).doubleValue()));
        taskMapper.updateById(task);
        if (task.getParentId() != 0L) {
            // 如果存在父任务继续更新进度
            updateTaskSchedule(task.getParentId(), workTime);
        }
    }
}
