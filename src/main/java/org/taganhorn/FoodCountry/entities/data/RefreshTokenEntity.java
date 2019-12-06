package org.taganhorn.FoodCountry.entities.data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "users_refresh_token")
public class RefreshTokenEntity {
    @NotNull
    private Integer userId;

    @Id
    @NotBlank
    @Size(max = 300)
    private String authToken;

    @NotBlank
    @Size(max = 300)
    private String refreshToken;

    @NotNull
    private Date createdDate = new Date();

    public RefreshTokenEntity() {
    }

    public RefreshTokenEntity(Integer userId, String authToken, String refreshToken) {
        this.userId = userId;
        this.authToken = authToken;
        this.refreshToken = refreshToken;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}