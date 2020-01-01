package com.lyfen.pear.event;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lyfen.pear.domain.Task;
import com.lyfen.pear.mapper.TaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TaskEventListener {

    @Autowired
    private TaskMapper taskMapper;

    /**
     * 子任务被创建时触发的事件
     */
    @EventListener
    public void subTaskCreatedEvent(TaskEvent event) {
        // 统计该任务下直接子任务的预估工时
        updateTaskEstimate(event.getTaskId());
    }

    private void updateTaskEstimate(Long taskId) {
        Task task = taskMapper.selectById(taskId);
        LambdaQueryWrapper<Task> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Task::getParentId, task.getId());
        List<Task> subTaskList = taskMapper.selectList(queryWrapper);
        Integer totalEstimateTime = 0;
        for (Task subTask : subTaskList) {
            totalEstimateTime += subTask.getEstimateTime();
        }
        task.setEstimateTime(totalEstimateTime);
        taskMapper.updateById(task);
        if (task.getParentId() != 0L) {
            updateTaskEstimate(task.getParentId());
        }
    }
}
