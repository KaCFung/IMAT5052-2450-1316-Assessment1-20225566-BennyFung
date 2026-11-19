package uk.ac.plymouth.comp2000hk.sharedofficebookingapp;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
public class BookingDataManager {
    public static String currentUserRole = "EMPLOYEE";
    public static String currentUserName = "Rex";
    public static List<Room> roomList = new ArrayList<>();
    public static List<Booking> bookingList = new ArrayList<>();

    //list all 7 room using(can easily add new room in the future if needed)
    static {
        roomList.add(new Room("Meeting Room 1", "Meeting", "Capacity:6 | Projector, Whiteboard"));
        roomList.add(new Room("Meeting Room 2", "Meeting", "Capacity:6 | TV Display, Whiteboard"));
        roomList.add(new Room("Meeting Room 3", "Meeting", "Capacity:4 | TV Display"));
        roomList.add(new Room("Meeting Room 4", "Meeting", "Capacity:8 | Projector, Whiteboard"));
        roomList.add(new Room("Meeting Room 5", "Meeting", "Capacity: 4 | Whiteboard"));
        roomList.add(new Room("Seminar Room A", "Seminar", "Capacity:30 | Audio, Mic, Projector, Desktop"));
        roomList.add(new Room("Seminar Room B", "Seminar", "Capacity:30| Audio, Mic, Projector, Desktop"));

    }

    // generate 30-minute interval time options based on user role
    public static String[] getTimeOptions(boolean isManager) {
        List<String> times = new ArrayList<>();
        int startHour = isManager ? 0 : 9;
        int endHour = isManager ? 23 : 18;

        for (int h = startHour; h <= endHour; h++) {
            times.add(String.format(Locale.getDefault(), "%02d:00", h));
            if (!isManager && h == 18) {
                break; // employee office hours end strictly at 18:00
            }
            times.add(String.format(Locale.getDefault(), "%02d:30", h));
        }
        return times.toArray(new String[0]);
    }

    // check if room is already booked at the same date and time
    public static boolean isRoomReserved(String roomName, String date, String timeSlot) {
        for (Booking b : bookingList) {
            if (b.getroomName().equals(roomName) && b.getdate().equals(date)
                    && b.timeSlot().equals(timeSlot) && b.getstatus().equals("Confirmed")) {
                return true;
            }
        }
        return false;
    }
}
