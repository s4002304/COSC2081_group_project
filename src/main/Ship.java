package main;

public class Ship extends Vehicle {
    public Ship(String id, String name, double carryingCapacity, double currentFuel, double fuelCapacity) {
        super(id, name, carryingCapacity, currentFuel, fuelCapacity);
        this.containerType.add(DryStorage.class);
        this.containerType.add(OpenTop.class);
        this.containerType.add(OpenSide.class);
        this.containerType.add(Refrigerated.class);
        this.containerType.add(Liquid.class);
    }
}
