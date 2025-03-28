package edu.eci.cvds.labReserves.dto;

public class AuthRequest {

    private String name;
    private String password;

    public AuthRequest() {
    }

    public AuthRequest(String username, String password) {
        this.name = username;
        this.password = password;
    }

    public String getUsername() {
        return name;
    }

    public void setUsername(String username) {
        this.name = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
