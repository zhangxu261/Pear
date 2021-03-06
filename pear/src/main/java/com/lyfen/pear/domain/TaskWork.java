package com.lyfen.pear.domain;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@ApiModel("任务工作")
@Data
@TableName("pear_task_work")
public class TaskWork implements Serializable {

    @ApiModelProperty("主键ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("任务编号")
    private Long taskId;

    @ApiModelProperty("成员id")
    private Long userId;

    @ApiModelProperty("工作内容")
    private String workContent;

    @ApiModelProperty("工作日期")
    private LocalDateTime workDate;

    @ApiModelProperty("工时")
    private Integer workTime;

    @ApiModelProperty("创建人")
    @TableField(fill = FieldFill.INSERT)
    private String createdBy;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdTime;

    @ApiModelProperty("更新人")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updatedBy;

    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedTime;
}
