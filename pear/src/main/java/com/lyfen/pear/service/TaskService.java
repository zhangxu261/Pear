package com.lyfen.pear.service;

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
}
