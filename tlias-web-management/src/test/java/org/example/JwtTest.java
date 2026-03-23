package org.example;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTest {
    // 生成JWT令牌， builder方法
    @Test
    public void testGenerateJwt(){
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("id", 1);
        dataMap.put("username", "admin");
        String jwt = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, "itehima") // 指定加密算法，密钥
                .addClaims(dataMap) // 添加自定义信息
                .setExpiration(new Date(System.currentTimeMillis() + 3600 * 1000)) // 设置过期时间1个小时
                .compact();// 生成令牌
        System.out.println(jwt);
    }

    // 解析Jwt令牌 parse()
    // 令牌被篡改或者过期，都会报错
    @Test
    public void testParseJWT(){
        String token="EyJhbGciOiJIUzI1NiJ9.eyJpZCI6MSwidXNlcm5hbWUiOiJhZG1pbiIsImV4cCI6MTc3MzY0NDU3Nn0.KOGa_knn3oX45hm0dgdl3riwG4N7jJHCnPD_PCUo-cY";
        Claims claims = Jwts.parser()
                .setSigningKey("itehima")  // 指定密钥
                .parseClaimsJws(token)     // 解析
                .getBody(); // getBody() 得到上面自定义数据 dataMap:{id=1, username=admin, exp=1773644576}
        System.out.println(claims);
    }
}
