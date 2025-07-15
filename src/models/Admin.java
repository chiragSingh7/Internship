package models;

public class Admin extends User{
    Admin(int ID, String name, String email, String password){
        super(ID, name,email,password);
    }

    public void viewAttendance(){
        System.out.println("Your attendance is: ");
    }
}
