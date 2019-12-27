package com.lyfen.pear.workbench.service;

import com.lyfen.pear.workbench.mapper.TeamMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamService {

    @Autowired
    private TeamMapper teamMapper;
}
