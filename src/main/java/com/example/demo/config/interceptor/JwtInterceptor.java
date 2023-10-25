package com.example.demo.config.interceptor;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.common.Constants;
import com.example.demo.entity.User;
import com.example.demo.exception.ServiceException;
import com.example.demo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class JwtInterceptor implements HandlerInterceptor {
    @Autowired
    private IUserService userService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader("token");
        // 如果不是映射到方法直接同
        if(!(handler instanceof HandlerMethod)) {
            return true;
        }

        // 执行认证
        if(StrUtil.isBlank(token)) {
            throw new ServiceException(Constants.CODE_401, "无token， 请重新登陆");
        }

        String userId;
        try {
            DecodedJWT decode = JWT.decode(token);
            List<String> audience = decode.getAudience();
            userId = audience.get(0);
        } catch(JWTDecodeException s) {
            throw new ServiceException(Constants.CODE_401, "token 验证失败");
        }

        // 根据token中的用户id 查询用户信息
        User user = userService.getById(Integer.valueOf(userId));
        if (user == null) {
            throw new ServiceException(Constants.CODE_401, "用户不存在，请重新登陆");
        }

        // 用户密码加签验证 token
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
        try {
            jwtVerifier.verify(token); // 验证token
        } catch(JWTVerificationException e) {
            throw new ServiceException(Constants.CODE_401, "用户不存在，请重新登陆");
        }

        return true;
    }
}