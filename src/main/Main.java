package main;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
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

        boolean isRunning = true;

        while (isRunning) {
            System.out.println("1. Login");
            System.out.println("2. Stop System");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    if (!controller.isLoggedIn()) {
                        // Only allow login if not already logged in
                        System.out.println("Please log in:");
                        System.out.print("Username: ");
                        String username = scanner.nextLine();
                        System.out.print("Password: ");
                        String password = scanner.nextLine();

                        // Attempt to log in the user
                        controller.login(username, password);

                        if (controller.isLoggedIn()) {
                            System.out.println("Login successful. Welcome, " + username + "!");
                        } else {
                            System.out.println("Invalid username or password. Please try again.");
                        }
                    } else {
                        System.out.println("You are already logged in.");
                    }
                    break;
                case 2:
                    System.out.println("Stopping the Container Port Management System. Goodbye!");
                    isRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
                    break;
            }

            if (controller.isLoggedIn() && isRunning) {
                boolean backToRoleMenu = true; // Add a flag to return to the role menu
                while (backToRoleMenu) {
                    // User is logged in, show role-based menu
                    controller.showRoleMenu();
                    int roleChoice = Integer.parseInt(scanner.nextLine());

                    switch (roleChoice) {
                        case 1:
                            if (controller.getCurrentUser().getRole() == UserRole.PORT_MANAGER) {
                                // You can add code specific to port manager role here
                            } else if (controller.getCurrentUser().getRole() == UserRole.SYSTEM_ADMIN) {
                                // You can add code specific to admin role here
                            }
                            break;
                        case 2:
                            controller.createContainer(scanner);
                            break;
                        case 3:
                            // Call methods from the controller for other functionalities
                            break;
                        case 0:
                            // Logout
                            controller.logout();
                            backToRoleMenu = false;
                            break;
                        default:
                            System.out.println("Invalid choice. Please select a valid option.");
                            break;
                    }
                }
            }
        }
    }
}
