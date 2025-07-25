package checkAndValidate;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import models.GsonImports;
import models.User;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class checkID {
    public static boolean checkIDExists(int ID){
        boolean check = false;

        try (FileReader reader = new FileReader("data/Database.json")) {
            Gson gson = GsonImports.createGson();

            Type userListType = new TypeToken<ArrayList<User>>() {}.getType();
            List<User> aUser = gson.fromJson(reader, userListType);

            for (User u : aUser) {
                if (u.getID() == ID) {
                    check = true;
                    break;
                }
            }

        } catch (IOException e) {
            System.out.println("Error reading the file.");
        }
        return check;
    }
}
