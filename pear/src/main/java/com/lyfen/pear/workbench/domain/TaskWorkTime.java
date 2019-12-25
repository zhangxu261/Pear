package com.lyfen.pear.workbench.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

@ApiModel("任务工时")
@Data
@TableName("pear_task_work_time")
public class TaskWorkTime implements Serializable {

    private Long id;

    private String taskCode;

    private String memberCode;

    private String content;

    private Integer num;
}
