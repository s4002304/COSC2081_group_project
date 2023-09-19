package main;

import java.util.HashMap;

public class Port implements PortInterface {
    private String id;
    private String name;
    private double latitude;
    private double longtitude;
    private double storingCapacity;
    private boolean landingAbility;
    HashMap<String, Container> currentContainers;
    HashMap<String, Vehicle> currentVehicles;
    HashMap<String, TrafficHistory> trafficHistoryList;

    public Port(String id, String name, double latitude, double longtitude, double storingCapacity,
            boolean landingAbility) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.storingCapacity = storingCapacity;
        this.landingAbility = landingAbility;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDistanceToPort(Port port) {
        // TODO: Implement
        return 0;
    }

    public boolean getLandingAbility() {
        return this.landingAbility;
    }

    public int getTotalNumberOfVehicles() {
        return this.currentVehicles.size();
    }

    public HashMap<String, Vehicle> getAllVehicles() {
        return this.currentVehicles;
    }

    public double getTotalWeight() {
        // TODO: Implement
        double totalWeight = 0;
        return 0;
    }

    public int getTotalNumberOfContainers() {
        return this.currentContainers.size();
    }

    public HashMap<String, Container> getAllContainers() {
        return this.currentContainers;
    }

    public void loadContainers(Vehicle vehicle, HashMap<String, Container> containers) {
        // TODO: Implement
    }

    public boolean isUnloadable(HashMap<String, Container> containers) {
        // TODO: Implement
        return false;
    }

    public void unloadContainers(Vehicle vehicle, HashMap<String, Container> containers) {
        // TODO: Implement
    }

    public void removeVehicle(String id) {
        // TODO: Implement
    }

    public void removeContainer(String id) {
        // TODO: Implement
    }
}
