package com.elife.MiniProject.dao.enums;


import org.springframework.security.core.authority.SimpleGrantedAuthority;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public enum Role {
    ADMIN(Set.of(Privilege.READ_PRIVILEGE, Privilege.WRITE_PRIVILEGE, Privilege.UPDATE_PRIVILEGE, Privilege.DELETE_PRIVILEGE)),
    MANAGER(Set.of(Privilege.READ_PRIVILEGE, Privilege.WRITE_PRIVILEGE, Privilege.UPDATE_PRIVILEGE)),
    COLLABORATOR(Set.of(Privilege.READ_PRIVILEGE, Privilege.WRITE_PRIVILEGE, Privilege.UPDATE_PRIVILEGE));

    private final Set<Privilege> privileges;

    public List<SimpleGrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = privileges.stream()
                .map(privilege -> new SimpleGrantedAuthority(privilege.name()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
