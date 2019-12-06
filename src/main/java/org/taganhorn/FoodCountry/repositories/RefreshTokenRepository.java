package org.taganhorn.FoodCountry.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.taganhorn.FoodCountry.entities.data.RefreshTokenEntity;
import org.taganhorn.FoodCountry.entities.data.UserEntity;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, String> {
}
