import checkAndValidate.checkUser;
import login.userLogin;
import login.userSignup;

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main{
    static public void main(String[] args) throws IOException {
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

                System.out.println("Enter your choice : ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch(choice) {
                    //if login
                    case 1 : System.out.println("\n Registered user login ");
                        System.out.println("Enter your email : ");
                        String mail = scanner.nextLine();

                        boolean exitLogin = false;

                        //checking if the ID exists among the registered users or not
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
                                 System.out.println("Enter your choice : ");
                                 int choice2 = scanner.nextInt();
                                 scanner.nextLine();

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

                        System.out.println("Successfully logged in!!\n\n");
                        checkUser.check(mail);

                        break;

                    //if signup
                    case 2 : System.out.println("\n New user signup");
                        System.out.println("Please fill in the following details : ");
                        //show details to verify with the user before moving onto the next step, if correction needed enter information again
                        userSignup.enterDetails();

                        break;

                    // if exit
                    case 0 : System.out.println("Exiting the program");
                        working = false;
                        break;

                    //default
                    default :
                        System.out.println("Enter a valid choice (1/2/0)");
                }
            }
            scanner.close();
        } catch (Exception e) {
            System.out.println("Something went wrong!!");
            e.printStackTrace();
        }

    }
}