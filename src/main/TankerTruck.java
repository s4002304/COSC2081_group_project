package main;

public class TankerTruck extends Truck{
    public TankerTruck(String id, String name, double carryingCapacity, double currentFuel, double fuelCapacity) {
        super(id, name, carryingCapacity, currentFuel, fuelCapacity);
        this.containerType.add(Liquid.class);
    }
}
