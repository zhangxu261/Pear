package com.lyfen.pear.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TeamServiceTest {

    @Autowired
    private TeamService teamService;

    @Test
    public void addMember() {
        teamService.addMember(1L, 1L);
        teamService.addMember(1L, 2L);
    }

    @Test
    public void removeMember() {
        teamService.removeMember(1L, 2L);
    }
}