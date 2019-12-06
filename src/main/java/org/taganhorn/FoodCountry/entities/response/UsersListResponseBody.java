package org.taganhorn.FoodCountry.entities.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.taganhorn.FoodCountry.entities.data.UserEntity;

import java.util.List;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class UsersListResponseBody {
    @JsonIgnoreProperties(value = {"roles", "tokens"})
    private List<UserEntity> list;

    public UsersListResponseBody(List<UserEntity> list) {
        this.list = list;
    }

    public List<UserEntity> getList() {
        return list;
    }

    public void setList(List<UserEntity> list) {
        this.list = list;
    }
}
