package com.ra.config;

import com.ra.security.jwt.CustomAccessDeniedHandler;
import com.ra.security.jwt.JwtEntryPoint;
import com.ra.security.jwt.JwtTokenFiler;
import com.ra.security.user_principal.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {
    @Autowired
    private JwtEntryPoint jwtEntryPoint;
    @Autowired
    private JwtTokenFiler jwtTokenFiler;
    @Autowired
    private CustomAccessDeniedHandler customAccessDeniedHandler;
    @Autowired
    private UserDetailService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // Cấu hình CORS (Cross-Origin Resource Sharing)
                .cors(auth -> auth.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOrigins(List.of("*")); // Chấp nhận tất cả các nguồn gốc (origin)
                    config.setAllowedMethods(List.of("*")); // Chấp nhận tất cả các phương thức HTTP
                    return config;
                }))
                // Tắt CSRF (Cross-Site Request Forgery) protection
                .csrf(AbstractHttpConfigurer::disable)
                // Cung cấp cấu hình xác thực (authentication) và ủy quyền (authorization)
                .authenticationProvider(authenticationProvider())
                // Cho phép tất cả các yêu cầu đến đường dẫn "/auth/**" mà không cần xác thực
                .authorizeHttpRequests((auth) -> auth.requestMatchers("/auth/**").permitAll().anyRequest().authenticated())
                // Xử lý các ngoại lệ xác thực và từ chối truy cập
                .exceptionHandling((auth) -> auth.authenticationEntryPoint(jwtEntryPoint).accessDeniedHandler(customAccessDeniedHandler))
                // Quản lý quản lý phiên (session management) và chọn cách tạo phiên (STATELESS: không lưu trạng thái)
                .sessionManagement((auth) -> auth.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Thêm một bộ lọc trước bộ lọc xác thực mật khẩu để xử lý mã JWT
                .addFilterBefore(jwtTokenFiler, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    // Bean để cung cấp PasswordEncoder cho việc mã hóa mật khẩu
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Bean để cung cấp AuthenticationProvider để sử dụng UserDetails và PasswordEncoder
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
}
