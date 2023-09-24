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
                            }
                            if (controller.getCurrentUser().getRole() == UserRole.SYSTEM_ADMIN) {
                                controller.createPort(scanner);
                            }
                            break;
                        case 2:
                            if (controller.getCurrentUser().getRole() == UserRole.SYSTEM_ADMIN) {
                                controller.listAllPorts();
                            }
                            break;
                        case 3:
                            if (controller.getCurrentUser().getRole() == UserRole.SYSTEM_ADMIN) {
                                controller.updatePort(scanner);
                            }
                            break;
                        case 4:
                            if (controller.getCurrentUser().getRole() == UserRole.SYSTEM_ADMIN) {
                                System.out.print("What is the Container id:");
                                String id = scanner.nextLine();
                                controller.deletePortById(id);
                            }
                            break;
                        case 5:
                            if (controller.getCurrentUser().getRole() == UserRole.SYSTEM_ADMIN) {
                                controller.createVehicle(scanner);
                            }
                            break;
                        case 6:
                            if (controller.getCurrentUser().getRole() == UserRole.SYSTEM_ADMIN) {
                                controller.listAllVehicles();
                            }
                            break;
                        case 7:
                            if (controller.getCurrentUser().getRole() == UserRole.SYSTEM_ADMIN) {
                                controller.updateVehicle(scanner);
                            }
                            break;
                        case 8:
                            if (controller.getCurrentUser().getRole() == UserRole.SYSTEM_ADMIN) {
                                System.out.print("What is the vehicle id:");
                                String id = scanner.nextLine();
                                controller.deleteVehicleById(id);
                            }
                            break;
                        case 9:
                            if (controller.getCurrentUser().getRole() == UserRole.SYSTEM_ADMIN) {
                                controller.createContainer(scanner);
                            }
                            break;
                        case 10:
                            if (controller.getCurrentUser().getRole() == UserRole.SYSTEM_ADMIN) {
                                controller.listAllContainers();
                            }
                            break;
                        case 11:
                            if (controller.getCurrentUser().getRole() == UserRole.SYSTEM_ADMIN) {

                                controller.updateContainer(scanner);
                            }
                            break;
                        case 12:
                            if (controller.getCurrentUser().getRole() == UserRole.SYSTEM_ADMIN) {
                                System.out.print("What is the Container id:");
                                String id = scanner.nextLine();
                                controller.deleteContainerById(id);
                            }
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
