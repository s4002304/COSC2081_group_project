package main;

public abstract class Vehicle {
    String id;

    public Vehicle(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }
}
