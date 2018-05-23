/**
 * Author: Jayson Kjenstad
 *
 * This java file is the model for a calendar event.  The class extends
 * Realm Object so it is able to serialize the JSON reponses.
 */

package com.example.a7142885.doorpanes;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class CalendarEvent extends RealmObject{

    //all needed values for the Calendar Event
    @PrimaryKey
    public int EventID;
    public String Title;
    public String Description;
    public String StartTime;
    public String EndTime;
    public int RepeatID;
    public String EventOwner;
    //public RoomModel Room;
    public boolean Cancelled;
    public int Type;

    //list of getters
    public int getEventID() {return EventID;}

    public String getTitle() {
        return Title;
    }

    public String getDescription() {return Description;}

    public String getStartTime () {return StartTime;}

    public String getEndTime () {return EndTime;}

    public int getRepeatID() {return RepeatID;}

    public  String getEventOwner() {return EventOwner;}

    //public  RoomModel getRoom() {return Room;}

    public  boolean getCancelled() {return Cancelled;}

    public int getType() {return Type;}

    //list of setters
    public void setId(int EventID) {
        this.EventID = EventID;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public void setDescription(String Description){
        this.Description = Description;
    }

    public void setStartTime(String StartTime){
        this.StartTime = StartTime;
    }

    public void setEndTime(String EndTime) {
        this.EndTime = EndTime;
    }

    public void setRepeatID(int RepeatID) {
        this.RepeatID = RepeatID;
    }

    public void setEventOwner(String EventOwner) {
        this.EventOwner = EventOwner;
    }

    // public void setRoom(RoomModel Room) {this.Room = Room;}

    public void setCancelled(boolean Cancelled) {this.Cancelled = Cancelled;}

    public void setType(int Type){this.Type = Type;}
}
