package modifyDB;

import attendance.localDateAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.reflect.TypeToken;
import models.User;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class updateUser {

    public static void updateName(int ID, String name) throws FileNotFoundException {
        try(FileReader reader = new FileReader("data/Database.json")){
            Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new localDateAdapter()).setPrettyPrinting().create();

            Type userListType = new TypeToken<ArrayList<User>>(){}.getType();
            List<User> aUser = gson.fromJson(reader, userListType);

            boolean found = false;
            for(User u : aUser){
                if(u.getID() == ID){
                    u.setName(name);
                    found = true;
                }
            }

            if(!found){
                System.out.println("User ID not found. Check the user ID : " + ID);
            }

            if(found){
                try(FileWriter writer = new FileWriter("data/Database.json")){
                    gson.toJson(aUser, writer);
                } catch (JsonIOException e) {
                    System.out.println("Error while writing to the file " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Error while reading the file " + e.getMessage());
        }
    }

    public static void updateMail(String newMail, int ID){
        try(FileReader reader = new FileReader("data/Database.json")){
            Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new localDateAdapter()).setPrettyPrinting().create();

            Type userListType = new TypeToken<ArrayList<User>>(){}.getType();
            List<User> aUser = gson.fromJson(reader, userListType);

            boolean found = false;
            for(User u : aUser){
                if(u.getID() == ID){
                    u.setEmail(newMail);
                    found = true;
                }
            }

            if(!found){
                System.out.println("User ID not found. Check the User ID : " + ID);
            }

            if(found){
                try(FileWriter writer = new FileWriter("data/Database.json")){
                    gson.toJson(aUser, writer);
                } catch (JsonIOException e) {
                    System.out.println("Error while writing to file " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Error while reading the file " + e.getMessage());
        }
    }

    public static void updatePassword(int ID){
        try(FileReader reader = new FileReader("data/Database.json")){
            Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new localDateAdapter()).setPrettyPrinting().create();
            Scanner scanner = new Scanner(System.in);

            Type userListType = new TypeToken<ArrayList<User>>(){}.getType();
            List<User> aUser = gson.fromJson(reader, userListType);

            boolean found = false;
            for(User u : aUser){
                if(u.getID() == ID){
                    found = true;
                    System.out.println("\nEnter your old password : ");
                    String password = scanner.nextLine();

                    if(u.getPassword().equals(password)){
                        System.out.println("Enter the new password : ");
                        password = scanner.nextLine();

                        System.out.println("Enter the password again to confirm : ");
                        String pass = scanner.nextLine();

                        while (!password.equals(pass)) {
                            System.out.println("\nThe passwords don't match. Re-check your password and enter again");
                            pass = scanner.nextLine();
                        }

                        u.setPassword(password);
                        break;
                    }
                }
            }

            if(!found){
                System.out.println("User ID not found. Check the User ID : " + ID);
            }

            if(found){
                try(FileWriter writer = new FileWriter("data/Database.json")){
                    gson.toJson(aUser, writer);
                } catch (JsonIOException e) {
                    System.out.println("Error while writing to the file " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Error while reading the file " + e.getMessage());
        }
    }

    public static void updateRole(String role, int ID){
        try(FileReader reader = new FileReader("data/Database.json")){
            Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new localDateAdapter()).setPrettyPrinting().create();

            Type userListType = new TypeToken<ArrayList<User>>(){}.getType();
            List<User> aUser = gson.fromJson(reader, userListType);

            boolean found = false;
            for(User u : aUser){
                if(u.getID() == ID){
                    u.setRole(role);
                    found = true;
                }
            }

            if(!found){
                System.out.println("User ID not found. Check the User ID : " + ID);
            }

            if(found){
                try(FileWriter writer = new FileWriter("data/Database.json")){
                    gson.toJson(aUser, writer);
                } catch (JsonIOException e) {
                    System.out.println("Error while writing to file " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Error while reading the file " + e.getMessage());
        }
    }

    public static void updateSubRole(String subRole, int ID){
        try(FileReader reader = new FileReader("data/Database.json")){
            Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new localDateAdapter()).setPrettyPrinting().create();

            Type userListType = new TypeToken<ArrayList<User>>(){}.getType();
            List<User> aUser = gson.fromJson(reader, userListType);

            boolean found = false;
            for(User u : aUser){
                if(u.getID() == ID){
                    u.setSubRole(subRole);
                    found = true;
                }
            }

            if(!found){
                System.out.println("User ID not found. Check the User ID : " + ID);
            }

            if(found){
                try(FileWriter writer = new FileWriter("data/Database.json")){
                    gson.toJson(aUser, writer);
                } catch (JsonIOException e) {
                    System.out.println("Error while writing to file " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Error while reading the file " + e.getMessage());
        }
    }
}
