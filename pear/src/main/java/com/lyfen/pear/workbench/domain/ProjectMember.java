package com.lyfen.pear.workbench.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel("项目成员")
@Data
@TableName("pear_project_member")
public class ProjectMember implements Serializable {

    @ApiModelProperty("主键ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("项目编号")
    private String projectCode;

    @ApiModelProperty("成员编号")
    private String memberCode;

    @ApiModelProperty("是否主要人")
    private Boolean master;

}
