package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class App {
    private static ArrayList<User> users = new ArrayList<>();
    private static User currentUser = null;
    private static HashMap<String, Port> ports = new HashMap<>();

    public static void main(String[] args) throws Exception {
        initializeUsersAndPorts();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Port Management System!");

        while (true) {
            if (currentUser == null) {
                System.out.println("Please log in:");
                System.out.print("Username: ");
                String username = scanner.nextLine();
                System.out.print("Password: ");
                String password = scanner.nextLine();

                // Attempt to log in the user
                currentUser = loginUser(username, password);

                if (currentUser == null) {
                    System.out.println("Invalid username or password. Please try again.");
                } else {
                    System.out.println("Login successful. Welcome, " + currentUser.getUsername() + "!");
                }
            } else {
                // User is logged in, show role-based menu
                showRoleMenu();
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        viewInformation();
                        break;
                    case 2:
                        modifyInformation();
                        break;
                    case 3:
                        processEntities();
                        break;
                    case 4:
                        currentUser = null; // Logout
                        break;
                    default:
                        System.out.println("Invalid choice. Please select a valid option.");
                        break;
                }
            }
        }
    }

    private static void initializeUsersAndPorts() {
        // Initialize predefined users and ports
        User admin = new User("admin", "admin", UserRole.SYSTEM_ADMIN);
        User manager1 = new User("manager1", "password", UserRole.PORT_MANAGER);
        User manager2 = new User("manager2", "password", UserRole.PORT_MANAGER);

        users.add(admin);
        users.add(manager1);
        users.add(manager2);

        Port port1 = new Port("Port1", "Port One", 0.0, 0.0, 1000.0, true);
        Port port2 = new Port("Port2", "Port Two", 0.0, 0.0, 800.0, true);

        ports.put(port1.getId(), port1);
        ports.put(port2.getId(), port2);

        // Assign ports to managers
        manager1.assignPort(port1.getId(), port1);
        manager2.assignPort(port2.getId(), port2);
    }

    private static User loginUser(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null; // User not found or password is incorrect
    }

    private static void showRoleMenu() {
        System.out.println("Role: " + currentUser.getRole());
        System.out.println("Select an option:");
        System.out.println("1. View Information");
        System.out.println("2. Modify Information");
        System.out.println("3. Process Entities");
        System.out.println("4. Logout");
    }

    private static void viewInformation() {
        // Implement viewing information based on user's role
        if (currentUser.getRole() == UserRole.SYSTEM_ADMIN) {
            // Admin can view all information
            System.out.println("Viewing all information...");
        } else if (currentUser.getRole() == UserRole.PORT_MANAGER) {
            // Port manager can view information about their assigned port
            Port assignedPort = currentUser.getAssignedPort();
            if (assignedPort != null) {
                System.out.println("Viewing information for Port: " + assignedPort.getName());
            } else {
                System.out.println("You are not assigned to any port.");
            }
        }
    }

    private static void modifyInformation() {
        // Implement modifying information based on user's role
        // This is a placeholder method for demonstration
        System.out.println("Modifying information...");
    }

    private static void processEntities() {
        // Implement processing entities based on user's role
        // This is a placeholder method for demonstration
        System.out.println("Processing entities...");
    }
}
