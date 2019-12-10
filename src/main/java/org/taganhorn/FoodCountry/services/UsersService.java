package org.taganhorn.FoodCountry.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.taganhorn.FoodCountry.entities.data.DishEntity;
import org.taganhorn.FoodCountry.entities.data.RefreshTokenEntity;
import org.taganhorn.FoodCountry.entities.data.UserEntity;
import org.taganhorn.FoodCountry.entities.response.AuthResponseBody;
import org.taganhorn.FoodCountry.entities.response.DishesListResponseBody;
import org.taganhorn.FoodCountry.entities.response.UsersListResponseBody;
import org.taganhorn.FoodCountry.mock.UsersMock;
import org.taganhorn.FoodCountry.repositories.RefreshTokenRepository;
import org.taganhorn.FoodCountry.repositories.UsersRepository;
import org.taganhorn.FoodCountry.security.JwtTokenUtil;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class UsersService {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public UsersListResponseBody getUsersList(int page, int limit) {
        Page<UserEntity> pages = usersRepository.findAll(PageRequest.of(page-1, limit));
        return new UsersListResponseBody(
                pages.getContent(),
                page,
                pages.getTotalPages(),
                limit,
                pages.getTotalElements()
        );
    }

    @Transactional
    public void putUsers(List<UserEntity> userEntities) {
        usersRepository.saveAll(userEntities);
    }

    @Transactional
    public UserEntity getUserById(Long userId) {
        try {
            UserEntity userEntity = usersRepository.findById(userId).get();
            Hibernate.initialize(userEntity.getRoles());
            return userEntity;
        } catch (NoSuchElementException n) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "USER_IS_NOT_FOUNDED");
        }
    }

    @Transactional
    public AuthResponseBody generateToken(String name, String password) {
        if (name.equals("SYSTEM")) throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "USER_IS_NOT_FOUNDED");
        try {
            UserEntity userEntity = usersRepository.findByEmail(name).get();
            if (!userEntity.getPassword().equals(password)) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "PASSWORD_IS_WRONG");
            }
            String token = jwtTokenUtil.generateToken(userEntity);
            String refreshToken = jwtTokenUtil.generateRefreshToken(token);
            refreshTokenRepository.save(new RefreshTokenEntity(
                    userEntity.getId(),
                    token,
                    refreshToken
            ));
            return new AuthResponseBody(
                    token,
                    refreshToken,
                    userEntity
            );
        } catch (NoSuchElementException n) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "USER_IS_NOT_FOUNDED");
        }
    }

    @Transactional
    public AuthResponseBody generateTokenByRefreshToken(String mAuthToken, String mRefreshToken) {
        ReentrantLock lock = null;
        try {
            RefreshTokenEntity refreshTokenEntity = refreshTokenRepository.findById(mAuthToken).get();
            if (!refreshTokenEntity.getRefreshToken().equals(mRefreshToken)) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "TOKEN_IS_NOT_FOUNDED");
            }

            UserEntity userEntity = usersRepository.findById(refreshTokenEntity.getUserId()).get();
            String token = jwtTokenUtil.generateToken(userEntity);
            String refreshToken = jwtTokenUtil.generateRefreshToken(token);
            refreshTokenRepository.save(new RefreshTokenEntity(
                    userEntity.getId(),
                    token,
                    refreshToken
            ));
            refreshTokenRepository.deleteById(mAuthToken);
            return new AuthResponseBody(
                    token,
                    refreshToken,
                    userEntity
            );
        } catch (NoSuchElementException n) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "USER_IS_NOT_FOUNDED");
        }
    }

    @Transactional
    public void removeRefreshToken(String authToken) {
        try {
            refreshTokenRepository.deleteById(authToken);
        } catch (Throwable t) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "TOKEN_IS_NOT_FOUNDED");
        }
    }
}
