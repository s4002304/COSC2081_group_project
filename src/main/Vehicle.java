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
    protected ArrayList<Class> containerType =  new ArrayList<>();
    private HashMap<String, Container> currentContainers = new HashMap<>();

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

    public void setName(String name) {
        this.name = name;
    }

    public void setCarryingCapacity(double carryingCapacity) {
        this.carryingCapacity = carryingCapacity;
    }

    public void setFuelCapacity(double fuelCapacity) {
        this.fuelCapacity = fuelCapacity;
    }

    public void setCurrentFuel(double currentFuel) {
        this.currentFuel = currentFuel;
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

    public void updateTotalWeight() {
        double totalWeight = 0;
        for (Container container : this.currentContainers.values()) {
            totalWeight += container.getWeight();
        }
        this.totalWeight = totalWeight;
    }

    public HashMap<String, Container> getAllContainers() {
        return this.currentContainers;
    }

    public boolean isLoadable(Container container) {
        double totalWeight = 0;
            if (!this.containerType.contains(container.getClass())) {
                System.out.println(this.getClass().getSimpleName() + " can not carry " + container.getClass().getSimpleName() + " container.");
                return false;
            }
            totalWeight += container.getWeight();
        return totalWeight + this.totalWeight <= this.carryingCapacity;
    }

    public void loadContainer(Container container) {
        this.currentContainers.put(container.getId(), container);
        this.updateTotalWeight();
    }

    public void unloadContainer(Container container) {
        this.currentContainers.remove(container.getId());
        this.updateTotalWeight();
    }

    public boolean isTravelableToPort(Port port) {
        double distance = this.currentPort.getDistanceToPort(port);
        double fuelRequirement = 0;
        for (Container container : this.currentContainers.values()) {
            if (this instanceof Truck) {
                fuelRequirement += container.getTruckFuelRequirement(distance);
            } else if (this instanceof Ship) {
                fuelRequirement += container.getShipFuelRequirement(distance);
            }
        }
        return fuelRequirement <= this.currentFuel;
    }

    public String toString() {
        StringBuilder result = new StringBuilder("Id: " + this.id + " Name: " + this.name + " Type: " + this.getClass().getSimpleName() + "\n" + "Containers: ");
        if (!this.currentContainers.isEmpty()) {
            for (Container container : this.currentContainers.values()) {
                result.append(container.toString());
            }
        } else {
            result.append("None\n");
        }

        return result.toString();
    }
}