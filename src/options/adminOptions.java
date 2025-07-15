package options;

import attendance.localDateAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import login.userSignup;
import models.Employee;
import models.User;
import modifyDB.showUser;

import static modifyDB.modifyDatabase.deleteFromDatabase;
import static modifyDB.modifyDatabase.editToDatabaseForAdmin;
import static modifyDB.showUser.showUserDetailsToUser;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class adminOptions {

    public static void showOptions(String mail){

        boolean working = true;
        while(working) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("\n------x------x------x------x------\n");
            System.out.println("Choose among the following choices : ");
            System.out.println("1. Mark Attendance");
            System.out.println("2. View Attendance");
            System.out.println("3. View personal details");
            System.out.println("4. Create a new User");
            System.out.println("5. View all Users");
            System.out.println("6. Update details for a User");
            System.out.println("7. Deleting a User");
            System.out.println("8. Assign Roles");
            System.out.println("0. Exit");
            System.out.println("\n------x------x------x------x------\n");

            int choice3 = 0;
            boolean valid = false;

            while (!valid) {
                System.out.print("Enter your choice (1/2/3/4/5/6/7/8/0) : ");
                String input = scanner.nextLine();

                try {
                    choice3 = Integer.parseInt(input);
                    valid = true;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number.");
                }
            }

            switch (choice3) {
                case 1:
                    System.out.println("Enter your ID : ");
                    int ID = scanner.nextInt();
                    scanner.nextLine();

                    System.out.println("Enter the date to mark (yyyy-mm-dd) : ");
                    String temp = scanner.nextLine();
                    try (FileReader reader = new FileReader("data/Database.json")) {
                        LocalDate date = LocalDate.parse(temp);
                        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new localDateAdapter()).setPrettyPrinting().create();

                        Type userListType = new TypeToken<ArrayList<User>>() {
                        }.getType();
                        List<User> aUser = gson.fromJson(reader, userListType);

                        boolean check = false;

                        for (User u : aUser) {
                            if (u.getID() == ID) {
                                check = true;
                                if (!u.getEmail().equalsIgnoreCase(mail)) {
                                    System.out.println("You cannot mark other people's attendance. Enter your ID.");
                                } else {
                                    u.getAttendance().markPresent(ID, date);
                                }
                                break;
                            }
                        }

                        if (!check) {
                            System.out.println("ID does not exist \n");
                        }

                    } catch (DateTimeException e) {
                        System.out.println("Enter valid date format (yyyy-mm-dd).");
                    } catch (IOException e) {
                        System.out.println("Error reading the file.");
                    }

                    break;

                case 2:
                    System.out.println("Enter your ID : ");
                    ID = scanner.nextInt();
                    scanner.nextLine();

                    try (FileReader reader = new FileReader("data/Database.json")) {
                        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new localDateAdapter()).setPrettyPrinting().create();

                        Type userListType = new TypeToken<ArrayList<User>>() {
                        }.getType();
                        List<User> aUser = gson.fromJson(reader, userListType);

                        boolean check = false;

                        for (User u : aUser) {
                            if (u.getID() == ID) {
                                System.out.println("Present days are : " + u.getAttendance().viewPresentDates(ID));
                                check = true;
                                break;
                            }
                        }

                        if (!check) {
                            System.out.println("ID not found. Enter a valid ID.\n");
                        }

                    } catch (IOException e) {
                        System.out.println("Error reading the file.");
                    }

                    break;

                case 3:
                    System.out.println("To view personal details enter your email ID : ");
                    String flag = scanner.nextLine();
                    showUserDetailsToUser(flag);

                    break;

                case 4:
                    System.out.println("Please fill in the following details : ");
                    userSignup.enterDetails();

                    break;

                case 5:
                    showUser.showAllUsers();
                    break;

                case 6:
                    System.out.println("Enter the ID you want to edit details for : ");
                    ID = scanner.nextInt();
                    scanner.nextLine();

                    try (FileReader reader = new FileReader("data/Database.json")) {
                        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new localDateAdapter()).setPrettyPrinting().create();

                        Type userListType = new TypeToken<ArrayList<User>>() {}.getType();
                        List<User> aUser = gson.fromJson(reader, userListType);

                        boolean check = false;

                        for (User u : aUser) {
                            if (u.getID() == ID) {
                                editToDatabaseForAdmin(ID);
                                check = true;
                                break;
                            }
                        }

                        if (!check) {
                            System.out.println("ID not found. Enter a valid ID.\n");
                        }

                        try(FileWriter writer = new FileWriter("data/Database.json")){
                            gson.toJson(aUser, writer);
                        }

                    } catch (IOException e) {
                        System.out.println("Error reading the file.");
                    }

                    break;

                case 7:
                    System.out.println("Enter the ID of the user you want to remove : ");
                    ID = scanner.nextInt();
                    scanner.nextLine();

                    if (ID == 20250001) {
                        System.out.println("You cannot delete the SuperUser !!");
                        break;
                    }

                    System.out.println("Are you sure you want to remove the details of ID : " + ID + " ? (YES/NO)");
                    temp = scanner.nextLine();

                    boolean yes = true;
                    while (yes) {
                        if (temp.equalsIgnoreCase("YES")) {
                            deleteFromDatabase(ID);
                            yes = false;
                        } else if (temp.equalsIgnoreCase("NO")) {
                            break;
                        } else {
                            System.out.println("Enter a valid input (YES/NO)");
                            temp = scanner.nextLine();
                        }
                    }

                    break;

                case 8:
                    adminOptions.assignRoles();
                    break;

                case 0:
                    working = false;
                    break;

                default:
                    System.out.println("Enter a valid input (1/2/3/4/5/6/7/8/0)");

            }
        }
    }

    public static void assignRoles(){
        try(FileReader reader = new FileReader("data/Database.json")){
            Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new localDateAdapter()).setPrettyPrinting().create();

            Scanner scanner = new Scanner(System.in);
            Type userListType = new TypeToken<ArrayList<User>>(){}.getType();
            List<User> aUser = gson.fromJson(reader, userListType);

            boolean found = false;
            for(User u : aUser){
                if(u.getRole().equalsIgnoreCase("unverified")){
                    found = true;

                    System.out.println("\nID : " + u.getID());
                    System.out.println("Role : " + u.getRole());
                    System.out.println("Sub-Role : " + u.getSubRole());

                    boolean input1 = true;
                    String newRole;

                    while(input1){
                        System.out.println("\nEnter the role for ID : " + u.getID() + "\n(Admin/Employee)");
                        newRole = scanner.nextLine();

                        //check if the input is among Admin and Employee only, else reject the input and ask for another
                        if(newRole.equalsIgnoreCase("Admin")){
                            u.setRole("Admin");
                            input1 = false;

                            boolean input2 = true;
                            while(input2){
                                System.out.println("\nEnter the Sub-Role for ID : " + u.getID() + "\n(ITHead/SuperUser");
                                String newSubRole = scanner.nextLine();

                                //check if the input is among ITHead and SuperUser only, else reject
                                if(newSubRole.equalsIgnoreCase("ITHead") || newSubRole.equalsIgnoreCase("SuperUser")){
                                    u.setSubRole(newSubRole);
                                    input2 = false;
                                } else{
                                    System.out.println("Enter a valid Sub-Role !!");
                                }
                            }
                        } else if (newRole.equalsIgnoreCase("Employee")) {
                            u.setRole("Employee");
                            input1 = false;

                            boolean input2 = true;
                            while(input2){
                                System.out.println("\nEnter the Sub-Role for ID : " + u.getID() + "\n(Intern/HR/Trainee)");
                                String newSubRole = scanner.nextLine();

                                //check if the input is among HR, Trainee and Intern only, else reject
                                if(newSubRole.equalsIgnoreCase("HR") || newSubRole.equalsIgnoreCase("Trainee") || newSubRole.equalsIgnoreCase("Intern")){
                                    u.setSubRole(newSubRole);
                                    input2 = false;
                                } else{
                                    System.out.println("Enter a valid Sub-Role !!");
                                }
                            }
                        } else{
                            System.out.println("Enter a valid Role !!");
                        }
                    }
                }
            }

            if(!found){
                System.out.println("No user with unverified roles/sub-roles.");
            }

            try(FileWriter writer = new FileWriter("data/Database.json")){
                gson.toJson(aUser, writer);
            }catch (IOException e){
                System.out.println("Error while writing to file " + e.getMessage());
            }
        } catch (IOException e) {
            System.out.println("Error while reading the file " + e.getMessage());
        }
    }

}
