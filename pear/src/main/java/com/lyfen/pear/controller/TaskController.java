package com.lyfen.pear.controller;

import com.lyfen.pear.framework.web.controller.BaseController;
import com.lyfen.pear.service.TaskService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("任务管理")
@RestController
@RequestMapping("/pear/task")
public class TaskController extends BaseController {

    @Autowired
    private TaskService taskService;


}
