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
                    System.out.println("Verify with your ID : ");
                    int ID = scanner.nextInt();
                    scanner.nextLine();

                    viewAttendance(ID);

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
                    System.out.println("\nEnter the ID for which you want to edit the attendance : ");
                    int ID = scanner.nextInt();

                    boolean working1 = true;
                    while(working1){
                        System.out.println("\n------x------x------x------x------\n");
                        System.out.println("Select what you want to do : ");
                        System.out.println("1. Mark an absent date as present");
                        System.out.println("2. Mark a present date as absent");
                        System.out.println("0. Exit");
                        System.out.println("\n------x------x------x------x------\n");

                        int choice6 = 0;
                        boolean valid1 = false;

                        while (!valid1) {
                            System.out.print("Enter your choice (1/2/0) : ");
                            String input = scanner.nextLine();

                            try {
                                choice6 = Integer.parseInt(input);
                                valid1 = true;
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid input. Please enter a number.\n");
                            }
                        }

                        switch (choice6) {

                            case 1:
                                System.out.println("\nEnter the date you want to change the attendance for in format(yyyy-mm-dd): ");
                                String date = scanner.nextLine();

                                try {
                                    LocalDate date1 = LocalDate.parse(date);
                                    editAbsentAttendance(ID, date1);
                                    viewAttendance(ID);
                                } catch (DateTimeParseException e) {
                                    System.out.println("Invalid date format. Please use yyyy-mm-dd");
                                }
                                working1 = false;
                                break;

                            case 2:
                                System.out.println("\nEnter the date you want to change the attendance for int format (yyyy-mm-dd) : ");
                                date = scanner.nextLine();

                                LocalDate date2 = null;
                                try{
                                    date2 = LocalDate.parse(date);
                                }catch(DateTimeException e){
                                    System.out.println("Invalid date format. Please use yyyy-mm-dd");
                                }
                                editPresentAttendance(ID, date2);
                                viewAttendance(ID);
//                                working1 = false;

                                break;

                            case 0:
                                working1 = false;
                                break;

                            default:
                                System.out.println("Enter a valid choice (1/2/0) ");
                                break;
                        }
                    }

                   break;

                case 2 :
                    System.out.println("Enter your ID : ");
                    ID = scanner.nextInt();
                    scanner.nextLine();

                    viewAttendance(ID);

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
