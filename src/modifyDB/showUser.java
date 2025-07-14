package modifyDB;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import models.User;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class showUser {

    public static void showUserDetails(String mail){
        try(FileReader reader = new FileReader("datae/Database.json")){
            Gson gson = new Gson();

            Type userListType = new TypeToken<ArrayList<User>>(){}.getType();
            List<User> aUser = gson.fromJson(reader, userListType);

            for(User u : aUser){
                if(u.getEmail().equalsIgnoreCase(mail)){
                    System.out.println("Your details : ");
                    System.out.println("Name : "+ u.getName());
                    System.out.println("ID : "+ u.getID());
                    System.out.println("Email : "+ u.getEmail());
                    return;
                }
            }
        }catch(IOException e){
            System.out.println("Error reading the file");
        }
    }
}
