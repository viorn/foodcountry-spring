package org.taganhorn.FoodCountry.security;

import org.springframework.security.core.AuthenticatedPrincipal;

public class UserPrincipal implements AuthenticatedPrincipal {
    Long id;
    String name;

    public UserPrincipal(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String getName() {
        return null;
    }
}
