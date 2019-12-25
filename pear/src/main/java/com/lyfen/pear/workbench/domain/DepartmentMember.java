package com.lyfen.pear.workbench.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

@ApiModel("部门成员")
@Data
@TableName("pear_department_member")
public class DepartmentMember implements Serializable {

    private Long id;

    private String departmentCode;

    private String memberCode;

    private Boolean leader;


}
