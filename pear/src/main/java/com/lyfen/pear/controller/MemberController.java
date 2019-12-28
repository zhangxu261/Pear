package com.lyfen.pear.controller;

import com.lyfen.pear.domain.Member;
import com.lyfen.pear.framework.web.controller.BaseController;
import com.lyfen.pear.framework.web.page.TableDataInfo;
import com.lyfen.pear.service.MemberService;
import com.lyfen.pear.service.dto.MemberDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api("成员管理")
@RestController
@RequestMapping("/pear/member")
public class MemberController extends BaseController {

    @Autowired
    private MemberService memberService;

    @ApiOperation("查询成员列表")
    @GetMapping("/list")
    public TableDataInfo list(Long teamId) {
        startPage();
        List<MemberDTO> list = memberService.selectList(teamId);
        return getDataTable(list);
    }
}
