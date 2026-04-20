/**
 * =============================================================
 * Use Case 2 : Basic Room Types & Static Availability
 * =============================================================
 *
 * Description:
 * Demonstrates different room types in a hotel and
 * tracks availability using static variables.
 *
 * @author Developer
 * @version 1.0
 */

class Room {

    String type;
    int price;

    static int singleAvailable = 3;
    static int doubleAvailable = 2;
    static int suiteAvailable = 1;

    Room(String type, int price) {
        this.type = type;
        this.price = price;
    }

    void displayRoom() {
        System.out.println(type + " Room");
        System.out.println("Price per night: $" + price);
    }

    static void displayAvailability() {
        System.out.println("Current Room Availability:");
        System.out.println("Single Rooms: " + singleAvailable);
        System.out.println("Double Rooms: " + doubleAvailable);
        System.out.println("Suite Rooms: " + suiteAvailable);
    }

    static void bookRoom(String roomType) {

        switch (roomType) {

            case "Single":
                if (singleAvailable > 0) {
                    singleAvailable--;
                    System.out.println("Single room booked successfully.");
                } else {
                    System.out.println("Single room not available.");
                }
                break;

            case "Double":
                if (doubleAvailable > 0) {
                    doubleAvailable--;
                    System.out.println("Double room booked successfully.");
                } else {
                    System.out.println("Double room not available.");
                }
                break;

            case "Suite":
                if (suiteAvailable > 0) {
                    suiteAvailable--;
                    System.out.println("Suite room booked successfully.");
                } else {
                    System.out.println("Suite room not available.");
                }
                break;

            default:
                System.out.println("Invalid room type.");
        }
    }
}


public class UseCase2RoomTypes {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("Hotel Booking Management System");
        System.out.println("Use Case 2: Basic Room Types & Availability");
        System.out.println("=================================\n");

        Room single = new Room("Single", 100);
        Room dbl = new Room("Double", 150);
        Room suite = new Room("Suite", 250);

        System.out.println("Room Types Available:\n");

        single.displayRoom();
        System.out.println();

        dbl.displayRoom();
        System.out.println();

        suite.displayRoom();
        System.out.println();

        Room.displayAvailability();

        System.out.println("\nBooking a Single Room...\n");

        Room.bookRoom("Single");

        System.out.println();
        Room.displayAvailability();
    }
}