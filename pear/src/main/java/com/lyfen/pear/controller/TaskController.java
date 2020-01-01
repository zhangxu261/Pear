package com.lyfen.pear.controller;

import com.lyfen.pear.domain.Project;
import com.lyfen.pear.domain.Task;
import com.lyfen.pear.domain.TaskWork;
import com.lyfen.pear.domain.Team;
import com.lyfen.pear.domain.dto.MemberDTO;
import com.lyfen.pear.domain.dto.TaskDTO;
import com.lyfen.pear.framework.web.controller.BaseController;
import com.lyfen.pear.framework.web.domain.AjaxResult;
import com.lyfen.pear.framework.web.page.TableDataInfo;
import com.lyfen.pear.service.ProjectService;
import com.lyfen.pear.service.TaskService;
import com.lyfen.pear.service.TaskWorkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("任务管理")
@RestController
@RequestMapping("/pear/task")
public class TaskController extends BaseController {

    @Autowired
    private TaskService taskService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private TaskWorkService taskWorkService;

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

    @ApiOperation("添加工作日志")
    @PostMapping("/addTaskWork")
    public AjaxResult addTaskWork(@RequestBody TaskWork taskWork) {
        return toAjax(taskWorkService.addTaskWork(taskWork));
    }

    @ApiOperation("添加任务")
    @PostMapping("/addTask")
    public AjaxResult addTask(@RequestBody Task task) {
        return toAjax(taskService.addTask(task));
    }

    @ApiOperation("获取当前用户的工作日志")
    @GetMapping("/listUserTaskWork")
    public AjaxResult listUserTaskWork() {
        List<TaskWork> list = taskWorkService.listUserTaskWork();
        return AjaxResult.success(list);
    }
}
