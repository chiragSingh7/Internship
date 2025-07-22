package checkAndValidate;

import attendance.attendanceTime;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import login.userLogin;
import login.userSignup;
import models.GsonImports;
import models.User;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class checkMail {
    public static boolean checkMailAndLogin(String mail) throws IOException {
        Scanner scanner = new Scanner(System.in);
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
        if(exitLogin) return exitLogin;

        System.out.println("Enter your password : ");
        String password = scanner.nextLine();

        // check if the password is correct or not
        while(!userLogin.checkPass(mail,password)){
            System.out.println("Password doesn't matches with the registered password. Try again.");
            password = scanner.nextLine();
        }

        int ID = getIdFromMail(mail);
        attendanceTime.addLoginTime(ID);
        System.out.println("Successfully logged in!!\n");
        checkUser.checkRole(mail);

        return false;
    }

    public static int getIdFromMail(String mail){
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
