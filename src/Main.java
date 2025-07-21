import attendance.attendanceTime;
import attendance.durationAdapter;
import attendance.localDateAdapter;
import attendance.localTimeAdapter;
import checkAndValidate.checkUser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import login.userLogin;
import login.userSignup;
import models.GsonImports;
import models.User;

import java.io.FileNotFoundException;
import java.lang.reflect.Type;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main{
    static public void main(String[] args) throws IOException {
        int ID = -1;
        try{
            Scanner scanner = new Scanner(System.in);
            boolean working = true;

            while(working){
                System.out.println("\nWelcome User ");
                System.out.println("\n------x------x------x------x------\n");
                System.out.println("Select whether to login/signup : \n");
                System.out.println("1. Login");
                System.out.println("2. Signup");
                System.out.println("0. Exit");
                System.out.println("\n------x------x------x------x------\n");

                int choice = 0;
                boolean validInput = false;

                while (!validInput) {
                    System.out.print("Enter your choice (1/2/0): ");
                    String input = scanner.nextLine();

                    try {
                        choice = Integer.parseInt(input);
                        validInput = true;
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a number.");
                    }
                }

                switch(choice) {
                    //if login
                    case 1 :
                        System.out.println("\nRegistered user login ");
                        System.out.println("Enter your email : ");
                        String mail = scanner.nextLine();

                        boolean exitLogin = false;

                        //checking if the mail exists among the registered users or not
                         while(!userLogin.checkMail(mail) && !exitLogin){
                             System.out.println("Check the email you have entered or go to signup");
                             System.out.println("The email you entered : " + mail);
                             boolean flag = true;

                             //if the mail does not match ask for signup or enter again
                             while(flag){
                                 System.out.println("\n------x------x------x------x------\n");
                                 System.out.println("1. Enter your email again...");
                                 System.out.println("2. Signup Instead");
                                 System.out.println("\n------x------x------x------x------\n");

                                 int choice2 = 0;
                                 boolean valid = false;

                                 while (!valid) {
                                     System.out.print("Enter your choice (1/2): ");
                                     String input = scanner.nextLine();

                                     try {
                                         choice2 = Integer.parseInt(input);
                                         valid = true;
                                     } catch (NumberFormatException e) {
                                         System.out.println("Invalid input. Please enter a number.");
                                     }
                                 }

                                 switch (choice2){
                                     case 1 : // asking for mail again not checking since we'll chek eventually in the while loop
                                         System.out.println("Enter the mail again : ");
                                         mail = scanner.nextLine();
                                         flag = false;
                                         break;

                                     case 2 :
                                         userSignup.enterDetails();
                                         exitLogin = true;
                                         flag = false;
                                         break;

                                     default :
                                         System.out.println("Enter a valid choice (1/2)");
                                         break;
                                 }
                             }
                         }

                         if(exitLogin){
                             break;
                         }

                         System.out.println("Enter your password : ");
                         String password = scanner.nextLine();

                         // check if the password is correct or not
                         while(!userLogin.checkPass(mail,password)){
                             System.out.println("Password doesn't matches with the registered password. Try again.");
                             password = scanner.nextLine();
                         }

                        ID = getIdFromMail(mail);
                        attendanceTime.addLoginTime(ID);
                        System.out.println("Successfully logged in!!\n");
                        checkUser.checkRole(mail);

                        break;

                    //if signup
                    case 2 :
                        System.out.println("\n New user signup");
                        System.out.println("Please fill in the following details : ");
                        //show details to verify with the user before moving onto the next step, if correction needed enter information again
                        userSignup.enterDetails();

                        break;

                    // if exit
                    case 0 :
                        System.out.println("Exiting the program");
                        working = false;
                        break;

                    //default
                    default :
                        System.out.println("Enter a valid choice (1/2/0)");
                }
            }
        } catch (Exception e) {
            System.out.println("Something went wrong!!");
            e.printStackTrace();
        }

    }

    public static int getIdFromMail(String mail) throws FileNotFoundException {
        try(FileReader reader = new FileReader("data/Database.json")){
            Gson gson = GsonImports.createGson();

            Type userListType = new TypeToken<ArrayList<User>>(){}.getType();
            List<User> aUser = gson.fromJson(reader, userListType);

            boolean found = false;
            for(User u : aUser){
                if(mail.equalsIgnoreCase(u.getEmail())){
                    return u.getID();
                }
            }

            if(!found){
                System.out.println("No such mail exists ");
            }
        } catch (IOException e) {
            System.out.println("Error reading the file ");
        }
        return -1;
    }
}
