package main;

import java.util.HashMap;

interface VehicleInterface {
    String getName();

    Port getCurrentPort();

    void setCurrentPort(Port port);

    int getTotalNumberOfContainers();

    int getTotalNumberOfContainersByTypes(Class containerType);

    void refilFuel();

    double getTotalWeight();

    HashMap<String, Container> getAllContainers();

    boolean isLoadable(HashMap<String, Container> containers);

    void loadContainer(Container container);

    void unloadContainer(Container container);

    boolean isTravelableToPort(Port port);
}
