

/**
 * Author: Jayson Kjenstad
 *
 * This file is the faculty model to fill the data from JSON respones
 * for the faculty lists.
 */


package com.example.a7142885.doorpanes;

/**
 * Created by 7142885 on 3/30/2017.
 */

public class FacultyModel{

    //all needed values
    public String Office;
    public String Department;
    public String Title;
    public String PersonID;
    public String LastName;
    public String FirstName;
    public String FullName;
    public String Username;
    public int Type;

    //list of getters
    public String getOffice() {return Office;}

    public String getDepartment() {return Department;}

    public String getTitle() {return Title;}

    public String getPersonID() {return PersonID;}

    public String getLastName() {return LastName;}

    public String getFirstName() {return FirstName;}

    public String getFullName() {return FullName;}

    public String getUsername() {return Username;}

    public int getType() {return Type;}

    //list of setters
    public void setOffice(String Office) {
        this.Office = Office;
    }

    public void setDepartment(String Department) {this.Department = Department;}

    public void setTitle(String Title) {this.Title = Title;}

    public void setPersonID(String PersonID) {this.PersonID = PersonID;}

    public void setLastName(String LastName) {this.LastName = LastName;}

    public void setFirstName(String FirstName) {this.FirstName = FirstName;}

    public void setFullName(String FullName) {this.FullName = FullName;}

    public void setUsername(String Username) {this.Username = Username; }

    public void setUsername(int Type) {this.Type = Type; }

}
