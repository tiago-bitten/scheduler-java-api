package com.labi.schedulerjava.adapters.security;

import com.labi.schedulerjava.core.domain.model.BaseEntity;
import com.labi.schedulerjava.core.domain.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

public class UserDetailsImp extends BaseEntity implements UserDetails {

    private String name;
    private String email;
    private String password;
    private Boolean isApproved;
    private Boolean isSuperUser;
    private Instant createdAt;

    public UserDetailsImp(Long id, String name, String email, String password, Boolean isApproved, Boolean isSuperUser, Instant createdAt) {
        super.setId(id);
        this.name = name;
        this.email = email;
        this.password = password;
        this.isApproved = isApproved;
        this.isSuperUser = isSuperUser;
        this.createdAt = createdAt;
    }

    public static UserDetailsImp build(User user) {
        return new UserDetailsImp(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getIsApproved(),
                user.getIsSuperUser(),
                user.getCreatedAt()
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
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
