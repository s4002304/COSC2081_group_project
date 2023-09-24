package main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Scanner;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Date;
import java.text.SimpleDateFormat;
public class SystemController {
    private HashMap<String, Port> ports;
    private HashMap<String, Vehicle> vehicles;
    private HashMap<String, Container> containers;
    private HashMap<String, User> users;
    private User currentUser = null;
    private Date currentDate;

    public SystemController() {
        this.ports = new HashMap<String, Port>();
        this.vehicles = new HashMap<String, Vehicle>();
        this.containers = new HashMap<String, Container>();
        this.users = new HashMap<String, User>();
        this.initializeData();
    }

    private void initializeData() {
        this.initializeContainerData();
        this.initializePortData();
        this.initializeVehicleData();
        this.initializeUserData();
        this.initializeSystemData();
    }
    // Intializing Container Data
    public void initializeContainerData() {
        try (Scanner file = new Scanner(Paths.get("src\\data\\containers.txt"))) {
            while (file.hasNextLine()) {
                String record = file.nextLine();
                String[] fields = record.split("&");
                if (fields.length == 3) {
                    String classType = fields[0];
                    String id = fields[1];
                    double weight = Double.valueOf(fields[2]);

                    switch (classType) {
                        case "DryStorage":
                            this.containers.put(id, new DryStorage(id, weight));
                            break;
                        case "OpenTop":
                            this.containers.put(id, new OpenTop(id, weight));
                            break;
                        case "OpenSide":
                            this.containers.put(id, new OpenSide(id, weight));
                            break;
                        case "Refrigerated":
                            this.containers.put(id, new Refrigerated(id, weight));
                            break;
                        case "Liquid":
                            this.containers.put(id, new Liquid(id, weight));
                            break;
                    }
                }
            }
        } catch (IOException xIo) {
            xIo.printStackTrace();
        }
    }

    // Intializing Port Data
    public void initializePortData() {
        try (Scanner file = new Scanner(Paths.get("src\\data\\ports.txt"))) {
            while (file.hasNextLine()) {
                String record = file.nextLine();
                String[] fields = record.split("&");
                if (fields.length == 6) {
                    String id = fields[0];
                    String name = fields[1];
                    double latitude = Double.valueOf(fields[2]);
                    double longtitude = Double.valueOf(fields[3]);
                    double storingCapacity = Double.valueOf(fields[4]);
                    boolean landingAbility = Boolean.valueOf(fields[5]);
                    this.ports.put(id, new Port(id, name, latitude, longtitude, storingCapacity,
                            landingAbility));
                }
            }
        } catch (IOException xIo) {
            xIo.printStackTrace();
        }
    }

    // Intializing Vehicle Data
    public void initializeVehicleData() {
        try (Scanner file = new Scanner(Paths.get("src\\data\\vehicles.txt"))) {
            while (file.hasNextLine()) {
                String record = file.nextLine();
                String[] fields = record.split("&");
                if (fields.length == 5) {
                    String id = fields[0];
                    String name = fields[1];
                    double carryingCapacity = Double.valueOf(fields[2]);
                    double currentFuel = Double.valueOf(fields[3]);
                    double fuelCapacity = Double.valueOf(fields[4]);
                    if (name.contains("ship"))
                        this.vehicles.put(id, new Ship(id, name, carryingCapacity, currentFuel, fuelCapacity));
                    if (name.contains("truck"))
                        this.vehicles.put(id, new Truck(id, name, carryingCapacity, currentFuel, fuelCapacity));
                    if (name.contains("reefer"))
                        this.vehicles.put(id, new ReeferTruck(id, name, carryingCapacity, currentFuel, fuelCapacity));
                    if (name.contains("tanker"))
                        this.vehicles.put(id, new TankerTruck(id, name, carryingCapacity, currentFuel, fuelCapacity));
                }
            }
        } catch (IOException xIo) {
            xIo.printStackTrace();
        }
    }

    // Intializing User Data
    public void initializeUserData() {
        try (Scanner file = new Scanner(Paths.get("src\\data\\user.txt"))) {
            while (file.hasNextLine()) {
                String record = file.nextLine();
                String[] fields = record.split("&");
                if (fields.length == 3) {
                    String username = fields[0];
                    String password = fields[1];
                    String role = fields[2];
                    this.users.put(username, new User(username, password, UserRole.SYSTEM_ADMIN));
                }
                if (fields.length == 4) {
                    String username = fields[0];
                    String password = fields[1];
                    String role = fields[2];
                    String portId = fields[3];
                    this.users.put(username, new User(username, password, UserRole.PORT_MANAGER));
                    // Assign port to manager
                    this.users.get(username).assignPort(this.ports.get(portId));
                }
            }
        } catch (IOException xIo) {
            xIo.printStackTrace();
        }
    }

