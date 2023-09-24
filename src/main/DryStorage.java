package main;

public class DryStorage extends Container{
    private final double shipFuelConsumptionWeightPerKm = 3.5;
    private final double truckFuelConsumptionWeightPerKm = 4.6;
    public DryStorage(String id, double weight) {
        super(id, weight);
    }
}
