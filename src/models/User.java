package models;

import attendance.Attendance;

import java.time.LocalDate;
import java.util.List;

public class User {
    protected String name;
    protected int ID;
    protected String email;
    protected String password;
    protected String role;
    protected String subRole;
    // composition of attendance to use the functions in user
    private Attendance attendance = new Attendance();

    public User(String name, String email, String password){
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = "unverified";
        this.subRole = "unverified";
    }

    public void setRole(String role){
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void addAttendance(LocalDate date){
        attendance.markPresent(date);
    }

    public void setID(int ID){
        this.ID = ID;
    }

    public int getID(){
        return this.ID;
    }

    public void setSubRole(String subRole){
        this.subRole = subRole;
    }

    public String getSubRole(){
        return subRole;
    }

    public String getEmail(){
        return this.email;
    }

    public String getName(){
        return this.name;
    }

    public String getPassword() {
        return password;
    }

    public Attendance getAttendance() {
        return this.attendance;
    }

    public List<LocalDate> getPresentDates(){
        return attendance.viewPresentDates();
    }

    public List<LocalDate> getAbsentDates(){
        return attendance.viewAbsentDates();
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", id='" + ID + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
    //public abstract void viewAttendance();
}
