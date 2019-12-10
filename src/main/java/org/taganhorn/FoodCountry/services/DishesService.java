package org.taganhorn.FoodCountry.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.taganhorn.FoodCountry.entities.data.DishEntity;
import org.taganhorn.FoodCountry.entities.data.UserEntity;
import org.taganhorn.FoodCountry.entities.response.DishResponseBody;
import org.taganhorn.FoodCountry.entities.response.DishesListResponseBody;
import org.taganhorn.FoodCountry.mock.DishMock;
import org.taganhorn.FoodCountry.mock.UsersMock;
import org.taganhorn.FoodCountry.repositories.DishesRepository;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.NoSuchElementException;

@Service
public class DishesService {
    @Autowired
    private DishesRepository dishesRepository;

    @Transactional
    public DishesListResponseBody getDishesList(int page, int limit) {
        Page<DishEntity> pages = dishesRepository.findAll(PageRequest.of(page-1, limit));
        return new DishesListResponseBody(
                pages.getContent(),
                page,
                pages.getTotalPages(),
                limit,
                pages.getTotalElements()
        );
    }

    @Transactional
    public DishResponseBody getDishesById(Long dishId) {
        try {
            DishEntity dishEntity = dishesRepository.findById(dishId).get();
            return new DishResponseBody(
                    dishEntity
            );
        } catch (NoSuchElementException n) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "DISH_IS_NOT_FOUNDED");
        }
    }
}
