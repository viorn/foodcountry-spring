package org.taganhorn.FoodCountry.entities.request;

public class RefreshTokenRequestBody {
    private String refreshToken;

    public RefreshTokenRequestBody() {
    }

    public RefreshTokenRequestBody(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
