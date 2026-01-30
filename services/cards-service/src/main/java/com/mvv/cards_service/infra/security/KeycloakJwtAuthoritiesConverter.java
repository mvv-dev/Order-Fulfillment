package com.mvv.cards_service.infra.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class KeycloakJwtAuthoritiesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {

        // Realm roles
        Set<String> roles = new HashSet<>(extractRealmRoles(jwt));

        return roles.stream()
                .map(role -> role.startsWith("ROLE_") ? role : "ROLE_" + role)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());

    }

    private Collection<String> extractRealmRoles(Jwt jwt) {

        Object realmAccessObj = jwt.getClaim("realm_access");
        if(!(realmAccessObj instanceof Map<?,?> realmAccess)) return List.of();

        Object rolesObj = realmAccess.get("roles");
        if(!(rolesObj instanceof Collection<?> roles)) return List.of();


        return roles.stream().filter(String.class::isInstance).map(String.class::cast).toList();

    }

}
