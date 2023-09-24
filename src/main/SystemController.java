package main;

import java.util.HashMap;
import java.util.Scanner;
import java.io.IOException;
import java.nio.file.Paths;

public class SystemController {
    private HashMap<String, Port> ports;
    private HashMap<String, Vehicle> vehicles;
    private HashMap<String, Container> containers;
    private HashMap<String, User> users;

    public SystemController() {
        this.ports = new HashMap<String, Port>();
        this.vehicles = new HashMap<String, Vehicle>();
        this.containers = new HashMap<String, Container>();
        this.users = new HashMap<String, User>();
        this.initializeContainerData();
        this.initializePortData();

        // for (String name : this.ports.keySet()) {
        // String key = name.toString();
        // String value = this.ports.get(name).toString();
        // System.out.println(key + " " + value);
        // }
        Port port1 = this.ports.get("p-1");
        Port port2 = this.ports.get("p-2");
        double distance = port1.getDistanceToPort(port2);
        System.out.println(distance);
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
