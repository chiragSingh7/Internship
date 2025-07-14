package options;

import attendance.Attendance;
import attendance.localDateAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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

import static modifyDB.showUser.showUserDetails;

public class employeeOptions {

    public static void showOptions(String mail){
        Scanner scanner = new Scanner(System.in);

        boolean working = true;
        while(working){
            System.out.println("\nChoose among the following choices : ");
            System.out.println("1. Mark Attendance");
            System.out.println("2. View Attendance");
            System.out.println("3. View Details");
            System.out.println("0. Exit");
            System.out.println("\nEnter your choice : ");
            int choice4 = scanner.nextInt();
            scanner.nextLine();

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
                                if(!u.getEmail().equalsIgnoreCase(mail)){
                                    System.out.println("You cannot view other people's attendance. Enter your ID.");
                                }
                                else{
                                    System.out.println("Your Present days are : " + u.getAttendance().viewPresentDates(ID));
                                    check = true;
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
                    showUserDetails(flag);

                case 0 :
                    working = false;
                    break;

                default :
                    System.out.println("Enter a valid choice (1/2/3/0)");
            }
        }

    }
}
