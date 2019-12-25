package com.lyfen.pear.workbench.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

@ApiModel("部门")
@Data
@TableName("pear_department")
public class Department implements Serializable {

    private Long id;

    private String code;

    private String name;

    private String parentCode;

    private Integer sort;
}
