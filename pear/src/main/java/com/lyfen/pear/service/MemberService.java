package com.lyfen.pear.service;

import com.lyfen.pear.mapper.MemberMapper;
import com.lyfen.pear.service.dto.MemberDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    @Autowired
    private MemberMapper memberMapper;

    public List<MemberDTO> selectList(Long teamId) {
        return memberMapper.selectListByTeamId(teamId);
    }
}
