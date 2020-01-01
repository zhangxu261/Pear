package com.lyfen.pear.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lyfen.pear.common.utils.SecurityUtils;
import com.lyfen.pear.domain.Task;
import com.lyfen.pear.domain.dto.TaskDTO;
import com.lyfen.pear.mapper.TaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private ProjectService projectService;

    public List<TaskDTO> selectList(Long projectId) {
        return taskMapper.selectListByProjectId(projectId);
    }

    public List<TaskDTO> buildTree(List<TaskDTO> list) {
        List<TaskDTO> returnList = new ArrayList<>();
        for (Iterator<TaskDTO> iterator = list.iterator(); iterator.hasNext(); ) {
            TaskDTO t = iterator.next();
            // 根据传入的某个父节点ID,遍历该父节点的所有子节点
            if (t.getParentId() == 0) {
                recursionFn(list, t);
                returnList.add(t);
            }
        }
        if (returnList.isEmpty()) {
            returnList = list;
        }
        return returnList;
    }

    /**
     * 递归列表
     */
    private void recursionFn(List<TaskDTO> list, TaskDTO t) {
        // 得到子节点列表
        List<TaskDTO> childList = getChildList(list, t);
        t.setChildren(childList);
        for (TaskDTO tChild : childList) {
            if (hasChild(list, tChild)) {
                // 判断是否有子节点
                Iterator<TaskDTO> it = childList.iterator();
                while (it.hasNext()) {
                    TaskDTO n = it.next();
                    recursionFn(list, n);
                }
            }
        }
    }

    private boolean hasChild(List<TaskDTO> list, TaskDTO t) {
        return getChildList(list, t).size() > 0;
    }

    private List<TaskDTO> getChildList(List<TaskDTO> list, TaskDTO t) {
        List<TaskDTO> tlist = new ArrayList<>();
        Iterator<TaskDTO> it = list.iterator();
        while (it.hasNext()) {
            TaskDTO n = it.next();
            if (n.getParentId().equals(t.getId())) {
                tlist.add(n);
            }
        }
        return tlist;
    }

    public int insert(Task task) {
        if (task.getParentId() == null) {
            task.setParentId(0L);
        }
        if (task.getUserId() == null) {
            task.setUserId(SecurityUtils.getLoginUser().getUser().getUserId());
        }
        task.setSchedule("0");
        task.setActualTime(0);
        int row = taskMapper.insert(task);
        if (task.getParentId() != 0L && row > 0) {
            // 新建的任务为子任务时，需要重新刷新父任务的预估工时(包括父任务的父任务)
            updateTaskEstimate(task.getParentId());
        }
        // 由于项目下面的任务的预估时间发生了变化，项目的进度也随之变化
        projectService.updateProjectSchedule(task.getProjectId());
        return row;
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
        int row = taskMapper.updateById(task);
        if (task.getParentId() != 0L && row > 0) {
            // 更新项目的进度,查询项目下面的一级任务做统计
            updateTaskEstimate(task.getParentId());
        }
    }

}
