package com.lyfen.pear.service;

import com.lyfen.pear.domain.Project;
import com.lyfen.pear.mapper.ProjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectMapper projectMapper;

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
        return projectMapper.insert(project);
    }
}
