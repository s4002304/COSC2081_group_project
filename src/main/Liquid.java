package main;

public class Liquid extends Container{
    private final double shipFuelConsumptionWeightPerKm = 4.8;
    private final double truckFuelConsumptionWeightPerKm = 5.3;
    public Liquid(String id) {
        super(id);
    }
}
