package org.taganhorn.FoodCountry.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.taganhorn.FoodCountry.entities.data.UserEntity;
import org.taganhorn.FoodCountry.mock.DishMock;
import org.taganhorn.FoodCountry.mock.UsersMock;
import org.taganhorn.FoodCountry.repositories.DishesRepository;
import org.taganhorn.FoodCountry.repositories.UsersRepository;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.Collections;

@Service
public class InitService {
    @Autowired
    private DishesRepository dishesRepository;
    @Autowired
    private UsersRepository usersRepository;

    @PostConstruct
    @Transactional
    public void init() {
        if (usersRepository.count()==0) {
            //Mock data
            usersRepository.saveAll(UsersMock.getUsers());
        }
        if (dishesRepository.count()==0) {
            //Mock data
            dishesRepository.saveAll(DishMock.getDishes());
        }
    }
}
