/**
 * UseCase7AddOnServiceSelection
 * 
 * Demonstrates adding optional services to an existing reservation
 * without modifying booking or inventory logic.
 * 
 * @author A.HARSHA VARDHAN
 * @version 7.0
 */

import java.util.*;

// Add-On Service Class
class AddOnService {
    private String serviceName;
    private double cost;

    public AddOnService(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getCost() {
        return cost;
    }

    public void display() {
        System.out.println(serviceName + " - ₹" + cost);
    }
}

// Add-On Service Manager
class AddOnServiceManager {

    // Map: Reservation ID → List of Services
    private HashMap<String, List<AddOnService>> serviceMap;

    public AddOnServiceManager() {
        serviceMap = new HashMap<>();
    }

    // Add service to a reservation
    public void addService(String reservationId, AddOnService service) {

        serviceMap.putIfAbsent(reservationId, new ArrayList<>());
        serviceMap.get(reservationId).add(service);

        System.out.println("Added " + service.getServiceName() +
                " to Reservation ID: " + reservationId);
    }

    // Display services for a reservation
    public void displayServices(String reservationId) {

        System.out.println("\n--- Services for Reservation: " + reservationId + " ---");

        List<AddOnService> services = serviceMap.get(reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No services selected.");
            return;
        }

        for (AddOnService s : services) {
            s.display();
        }
    }

    // Calculate total add-on cost
    public double calculateTotalCost(String reservationId) {

        List<AddOnService> services = serviceMap.get(reservationId);

        double total = 0;

        if (services != null) {
            for (AddOnService s : services) {
                total += s.getCost();
            }
        }

        return total;
    }
}

// Main Class
public class UseCase7AddOnServiceSelection {

    public static void main(String[] args) {

        System.out.println("=======================================");
        System.out.println(" Book My Stay - Hotel Booking System");
        System.out.println(" Version 7.0");
        System.out.println("=======================================\n");

        // Assume reservation IDs from previous use case
        String reservation1 = "SI101";
        String reservation2 = "SU102";

        // Create service manager
        AddOnServiceManager manager = new AddOnServiceManager();

        // Add services
        manager.addService(reservation1, new AddOnService("Breakfast", 500));
        manager.addService(reservation1, new AddOnService("Airport Pickup", 1200));
        manager.addService(reservation2, new AddOnService("Spa", 2000));

        // Display services
        manager.displayServices(reservation1);
        manager.displayServices(reservation2);

        // Calculate total cost
        double total1 = manager.calculateTotalCost(reservation1);
        double total2 = manager.calculateTotalCost(reservation2);

        System.out.println("\nTotal Add-On Cost for " + reservation1 + ": ₹" + total1);
        System.out.println("Total Add-On Cost for " + reservation2 + ": ₹" + total2);

        System.out.println("\nNote: Booking & Inventory remain unchanged.");
    }
}
