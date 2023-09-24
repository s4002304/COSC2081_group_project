package main;

import java.util.HashMap;
import java.util.Map;

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

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public double getLongtitude() {
        return this.longtitude;
    }

    public double getDistanceToPort(Port port) {
        double x1 = this.latitude;
        double y1 = this.longtitude;
        double x2 = port.getLatitude();
        double y2 = port.getLongtitude();
        return Math.sqrt((y1 - y2) * (y1 - y2) + (x1 - x2) * (x1 - x2));
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
        double totalWeight = 0;
        for (Container container : this.currentContainers.values()) {
            totalWeight += container.getWeight();
        }
        return totalWeight;
    }

    public int getTotalNumberOfContainers() {
        return this.currentContainers.size();
    }

    public HashMap<String, Container> getAllContainers() {
        return this.currentContainers;
    }

    public void loadContainers(Vehicle vehicle, HashMap<String, Container> containers) {
        if (vehicle.isLoadable(containers)) {
            for (Container container : containers.values()) {
                this.currentContainers.remove(container.getId());
                vehicle.loadContainer(container);
            }
        }
    }

    public boolean isUnloadable(Container container) {
        double totalWeight = 0;
        totalWeight += container.getWeight();
        return totalWeight + this.getTotalWeight() <= this.storingCapacity;
    }

    public void unloadContainers(Vehicle vehicle, Container container) {
        vehicle.unloadContainer(container);
        this.currentContainers.put(container.getId(), container);
    }

    public void addVehicle(Vehicle vehicle) {
        this.currentVehicles.put(vehicle.getId(), vehicle);
    }

    public void removeVehicle(String id) {
        Vehicle vehicle = this.currentVehicles.get(id);
        HashMap<String, Container> containers = vehicle.getAllContainers();
        for (String containerId : containers.keySet()) {
            this.currentContainers.remove(containerId);
        }
        this.currentVehicles.remove(vehicle.getId());
    }

    public void removeContainer(String id) {
        this.currentContainers.remove(id);
    }

    public String toString() {
        return "Id: " + this.id + " Name: " + this.name;
    }
}
