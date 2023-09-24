package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    private static ArrayList<User> users = new ArrayList<>();
    private static User currentUser = null;
    private static HashMap<String, Port> ports = new HashMap<>();

    public static void main(String[] args) throws Exception {
        initializeUsersAndPorts();
        SystemController controller = new SystemController();

        Scanner scanner = new Scanner(System.in);
        System.out.println("COSC2081 GROUP ASSIGNMENT");
        System.out.println("CONTAINER PORT MANAGEMENT SYSTEM");
        System.out.println("Instructor: Mr. Minh Vu & Dr. Phong Ngo");
        System.out.println("Group: Group 26");
        System.out.println("s3978210, Le Dac Duy");
        System.out.println("s4002304, Le Quy Duong");
        System.out.println("s3927046, To Hai Dang");
        System.out.println("------------------------------------------------");

        boolean isRunning = true; // Add a flag to control the application's state

        while (isRunning) {
            System.out.println("1. Login");
            System.out.println("2. Stop System");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    if (currentUser == null) {
                        // Only allow login if not already logged in
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
                            System.out.println("Login successful. Welcome, "
                                    + currentUser.getUsername() + "!");
                        }
                    } else {
                        System.out.println(
                                "You are already logged in as " + currentUser.getUsername() + ".");
                    }
                    break;
                case 2:
                    System.out.println("Stopping the Container Port Management System. Goodbye!");
                    isRunning = false; // Stop the system
                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
                    break;
            }

            if (currentUser != null && isRunning) {
                // User is logged in, show role-based menu
                showRoleMenu();
                choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        // Call methods from the controller for functionalities
                        break;
                    case 2:
                        // Call methods from the controller for functionalities
                        break;
                    case 3:
                        // Call methods from the controller for functionalities
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
}
