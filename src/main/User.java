package main;

import java.util.HashMap;

public class User {
    private String username;
    private String password;
    private UserRole role;
    private HashMap<String, Port> assignedPorts; // Mapping of usernames to assigned ports

    public User(String username, String password, UserRole role) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.assignedPorts = new HashMap<>();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public UserRole getRole() {
        return role;
    }

    public Port getAssignedPort() {
        return assignedPorts.get(username); // Retrieve the assigned port for the current user
    }

    public boolean canAccessPort(Port port) {
        if (role == UserRole.SYSTEM_ADMIN) {
            return true; // System admin can access all ports
        } else if (role == UserRole.PORT_MANAGER && port != null) {
            // Port manager can access their assigned port
            Port assignedPort = assignedPorts.get(username);
            return assignedPort != null && assignedPort.getId().equals(port.getId());
        }
        return false; // Default to denying access
    }

    // Assign a port to a port manager
    public void assignPort(String username, Port port) {
        if (role == UserRole.SYSTEM_ADMIN) {
            assignedPorts.put(username, port);
        }
    }
}

enum UserRole {
    SYSTEM_ADMIN,
    PORT_MANAGER
}
