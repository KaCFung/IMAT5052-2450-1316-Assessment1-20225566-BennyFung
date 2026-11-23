package uk.ac.plymouth.comp2000hk.sharedofficebookingapp;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BookingDataManager {
    public static String currentUserRole = "EMPLOYEE";
    public static String currentUserName = "Rex";
    public static List<Room> roomList = new ArrayList<>();
    public static List<Booking> bookingList = new ArrayList<>();

    static {
        roomList.add(new Room("Meeting Room 1", "Meeting", "Capacity:6 | Projector, Whiteboard"));
        roomList.add(new Room("Meeting Room 2", "Meeting", "Capacity:6 | TV Display, Whiteboard"));
        roomList.add(new Room("Meeting Room 3", "Meeting", "Capacity:4 | TV Display"));
        roomList.add(new Room("Meeting Room 4", "Meeting", "Capacity:8 | Projector, Whiteboard"));
        roomList.add(new Room("Meeting Room 5", "Meeting", "Capacity:4 | Whiteboard"));
        roomList.add(new Room("Seminar Room A", "Seminar", "Capacity:30 | Audio, Mic, Projector, Desktop"));
        roomList.add(new Room("Seminar Room B", "Seminar", "Capacity:30 | Audio, Mic, Projector, Desktop"));

        // Demo Data
        bookingList.add(new Booking("Meeting Room 2", "12/11/2026", "14:00 - 15:00", "Client Sync", "Alex (Employee)", "Confirmed"));
        bookingList.add(new Booking("Seminar Room B", "12/11/2026", "10:00 - 12:00", "Department Workshop", "Chris", "Confirmed"));
        bookingList.add(new Booking("Meeting Room 3", "13/11/2026", "11:00 - 11:30", "Sprint Review (Cancel Requested)", "Sarah (Employee)", "Cancel Requested"));
    }

    public static String[] getTimeOptions(boolean isManager) {
        List<String> times = new ArrayList<>();
        int startHour = isManager ? 0 : 9;
        int endHour = isManager ? 23 : 18;

        for (int h = startHour; h <= endHour; h++) {
            times.add(String.format(Locale.getDefault(), "%02d:00", h));
            if (!isManager && h == 18) {
                break;
            }
            times.add(String.format(Locale.getDefault(), "%02d:30", h));
        }
        return times.toArray(new String[0]);
    }

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