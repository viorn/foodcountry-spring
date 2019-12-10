package org.taganhorn.FoodCountry.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.taganhorn.FoodCountry.entities.data.DishEntity;
import org.taganhorn.FoodCountry.entities.data.UserEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface DishesRepository extends JpaRepository<DishEntity, Long> {
}
