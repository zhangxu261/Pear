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

    @ApiModelProperty("任务编号")
    private String code;

    @ApiModelProperty("项目编号")
    private String projectCode;

    @ApiModelProperty("父级任务")
    private Long parentCode;

    @ApiModelProperty("任务主题")
    private String subject;

    @ApiModelProperty("任务描述")
    private String description;

    @ApiModelProperty("任务进度")
    private Float schedule;

    @ApiModelProperty("任务类型")
    private Integer type;

    @ApiModelProperty("任务状态")
    private Integer status;

    @ApiModelProperty("预估工时")
    private Integer estimateTime;

    @ApiModelProperty("实际工时")
    private Integer actualTime;

    @ApiModelProperty("指派人")
    private String assignee;

    @ApiModelProperty("创建人")
    private String createdBy;

    @ApiModelProperty("创建时间")
    private Date createdTime;

    @ApiModelProperty("更新人")
    private String updatedBy;

    @ApiModelProperty("更新时间")
    private Date updatedTime;
}
