package modifyDB;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import models.User;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class modifyDatabase {

    public static void addToDatabase(String name, String mail, String password){
        try{
            File file = new File("data/Database.json");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            User u1 = new User(name, mail, password);

            //check if the file is empty
            if (file.length() == 0) {
                //if yes then create a list and add the first user to list and write
                List<User> allUser = new ArrayList<>();
                allUser.add(u1);
                String myJson = gson.toJson(allUser);

                try(FileWriter writer = new FileWriter("data/Database.json")){
                    writer.write(myJson);
                } catch (IOException e) {
                    throw new IOException(e);
                }
            } else {
                Type userListType = new TypeToken<ArrayList<User>>() {}.getType();
                List<User> aUser = new ArrayList<>();

                try (FileReader reader = new FileReader("data/Database.json")){
                    // read the list from json file and add the new user
                    aUser = gson.fromJson(reader, userListType);
                } catch (IOException e) {
                    throw new IOException(e);
                }

                aUser.add(u1);

                try(FileWriter writer = new FileWriter("data/Database.json")){
                    gson.toJson(aUser, writer);
                } catch (IOException e) {
                    throw new IOException(e);
                }
            }
        }catch (Exception e){
            System.out.println("Something went wrong while dealing with database.");
            e.printStackTrace();
        }
    }

    public static void editDatabase(){

    }
}
