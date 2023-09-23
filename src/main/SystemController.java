package main;

import java.util.HashMap;

public class SystemController {
    private HashMap<String, Port> ports;

    public SystemController(HashMap<String, Port> ports) {
        this.ports = ports;
    }

    public void listAllContainers(User user) {
        if (user.getRole() == UserRole.SYSTEM_ADMIN) {
            // Admin logic for listing all containers
            System.out.println("Listing all containers as an admin...");
        } else if (user.getRole() == UserRole.PORT_MANAGER) {
            Port assignedPort = user.getAssignedPort();
            if (assignedPort != null) {
                // Port manager logic for listing containers of their assigned port
                System.out.println("Listing containers for Port: " + assignedPort.getName());
            } else {
                System.out.println("You are not assigned to any port.");
            }
        }
    }
}

