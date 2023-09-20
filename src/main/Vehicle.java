package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class Vehicle implements VehicleInterface {
    private String id;
    private String name;
    private Port currentPort;
    private double currentFuel;
    private double fuelCapacity;
    private double carryingCapacity;
    private double totalWeight;
    private ArrayList<Class> containerType;
    private HashMap<String, Container> currentContainers;

    public Vehicle(String id, String name, double carryingCapacity,
            double currentFuel, double fuelCapacity) {
        this.id = id;
        this.name = name;
        this.carryingCapacity = carryingCapacity;
        this.currentFuel = currentFuel;
        this.fuelCapacity = fuelCapacity;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Port getCurrentPort() {
        return this.currentPort;
    }

    public void setCurrentPort(Port port) {
        this.currentPort = port;
    }

    public int getTotalNumberOfContainers() {
        return this.currentContainers.size();
    }

    public int getTotalNumberOfContainersByTypes(Class containerType) {
        int count = 0;
        for (Map.Entry<String, Container> set : this.currentContainers
                .entrySet()) {
            Container currentContainer = set.getValue();
            if (containerType.isInstance(currentContainer)) {
                count += 1;
            }
        }
        return count;
    }

    public void refilFuel() {
        this.currentFuel = this.fuelCapacity;
    }

    public double getTotalWeight() {
        return this.totalWeight;
    }

    public HashMap<String, Container> getAllContainers() {
        return this.currentContainers;
    }

    public boolean isLoadable(HashMap<String, Container> containers) {
        // TODO: Implement
        return false;
    }

    public void loadContainer(Container container) {
        // TODO: Implement
    }

    public void unloadContainer(Container container) {
        // TODO: Implement
    }

    public boolean isTravelableToPort(Port port) {
        // TODO: Implement
        return false;
    }
}