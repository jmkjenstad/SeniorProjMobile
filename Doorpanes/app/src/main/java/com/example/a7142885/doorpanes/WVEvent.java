/**
 * Author: alamkanak
 * Modified: Jayson Kjenstad
 *
 * This is the Week View Event. class from the open source calendar framework used.  I modified this
 * class to add in a couple fields we needed such as the event owner.  The read me on alamkanak's GitHub page specifies
 * this class can be modified as long as it is documented that it is almakanak's work.
 */


package com.example.a7142885.doorpanes;

import com.alamkanak.weekview.WeekViewEvent;

import java.util.Calendar;
import java.util.StringTokenizer;

public class WVEvent{
    private long mId;
    private Calendar mStartTime;
    private Calendar mEndTime;
    private String mName;
    private int mColor;
    private String mDescription;
    private String mOwner;

    public WVEvent(){

    }

    /**
     * Initializes the event for week view.
     * @param name Name of the event.
     * @param startYear Year when the event starts.
     * @param startMonth Month when the event starts.
     * @param startDay Day when the event starts.
     * @param startHour Hour (in 24-hour format) when the event starts.
     * @param startMinute Minute when the event starts.
     * @param endYear Year when the event ends.
     * @param endMonth Month when the event ends.
     * @param endDay Day when the event ends.
     * @param endHour Hour (in 24-hour format) when the event ends.
     * @param endMinute Minute when the event ends.
     * @param description Description of event
     * @param owner Who owns the event
     */
    public WVEvent(long id, String name, int startYear, int startMonth, int startDay, int startHour, int startMinute, int endYear, int endMonth, int endDay, int endHour, int endMinute, String description, String owner) {
        this.mId = id;

        this.mStartTime = Calendar.getInstance();
        this.mStartTime.set(Calendar.YEAR, startYear);
        this.mStartTime.set(Calendar.MONTH, startMonth-1);
        this.mStartTime.set(Calendar.DAY_OF_MONTH, startDay);
        this.mStartTime.set(Calendar.HOUR_OF_DAY, startHour);
        this.mStartTime.set(Calendar.MINUTE, startMinute);

        this.mEndTime = Calendar.getInstance();
        this.mEndTime.set(Calendar.YEAR, endYear);
        this.mEndTime.set(Calendar.MONTH, endMonth-1);
        this.mEndTime.set(Calendar.DAY_OF_MONTH, endDay);
        this.mEndTime.set(Calendar.HOUR_OF_DAY, endHour);
        this.mEndTime.set(Calendar.MINUTE, endMinute);

        this.mName = name;

        this.mDescription = description;

        this.mOwner = owner;
    }

    /**
     * Initializes the event for week view.
     * @param name Name of the event.
     * @param startTime The time when the event starts.
     * @param endTime The time when the event ends.
     * @param description Description of event
     * @param owner  Who owns the event
     */
    public WVEvent(long id, String name, Calendar startTime, Calendar endTime, String description, String owner) {
        this.mId = id;
        this.mName = name;
        this.mStartTime = startTime;
        this.mEndTime = endTime;
        this.mDescription = description;
        this.mOwner = owner;
    }


    public Calendar getStartTime() {
        return mStartTime;
    }

    public void setStartTime(Calendar startTime) {
        this.mStartTime = startTime;
    }

    public Calendar getEndTime() {
        return mEndTime;
    }

    public void setEndTime(Calendar endTime) {
        this.mEndTime = endTime;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public int getColor() {
        return mColor;
    }

    public void setColor(int color) {
        this.mColor = color;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        this.mId = id;
    }

    public String getDescription() {return mDescription;}

    public void setDescription(String description){this.mDescription = description;}

    public String getOwner() {return mOwner;}

    public void setOwner(String owner){this.mOwner = owner;}
}