import login.userSignup;
import java.util.Scanner;

import static checkAndValidate.checkMail.checkMailAndLogin;
import static checkAndValidate.validateMail.isValidMail;
import static checkAndValidate.validateMail.validDomain;

public class Main{
    static public void main(String[] args) {
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
                        while(!validDomain(mail) && isValidMail(mail)){
                            System.out.println("Enter a valid mail : ");
                            mail = scanner.nextLine();
                        }

                        boolean exit = checkMailAndLogin(mail);
                        if(exit){
                            break;
                        }

                        break;

                    //if signup
                    case 2 :
                        System.out.println("\n New user signup");
                        System.out.println("Please fill in the following details : ");
                        //show details to verify with the user before moving onto the next step, if correction needed give information again
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
}
