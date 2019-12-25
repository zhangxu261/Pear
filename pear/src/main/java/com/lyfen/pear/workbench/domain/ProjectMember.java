package com.lyfen.pear.workbench.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

@ApiModel("项目成员")
@Data
@TableName("pear_project_member")
public class ProjectMember implements Serializable {

    private Long id;

    private String projectCode;

    private String memberCode;

    private Boolean leader;
}
