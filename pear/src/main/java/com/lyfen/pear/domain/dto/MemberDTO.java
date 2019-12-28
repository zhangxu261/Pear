package com.lyfen.pear.domain.dto;

import com.lyfen.pear.domain.Member;
import lombok.Data;

@Data
public class MemberDTO extends Member {

    private Long teamId;

    private Integer isLeader;
}
