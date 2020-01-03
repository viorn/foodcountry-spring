package org.taganhorn.FoodCountry.entities.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.taganhorn.FoodCountry.entities.data.UserEntity;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class UsersListResponseBody extends ListResponseBody<UsersListResponseBody.User> {
    public UsersListResponseBody() {
    }

    public UsersListResponseBody(List<UserEntity> list, int page, int totalPage, int limit, long totalItems) {
        super(User.buildUser(list), page, totalPage, limit, totalItems);
    }

    @Override
    @JsonIgnoreProperties(value = { "roles", "tokens" })
    public List<User> getList() {
        return super.getList();
    }

    public static class User {
        public Long id;
        public String name;
        public String email;
        public String avatarUrl;

        static User buildUser(UserEntity userEntity) {
            User user = new User();
            user.id = userEntity.getId();
            user.name = userEntity.getName();
            user.email = userEntity.getEmail();
            user.avatarUrl = userEntity.getAvatarUrl();
            return user;
        }

        static List<User> buildUser(List<UserEntity> userEntity) {
            return userEntity.stream().map(new Function<UserEntity,User>() {
                @Override
                public User apply(UserEntity t) {
                    return User.buildUser(t);
                } 
            }).collect(Collectors.toList());
        }
    }
}
