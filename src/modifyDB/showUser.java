package modifyDB;

import attendance.durationAdapter;
import attendance.localDateAdapter;
import attendance.localTimeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import models.GsonImports;
import models.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class showUser {

    public static void showUserDetailsToUser(String mail){
        try(FileReader reader = new FileReader("data/Database.json")){
            Gson gson = GsonImports.createGson();

            Type userListType = new TypeToken<ArrayList<User>>(){}.getType();
            List<User> aUser = gson.fromJson(reader, userListType);

            boolean found = false;
            for(User u : aUser){
                if(u.getEmail().equalsIgnoreCase(mail)) {
                    System.out.println("\n------x------x------x------x------\n");
                    System.out.println("Your details : ");
                    System.out.println("Name : " + u.getName());
                    System.out.println("ID : " + u.getID());
                    System.out.println("Email : " + u.getEmail());
                    System.out.println("Role : " + u.getRole());
                    System.out.println("Sub-Role : " + u.getSubRole());
                    System.out.println("Present Days : " + u.getPresentDates());
                    System.out.println("Absent Days : " + u.getAbsentDates());
                    System.out.println("\n------x------x------x------x------\n");
                    found = true;
                    break;
                }
            }
            if(!found){
                System.out.println("No such Email ID exists. ");
            }
        }catch(IOException e){
            System.out.println("Error reading the file");
        }
    }

    public static void showUserDetailsToAdmin(int ID){
        try(FileReader reader = new FileReader("data/Database.json")){
            Gson gson = GsonImports.createGson();

            Type userListType = new TypeToken<ArrayList<User>>(){}.getType();
            List<User> aUser = gson.fromJson(reader, userListType);

            for(User u : aUser){
                if(u.getID() == ID){
                    System.out.println("\n------x------x------x------x------\n");
                    System.out.println("Your details : ");
                    System.out.println("Name : "+ u.getName());
                    System.out.println("ID : "+ u.getID());
                    System.out.println("Role : " + u.getRole());
                    System.out.println("Sub-Role : " + u.getSubRole());
                    System.out.println("Present Days : " + u.getPresentDates());
                    System.out.println("Absent Days : " + u.getAbsentDates());
                    System.out.println("\n------x------x------x------x------\n");
                    return;
                }
            }
        }catch(IOException e){
            System.out.println("Error reading the file");
        }
    }

    public static void showAllUsers(){
        try(FileReader reader = new FileReader("data/Database.json")){
            Gson gson = GsonImports.createGson();

            Type userListType = new TypeToken<ArrayList<User>>(){}.getType();
            List<User> aUser = gson.fromJson(reader, userListType);

            for(User u : aUser){
                System.out.println("------x------x------x------x------\n");
                System.out.printf("-->");
                System.out.println("Name : "+ u.getName());
                System.out.println("ID : "+ u.getID());
                System.out.println("Role : " + u.getRole());
                System.out.println("Sub-Role : " + u.getSubRole());
                System.out.println("Present Days : " + u.getPresentDates());
                System.out.println("Absent Days : " + u.getAbsentDates());
                System.out.println("\n------x------x------x------x------\n");
            }
        }catch(IOException e){
            System.out.println("Error reading the file");
        }
    }

    public static int assignID(){
        try(FileReader reader = new FileReader("data/Database.json")){
            Gson gson = GsonImports.createGson();

            Type userListType = new TypeToken<ArrayList<User>>(){}.getType();
            List<User> aUser = gson.fromJson(reader, userListType);

            return checkMaxID(aUser);

        }catch(IOException e){
            System.out.println("Error in reading the file : " + e.getMessage());
            return 20250001;
        }
    }

    public static int checkMaxID(List<User> aUser){
        int maxID = 0;

        for(User u : aUser){
            if(u.getID() >= maxID){
                maxID = u.getID();
            }
        }

        return maxID + 1;
    }

    public static void showUserIDsToAdmin() throws FileNotFoundException {
        try(FileReader reader = new FileReader("data/Database.json")){
            Gson gson = GsonImports.createGson();

            Type userListType = new TypeToken<ArrayList<User>>(){}.getType();
            List<User> aUser = gson.fromJson(reader, userListType);

            for(User user : aUser){
                System.out.println("\n -> ID : " + user.getID());
                System.out.println("Name : " + user.getName());
            }

        } catch (IOException e) {
            throw new FileNotFoundException();
        }
    }
}
