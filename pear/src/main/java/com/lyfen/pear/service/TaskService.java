package com.lyfen.pear.service;

import com.lyfen.pear.common.utils.SecurityUtils;
import com.lyfen.pear.domain.Task;
import com.lyfen.pear.domain.dto.TaskDTO;
import com.lyfen.pear.event.TaskEvent;
import com.lyfen.pear.mapper.TaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private ApplicationEventPublisher publisher;

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
            publisher.publishEvent(new TaskEvent(task.getParentId()));
        }
        return row;
    }
}
