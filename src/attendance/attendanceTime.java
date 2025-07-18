package attendance;

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
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class attendanceTime {
    private List<LocalTime> loginTime;
    private List<LocalTime> logoutTime;
    private Duration reqTime;

    public void setReqTime(Duration reqTime) {
        this.reqTime = reqTime;
    }

    public Duration getReqTime(){
        return this.reqTime;
    }

    public void setLoginTime(List<LocalTime> loginTime) {
        this.loginTime = loginTime;
    }

    public List<LocalTime> getLoginTime() {
        return this.loginTime;
    }

    public void setLogoutTime(List<LocalTime> logoutTime){
        this.logoutTime = logoutTime;
    }

    public List<LocalTime> getLogoutTime() {
        return this.logoutTime;
    }

    public attendanceTime(){
        this.loginTime = new ArrayList<>();
        this.logoutTime = new ArrayList<>();
        this.reqTime = Duration.ofMinutes(2);
    }

    public static void addLoginTime(int ID){
        try(FileReader reader = new FileReader("data/Database.json")) {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new localDateAdapter())
                    .registerTypeAdapter(LocalTime.class , new localTimeAdapter())
                    .registerTypeAdapter(Duration.class, new durationAdapter())
                    .setPrettyPrinting()
                    .create();

            Type userListType = new TypeToken<ArrayList<User>>(){}.getType();
            List<User> aUser = gson.fromJson(reader, userListType);

            boolean found = false;

            for(User u : aUser){
                if(u.getID() == ID){
                    found = true;

                    List<LocalTime> loginTime = u.getAttendanceTime().getLoginTime();

                    if(loginTime == null){
                        loginTime = new ArrayList<>();
                        u.getAttendanceTime().setLoginTime(loginTime);
                    }

                    loginTime.add(LocalTime.now());
                }
            }

            if (!found) {
                System.out.println("No such user exists");
            }
            try(FileWriter writer = new FileWriter("data/Database.json")){
                gson.toJson(aUser, userListType, writer);
            }
        } catch (IOException e) {
            System.out.println("Error reading the file ");
        }
    }

    public static void addLogoutTime(String mail) {
        try(FileReader reader = new FileReader("data/Database.json")){
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new localDateAdapter())
                    .registerTypeAdapter(LocalTime.class , new localTimeAdapter())
                    .registerTypeAdapter(Duration.class, new durationAdapter())
                    .setPrettyPrinting()
                    .create();

            Type userListType = new TypeToken<ArrayList<User>>(){}.getType();
            List<User> aUser = gson.fromJson(reader, userListType);

            boolean found = false;
            for(User u : aUser){
                if(u.getEmail().equalsIgnoreCase(mail)){
                    found = true;

                    List<LocalTime> logoutTime = u.getAttendanceTime().getLogoutTime();

                    if(logoutTime == null){
                        logoutTime = new ArrayList<>();
                        u.getAttendanceTime().setLogoutTime(logoutTime);
                    }

                    logoutTime.add(LocalTime.now());
                }
            }

            if(!found){
                System.out.println("No such mail exists ");
            }
            try(FileWriter writer = new FileWriter("data/Database.json")){
                gson.toJson(aUser, userListType, writer);
            }
        } catch (IOException e) {
            System.out.println("Error reading the file ");
        }
    }

    public static void markAttendance(String mail) throws FileNotFoundException {
        try (FileReader reader = new FileReader("data/Database.json")) {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new localDateAdapter())
                    .registerTypeAdapter(LocalTime.class , new localTimeAdapter())
                    .registerTypeAdapter(Duration.class, new durationAdapter())
                    .setPrettyPrinting()
                    .create();

            Type userListType = new TypeToken<ArrayList<User>>() {}.getType();
            List<User> aUser = gson.fromJson(reader, userListType);

            attendanceTime time = new attendanceTime();
            Attendance attendance = new Attendance();

            boolean found = false;
            for (User u : aUser) {
                if (u.getEmail().equalsIgnoreCase(mail)) {
                    found = true;
                    if(time.checkTime(u.getLoginTime(), u.getLogoutTime())){
                        attendance.markPresent(u.getID(), LocalDate.now());
                    }else{
                        attendance.markAbsent(u.getID(),LocalDate.now());
                    }
                }
            }

            if(!found){
                System.out.println("No user found with mail : " + mail);
            }
            try(FileWriter writer = new FileWriter("data/Database.json")){
                gson.toJson(aUser, userListType, writer);
            } catch (JsonIOException e) {
                System.out.println("Error occurred while writing to the file");
            }
        } catch (IOException e) {
            System.out.println("Error occurred while reading the file");
        }
    }

    public boolean checkTime(List<LocalTime> loginTime, List<LocalTime> logoutTime){
        if (loginTime == null || logoutTime == null || loginTime.isEmpty()) {
            System.out.println("Login or logout list is empty. Cannot calculate time.");
            return false;
        }

        int n = loginTime.size();
        long minutes = 0;

        for(int i=0; i<n ; i++){
            Duration d = Duration.between(logoutTime.get(i), loginTime.get(i));
            minutes += d.toMinutes();
        }
        return minutes >= getReqTime().toMinutes();
    }

    public List<LocalTime> viewLoginTime(int ID){
        try(FileReader reader = new FileReader("data/Database.json")){
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new localDateAdapter())
                    .registerTypeAdapter(LocalTime.class , new localTimeAdapter())
                    .registerTypeAdapter(Duration.class, new durationAdapter())
                    .setPrettyPrinting()
                    .create();

            Type userListType = new TypeToken<ArrayList<User>>(){}.getType();
            List<User> aUser = gson.fromJson(reader, userListType);

            for(User u : aUser){
                if(u.getID() == ID){
                    return u.getAttendanceTime().loginTime;
                }
            }
        }catch (IOException e){
            System.out.println("Error in reading the file");
        }

        System.out.println("Check the ID you have entered : " + ID);
        return null;
    }

    public List<LocalTime> viewLogoutTime(int ID){
        try(FileReader reader = new FileReader("data/Database.json")){
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new localDateAdapter())
                    .registerTypeAdapter(LocalTime.class , new localTimeAdapter())
                    .registerTypeAdapter(Duration.class, new durationAdapter())
                    .setPrettyPrinting()
                    .create();

            Type userListType = new TypeToken<ArrayList<User>>(){}.getType();
            List<User> aUser = gson.fromJson(reader, userListType);

            for(User u : aUser){
                if(u.getID() == ID){
                    return u.getAttendanceTime().logoutTime;
                }
            }
        }catch (IOException e){
            System.out.println("Error in reading the file");
        }

        System.out.println("Check the ID you have entered : " + ID);
        return null;
    }

}



