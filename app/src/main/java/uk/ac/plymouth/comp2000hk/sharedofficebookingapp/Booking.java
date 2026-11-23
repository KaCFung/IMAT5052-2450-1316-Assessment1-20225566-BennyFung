package uk.ac.plymouth.comp2000hk.sharedofficebookingapp;
// Data model representing a single room reservation record.
// Fields as public for getters/setters updates in the prototype.
public class Booking {
    public String roomName;
    public String date;
    public String timeSlot;
    public String purpose;
    public String bookedFor;
    public String status; // confirmed or cancelled

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