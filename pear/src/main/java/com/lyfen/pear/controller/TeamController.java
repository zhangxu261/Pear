package com.lyfen.pear.controller;

import com.lyfen.pear.domain.Team;
import com.lyfen.pear.domain.dto.MemberDTO;
import com.lyfen.pear.framework.web.controller.BaseController;
import com.lyfen.pear.framework.web.domain.AjaxResult;
import com.lyfen.pear.framework.web.page.TableDataInfo;
import com.lyfen.pear.service.TeamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("团队管理")
@RestController
@RequestMapping("/pear/team")
public class TeamController extends BaseController {

    @Autowired
    private TeamService teamService;

    @ApiOperation("查询团队列表")
    @GetMapping("/list")
    public TableDataInfo list() {
        startPage();
        List<Team> list = teamService.selectList();
        return getDataTable(list);
    }

    @ApiOperation("查询成员列表")
    @GetMapping("/listMember")
    public TableDataInfo list(Long teamId) {
        startPage();
        List<MemberDTO> list = teamService.selectMemberList(teamId);
        return getDataTable(list);
    }

    @ApiOperation("新增团队")
    @PostMapping
    public AjaxResult add(@RequestBody Team team) {
        return toAjax(teamService.insert(team));
    }

    @ApiOperation("修改团队信息")
    @PutMapping
    public AjaxResult edit(@RequestBody Team team) {
        return toAjax(teamService.update(team));
    }

    @ApiOperation("根据ID删除团队")
    @DeleteMapping(value = "/{id}")
    public AjaxResult delete(@PathVariable Long id) {
        return toAjax(teamService.delete(id));
    }
}
