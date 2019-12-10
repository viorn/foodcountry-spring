package org.taganhorn.FoodCountry.routers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.taganhorn.FoodCountry.entities.response.DishResponseBody;
import org.taganhorn.FoodCountry.entities.response.DishesListResponseBody;
import org.taganhorn.FoodCountry.services.DishesService;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RestController
@RequestMapping("dish")
public class DishRoute {
    @Autowired
    private DishesService dishesService;

    @GetMapping("list")
    Mono<DishesListResponseBody> getAllDishes(@RequestParam Integer page, @RequestParam Integer limit) {
        if (page == null) page = 10;
        final Integer finalPage = page;
        return Mono.fromCallable(() -> {
            return dishesService.getDishesList(finalPage, limit);
        }).subscribeOn(Schedulers.elastic());
    }

    @GetMapping("{id}")
    Mono<DishResponseBody> getAllDishes(@PathVariable("id") Long id) {
        return Mono.fromCallable(() -> {
            return dishesService.getDishesById(id);
        }).subscribeOn(Schedulers.elastic());
    }
}
