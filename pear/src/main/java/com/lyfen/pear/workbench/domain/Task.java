package com.lyfen.pear.workbench.domain;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@ApiModel("任务")
@Data
@TableName("pear_task")
public class Task implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("项目编号")
    private String projectCode;

    @ApiModelProperty("任务编号")
    private String taskCode;

    @ApiModelProperty("父级任务")
    private Long parentCode;

    @ApiModelProperty("任务名称")
    private String name;

    @ApiModelProperty("任务描述")
    private String description;

    @ApiModelProperty("任务进度")
    private Float schedule;

    @ApiModelProperty("预估工时")
    private Integer estimateTime;

    @ApiModelProperty("实际工时")
    private Integer actualTime;

    @ApiModelProperty("指派人")
    private String assignee;

    @ApiModelProperty("创建人")
    @TableField(fill = FieldFill.INSERT)
    private String createdBy;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdTime;

    @ApiModelProperty("更新人")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updatedBy;

    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updatedTime;
}
