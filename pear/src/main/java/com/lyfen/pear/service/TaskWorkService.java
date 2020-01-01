package com.lyfen.pear.service;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lyfen.pear.common.utils.SecurityUtils;
import com.lyfen.pear.domain.TaskWork;
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

    public int addTaskWork(TaskWork taskWork) {
        if (taskWork.getWorkDate() == null) {
            taskWork.setWorkDate(LocalDateTime.now());
        }
        taskWork.setUserId(SecurityUtils.getLoginUser().getUser().getUserId());
        taskWorkMapper.insert(taskWork);
        //todo

        return 0;
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
