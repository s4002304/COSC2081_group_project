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
    HashMap<String, Container> currentContainers = new HashMap<>();
    HashMap<String, Vehicle> currentVehicles = new HashMap<>();
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

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongtitude() {
        return this.longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }

    public double getStoringCapacity() {
        return this.storingCapacity;
    }

    public void setStoringCapacity(double storingCapacity) {
        this.storingCapacity = storingCapacity;
    }

    public boolean getLandingAbility() {
        return this.landingAbility;
    }

    public void setLandingAbility(boolean landingAbility) {
        this.landingAbility = landingAbility;
    }

    public double getDistanceToPort(Port port) {
        double x1 = this.latitude;
        double y1 = this.longtitude;
        double x2 = port.getLatitude();
        double y2 = port.getLongtitude();
        return Math.sqrt((y1 - y2) * (y1 - y2) + (x1 - x2) * (x1 - x2));
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

    public void loadContainer(Vehicle vehicle, Container container) {
        if (vehicle.isLoadable(container)) {
                this.currentContainers.remove(container.getId());
                vehicle.loadContainer(container);
            }
    }

    public boolean isUnloadable(Container container) {
        double totalWeight = 0;
        totalWeight += container.getWeight();
        return totalWeight + this.getTotalWeight() <= this.storingCapacity;
    }

    public void unloadContainer(Vehicle vehicle, Container container) {
        vehicle.unloadContainer(container);
        this.currentContainers.put(container.getId(), container);
    }

    public void addVehicle(Vehicle vehicle) {
        this.currentVehicles.put(vehicle.getId(), vehicle);
    }

    public void addContainer(Container container) {
        this.currentContainers.put(container.getId(), container);
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
        StringBuilder result = new StringBuilder("Id: " + this.id + " Name: " + this.name + " Latitude: " + this.latitude + " Longtitude: " + this.longtitude + " Storing capacity: " + this.storingCapacity + " Landing ability: " + this.landingAbility + "\n");
        result.append("Containers:\n");
        if (!this.currentContainers.isEmpty()) {
            for (Container container : this.currentContainers.values()) {
                result.append(container.toString());
            }
        } else {
            result.append("None\n");
        }
        result.append("Vehicles:\n");
        if (!this.currentVehicles.isEmpty()) {
            for (Vehicle vehicle : this.currentVehicles.values()) {
                result.append(vehicle.toString());
            }
        } else {
            result.append("None\n");
        }

        return result.toString();
    }
}
