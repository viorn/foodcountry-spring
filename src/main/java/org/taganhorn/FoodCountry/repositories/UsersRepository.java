package org.taganhorn.FoodCountry.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.taganhorn.FoodCountry.entities.data.UserEntity;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByEmail(String email);
}
