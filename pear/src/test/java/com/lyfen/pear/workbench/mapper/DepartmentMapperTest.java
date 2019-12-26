package com.lyfen.pear.workbench.mapper;

import com.lyfen.pear.workbench.domain.Department;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DepartmentMapperTest {

    @Resource
    private DepartmentMapper departmentMapper;

    @Test
    public void insert() {
        Department dep = new Department();
        dep.setCode("T001");
        dep.setName("供应链开发组");
        dep.setSort(1);
        departmentMapper.insert(dep);
    }


}