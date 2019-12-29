package com.lyfen.pear.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyfen.pear.domain.Task;
import com.lyfen.pear.domain.dto.TaskDTO;

import java.util.List;

public interface TaskMapper extends BaseMapper<Task> {
    List<TaskDTO> selectListByProjectId(Long projectId);
}
