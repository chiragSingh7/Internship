package login;

import java.util.Scanner;
import modifyDB.modifyDatabase;

import static checkAndValidate.checkName.validName;
import static checkAndValidate.validateMail.isValidMail;
import static checkAndValidate.validateMail.validDomain;
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
            System.out.println("\nEnter a valid mail format : ");
            mail = scanner.nextLine();
        }

        boolean wrong = true;
        while (wrong) {
            wrong = !validDomain(mail);

            //if the mail is not valid show the mail and ask for another valid input
            if (wrong) {
                System.out.println("\nEnter a valid domain");
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