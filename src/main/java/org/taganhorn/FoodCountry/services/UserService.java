package org.taganhorn.FoodCountry.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.taganhorn.FoodCountry.entities.data.RefreshTokenEntity;
import org.taganhorn.FoodCountry.entities.data.UserEntity;
import org.taganhorn.FoodCountry.entities.response.AuthResponseBody;
import org.taganhorn.FoodCountry.repositories.RefreshTokenRepository;
import org.taganhorn.FoodCountry.repositories.UsersRepository;
import org.taganhorn.FoodCountry.security.JwtTokenUtil;

import javax.transaction.Transactional;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class UserService {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public List<UserEntity> getAllUsers() {
        return usersRepository.findAll();
    }

    @Transactional
    public void putUsers(List<UserEntity> userEntities) {
        usersRepository.saveAll(userEntities);
    }

    @Transactional
    public UserEntity getUser() {
        try {
            UserEntity userEntity = usersRepository.findById(1).get();
            Hibernate.initialize(userEntity.getRoles());
            return userEntity;
        } catch (NoSuchElementException n) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "USER_IS_NOT_FOUNDED");
        }
    }

    @Transactional
    public UserEntity getUserById(Integer userId) {
        try {
            UserEntity userEntity = usersRepository.findById(userId).get();
            Hibernate.initialize(userEntity.getRoles());
            return userEntity;
        } catch (NoSuchElementException n) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "USER_IS_NOT_FOUNDED");
        }
    }

    @Transactional
    public UserEntity findUserByNameAndPassword(String name, String password) {
        try {
            UserEntity userEntity = usersRepository.findByEmail(name).get();
            if (!userEntity.getPassword().equals(password)) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "PASSWORD_IS_WRONG");
            }
            Hibernate.initialize(userEntity.getRoles());
            return userEntity;
        } catch (NoSuchElementException n) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "USER_IS_NOT_FOUNDED");
        }
    }

    @Transactional
    public AuthResponseBody generateToken(String name, String password) {
        ReentrantLock lock = null;
        try {
            UserEntity userEntity = usersRepository.findByEmail(name).get();
            if (!userEntity.getPassword().equals(password)) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Password is wrong");
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
