package com.lyfen.pear.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyfen.pear.domain.TeamMember;
import com.lyfen.pear.domain.dto.MemberDTO;

import java.util.List;

public interface TeamMemberMapper extends BaseMapper<TeamMember> {

    List<MemberDTO> selectListByTeamId(Long teamId);
}
