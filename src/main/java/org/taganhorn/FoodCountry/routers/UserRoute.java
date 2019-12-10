package org.taganhorn.FoodCountry.routers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.taganhorn.FoodCountry.entities.data.UserEntity;
import org.taganhorn.FoodCountry.entities.request.AuthRequestBody;
import org.taganhorn.FoodCountry.entities.request.RefreshTokenRequestBody;
import org.taganhorn.FoodCountry.entities.response.AuthResponseBody;
import org.taganhorn.FoodCountry.entities.response.SimpleResponse;
import org.taganhorn.FoodCountry.entities.response.UserResponseBody;
import org.taganhorn.FoodCountry.entities.response.UsersListResponseBody;
import org.taganhorn.FoodCountry.security.JwtTokenUtil;
import org.taganhorn.FoodCountry.security.UserPrincipal;
import org.taganhorn.FoodCountry.services.UsersService;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RestController()
@RequestMapping("user")
public class UserRoute {
    @Autowired
    private UsersService usersService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("auth/email")
    Mono<AuthResponseBody> auth(@RequestBody AuthRequestBody body) {
        return Mono.fromCallable(() -> {
            return usersService.generateToken(body.getName(), body.getPassword());
        }).subscribeOn(Schedulers.elastic());
    }

    @PostMapping("auth/refresh")
    Mono<AuthResponseBody> refresh(@RequestHeader("Authorization") String authorizationHeader,
                                   @RequestBody RefreshTokenRequestBody body) {
        return Mono.fromCallable(() -> {
            if (authorizationHeader.isEmpty())
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "TOKEN_IS_NOT_FOUNDED");
            String authToken = authorizationHeader.substring(7);
            return usersService.generateTokenByRefreshToken(authToken, body.getRefreshToken());
        }).subscribeOn(Schedulers.elastic());
    }

    @PostMapping("logout")
    Mono<SimpleResponse> logout(Authentication authentication) {
        return Mono.fromCallable(() -> {
            String authToken = (String) ((UsernamePasswordAuthenticationToken) authentication).getCredentials();
            usersService.removeRefreshToken(authToken);
            return SimpleResponse.SUCCESS;
        }).subscribeOn(Schedulers.elastic());
    }

    @GetMapping
    Mono<UserResponseBody> getUser(Authentication authentication) {
        return Mono.fromCallable(() -> {
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            UserEntity userEntity = usersService.getUserById(userPrincipal.getId());
            userEntity.setTokens(null);
            return new UserResponseBody(userEntity);
        }).subscribeOn(Schedulers.elastic());
    }

    @GetMapping("{id}")
    Mono<UserResponseBody> getUserById(@PathVariable("id") Long userId) {
        return Mono.fromCallable(() -> {
            UserEntity userEntity = usersService.getUserById(userId);
            userEntity.setTokens(null);
            return new UserResponseBody(userEntity);
        }).subscribeOn(Schedulers.elastic());
    }

    @GetMapping("list")
    Mono<UsersListResponseBody> getAllUser(@RequestParam Integer page, @RequestParam Integer limit) {
        return Mono.fromCallable(() -> {
            return usersService.getUsersList(page, limit);
        }).subscribeOn(Schedulers.elastic());
    }
}
