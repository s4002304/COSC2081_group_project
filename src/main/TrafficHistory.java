package main;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class TrafficHistory {
    private String vehicleId;
    private double totalWeight;
    private HashMap<String, Container> carriedContainers;
    private Date departureDate;
    private Date arrivalDate;
    private String portOfDeparture;
    private String portOfArrival;
    private String status;

    public TrafficHistory(Vehicle vehicle, Port departurePort, Port arrivalPort,
            Date departureDate, Date arrivalDate, String status) {
        this.vehicleId = vehicle.getId();
        this.totalWeight = vehicle.getTotalWeight();
        this.carriedContainers = vehicle.getAllContainers();
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.portOfDeparture = departurePort.getId();
        this.portOfArrival = departurePort.getId();
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String toString() {
        DateFormat dateFormater = new SimpleDateFormat("yyyy/MM/dd");
        String result = "";
        result += "Port of departure: " + this.portOfDeparture + "\n";
        result += "Date of departure: "
                + dateFormater.format(this.departureDate) + '\n';
        result += "Port of arrival: " + this.portOfArrival + '\n';
        result += "Date of arrival: " + dateFormater.format(this.arrivalDate)
                + '\n';
        result += "Vehicle: " + this.vehicleId + '\n';
        result += "Total weight: " + this.totalWeight + '\n';
        return result;
    }
}
