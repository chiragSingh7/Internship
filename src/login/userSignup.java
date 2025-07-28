package login;

import java.util.Scanner;
import modifyDB.modifyDatabase;

import static checkAndValidate.checkName.validName;
import static checkAndValidate.validateMail.isValidMail;
import static checkAndValidate.validateMail.validMail;
import static modifyDB.showUser.*;

public class userSignup {

    public static void enterDetails() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter your name : ");
        String name = scanner.nextLine();

        while(!validName(name)){
            System.out.println("\nEnter a valid name : ");
            name = scanner.nextLine();
        }

        System.out.println("Enter your mail : ");
        String mail = scanner.nextLine();

        while(!isValidMail(mail)){
            System.out.println("\nEnter a valid mail : ");
            mail = scanner.nextLine();
        }

        //run the loop till the mail entered is valid
        boolean wrong = true;
        while (wrong) {
            //if mail is valid then wrong variable becomes false
            wrong = !validMail(mail);

            //if the mail is not valid show the mail and ask for another valid input
            if (wrong) {
                System.out.println("\nEnter a valid domain");
                System.out.println("Your provided mail : " + mail);
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

        System.out.println("\nSuccessfully Registered !!\n");

        int ID = assignID();
        modifyDatabase.addToDatabase(ID, name, mail, password);
        showUserDetailsToUser(mail);

    }

}