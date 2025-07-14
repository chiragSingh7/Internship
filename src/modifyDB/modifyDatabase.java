package modifyDB;

import attendance.localDateAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import models.User;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class modifyDatabase {

    public static void addToDatabase(String name, String mail, String password){
        try{
            File file = new File("data/Database.json");
            Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new localDateAdapter()).setPrettyPrinting().create();

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

    public static void deleteFromDatabase(int ID){
        try(FileReader reader = new FileReader("data/Database.json")){
            Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new localDateAdapter()).setPrettyPrinting().create();

            Type userListType = new TypeToken<ArrayList<User>>(){}.getType();
            List<User> aUser = gson.fromJson(reader, userListType);

            boolean found = false;
            for(User u : aUser){
                if(u.getID() == ID){
                    aUser.remove(u);
                    found = true;
                    break;
                }
            }

            if(!found){
                System.out.println("No user found with this ID");
            }

            try(FileWriter writer = new FileWriter("data/Database.json")){
                gson.toJson(aUser, writer);

                System.out.println("User ID " + ID + " removed successfully");
            }catch (IOException e){
                System.out.println("Error while writing file " + e.getMessage());
            }
        }catch(IOException e){
            System.out.println("Error while reading file " + e.getMessage());
        }
    }

    public static void editToDatabase(int ID){
        try(FileReader reader = new FileReader("data/Database.json")){
            Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new localDateAdapter()).setPrettyPrinting().create();

            Type userListType = new TypeToken<ArrayList<User>>(){}.getType();
            List<User> aUser = gson.fromJson(reader, userListType);

            for(User u : aUser){

            }
        }catch (IOException e){
            System.out.println("Error in reading the file " + e.getMessage());
        }
    }
}
