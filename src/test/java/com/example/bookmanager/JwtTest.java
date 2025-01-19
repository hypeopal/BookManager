package com.example.bookmanager;

import com.example.bookmanager.Utils.JWTUtil;
import com.example.bookmanager.Utils.UserClaims;
import org.junit.jupiter.api.Test;

public class JwtTest {
    @Test
    public void testGen() {
        UserClaims claims = new UserClaims("1", "admin");
        String token = JWTUtil.generateToken(claims);
        System.out.println(token);
    }
    @Test
    public void testParse() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyTmFtZSI6ImFkbWluIiwidXNlcklkIjoiMSIsImV4cCI6MTczNzMwNDE2OCwiaWF0IjoxNzM3Mjk2OTY4fQ.iYgtsown1z6MbsA4_qH2DSK2LdXnbIsOKL0wo8fjPhM";
        UserClaims claims = JWTUtil.parseToken(token);
        System.out.println(JWTUtil.isExpired(token));
        System.out.println(claims.toString());
    }
}
