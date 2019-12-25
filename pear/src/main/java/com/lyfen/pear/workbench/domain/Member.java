package com.lyfen.pear.workbench.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel("成员")
@Data
@TableName("pear_member")
public class Member implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    private String code;

    private String loginName;

    private String password;

    private String realname;

    private String email;

    private String avatar;

    private Integer status;
}
