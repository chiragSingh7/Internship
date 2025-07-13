package login;

import java.util.Scanner;
import modifyDB.modifyDatabase;
import static checkAndValidate.validateMail.validMail;

public class userSignup {

    public static void enterDetails() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter your name : ");
        String name = scanner.nextLine();

        System.out.println("Enter your mail : ");
        String mail = scanner.nextLine();

        //run the loop till the mail entered is valid
        boolean wrong = true;
        while (wrong) {
            //if mail is valid then wrong variable becomes false
            wrong = !validMail(mail);

            //if the mail is not valid show the mail and ask for another valid input
            if (wrong) {
                System.out.println("\nInvalid mail!!. Check the mail you have provided and enter again.");
                System.out.println("Your provided mail : " + mail);
                System.out.println("Enter the mail again : ");
                mail = scanner.nextLine();
            }
        }

        System.out.println("\nEnter the password you want to set : ");
        String password = scanner.nextLine();

        System.out.println("\nEnter the password again to confirm : ");
        String pass = scanner.nextLine();

        while (!password.equals(pass)) {
            System.out.println("\nThe passwords don't match. Re-Enter your password. ");
            pass = scanner.nextLine();
        }

        System.out.println("\n\nSuccessfully Registered !!\n\n");

        modifyDatabase.addToDatabase(name, mail, password);
    }

    public static void showDetails(){

    }
}