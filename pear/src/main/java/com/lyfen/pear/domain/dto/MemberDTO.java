package com.lyfen.pear.domain.dto;

import com.lyfen.pear.domain.system.SysUser;
import lombok.Data;

@Data
public class MemberDTO extends SysUser {

    private Long teamId;

    private Integer isLeader;
}
