package com.lyfen.pear.controller;

import com.lyfen.pear.framework.aspectj.lang.annotation.Log;
import com.lyfen.pear.framework.aspectj.lang.enums.BusinessType;
import com.lyfen.pear.framework.web.controller.BaseController;
import com.lyfen.pear.framework.web.domain.AjaxResult;
import com.lyfen.pear.framework.web.page.TableDataInfo;
import com.lyfen.pear.domain.Project;
import com.lyfen.pear.service.ProjectService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("项目管理")
@RestController
@RequestMapping("/pear/project")
public class ProjectController extends BaseController {

    @Autowired
    private ProjectService projectService;

    /**
     * 查询项目列表
     */
    @PreAuthorize("@ss.hasPermi('pear:project:list')")
    @GetMapping("/list")
    public TableDataInfo list(Project project) {
        startPage();
        List<Project> list = projectService.selectList(project);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('pear:project:add')")
    @Log(title = "项目", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Project project) {
        return toAjax(projectService.insert(project));
    }

    /**
     * 获取项目详细信息
     */
    @PreAuthorize("@ss.hasPermi('pear:project:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(projectService.selectById(id));
    }

    /**
     * 修改项目
     */
    @PreAuthorize("@ss.hasPermi('pear:project:edit')")
    @Log(title = "项目", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Project project) {
        return toAjax(projectService.update(project));
    }
}
