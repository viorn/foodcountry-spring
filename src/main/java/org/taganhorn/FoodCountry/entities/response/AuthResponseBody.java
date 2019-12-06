package org.taganhorn.FoodCountry.entities.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.taganhorn.FoodCountry.entities.data.UserEntity;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthResponseBody {
    String token;
    String refreshToken;

    @JsonIgnoreProperties(value = {"tokens"})
    UserEntity user;

    public AuthResponseBody() {
    }

    public AuthResponseBody(String token, String refreshToken, UserEntity user) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
