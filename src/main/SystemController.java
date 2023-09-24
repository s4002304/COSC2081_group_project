package main;

import java.util.ArrayList;
import java.util.Date;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Scanner;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
public class SystemController {
    private HashMap<String, Port> ports;
    private HashMap<String, Vehicle> vehicles;
    private HashMap<String, Container> containers;
    private HashMap<String, User> users;
    private User currentUser = null;
    private ArrayList<TrafficHistory> trafficHistories = new ArrayList<>();
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

    public void createVehicle(Scanner scanner) {
        try {
            System.out.println("Select a vehicle type:");
            System.out.println("1. Ship");
            System.out.println("2. Truck");
            System.out.println("3. Reefer Truck");
            System.out.println("4. Tanker Truck");
            int containerTypeChoice = scanner.nextInt();
            scanner.nextLine();
            System.out.print("What is the Vehicle id:");
            String id = scanner.nextLine();
            if (this.vehicles.containsKey(id)) {
                System.out.println("Vehicle id must be unique");
                return;
            }
            System.out.print("What is the name of the vehicle:");
            String name = scanner.nextLine();
            System.out.print("What is the carrying capacity (min 0, max 100):");
            double carryingCapacity = scanner.nextDouble();
            if (carryingCapacity < 0 || carryingCapacity > 100) {
                System.out.println("Weight must be in range 0-100");
                return;
            }
            System.out.print("What is the fuel capacity (min 5000, max 10000):");
            double fuelCapacity = scanner.nextDouble();
            if (fuelCapacity < 5000 || fuelCapacity > 10000) {
                System.out.println("Weight must be in range 5000-10000");
                return;
            }
            scanner.nextLine();
            switch (containerTypeChoice) {
                case 1:
                    this.vehicles.put(id, new Ship(id, name, carryingCapacity, fuelCapacity, fuelCapacity));
                    break;
                case 2:
                    this.vehicles.put(id, new Truck(id, name, carryingCapacity, fuelCapacity, fuelCapacity));
                    break;
                case 3:
                    this.vehicles.put(id, new ReeferTruck(id, name, carryingCapacity, fuelCapacity, fuelCapacity));
                    break;
                case 4:
                    this.vehicles.put(id, new TankerTruck(id, name, carryingCapacity, fuelCapacity, fuelCapacity));
                    break;
                default:
                    System.out.println("Invalid Vehicle type choice.");
                    return;
            }
            System.out.println("Vehicle created!");
            System.out.println(this.vehicles.get(id).toString());
        } catch (Exception e) {
            System.out.println("Error:" + e);
        }
    }

    public Vehicle findVehicleById(String id) {
        return this.vehicles.get(id);
    }

    public void deleteVehicleById(String id) {
        Vehicle vehicle = this.vehicles.get(id);
        if (!vehicle.getAllContainers().isEmpty()) {
            for (Container container : vehicle.getAllContainers().values()) {
                this.deleteContainerById(container.getId());
            }
        }
        this.vehicles.remove(id);
        System.out.println("Vehicle deleted");
    }

    public void updateVehicleById(String id, Scanner scanner) {
        Vehicle vehicle = this.vehicles.get(id);
        System.out.print("What is the name of the vehicle:");
        String name = scanner.nextLine();
        System.out.print("What is the carrying capacity (min 0, max 100):");
        double carryingCapacity = scanner.nextDouble();
        if (carryingCapacity < 0 || carryingCapacity > 100) {
            System.out.println("Weight must be in range 0-100");
            return;
        }
        System.out.print("What is the fuel capacity (min 5000, max 10000):");
        double fuelCapacity = scanner.nextDouble();
        if (fuelCapacity < 5000 || fuelCapacity > 10000) {
            System.out.println("Weight must be in range 5000-10000");
            return;
        }
        scanner.nextLine();
        vehicle.setName(name);
        vehicle.setCarryingCapacity(carryingCapacity);
        vehicle.setFuelCapacity(fuelCapacity);
        vehicle.setCurrentFuel(fuelCapacity);
        System.out.println("Vehicle updated");
    }

