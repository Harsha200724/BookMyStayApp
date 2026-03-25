import java.util.*;

class Reservation {
    private int bookingId;
    private String customerName;
    private String roomType;
    private boolean isCancelled;

    public Reservation(int bookingId, String customerName, String roomType) {
        this.bookingId = bookingId;
        this.customerName = customerName;
        this.roomType = roomType;
        this.isCancelled = false;
    }

    public int getBookingId() {
        return bookingId;
    }

    public String getRoomType() {
        return roomType;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void cancel() {
        isCancelled = true;
    }

    public String toString() {
        return "BookingID: " + bookingId +
               ", Name: " + customerName +
               ", Room: " + roomType +
               ", Status: " + (isCancelled ? "Cancelled" : "Confirmed");
    }
}

class RoomInventory {
    private Map<String, Integer> rooms;

    public RoomInventory() {
        rooms = new HashMap<>();
        rooms.put("Single", 2);
        rooms.put("Double", 2);
        rooms.put("Suite", 1);
    }

    public boolean isAvailable(String type) {
        return rooms.containsKey(type) && rooms.get(type) > 0;
    }

    public void allocateRoom(String type) {
        rooms.put(type, rooms.get(type) - 1);
    }

    public void releaseRoom(String type) {
        rooms.put(type, rooms.get(type) + 1);
    }

    public void displayInventory() {
        System.out.println("\n--- Inventory ---");
        for (String type : rooms.keySet()) {
            System.out.println(type + " : " + rooms.get(type));
        }
    }
}

class BookingService {
    private Map<Integer, Reservation> bookings;
    private RoomInventory inventory;
    private int bookingCounter = 1;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
        bookings = new HashMap<>();
    }

    public int bookRoom(String name, String type) {
        if (!inventory.isAvailable(type)) {
            System.out.println("Booking Failed: Room not available.");
            return -1;
        }

        Reservation r = new Reservation(bookingCounter, name, type);
        bookings.put(bookingCounter, r);
        inventory.allocateRoom(type);

        System.out.println("Booking Successful: " + r);
        return bookingCounter++;
    }

    public Reservation getReservation(int id) {
        return bookings.get(id);
    }

    public void displayBookings() {
        System.out.println("\n--- All Bookings ---");
        for (Reservation r : bookings.values()) {
            System.out.println(r);
        }
    }
}

class CancellationService {
    private Stack<String> rollbackStack; // LIFO structure
    private BookingService bookingService;
    private RoomInventory inventory;

    public CancellationService(BookingService bookingService, RoomInventory inventory) {
        this.bookingService = bookingService;
        this.inventory = inventory;
        rollbackStack = new Stack<>();
    }

    public void cancelBooking(int bookingId) {

        Reservation r = bookingService.getReservation(bookingId);

        if (r == null) {
            System.out.println("Cancellation Failed: Booking does not exist.");
            return;
        }

        if (r.isCancelled()) {
            System.out.println("Cancellation Failed: Already cancelled.");
            return;
        }

        rollbackStack.push(r.getRoomType());

        inventory.releaseRoom(r.getRoomType());

        r.cancel();

        System.out.println("Cancellation Successful: Booking ID " + bookingId);
    }

    public void showRollbackStack() {
        System.out.println("\nRollback Stack (Recent Releases): " + rollbackStack);
    }
}

public class UseCase10BookingCancellation {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        BookingService bookingService = new BookingService(inventory);
        CancellationService cancelService = new CancellationService(bookingService, inventory);

        int id1 = bookingService.bookRoom("Alice", "Single");
        int id2 = bookingService.bookRoom("Bob", "Suite");

        cancelService.cancelBooking(id2);

        cancelService.cancelBooking(id2);

        cancelService.cancelBooking(999);

        bookingService.displayBookings();
        inventory.displayInventory();
        cancelService.showRollbackStack();
    }
}
