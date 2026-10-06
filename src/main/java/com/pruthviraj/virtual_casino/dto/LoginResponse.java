
package com.pruthviraj.virtual_casino.dto;

public class LoginResponse {

    private Long id;
    private String username;
    private Long virtualCoins;
    private String token;
    private String message;

    public LoginResponse() {
    }

    public LoginResponse(
            Long id,
            String username,
            Long virtualCoins,
            String token,
            String message) {

        this.id = id;
        this.username = username;
        this.virtualCoins = virtualCoins;
        this.token = token;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Long getVirtualCoins() {
        return virtualCoins;
    }

    public String getToken() {
        return token;
    }

    public String getMessage() {
        return message;
    }
}
