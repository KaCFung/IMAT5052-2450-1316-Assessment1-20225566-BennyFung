package uk.ac.plymouth.comp2000hk.sharedofficebookingapp;

public class Booking {
    private String roomName;
    private String date;
    private String timeSlot;
    private String purpose;
    private String bookedFor;
    private String status; //confirmed or cancelled)

    public Booking(String roomName, String date, String timeSlot, String purpose, String bookedFor, String status) {
        this.roomName = roomName;
        this.date = date;
        this.timeSlot = timeSlot;
        this.purpose = purpose;
        this.bookedFor = bookedFor;
        this.status = status;
    }
    public String getroomName(){
        return roomName;
    }
    public String getdate() {
        return date;
    }
    public String timeSlot(){
        return timeSlot;
    }
    public String getpurpose(){
        return purpose;
    }
    public String getbookedFor(){
        return bookedFor;
    }
    public String getstatus(){
        return status;
    }
    public void setStatus(String status){
        this.status = status;
    }
}
