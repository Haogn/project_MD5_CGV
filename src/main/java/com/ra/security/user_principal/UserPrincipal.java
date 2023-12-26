package com.ra.security.user_principal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ra.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserPrincipal implements UserDetails {
    private Long id;
    private String email;
    private String userName;
    @JsonIgnore
    private String password;
    private Boolean status;
    private Date birthday; // Ngày sinh nhật của thành viên
    private String membershipLevel; // Cấp độ thành viên (Bronze, Silver, Gold, Platinum, v.v.)
    private Collection<? extends GrantedAuthority> authorities;

    public static UserDetails build(Users users) {
        UserPrincipal userPrincipal = UserPrincipal.builder()
                .id(users.getId())
                .email(users.getEmail())
                .userName(users.getUserName())
                .password(users.getPassword())
                .status(users.getStatus())
                .birthday(users.getBirthday())
                .membershipLevel(users.getMemberLevers().name())
                .authorities(users.getRoles().stream().map(item -> new SimpleGrantedAuthority(item.getRoleName().name())).collect(Collectors.toList()))
                .build();
        return userPrincipal;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
