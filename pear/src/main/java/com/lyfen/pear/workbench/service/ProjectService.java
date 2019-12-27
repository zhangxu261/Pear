package com.lyfen.pear.workbench.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lyfen.pear.workbench.domain.Project;
import com.lyfen.pear.workbench.mapper.ProjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectMapper projectMapper;

    /**
     * 项目列表
     * 管理员可以查看所有项目
     * 部门领导可以查看所有项目
     * 成员只能看到所属的项目
     * 先不考虑权限角色
     *
     * @param project
     * @return
     */
    public List<Project> selectList(Project project) {
        LambdaQueryWrapper<Project> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(project.getCode())) {
            queryWrapper.eq(Project::getCode, project.getCode());
        }
        if (StringUtils.isNotBlank(project.getName())) {
            queryWrapper.like(Project::getName, project.getName());
        }
        return projectMapper.selectList(queryWrapper);
    }

    public int insert(Project project) {
        return projectMapper.insert(project);
    }

    public int update(Project project) {
        return projectMapper.updateById(project);
    }

    public Project selectById(Long id) {
        return projectMapper.selectById(id);
    }
}
