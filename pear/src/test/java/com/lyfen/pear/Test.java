package com.lyfen.pear;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Test {

    @org.junit.Test
    public void test() {

        List<GrantedAuthority> grantedAuthorities = Arrays.stream("R,A,B,C".split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        System.out.println(grantedAuthorities);
    }
}
