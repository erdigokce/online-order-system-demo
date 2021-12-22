package com.onlineordersystem.model;

import com.onlineordersystem.security.roles.UserRole;
import java.util.Collection;
import java.util.Collections;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Getter
@Setter
public class UserDTO extends PrincipleDTO {

    private static final GrantedAuthority USER_ROLE = new UserRole();
    private String address;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(USER_ROLE);
    }

}
