package org.taganhorn.FoodCountry.mock;

import org.taganhorn.FoodCountry.entities.data.DishEntity;
import org.taganhorn.FoodCountry.entities.data.UserEntity;
import org.taganhorn.FoodCountry.services.InitService;
import org.taganhorn.FoodCountry.services.UsersService;

import java.util.ArrayList;
import java.util.List;

public abstract class DishMock {
    static public List<DishEntity> getDishes() {
        List<DishEntity> list = new ArrayList<DishEntity>();
        for (int i = 1; i <= 100; i++) {
            list.add(new DishEntity(
                    null,
                    "Блюдо №"+i,
                    "Обисание блюда №"+i
            ));
        }
        return list;
    }
}
