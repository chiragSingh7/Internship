package options;

import attendance.Attendance;
import attendance.localDateAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import models.User;

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
        while(working){
            Scanner scanner = new Scanner(System.in);
            System.out.println("\nChoose among the following choices : ");
            System.out.println("1. Mark Attendance");
            System.out.println("2. View Attendance");
            System.out.println("3. Edit Employee Details");
            System.out.println("4. Edit Employee's Attendance");
            System.out.println("5. Assign Roles");
            System.out.println("0. Exit");
            System.out.println("Enter your choice : ");
            int choice3 = scanner.nextInt();
            scanner.nextLine();

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
                                if(!u.getEmail().equalsIgnoreCase(mail)){
                                    System.out.println("You cannot mark other people's attendance. Enter your ID.");
                                }
                                else{
                                    u.getAttendance().markPresent(ID,date);
                                    check = true;
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

                case 4 :

                case 5 :
                    adminOptions.assignRoles();
                    break;

                case 0 :working = false;
                    break;

                default :
                    System.out.println("Enter a valid input (1/2/3/4/0)");

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
                if(u.getRole().equals("unverified")){
                    System.out.println("\nID : " + u.getID());
                    System.out.println("Role : " + u.getRole());
                    System.out.println("Sub-Role : \n" + u.getSubRole());

                    System.out.println("\nEnter the role for ID : " + u.getID());
                    String newRole = scanner.nextLine();
                    u.setRole(newRole);

                    System.out.println("\nEnter the sub-role for ID : " + u.getID());
                    String newSubRole = scanner.nextLine();
                    u.setSubRole(newSubRole);
                }
            }

            try(FileWriter writer = new FileWriter("data/Database.json")){
                gson.toJson(aUser, writer);
            }catch (IOException e){
                System.out.println("Error while writing to file " + e.getMessage());
            }

            scanner.close();
        } catch (IOException e) {
            System.out.println("Error while reading the file " + e.getMessage());
        }
    }


}
