package org.taganhorn.FoodCountry.entities.response;

public class SimpleResponse {
    public static final SimpleResponse SUCCESS = new SimpleResponse("success");

    private String message;

    public SimpleResponse() {
    }

    public SimpleResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
