package com.lyfen.pear.service;

import cn.hutool.core.util.NumberUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lyfen.pear.domain.Project;
import com.lyfen.pear.domain.Task;
import com.lyfen.pear.mapper.ProjectMapper;
import com.lyfen.pear.mapper.TaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectMapper projectMapper;
    @Autowired
    private TaskMapper taskMapper;

    /**
     * 项目列表
     *
     * @return
     */
    public List<Project> selectList() {
        //todo
        /*
         * 管理员可以查看所有项目
         * 部门领导可以查看所有项目
         * 成员只能看到所属的项目
         * 先不考虑权限角色
         */
//        LambdaQueryWrapper<Project> queryWrapper = new LambdaQueryWrapper<>();
//        if (StringUtils.isNotBlank(project.getCode())) {
//            queryWrapper.eq(Project::getCode, project.getCode());
//        }
//        if (StringUtils.isNotBlank(project.getName())) {
//            queryWrapper.like(Project::getName, project.getName());
//        }
        return projectMapper.selectList(null);
    }

    public int update(Project project) {
        return projectMapper.updateById(project);
    }

    public Project selectById(Long id) {
        return projectMapper.selectById(id);
    }

    public int delete(Long id) {
        return projectMapper.deleteById(id);
    }

    public int insert(Project project) {
        project.setSchedule("0");
        return projectMapper.insert(project);
    }

    public void updateProjectSchedule(Long projectId) {
        LambdaQueryWrapper<Task> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Task::getProjectId, projectId);
        queryWrapper.eq(Task::getParentId, 0);
        List<Task> taskList = taskMapper.selectList(queryWrapper);
        Integer totalActualTime = 0;
        Integer totalEstimateTime = 0;
        for (Task task : taskList) {
            totalActualTime += task.getActualTime();
            totalEstimateTime += task.getEstimateTime();
        }
        String projectSchedule = NumberUtil.decimalFormat("#.##", NumberUtil.div(totalActualTime, totalEstimateTime).floatValue() * 100);
        Project project = new Project();
        project.setId(projectId);
        project.setSchedule(projectSchedule);
        projectMapper.updateById(project);
    }
}
