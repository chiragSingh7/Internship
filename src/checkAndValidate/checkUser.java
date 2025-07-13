package checkAndValidate;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import models.User;

import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class checkUser {
    public void check(){
        try(FileReader reader = new FileReader("data/Database.json")){
            Gson gson = new Gson();

            Type userListType = new TypeToken<ArrayList<User>>(){}.getType();
            List<User> aUser = gson.fromJson(reader, userListType);

            for(User u : aUser){
                if(u.getRole().equals("Admin")){
                    System.out.println("\nChoose among the following choices : ");
                    System.out.println("");
                }
                else if (u.getRole().equals("Employee")){
                    System.out.println("Choose among the following choices : ");
                    System.out.println("1. Mark Attendance");
                    System.out.println("2. View Attendance");
                    System.out.println("3. View Details");
                    System.out.println("4. Exit");
                    
                }
            }
        }
    }
}
