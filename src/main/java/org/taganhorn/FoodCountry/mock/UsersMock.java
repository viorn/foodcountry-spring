package org.taganhorn.FoodCountry.mock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.taganhorn.FoodCountry.entities.data.UserEntity;
import org.taganhorn.FoodCountry.services.UserService;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class UsersMock {

    @Autowired
    private UserService userService;

    public UsersMock() {
    }

    @PostConstruct
    public void init() {
        userService.putUsers(getUsers());
    }

    static public List<UserEntity> getUsers() {
        return new ArrayList<UserEntity>() {{
            add(new UserEntity(
                    "Василий",
                    "qwerty1",
                    "vasia@mock.com",
                    "https://i.pinimg.com/originals/1c/ba/1e/1cba1e5e40356f6edb0235c8a09a07d5.jpg",
                    new ArrayList<String>() {
                        {
                            add("USER");
                            add("ADMIN");
                        }
                    }
            ));
            add(new UserEntity(
                    "Юлия",
                    "qwerty2",
                    "null",
                    "https://cdn25.img.ria.ru/images/148839/96/1488399659_0:0:960:960_600x600_80_0_1_b5d1bbc3011e64dcc29927f4f599fc75.jpg",
                    new ArrayList<String>() {
                        {
                            add("USER");
                        }
                    }
            ));
            add(new UserEntity(
                    "Иван",
                    "qwerty3",
                    "ivan@mock.com",
                    " https://ichef.bbci.co.uk/news/660/cpsprodpb/14236/_104368428_gettyimages-543560762.jpg",
                    new ArrayList<String>() {
                        {
                            add("USER");
                        }
                    }
            ));
            add(new UserEntity(
                     "Владимир",
                    "qwerty5",
                    "null2",
                    "https://i.pinimg.com/originals/1c/ba/1e/1cba1e5e40356f6edb0235c8a09a07d5.jpg",
                    new ArrayList<String>() {
                        {
                            add("USER");
                        }
                    }
            ));
            add(new UserEntity(
                    "Ольга",
                    "qwerty6",
                    "olga@mock.com",
                    "https://cdn25.img.ria.ru/images/148839/96/1488399659_0:0:960:960_600x600_80_0_1_b5d1bbc3011e64dcc29927f4f599fc75.jpg",
                    new ArrayList<String>() {
                        {
                            add("USER");
                        }
                    }
            ));
            add(new UserEntity(
                    "Коша",
                    "qwerty7",
                    "cosha_yu@mock.com",
                    "http://ftp96673.hostfx.ru/photo_2019-12-04_20-09-23.jpg",
                    new ArrayList<String>() {
                        {
                            add("SUPERADMIN");
                            add("USER");
                        }
                    }
            ));
        }};
    }
}
