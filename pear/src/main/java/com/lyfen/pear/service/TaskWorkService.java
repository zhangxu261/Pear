package com.lyfen.pear.service;

import com.lyfen.pear.common.utils.SecurityUtils;
import com.lyfen.pear.domain.TaskWork;
import com.lyfen.pear.mapper.TaskWorkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TaskWorkService {

    @Autowired
    private TaskWorkMapper taskWorkMapper;

    public int addTaskWork(TaskWork taskWork) {
        if (taskWork.getWorkDate() == null) {
            taskWork.setWorkDate(LocalDateTime.now());
        }
        taskWork.setUserId(SecurityUtils.getLoginUser().getUser().getUserId());
        taskWorkMapper.insert(taskWork);
        //todo

        return 0;
    }
}
