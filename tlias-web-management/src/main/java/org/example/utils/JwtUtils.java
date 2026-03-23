package org.example.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

/**
 * JWT 令牌操作工具类
 */
public class JwtUtils {

    // 密钥（和测试类一致）
    private static final String SECRET_KEY = "itheima";

    // 过期时间：12 小时（12 * 60 * 60 * 1000 毫秒）
    private static final long EXPIRE_TIME = 12 * 60 * 60 * 1000;

    /**
     * 生成 JWT 令牌
     * @param claims 自定义数据（如用户 ID、用户名等）
     * @return JWT 令牌字符串
     */
    public static String generateJwt(Map<String, Object> claims) {
        String jwt = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)  // 指定加密算法和密钥
                .addClaims(claims)                               // 添加自定义信息
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_TIME))  // 设置过期时间 12 小时
                .compact();                                      // 生成令牌
        return jwt;
    }

    /**
     * 解析 JWT 令牌
     * @param token JWT 令牌字符串
     * @return Claims 对象（包含自定义数据）
     * @throws Exception 令牌被篡改或过期时会抛出异常
     */
    public static Claims parseJwt(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)    // 指定密钥
                .parseClaimsJws(token)        // 解析
                .getBody();                   // 获取自定义数据
        return claims;
    }
}