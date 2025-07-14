package options;

import attendance.Attendance;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import models.User;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class adminOptions {

    public static void showOptions(){

        boolean working = true;
        while(working){
            Scanner scanner = new Scanner(System.in);
            System.out.println("\nChoose among the following choices : ");
            System.out.println("1. Mark Attendance");
            System.out.println("2. View Attendance");
            System.out.println("3. Edit Employee");
            System.out.println("4. Edit Employee's Attendance");
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
                        Gson gson = new Gson();

                        Type userListType = new TypeToken<ArrayList<User>>(){}.getType();
                        List<User> aUser = gson.fromJson(reader, userListType);

                        boolean check = false;

                        for(User u : aUser){
                            if(u.getID() == ID){
                                u.getAttendance().markPresent(date);
                                check = true;
                                break;
                            }
                        }

                        if(!check){
                            System.out.println("ID not found. Enter a valid ID.\n");
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
                        Gson gson = new Gson();

                        Type userListType = new TypeToken<ArrayList<User>>(){}.getType();
                        List<User> aUser = gson.fromJson(reader, userListType);

                        boolean check = false;

                        for(User u : aUser){
                            if(u.getID() == ID){
                                u.getAttendance().viewPresentDates(ID);
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

                case 0 :working = false;
                    break;

                default :
                    System.out.println("Enter a valid input (1/2/3/4/0)");

            }
        }

    }

}
