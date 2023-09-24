package main;

import java.util.HashMap;

interface PortInterface {
    String getName();

    void setName(String name);

    double getDistanceToPort(Port port);

    boolean getLandingAbility();

    int getTotalNumberOfVehicles();

    HashMap<String, Vehicle> getAllVehicles();

    double getTotalWeight();

    int getTotalNumberOfContainers();

    HashMap<String, Container> getAllContainers();

    void loadContainers(Vehicle vehicle, HashMap<String, Container> containers);

    boolean isUnloadable(Container container);

    void unloadContainers(Vehicle vehicle, HashMap<String, Container> containers);

    void removeVehicle(String id);

    void removeContainer(String id);
}
