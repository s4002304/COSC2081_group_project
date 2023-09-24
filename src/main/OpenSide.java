package main;

public class OpenSide extends Container{
    private final double shipFuelConsumptionWeightPerKm = 2.7;
    private final double truckFuelConsumptionWeightPerKm = 3.2;
    public OpenSide(String id, double weight) {
        super(id, weight);
    }
}
