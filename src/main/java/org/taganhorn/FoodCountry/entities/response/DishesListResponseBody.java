package org.taganhorn.FoodCountry.entities.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.taganhorn.FoodCountry.entities.data.DishEntity;

import java.util.List;

public class DishesListResponseBody extends ListResponseBody<DishEntity> {
    public DishesListResponseBody() {
    }

    public DishesListResponseBody(List<DishEntity> list, int page, int totalPage, int limit, long totalItems) {
        super(list, page, totalPage, limit, totalItems);
    }
}
