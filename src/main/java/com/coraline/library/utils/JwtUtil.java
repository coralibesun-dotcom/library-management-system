package com.coraline.library.utils;


import com.coraline.library.common.enums.ResultCodeEnum;
import com.coraline.library.exception.BusinessException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;


@Component
public class JwtUtil {


    private final SecretKey key;


    private final long expireTime;



    public JwtUtil(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expire}") long expireTime
    ){

        this.key =
                Keys.hmacShaKeyFor(
                        secret.getBytes()
                );


        this.expireTime = expireTime;

    }




    /**
     * 创建token
     *
     * @param userId 用户id
     * @param username 用户名
     * @param role 用户角色
     */
    public String createToken(
            Long userId,
            String username,
            String role
    ){


        return Jwts.builder()


                // 用户id
                .subject(
                        String.valueOf(userId)
                )


                // 用户名
                .claim(
                        "username",
                        username
                )


                // 角色
                .claim(
                        "role",
                        role
                )


                // 创建时间
                .issuedAt(
                        new Date()
                )


                // 过期时间
                .expiration(
                        new Date(
                                System.currentTimeMillis()
                                        + expireTime
                        )
                )


                // 签名
                .signWith(key)


                .compact();

    }





    /**
     * 解析token
     */
    public Claims parseToken(
            String token
    ){

        try {


            return Jwts.parser()


                    .verifyWith(key)


                    .build()


                    .parseSignedClaims(token)


                    .getPayload();



        }catch (JwtException e){


            throw new BusinessException(
                    ResultCodeEnum.TOKEN_INVALID,
                    "token无效或已过期"
            );


        }

    }




    /**
     * 获取用户id
     */
    public Long getUserId(
            String token
    ){

        return Long.valueOf(
                parseToken(token)
                        .getSubject()
        );

    }




    /**
     * 获取用户名
     */
    public String getUsername(
            String token
    ){

        return parseToken(token)
                .get("username", String.class);

    }




    /**
     * 获取角色
     */
    public String getRole(
            String token
    ){

        return parseToken(token)
                .get("role", String.class);

    }



}
