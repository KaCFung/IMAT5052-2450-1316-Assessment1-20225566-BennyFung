package uk.ac.plymouth.comp2000hk.sharedofficebookingapp;

//room information
public class Room {
    private String name;
    private String type; //meeting or seminar
    private String capacityinfo;

    public Room (String name, String type, String capacityinfo){
        this.name = name;
        this.type = type;
        this.capacityinfo = capacityinfo;
    }
    public String getName(){
            return name;
    }
        public String getType(){
            return type;
    }
    public String getCapacityinfo(){
            return capacityinfo;
    }
}
