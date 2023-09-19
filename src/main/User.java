package main;

import java.util.ArrayList;

public class User {
    private String username;
    private String password;
    private String role;
    public ArrayList<Port> authorizedPorts;

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
