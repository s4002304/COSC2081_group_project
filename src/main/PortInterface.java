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

    void loadContainer(Vehicle vehicle, Container container);

    boolean isUnloadable(Container container);

    void unloadContainer(Vehicle vehicle, Container container);

    void removeVehicle(String id);

    void removeContainer(String id);
}
