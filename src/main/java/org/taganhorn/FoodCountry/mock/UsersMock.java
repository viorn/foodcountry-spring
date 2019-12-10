package org.taganhorn.FoodCountry.mock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.taganhorn.FoodCountry.entities.data.UserEntity;
import org.taganhorn.FoodCountry.services.UsersService;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class UsersMock {

    static public List<UserEntity> getUsers() {
        List<UserEntity> list = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            list.add(new UserEntity(
                    "Пользователь №"+i,
                    "qwerty"+i,
                    "user"+i+"@mock.com",
                    "http://ftp96673.hostfx.ru/photo_2019-12-04_20-09-23.jpg",
                    new ArrayList<String>() {
                        {
                            add("USER");
                        }
                    }
            ));
        }
        return list;
    }
}
