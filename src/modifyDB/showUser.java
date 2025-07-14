package modifyDB;

import attendance.localDateAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import models.User;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class showUser {

    public static void showUserDetails(String mail){
        try(FileReader reader = new FileReader("data/Database.json")){
            Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new localDateAdapter()).setPrettyPrinting().create();

            Type userListType = new TypeToken<ArrayList<User>>(){}.getType();
            List<User> aUser = gson.fromJson(reader, userListType);

            for(User u : aUser){
                if(u.getEmail().equalsIgnoreCase(mail)){
                    System.out.println("Your details : ");
                    System.out.println("Name : "+ u.getName());
                    System.out.println("ID : "+ u.getID());
                    System.out.println("Email : "+ u.getEmail());
                    System.out.println("Role : " + u.getRole());
                    System.out.println("Sub-Role : " + u.getSubRole());
                    System.out.println("Present Days : " + u.getPresentDates());
                    System.out.println("Absent Days : " + u.getAbsentDates());
                    return;
                }
            }
        }catch(IOException e){
            System.out.println("Error reading the file");
        }
    }

    public static void assignID(String mail){
        try(FileReader reader = new FileReader("data/Database.json")){
            Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new localDateAdapter()).setPrettyPrinting().create();

            Type userListType = new TypeToken<ArrayList<User>>(){}.getType();
            List<User> aUser = gson.fromJson(reader, userListType);

            int ID = maxID(aUser) + 1;
            for(User u : aUser){
                if(u.getEmail().equalsIgnoreCase(mail)){
                    u.setID(ID);
                }
            }

        }catch(IOException e){
            System.out.println("Error in reading the file : " + e.getMessage());
        }
    }

    public static int maxID(List<User> aUser){
        int maxID = 0;

        for(User u : aUser){
            if(u.getID() > maxID){
                maxID = u.getID();
            }
        }

        return maxID;
    }

}
