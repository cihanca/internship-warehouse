package com.kafein.intern.warehouse.enums;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.google.common.collect.Sets;
import java.util.Set;
import java.util.stream.Collectors;

import static com.kafein.intern.warehouse.enums.Permission.*;

public enum RoleBasedPermission {

    ROLE_USER(Sets.newHashSet()),
    ROLE_ADMIN(Sets.newHashSet(USER_READ, USER_WRITE, ADMIN_READ, ADMIN_WRITE)),
    ROLE_READER(Sets.newHashSet(USER_READ, ADMIN_READ));

    private final Set<Permission> permissions;

    RoleBasedPermission(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority( this.name()));
        return permissions;
    }
}
