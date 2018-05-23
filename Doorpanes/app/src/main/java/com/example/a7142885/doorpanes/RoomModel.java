/**
 * Author: Jayson Kjenstad
 *
 * This file is the room model.  Holds all the JSON room responses.
 */

package com.example.a7142885.doorpanes;

    //all needed variables
    public class RoomModel {
    public String RoomID;
    public String RoomNumber;
    public String RoomName;
    public String RoomOwner;
    public String Building;
    public  String Campus;
    public String City;
    public int Type;


    //list of getters
    public String getRoomID() {return RoomID;}

    public String getRoomName() {return RoomName;}

    public String getRoomNumber() {return RoomNumber;}

    public String getRoomOwner() {return RoomOwner;}

    public String getBuilding() {return Building;}

    public String getCampus() {return Campus;}

    public String getCity() {return City;}

    public int getType() {return Type;}

    //list of setters
    public void setRoomID(String RoomID) {
        this.RoomID = RoomID;
    }

    public void setRoomName(String RoomName) {this.RoomName = RoomName;}

    public void setRoomNumber(String RoomNumber) {
        this.RoomNumber = RoomNumber;
    }

    public void setRoomOwner(String RoomOwner) {
        this.RoomOwner = RoomOwner;
    }

    public void setBuilding(String Building) {
        this.Building = Building;
    }

    public void setCampus(String Campus) {
        this.Campus = Campus;
    }

    public void setCity(String City) {
        this.City = City;
    }

    public void setRoomID(int Type) {
        this.Type = Type;
    }

}
