package options;

import attendance.*;
import login.userSignup;
import modifyDB.showUser;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import static attendance.Attendance.*;
import static modifyDB.modifyDatabase.*;
import static modifyDB.showUser.showUserDetailsToUser;

public class employeeOptions {

    public static void showOptions(String mail) throws IOException {
        Scanner scanner = new Scanner(System.in);

        boolean working = true;
        while(working){
            System.out.println("\n------x------x------x------x------\n");
            System.out.println("Choose among the following choices : ");
            System.out.println("1. ");
            System.out.println("2. View Attendance");
            System.out.println("3. View Personal Details");
            System.out.println("0. Exit");
            System.out.println("\n------x------x------x------x------\n");

            int choice4 = 0;
            boolean valid = false;

            while (!valid) {
                System.out.print("Enter your choice (1/2/3/0) : ");
                String input = scanner.nextLine();

                try {
                    choice4 = Integer.parseInt(input);
                    valid = true;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number.");
                }
            }

            switch (choice4){
                case 1 :
                    break;

                case 2 :
                    viewAttendance(mail);
                    break;

                case 3 :
                    showUserDetailsToUser(mail);
                    break;

                case 0 :
                    attendanceTime.addLogoutTime(mail);
                    attendanceTime.markAttendance(mail);
                    working = false;
                    break;

                default :
                    System.out.println("Enter a valid choice (1/2/3/0)");
            }
        }

    }

    public static void showOptionsForHr(String mail) throws IOException {

        boolean working = true;
        while(working){
            Scanner scanner = new Scanner(System.in);
            System.out.println("\n------x------x------x------x------\n");
            System.out.println("Choose among the following choices : ");
            System.out.println("1. Edit Attendance");
            System.out.println("2. View Attendance");
            System.out.println("3. View Personal Details");
            System.out.println("4. Edit Employee Details");
            System.out.println("5. Create a new User");
            System.out.println("6. View all Users");
            System.out.println("0. Exit");
            System.out.println("\n------x------x------x------x------\n");

            int choice3 = 0;
            boolean valid = false;

            while (!valid) {
                System.out.print("Enter your choice (1/2/3/4/5/6/0) : ");
                String input = scanner.nextLine();

                try {
                    choice3 = Integer.parseInt(input);
                    valid = true;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number.");
                }
            }

            switch(choice3){

                case 1 :
                    int ID = 0;
                    boolean validInput = false;

                    while (!validInput) {
                        System.out.print("\nEnter the ID for which you want to edit the attendance : ");
                        String input = scanner.nextLine();

                        try {
                            ID = Integer.parseInt(input);
                            validInput = true;
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please enter a number.");
                        }
                    }

                    selectEditAttendance(ID,mail);
                    break;

                case 2 :
                    viewAttendance(mail);
                    break;

                case 3 :
                    showUserDetailsToUser(mail);
                    break;

                case 4 :
                    System.out.println("Enter the ID you want to edit details for : ");
                    ID = scanner.nextInt();
                    editToDatabaseForHR(ID);
                    break;

                case 5 :System.out.println("Please fill in the following details : ");
                    userSignup.enterDetails();
                    break;

                case 6 :
                    showUser.showAllUsers();
                    break;

                case 0 :
                    attendanceTime.addLogoutTime(mail);
                    attendanceTime.markAttendance(mail);
                    working = false;
                    break;

                default :
                    System.out.println("Enter a valid input (1/2/3/4/5/0)");

            }
        }
    }
}
