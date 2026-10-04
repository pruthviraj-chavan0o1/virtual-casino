
package com.pruthviraj.virtual_casino.dto;

public class RegisterResponse {

    private Long id;
    private String username;
    private String email;
    private Long virtualCoins;
    private String message;

    public RegisterResponse() {
    }

    public RegisterResponse(Long id, String username, String email,
                            Long virtualCoins, String message) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.virtualCoins = virtualCoins;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public Long getVirtualCoins() {
        return virtualCoins;
    }

    public String getMessage() {
        return message;
    }
}

