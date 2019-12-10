package org.taganhorn.FoodCountry.entities.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.taganhorn.FoodCountry.entities.data.UserEntity;

import java.util.List;


public class UsersListResponseBody extends ListResponseBody<UserEntity> {
    public UsersListResponseBody() {
    }

    public UsersListResponseBody(List<UserEntity> list, int page, int totalPage, int limit, long totalItems) {
        super(list, page, totalPage, limit, totalItems);
    }

    @Override
    @JsonIgnoreProperties(value = {"roles", "tokens"})
    public List<UserEntity> getList() {
        return super.getList();
    }
}
