package com.lyfen.pear.controller;

import com.lyfen.pear.domain.Project;
import com.lyfen.pear.domain.Task;
import com.lyfen.pear.domain.Team;
import com.lyfen.pear.domain.dto.MemberDTO;
import com.lyfen.pear.domain.dto.TaskDTO;
import com.lyfen.pear.framework.web.controller.BaseController;
import com.lyfen.pear.framework.web.domain.AjaxResult;
import com.lyfen.pear.framework.web.page.TableDataInfo;
import com.lyfen.pear.service.ProjectService;
import com.lyfen.pear.service.TaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api("任务管理")
@RestController
@RequestMapping("/pear/task")
public class TaskController extends BaseController {

    @Autowired
    private TaskService taskService;
    @Autowired
    private ProjectService projectService;

    @ApiOperation("查询项目列表")
    @GetMapping("/listProject")
    public TableDataInfo listProject() {
        startPage();
        List<Project> list = projectService.selectList();
        return getDataTable(list);
    }

    @ApiOperation("查询任务列表")
    @GetMapping("/list")
    public AjaxResult list(Long projectId) {
        List<TaskDTO> list = taskService.selectList(projectId);
        return AjaxResult.success(taskService.buildTree(list));
    }
}
