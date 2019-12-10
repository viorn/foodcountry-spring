package org.taganhorn.FoodCountry.entities.response;

import org.taganhorn.FoodCountry.entities.data.DishEntity;

public class DishResponseBody {
    private DishEntity dishEntity;

    public DishResponseBody() {
    }

    public DishResponseBody(DishEntity dishEntity) {
        this.dishEntity = dishEntity;
    }

    public DishEntity getDishEntity() {
        return dishEntity;
    }

    public void setDishEntity(DishEntity dishEntity) {
        this.dishEntity = dishEntity;
    }
}
