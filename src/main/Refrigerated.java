package main;

public class Refrigerated extends Container {
    private final double shipFuelConsumptionWeightPerKm = 4.5;
    private final double truckFuelConsumptionWeightPerKm = 5.4;

    public Refrigerated(String id) {
        super(id);
    }
}
