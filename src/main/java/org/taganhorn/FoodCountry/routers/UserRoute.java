package org.taganhorn.FoodCountry.routers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.taganhorn.FoodCountry.entities.data.UserEntity;
import org.taganhorn.FoodCountry.entities.request.AuthRequestBody;
import org.taganhorn.FoodCountry.entities.request.RefreshTokenRequestBody;
import org.taganhorn.FoodCountry.entities.response.AuthResponseBody;
import org.taganhorn.FoodCountry.entities.response.SimpleResponse;
import org.taganhorn.FoodCountry.entities.response.UserResponseBody;
import org.taganhorn.FoodCountry.entities.response.UsersListResponseBody;
import org.taganhorn.FoodCountry.security.JwtTokenUtil;
import org.taganhorn.FoodCountry.security.UserPrincipal;
import org.taganhorn.FoodCountry.services.UserService;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Iterator;
import java.util.List;

@RestController()
@RequestMapping("user")
public class UserRoute {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("auth/email")
    Mono<AuthResponseBody> auth(@RequestBody AuthRequestBody body) {
        return Mono.fromCallable(() -> {
            return userService.generateToken(body.getName(), body.getPassword());
        }).subscribeOn(Schedulers.elastic());
    }

    @PostMapping("auth/refresh")
    Mono<AuthResponseBody> refresh(@RequestBody RefreshTokenRequestBody body,
                                   Authentication authentication) {
        return Mono.fromCallable(() -> {
            String authToken = (String) ((UsernamePasswordAuthenticationToken) authentication).getCredentials();
            return userService.generateTokenByRefreshToken(authToken, body.getRefreshToken());
        }).subscribeOn(Schedulers.elastic());
    }

    @PostMapping("logout")
    Mono<SimpleResponse> logout(Authentication authentication) {
        return Mono.fromCallable(()->{
            String authToken = (String) ((UsernamePasswordAuthenticationToken) authentication).getCredentials();
            userService.removeRefreshToken(authToken);
            return SimpleResponse.SUCCESS;
        }).subscribeOn(Schedulers.elastic());
    }

    @GetMapping
    Mono<UserResponseBody> getUser(Authentication authentication) {
        return Mono.fromCallable(() -> {
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            UserEntity userEntity = userService.getUserById(userPrincipal.getId());
            userEntity.setTokens(null);
            return new UserResponseBody(userEntity);
        }).subscribeOn(Schedulers.elastic());
    }

    @GetMapping("{id}")
    Mono<UserResponseBody> getUserById(@PathVariable("id") Integer userId) {
        return Mono.fromCallable(() -> {
            UserEntity userEntity = userService.getUserById(userId);
            userEntity.setTokens(null);
            return new UserResponseBody(userEntity);
        }).subscribeOn(Schedulers.elastic());
    }

    @GetMapping("all")
    Mono<UsersListResponseBody> getAllUser() {
        return Mono.fromCallable(() -> {
            return new UsersListResponseBody(userService.getAllUsers());
        }).subscribeOn(Schedulers.elastic());
    }
}
