package models;

import attendance.Attendance;
import attendance.attendanceTime;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class User {
    protected String name;
    protected int ID;
    protected String email;
    protected String password;
    protected String role;
    protected String subRole;
    private static String[] userRoles = {"Admin" , "Employee"};
    private static String[] empSubRoles = {"HR", "Intern", "Trainee"};
    private static String[] adminSubRoles = {"SuperUser", "ITHead"};
    // composition of attendance to use the functions in user
    private Attendance attendance = new Attendance();
    private attendanceTime attendanceTime = new attendanceTime();

    public User(int ID, String name, String email, String password){
        this.name = name;
        this.ID = ID;
        this.email = email;
        this.password = password;
        this.role = "unverified";
        this.subRole = "unverified";
    }

    public static String[] getAdminSubRoles() {
        return adminSubRoles;
    }

    public void setAttendanceTime (attendanceTime attendanceTime){
        this.attendanceTime = attendanceTime;
    }

    public attendanceTime getAttendanceTime(){
        return this.attendanceTime;
    }

    public static String[] getEmpSubRoles() {
        return empSubRoles;
    }

    public static String[] getUserRoles() {
        return userRoles;
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
        return this.password;
    }

    public Attendance getAttendance() {
        return this.attendance;
    }

    public List<LocalTime> getLoginTime(){
        return attendanceTime.getLoginTime();
    }

    public List<LocalTime> getLogoutTime(){
        return attendanceTime.getLogoutTime();
    }

    public List<LocalDate> getPresentDates(){
        return attendance.getPresentDays();
    }

    public List<LocalDate> getAbsentDates(){
        return attendance.getAbsentDays();
    }

    @Override
    public String toString() {
        try {
            return "Employee{" +
                    "name='" + name + '\'' +
                    ", id='" + ID + '\'' +
                    ", email='" + email + '\'' +
                    ", present days='" + attendance.viewPresentDates(ID) + '\'' +
                    ", absent days='" + attendance.viewAbsentDates(ID) + '\'' +
                    ", login time='" + attendanceTime.viewLoginTime(ID) + '\'' +
                    ", logout time='" + attendanceTime.viewLogoutTime(ID) + '\'' +
                    '}';
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
