package checkAndValidate;

import attendance.durationAdapter;
import attendance.localDateAdapter;
import attendance.localTimeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import models.GsonImports;
import models.User;
import options.adminOptions;
import options.employeeOptions;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class checkUser {

    public static void checkRole(String mail) throws FileNotFoundException {
        try(FileReader reader = new FileReader("data/Database.json")){
            Gson gson = GsonImports.createGson();

            Type userListType = new TypeToken<ArrayList<User>>(){}.getType();
            List<User> aUser = gson.fromJson(reader, userListType);

            for(User u : aUser){
                if(u.getEmail().equalsIgnoreCase(mail)){
                    if(u.getRole().equalsIgnoreCase("Admin")){
                        if(u.getSubRole().equalsIgnoreCase("ITHead") || u.getSubRole().equalsIgnoreCase("SuperUser")){
                            adminOptions.showOptions(u.getEmail());
                        }
                    }
                    else if (u.getRole().equals("Employee")){
                        if(u.getSubRole().equalsIgnoreCase("Intern") || u.getSubRole().equalsIgnoreCase("Trainee")){
                            employeeOptions.showOptions(u.getEmail());
                        }
                        else if(u.getSubRole().equalsIgnoreCase("HR")){
                            employeeOptions.showOptionsForHr(u.getEmail());
                        }
                    } else if (u.getRole().equals("unverified")) {
                        System.out.println("Contact the admin to update your role and sub-role then login again.");
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
