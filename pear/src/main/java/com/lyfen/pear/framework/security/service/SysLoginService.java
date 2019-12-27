package com.lyfen.pear.framework.security.service;

import com.lyfen.pear.common.exception.CustomException;
import com.lyfen.pear.common.exception.user.UserPasswordNotMatchException;
import com.lyfen.pear.framework.security.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 登录校验方法
 *
 * @author lyfen
 */
@Component
public class SysLoginService {
    @Autowired
    private TokenService tokenService;

    @Resource
    private AuthenticationManager authenticationManager;

    /**
     * 登录验证
     *
     * @param username 用户名
     * @param password 密码
     * @return 结果
     */
    public String login(String username, String password) {
        // 用户验证
        Authentication authentication = null;
        try {
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception e) {
            if (e instanceof BadCredentialsException) {
                throw new UserPasswordNotMatchException();
            } else {
                throw new CustomException(e.getMessage());
            }
        }
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        // 生成token
        return tokenService.createToken(loginUser);
    }
}
