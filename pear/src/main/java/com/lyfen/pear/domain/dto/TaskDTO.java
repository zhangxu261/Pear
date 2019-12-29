package com.lyfen.pear.domain.dto;

import com.lyfen.pear.domain.Task;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TaskDTO extends Task {

    private String realName;

    private List<TaskDTO> children = new ArrayList<>();
}
