package options;

import attendance.Attendance;
import attendance.localDateAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import login.userSignup;
import models.User;
import modifyDB.showUser;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static modifyDB.modifyDatabase.deleteFromDatabase;
import static modifyDB.modifyDatabase.editToDatabaseForAdmin;
import static modifyDB.showUser.showUserDetailsToUser;

public class employeeOptions {

    public static void showOptions(String mail){
        Scanner scanner = new Scanner(System.in);

        boolean working = true;
        while(working){
            System.out.println("\n------x------x------x------x------\n");
            System.out.println("Choose among the following choices : ");
            System.out.println("1. Mark Attendance");
            System.out.println("2. View Attendance");
            System.out.println("3. View Personal Details");
            System.out.println("0. Exit");
            System.out.println("\n------x------x------x------x------\n");

            int choice4 = 0;
            boolean valid = false;

            while (!valid) {
                System.out.print("Enter your choice (1/2/3/0) : ");
                String input = scanner.nextLine();

                try {
                    choice4 = Integer.parseInt(input);
                    valid = true;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number.");
                }
            }

            switch (choice4){
                case 1 :
                    System.out.println("Enter your ID : ");
                    int ID = scanner.nextInt();
                    scanner.nextLine();

                    System.out.println("Enter the date to mark (yyyy-mm-dd) : ");
                    String temp = scanner.nextLine();
                    try(FileReader reader = new FileReader("data/Database.json")){
                        LocalDate date = LocalDate.parse(temp);
                        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new localDateAdapter()).setPrettyPrinting().create();

                        Type userListType = new TypeToken<ArrayList<User>>(){}.getType();
                        List<User> aUser = gson.fromJson(reader, userListType);

                        boolean check = false;

                        for(User u : aUser){
                            if(u.getID() == ID){
                                check = true;
                                if(!u.getEmail().equalsIgnoreCase(mail)){
                                    System.out.println("You cannot mark other people's attendance. Enter your ID.");
                                }
                                else{
                                    u.getAttendance().markPresent(ID,date);
                                }
                                break;
                            }
                        }

                        if(!check){
                            System.out.println("ID does not exist \n");
                        }

                    }catch(DateTimeException e){
                        System.out.println("Enter valid date format (yyyy-mm-dd).");
                    }catch(IOException e){
                        System.out.println("Error reading the file.");
                    }

                    break;

                case 2 :
                    System.out.println("Enter your ID : ");
                    ID = scanner.nextInt();
                    scanner.nextLine();

                    try(FileReader reader = new FileReader("data/Database.json")){
                        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new localDateAdapter()).setPrettyPrinting().create();

                        Type userListType = new TypeToken<ArrayList<User>>(){}.getType();
                        List<User> aUser = gson.fromJson(reader, userListType);

                        boolean check = false;

                        for(User u : aUser){
                            if(u.getID() == ID){
                                check = true;
                                if(!u.getEmail().equalsIgnoreCase(mail)){
                                    System.out.println("You cannot view other people's attendance. Enter your ID.");
                                }
                                else{
                                    System.out.println("Your Present days are : " + u.getAttendance().viewPresentDates(ID));
                                }
                                break;
                            }
                        }

                        if(!check){
                            System.out.println("ID does not exist \n");
                        }

                    }catch(IOException e){
                        System.out.println("Error reading the file.");
                    }

                    break;

                case 3 :
                    System.out.println("To view personal details enter your email ID : ");
                    String flag = scanner.nextLine();
                    showUserDetailsToUser(flag);

                    break;

                case 0 :
                    working = false;
                    break;

                default :
                    System.out.println("Enter a valid choice (1/2/3/0)");
            }
        }

    }

    public static void showOptionsForHr(String mail){

        boolean working = true;
        while(working){
            Scanner scanner = new Scanner(System.in);
            System.out.println("\n------x------x------x------x------\n");
            System.out.println("Choose among the following choices : ");
            System.out.println("1. Mark Attendance");
            System.out.println("2. View Attendance");
            System.out.println("3. View Personal Details");
            System.out.println("4. Create a new User");
            System.out.println("5. View all Users");
            System.out.println("0. Exit");
            System.out.println("\n------x------x------x------x------\n");

            int choice3 = 0;
            boolean valid = false;

            while (!valid) {
                System.out.print("Enter your choice (1/2/3/4/5/0) : ");
                String input = scanner.nextLine();

                try {
                    choice3 = Integer.parseInt(input);
                    valid = true;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number.");
                }
            }

            switch(choice3){
                case 1 :
                    System.out.println("Enter your ID : ");
                    int ID = scanner.nextInt();
                    scanner.nextLine();

                    System.out.println("Enter the date to mark (yyyy-mm-dd) : ");
                    String temp = scanner.nextLine();
                    try(FileReader reader = new FileReader("data/Database.json")){
                        LocalDate date = LocalDate.parse(temp);
                        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new localDateAdapter()).setPrettyPrinting().create();

                        Type userListType = new TypeToken<ArrayList<User>>(){}.getType();
                        List<User> aUser = gson.fromJson(reader, userListType);

                        boolean check = false;

                        for(User u : aUser){
                            if(u.getID() == ID){
                                check = true;
                                if(!u.getEmail().equalsIgnoreCase(mail)){
                                    System.out.println("You cannot mark other people's attendance. Enter your ID.");
                                }
                                else{
                                    u.getAttendance().markPresent(ID,date);
                                }
                                break;
                            }
                        }

                        if(!check){
                            System.out.println("ID does not exist \n");
                        }

                    }catch(DateTimeException e){
                        System.out.println("Enter valid date format (yyyy-mm-dd).");
                    }catch(IOException e){
                        System.out.println("Error reading the file.");
                    }

                    break;

                case 2 :
                    System.out.println("Enter your ID : ");
                    ID = scanner.nextInt();
                    scanner.nextLine();

                    try(FileReader reader = new FileReader("data/Database.json")){
                        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new localDateAdapter()).setPrettyPrinting().create();

                        Type userListType = new TypeToken<ArrayList<User>>(){}.getType();
                        List<User> aUser = gson.fromJson(reader, userListType);

                        boolean check = false;

                        for(User u : aUser){
                            if(u.getID() == ID){
                                System.out.println("Present days are : " + u.getAttendance().viewPresentDates(ID));
                                check = true;
                                break;
                            }
                        }

                        if(!check){
                            System.out.println("ID not found. Enter a valid ID.\n");
                        }

                    }catch(IOException e){
                        System.out.println("Error reading the file.");
                    }

                    break;

                case 3 :
                    System.out.println("To view personal details enter your email ID : ");
                    String flag = scanner.nextLine();
                    showUserDetailsToUser(flag);
                    break;

                case 4 :System.out.println("Please fill in the following details : ");
                    userSignup.enterDetails();
                    break;

                case 5 :
                    showUser.showAllUsers();
                    break;

                case 0 :working = false;
                    break;

                default :
                    System.out.println("Enter a valid input (1/2/3/4/5/0)");

            }
        }
    }
}
