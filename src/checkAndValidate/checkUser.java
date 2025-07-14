package checkAndValidate;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import models.User;
import options.adminOptions;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class checkUser {

    public void check() throws FileNotFoundException {
        try(FileReader reader = new FileReader("data/Database.json")){
            Gson gson = new Gson();
            Scanner scanner = new Scanner(System.in);

            Type userListType = new TypeToken<ArrayList<User>>(){}.getType();
            List<User> aUser = gson.fromJson(reader, userListType);

            for(User u : aUser){
                if(u.getRole().equals("Admin")){
                    adminOptions.showOptions();
                }
                else if (u.getRole().equals("Employee")){
                    System.out.println("Choose among the following choices : ");
                    System.out.println("1. Mark Attendance");
                    System.out.println("2. View Attendance");
                    System.out.println("3. View Details");
                    System.out.println("0. Exit");
                    System.out.println("Enter your choice : ");
                    int choice4 = scanner.nextInt();
                    scanner.nextLine();

                    while
                } else if (u.getRole().equals("unverified")) {
                    System.out.println("Contact the admin to update your role and sub-role");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
