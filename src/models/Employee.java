package models;

public class Employee extends User {

    Employee(int ID, String name, String email, String password){
        super(ID, name, email, password);
    }
    public void markPresent(){

    }

    public void viewAttendance(){
        System.out.println();
    }

    public void viewDetails(int ID){

    }
}
