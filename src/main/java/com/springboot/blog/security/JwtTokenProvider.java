package com.springboot.blog.security;

import com.springboot.blog.exception.BlogAPIException;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class JwtTokenProvider {
    @Value("${jwt.secret}")
    private String Jwtsecret;

    @Value("${jwt.jwtExpirationInMs}")
    private int jwtExpirationInMs;

    //Sinh token
    public String GenerateToken(Authentication authentication){
        String username= authentication.getName();
        Date currentDate=new Date();
        Date ExpireDate=new Date(currentDate.getTime()+ jwtExpirationInMs);
        String token= Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(ExpireDate)
                .signWith(SignatureAlgorithm.HS512,Jwtsecret)
                .compact();
        return token;
    }
    //Lấy username từ trong token
    public String getUsernameFormJWT(String Token){
        Claims claims=Jwts.parser()
                .setSigningKey(Jwtsecret)
                .parseClaimsJws(Token)
                .getBody();
        return claims.getSubject();
    }
    //kiểm tra hạn sử dụng của Token
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(Jwtsecret).parseClaimsJws(token);
            return true;
        }catch (SignatureException ex) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Chữ ký Jwt không hợp lệ");
        }
        catch (ExpiredJwtException ex){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Jwt hết hạn");
        }
        catch (MalformedJwtException ex){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Jwt Không đúng định dạng");
        }
        catch (UnsupportedJwtException ex){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Jwt Không được hỗ trợ");
        }
        catch (IllegalArgumentException ex){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Jwt trống");
        }
    }
}
