package com.lyfen.pear.service;

import com.lyfen.pear.domain.Team;
import com.lyfen.pear.domain.dto.MemberDTO;
import com.lyfen.pear.mapper.TeamMapper;
import com.lyfen.pear.mapper.TeamMemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {

    @Autowired
    private TeamMapper teamMapper;
    @Autowired
    private TeamMemberMapper teamMemberMapper;

    public List<Team> selectList() {
        return teamMapper.selectList(null);
    }

    public int insert(Team team) {
        return teamMapper.insert(team);
    }

    public int update(Team team) {
        return teamMapper.updateById(team);
    }

    public int delete(Long id) {
        return teamMapper.deleteById(id);
    }

    public List<MemberDTO> selectMemberList(Long teamId) {
        return teamMemberMapper.selectListByTeamId(teamId);
    }
}
