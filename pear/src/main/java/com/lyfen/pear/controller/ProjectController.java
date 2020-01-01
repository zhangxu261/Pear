package com.lyfen.pear.controller;

import com.lyfen.pear.domain.Project;
import com.lyfen.pear.framework.web.controller.BaseController;
import com.lyfen.pear.framework.web.domain.AjaxResult;
import com.lyfen.pear.framework.web.page.TableDataInfo;
import com.lyfen.pear.service.ProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("项目管理")
@RestController
@RequestMapping("/pear/project")
public class  ProjectController extends BaseController {

    @Autowired
    private ProjectService projectService;

    @ApiOperation("项目列表")
    @GetMapping("/list")
    public TableDataInfo list() {
        startPage();
        List<Project> list = projectService.selectList();
        return getDataTable(list);
    }

    @ApiOperation("新增项目")
    @PostMapping("/addProject")
    public AjaxResult addProject(@RequestBody Project project) {
        return toAjax(projectService.addProject(project));
    }

    @ApiOperation("跟ID获取项目信息")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(projectService.selectById(id));
    }

    @ApiOperation("修改项目信息")
    @PutMapping
    public AjaxResult edit(@RequestBody Project project) {
        return toAjax(projectService.update(project));
    }

    @ApiOperation("项目删除")
    @DeleteMapping("/{id}")
    public AjaxResult delProject(@PathVariable("id") Long id) {
        return toAjax(projectService.delProject(id));
    }

}
