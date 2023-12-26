package com.ra.security.jwt;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.util.Objects;

@Component
public class JwtEntryPoint implements AuthenticationEntryPoint {
    // TODO :  điểm cấp lỗi

    private final Logger logger = LoggerFactory.getLogger(JwtEntryPoint.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // Ghi log lỗi khi xác thực không thành công
        logger.error("Un Authentication", authException.getMessage());

        // Tạo đối tượng ResponseEntity với thông báo lỗi và mã trạng thái HTTP 401 (UNAUTHORIZED)
        ResponseEntity<String> responseEntity = new ResponseEntity<>("Un Authentication", HttpStatus.UNAUTHORIZED);

        // Thiết lập mã trạng thái của response và ghi thông báo lỗi vào body của response
        response.setStatus(responseEntity.getStatusCodeValue());
        response.getWriter().write(Objects.requireNonNull(responseEntity.getBody()));
    }
}
