package org.taganhorn.FoodCountry.security;

import org.springframework.security.core.AuthenticatedPrincipal;

public class UserPrincipal implements AuthenticatedPrincipal {
    Integer id;
    String name;

    public UserPrincipal(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public String getName() {
        return null;
    }
}
