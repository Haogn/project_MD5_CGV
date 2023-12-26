package com.ra.security.jwt;

import com.ra.security.user_principal.UserPrincipal;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {
    // TODO : nơi sinh ra token và lấy ra UserLogin

    private final Logger logger = LoggerFactory.getLogger(JwtEntryPoint.class);

    @Value("${jwt.secret-key}")
    private String SECRET_KEY;

    @Value("${jwt.expired}")
    private Long EXPIRED ;

    // TODO : phương thức tạo ra token
    public String generateToken(UserPrincipal userPrincipal) {
        return Jwts.builder().setSubject(userPrincipal.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + EXPIRED))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    // TODO : Phương thức kiểm tra tính hợp lệ của token
    public boolean validateToken(String token) {
        try {
            // Parse và kiểm tra chữ ký của token
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token); // Lưu ý chính tả
            return true;
        } catch (ExpiredJwtException e) {
            // Xử lý khi token đã hết hạn
            logger.error("Failed -> Expired Token Message {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            // Xử lý khi token không được hỗ trợ
            logger.error("Failed -> Unsupported Token Message {}", e.getMessage());
        } catch (MalformedJwtException e) {
            // Xử lý khi token có định dạng không hợp lệ
            logger.error("Failed -> Invalid Format Token Message {}", e.getMessage());
        } catch (SignatureException e) {
            // Xử lý khi chữ ký của token không hợp lệ
            logger.error("Failed -> Invalid Signature Token Message {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            // Xử lý khi đối số truyền vào phương thức không hợp lệ
            logger.error("Failed -> Claims Empty Token Message {}", e.getMessage());
        }
        return false;
    }

    // TODO : phương thức lấy tên người dùng từ token
    public String getUsernameFromToken(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
    }
}
