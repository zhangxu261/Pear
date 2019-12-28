package com.lyfen.pear.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyfen.pear.domain.Member;
import com.lyfen.pear.domain.dto.MemberDTO;

import java.util.List;

public interface MemberMapper extends BaseMapper<Member> {

    List<MemberDTO> selectListByTeamId(Long teamId);
}