    public void setDate(String newDate) {
        try {
            this.currentDate = new SimpleDateFormat("yyyy/MM/dd").parse(newDate);
            File file = new File("src\\data\\system.txt");
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(newDate);
            bw.close();
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }

    // Initializing System
    public void initializeSystemData() {
        try (Scanner file = new Scanner(Paths.get("src\\data\\system.txt"))) {
                String dateString = file.nextLine();
                this.currentDate = new SimpleDateFormat("yyyy/MM/dd").parse(dateString);
        } catch (IOException xIo) {
            xIo.printStackTrace();
        } catch (java.text.ParseException parseException) {
            parseException.printStackTrace();
        }
    }

    public void login(String username, String password) {
        for (User user : users.values()) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                this.currentUser = user;
            }
        }
        if (this.currentUser == null) {
            System.out.println("User not found");
        }
    }

    public void logout() {
        this.currentUser = null;
    }

    public User getCurrentUser() {
        return this.currentUser;
    }

    public boolean isLoggedIn() {
        return this.currentUser != null;
    }

    public boolean isAuthorized(User user, String portId) {
        if (user.getRole() == UserRole.SYSTEM_ADMIN) {
            return true;
        } else {
            return user.getAssignedPort().getId().equals(portId);
        }
    }

    public void createContainer(Scanner scanner) {
        try {
            System.out.println("Select a container type:");
            System.out.println("1. Dry Storage");
            System.out.println("2. Open Top");
            System.out.println("3. Open Side");
            System.out.println("4. Refrigerated");
            System.out.println("5. Liquid");
            int containerTypeChoice = scanner.nextInt();
            scanner.nextLine();
            System.out.print("What is the container id:");
            String id = scanner.nextLine();
            if (this.containers.containsKey(id)) {
                System.out.println("Container id must be unique");
                return;
            }
            System.out.print("What is the container weight (min 0, max 10):");
            double weight = scanner.nextDouble();
            if (weight < 0 || weight > 10) {
                System.out.println("Weight must be in range 0-10");
                return;
            }
            scanner.nextLine();
            switch (containerTypeChoice) {
                case 1:
                    this.containers.put(id, new DryStorage(id, weight));
                    break;
                case 2:
                    this.containers.put(id, new OpenTop(id, weight));
                    break;
                case 3:
                    this.containers.put(id, new OpenSide(id, weight));
                    break;
                case 4:
                    this.containers.put(id, new Refrigerated(id, weight));
                    break;
                case 5:
                    this.containers.put(id, new Liquid(id, weight));
                    break;
                default:
                    System.out.println("Invalid container type choice.");
                    return;
            }
            System.out.println("Container created!");
            System.out.println(this.containers.get(id).toString());
        } catch (Exception e) {
            System.out.println("Error:" + e);
        }
    }

    public Container findContainerById(String id) {
        return this.containers.get(id);
    }

    public void deleteContainerById(String id) {
        this.containers.remove(id);
        System.out.println("Container deleted");
    }

    public void updateContainerById(String id, Scanner scanner) {
        Container container = this.containers.get(id);
        double weight = scanner.nextDouble();
        if (weight < 0 || weight > 10) {
            System.out.println("Weight must be in range 0-10");
            return;
        }
        scanner.nextLine();
        container.setWeight(weight);
        System.out.println("Container updated");
    }

    public void showRoleMenu() {
        System.out.println("Role: " + this.getCurrentUser().getRole());
        System.out.println("Select an option:");

        if (this.getCurrentUser().getRole() == UserRole.PORT_MANAGER || this.getCurrentUser().getRole() == UserRole.SYSTEM_ADMIN) {
            System.out.println("1. View Data");
            System.out.println("2. Create Containers");
        }
        System.out.println("0. Logout");
    }

    public void listPortsForManager() {
        if (currentUser.getRole() == UserRole.PORT_MANAGER) {
            Port assignedPort = currentUser.getAssignedPort();
            if (assignedPort != null) {
                System.out.println("List of Ports:");
                System.out.println(assignedPort.getName());
            } else {
                System.out.println("You are not assigned to any port.");
            }
        }
    }

    public void listContainersForManager() {
        if (currentUser.getRole() == UserRole.PORT_MANAGER) {
            if (containers != null) { // Check if containers is not null
                Port assignedPort = currentUser.getAssignedPort();
                if (assignedPort != null) {
                    HashMap<String, Container> containers = assignedPort.getAllContainers();
                    System.out.println("List of Containers for your Port:");
                    for (Container container : containers.values()) {
                        System.out.println(container.toString());
                    }
                } else {
                    System.out.println("You are not assigned to any port.");
                }
            } else {
                System.out.println("Containers data is not initialized.");
            }
        }
    }

    public void listVehiclesForManager() {
        if (currentUser.getRole() == UserRole.PORT_MANAGER) {
            Port assignedPort = currentUser.getAssignedPort();
            if (assignedPort != null) {
                HashMap<String, Vehicle> vehicles = assignedPort.getAllVehicles();
                System.out.println("List of Vehicles for your Port:");
                for (Vehicle vehicle : vehicles.values()) {
                    System.out.println(vehicle.toString());
                }
            } else {
                System.out.println("You are not assigned to any port.");
            }
        }
    }

    public void listEverythingForAdmin() {
        if (currentUser.getRole() == UserRole.SYSTEM_ADMIN) {
            // List everything here (Ports, Containers, and Vehicles)
            System.out.println("List of Ports:");
            for (Port port : ports.values()) {
                System.out.println(port.toString());
            }
            System.out.println("List of Containers:");
            for (Container container : containers.values()) {
                System.out.println(container.toString());
            }
            System.out.println("List of Vehicles:");
            for (Vehicle vehicle : vehicles.values()) {
                System.out.println(vehicle.toString());
            }
        }
    }

    public void loadContainerToVehicle(Vehicle vehicle, Container container) {
        if (vehicle.isLoadable(container)) {
            vehicle.loadContainer(container);
        }
    }

    public void unloadContainerFromVehicle(Vehicle vehicle, Container container) {
        Port currentPort = vehicle.getCurrentPort();
        if (currentPort.isUnloadable(container)) {
            currentPort.unloadContainers(vehicle, container);
        }
    }

    public void assignVehicleToPort(Port port, Vehicle vehicle) {
        if (this.isAuthorized(this.currentUser, port.getId())) {
            port.addVehicle(vehicle);
        }
    }

    public void listData() {
        if (currentUser.getRole() == UserRole.PORT_MANAGER) {
            listPortsForManager();
            listContainersForManager();
            listVehiclesForManager();
        } else if (currentUser.getRole() == UserRole.SYSTEM_ADMIN) {
            listEverythingForAdmin();
        }
    }
}