    public void createPort(Scanner scanner) {
        try {
            System.out.print("What is the Port id:");
            String id = scanner.nextLine();
            if (this.ports.containsKey(id)) {
                System.out.println("Port id must be unique");
                return;
            }
            System.out.print("What is the name of the port:");
            String name = scanner.nextLine();
            System.out.print("What is the latitude (min 0, max 100):");
            double latitude = scanner.nextDouble();
            if (latitude < 0 || latitude > 100) {
                System.out.println("latitude must be in range 0-100");
                return;
            }
            scanner.nextLine();
            System.out.print("What is the latitude (min 0, max 100):");
            double longtitude = scanner.nextDouble();
            if (longtitude < 0 || longtitude > 100) {
                System.out.println("latitude must be in range 0-100");
                return;
            }
            scanner.nextLine();
            System.out.print("What is the storing capacity:");
            double storingCapacity = scanner.nextDouble();
            scanner.nextLine();
            System.out.print("What is the landing Ability:");
            boolean landingAbility = scanner.nextBoolean();
            scanner.nextLine();
            this.ports.put(id, new Port(id, name, latitude, longtitude, storingCapacity, landingAbility));
            System.out.println("Port created!");
            System.out.println(this.ports.get(id).toString());
        } catch (Exception e) {
            System.out.println("Error:" + e);
        }
    }

    public void showRoleMenu() {
        System.out.println("Role: " + this.getCurrentUser().getRole());
        System.out.println("Select an option:");
        if (this.getCurrentUser().getRole() == UserRole.PORT_MANAGER || this.getCurrentUser().getRole() == UserRole.SYSTEM_ADMIN) {
            System.out.println("1. View Data");
            System.out.println("2. Create Containers");
        }
        if (this.getCurrentUser().getRole() == UserRole.PORT_MANAGER) {
            System.out.println("1. View Vehicles ");
            System.out.println("2. View Containers ");
            System.out.println("3. Assign Container to Vehicle ");
            System.out.println("4. Load Container to Vehicle ");
            System.out.println("5. Unload Container from Vehicle ");
        } else if (this.getCurrentUser().getRole() == UserRole.SYSTEM_ADMIN) {
            System.out.println("1. Create Ports ");
            System.out.println("2. View Ports ");
            System.out.println("3. Update Ports ");
            System.out.println("4. Delete Ports ");
            System.out.println("5. Create Vehicles ");
            System.out.println("6. View Vehicles ");
            System.out.println("7. Update Vehicles ");
            System.out.println("8. Delete Vehicles ");
            System.out.println("9. Create Containers ");
            System.out.println("10. View Containers ");
            System.out.println("11. Update Containers ");
            System.out.println("12. Delete Containers ");
            System.out.println("13. Load A Container To Vehicle ");
            System.out.println("14. Unload A Container From Vehicle ");
            System.out.println("15. Assign A Vehicle To Port ");
            System.out.println("16. Assign A Container To Port ");
            System.out.println("17. Assign A Container To Vehicle ");
            System.out.println("18. Move A Vehicle To Port ");
            System.out.println("19. Refuel A Vehicle ");
            System.out.println("20. Show Traffic History ");
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
            currentPort.unloadContainer(vehicle, container);
        }
    }

    public void assignVehicleToPort(Port port, Vehicle vehicle) {
        if (this.isAuthorized(this.currentUser, port.getId())) {
            port.addVehicle(vehicle);
        }
    }

    public void createTrafficHistory(Port departurePort, Port arrivalPort, Vehicle vehicle, HashMap<String, Container> containers, Date departureDate, Date arrivalDate) {
        TrafficHistory trafficHistory = new  TrafficHistory(vehicle, departurePort, arrivalPort, departureDate, arrivalDate, "incomplete");
        trafficHistories.add(trafficHistory);
    }

    public void moveVehicleToPort(Vehicle vehicle, Port port) {
        if (vehicle.isTravelableToPort(port)) {
            this.createTrafficHistory(vehicle.getCurrentPort(), port, vehicle, vehicle.getAllContainers(), this.currentDate, new Date(this.currentDate.getTime()+1000 * 60 * 60 * 24 * 3));
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
