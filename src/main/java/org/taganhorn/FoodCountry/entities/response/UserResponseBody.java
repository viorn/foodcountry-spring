package org.taganhorn.FoodCountry.entities.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.taganhorn.FoodCountry.entities.data.UserEntity;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponseBody {
    @JsonIgnoreProperties(value = {"tokens"})
    private UserEntity user;

    public UserResponseBody() {
    }

    public UserResponseBody(UserEntity userEntity) {
        this.user = userEntity;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
