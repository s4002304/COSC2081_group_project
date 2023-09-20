package main;

public abstract class Truck extends Vehicle{
    public Truck(String id, String name, double carryingCapacity, double currentFuel, double fuelCapacity) {
        super(id, name, carryingCapacity, currentFuel, fuelCapacity);
    }
}
