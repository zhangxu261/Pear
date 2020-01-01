package com.lyfen.pear;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Test {

    @org.junit.Test
    public void test() {

        List<GrantedAuthority> grantedAuthorities = Arrays.stream("R,A,B,C".split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        System.out.println(grantedAuthorities);


        Date current = new Date();
        DateTime begin = DateUtil.beginOfMonth(current);
        DateTime end = DateUtil.endOfMonth(current);
        System.out.println(begin);
        System.out.println(end);
    }
}
