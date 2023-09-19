package main;
public abstract class Container {
    private String id;
    private double weight;
    private double shipFuelConsumptionWeightPerKm;
    private double truckFuelConsumptionWeightPerKm;

    public Container(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public double getWeight () { 
        return this.weight;
    }

    public double getShipFuelRequirement(double distance) {
        return this.shipFuelConsumptionWeightPerKm * distance;
    }

    public double getTruckFuelRequirement(double distance) {
        return this.truckFuelConsumptionWeightPerKm * distance;
    }
}
