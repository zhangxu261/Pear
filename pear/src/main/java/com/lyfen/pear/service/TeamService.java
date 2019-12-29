package com.lyfen.pear.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lyfen.pear.domain.Team;
import com.lyfen.pear.domain.TeamMember;
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

    public List<MemberDTO> searchMemberList(String search) {
        return teamMemberMapper.searchList(search);
    }

    public int addMember(Long teamId, Long userId) {
        LambdaQueryWrapper<TeamMember> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TeamMember::getTeamId, teamId).eq(TeamMember::getUserId, userId);
        List<TeamMember> list = teamMemberMapper.selectList(wrapper);
        if (list.isEmpty()) {
            TeamMember teamMember = new TeamMember();
            teamMember.setTeamId(teamId);
            teamMember.setUserId(userId);
            return teamMemberMapper.insert(teamMember);
        }
        return 0;
    }

    public int removeMember(Long teamId, Long userId) {
        LambdaQueryWrapper<TeamMember> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TeamMember::getTeamId, teamId).eq(TeamMember::getUserId, userId);
        return teamMemberMapper.delete(wrapper);
    }
}
