package com.example.demo.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.demo.entity.User;
import com.example.demo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class TokenUtils {
    private static IUserService staticUserService;

    @Autowired
    private IUserService userService;

    @PostConstruct
    public void  setUserService() {
        staticUserService = userService;
    }
    public static String generatorToken(String userId, String sign) {
        String token = JWT
                .create()
                .withAudience(userId)// 将userid 作为载荷
                .withExpiresAt(DateUtil.offsetHour(new Date(), 2)) // token 过期时间
                .sign(Algorithm.HMAC256(sign)); // 密钥
        return token;
    }

    /**
     *  获取当前登陆用户的信息
     * @return user对象
     */
    public static User getCurrentUser() {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

            String token = request.getHeader("token");
            if (StrUtil.isNotBlank(token)){

                String userId = JWT.decode(token).getAudience().get(0);
                return staticUserService.getById(Integer.valueOf(userId));
            }
        } catch (Exception e) {
            return null;
        }

        return null;
    }
}
