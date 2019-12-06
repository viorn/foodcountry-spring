package org.taganhorn.FoodCountry.entities.request;

public class AuthRequestBody {
    private String name;
    private String password;

    public AuthRequestBody() {
    }

    public AuthRequestBody(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}
