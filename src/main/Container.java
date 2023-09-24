package main;

public abstract class Container {
    private String id;
    private double weight;
    private double shipFuelConsumptionWeightPerKm;
    private double truckFuelConsumptionWeightPerKm;

    public Container(String id, double weight) {
        this.id = id;
        this.weight = weight;
    }

    public String getId() {
        return this.id;
    }

    public double getWeight() {
        return this.weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getShipFuelRequirement(double distance) {
        return this.shipFuelConsumptionWeightPerKm * distance;
    }

    public double getTruckFuelRequirement(double distance) {
        return this.truckFuelConsumptionWeightPerKm * distance;
    }

    public String toString() {
        return "Id: " + this.id + "\nWeight: " + this.weight + "\nType: " + this.getClass().getSimpleName() + "\n";
    }
}
