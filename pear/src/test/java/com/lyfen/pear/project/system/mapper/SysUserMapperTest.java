package com.lyfen.pear.project.system.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SysUserMapperTest {

    @Resource
    private SysUserMapper userMapper;

    @Test
    public void test() {
        userMapper.selectUserByUserName("admin");
    }
}
