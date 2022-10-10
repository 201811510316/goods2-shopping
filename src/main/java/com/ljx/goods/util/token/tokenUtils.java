package com.ljx.goods.util.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ljx.goods.pojo.user;

import java.util.Date;

public class tokenUtils {

    private static final long EXPIRE_TIME = 3600L * 1000;

    //创建token
    public static String signByToken(user user){
        //设置过期时间
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        String token  = JWT.create()
                .withIssuer("auth0")
                .withAudience(user.getUsername())
                .withClaim("id",user.getId())
                .withClaim("username",user.getUsername())
                .withExpiresAt(date)
                .sign(Algorithm.HMAC256(user.getPassword()));
        return token;
    }

    //解析token
    public static boolean verify(String token,user user){
        try {
            //创建解析对象
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).withIssuer("auth0").build();
            //解析指定token
            jwtVerifier.verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //获取token中的id
    public static Integer getUserId(String token,user user){
        try {
            //创建解析对象
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).withIssuer("auth0").build();
            //解析指定token
            DecodedJWT verify = jwtVerifier.verify(token);
            Integer id = verify.getClaim("id").asInt();
            return id;
        } catch (Exception e) {
            return null;
        }
    }
}
