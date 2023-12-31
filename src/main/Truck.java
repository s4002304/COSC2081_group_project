package main;

public class Truck extends Vehicle {
    public Truck(String id, String name, double carryingCapacity, double currentFuel,
            double fuelCapacity) {
        super(id, name, carryingCapacity, currentFuel, fuelCapacity);
        this.containerType.add(DryStorage.class);
        this.containerType.add(OpenTop.class);
        this.containerType.add(OpenSide.class);
    }
}
