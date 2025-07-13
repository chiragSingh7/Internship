package options;

import java.util.Scanner;

public class adminOptions {

    public static void showOptions(){

        boolean working = true;
        while(working){
            Scanner scanner = new Scanner(System.in);
            System.out.println("\nChoose among the following choices : ");
            System.out.println("1. Mark Attendance");
            System.out.println("2. View Attendance");
            System.out.println("3. Edit Employee");
            System.out.println("4. Edit Employee's Attendance");
            System.out.println("0. Exit");
            System.out.println("Enter your choice : ");
            int choice3 = scanner.nextInt();
            scanner.nextLine();

            switch(choice3){
                case 1 :

                case 2 :

                case 3 :

                case 4 :

                case 0 :working = false;
                    break;

                default :
                    System.out.println("Enter a valid input (1/2/3/4/0)");

            }
        }

    }

}
