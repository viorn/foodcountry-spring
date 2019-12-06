package org.taganhorn.FoodCountry.security;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthenticationManager implements ReactiveAuthenticationManager {

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String authToken = authentication.getCredentials().toString();
        String username;
        try {
            username = jwtTokenUtil.getUsernameFromToken(authToken);
        } catch (Exception e) {
            username = null;
        }
        if (username != null && jwtTokenUtil.validateToken(authToken)) {
            Integer id = jwtTokenUtil.getUserIdFromToken(authToken);
            List<String> rolesMap = jwtTokenUtil.getRoleFromToken(authToken);
            System.out.println(Arrays.toString(rolesMap.toArray()));
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    new UserPrincipal(id, username),
                    authToken,
                    rolesMap.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())
            );
            return Mono.just(auth);
        } else {
            return Mono.empty();
        }
    }
}
