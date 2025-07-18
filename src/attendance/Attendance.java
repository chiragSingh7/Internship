package attendance;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.reflect.TypeToken;
import models.User;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Attendance implements attendanceMethods{
    private List<LocalDate> presentDays;
    private List<LocalDate> absentDays;

    public void setPresentDays(List<LocalDate> presentDays){
        this.presentDays = presentDays;
    }

    public void setAbsentDays(List<LocalDate> absentDays){
        this.absentDays = absentDays;
    }

    public List<LocalDate> getPresentDays(){
        return this.presentDays;
    }

    public List<LocalDate> getAbsentDays(){
        return this.absentDays;
    }

    public Attendance(){
        this.presentDays = new ArrayList<>();
        this.absentDays = new ArrayList<>();
    }

    @Override
    public void markPresent(int ID, LocalDate date) {

        try (FileReader reader = new FileReader("data/Database.json")) {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new localDateAdapter())
                    .registerTypeAdapter(LocalTime.class , new localTimeAdapter())
                    .setPrettyPrinting()
                    .create();

            Type userListType = new TypeToken<ArrayList<User>>() {}.getType();
            List<User> aUser = gson.fromJson(reader, userListType);

            boolean found = false;
            for (User u : aUser) {
                if (u.getID() == ID) {
                    found = true;

                    List<LocalDate> presentDays = u.getAttendance().getPresentDays();
                    List<LocalDate> absentDays = u.getAttendance().getAbsentDays();

                    if (presentDays == null) {
                        presentDays = new ArrayList<>();
                        u.getAttendance().setPresentDays(presentDays);
                    }

                    if (presentDays.isEmpty()) {
                        if(absentDays != null && absentDays.contains(date)){
                            absentDays.remove(date);
                        }
                        presentDays.add(date);
                        System.out.println("Attendance successfully marked for " + date);
                    } else {
                        if (presentDays.contains(date) && absentDays.contains(date)) {
                            absentDays.remove(date);
                            return;
                        }

                        if(absentDays.contains(date)){
                            absentDays.remove(date);
                            presentDays.add(date);
                            System.out.println("Attendance marked for " + date);
                        }
                    }
                    break;
                }
            }

            if (!found) {
                System.out.println("No user found with ID : " + ID);
                return;
            }
            try (FileWriter writer = new FileWriter("data/Database.json")) {
                gson.toJson(aUser,userListType, writer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void markAbsent(int ID, LocalDate date) throws IOException {
        try(FileReader reader = new FileReader("data/Database.json")){
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new localDateAdapter())
                    .registerTypeAdapter(LocalTime.class , new localTimeAdapter())
                    .registerTypeAdapter(Duration.class, new durationAdapter())
                    .setPrettyPrinting()
                    .create();

            Type userListType = new TypeToken<ArrayList<User>>() {}.getType();
            List<User> aUser = gson.fromJson(reader, userListType);

            for(User u : aUser){
                if(u.getID() == ID){

                    List<LocalDate> absentDays = u.getAttendance().getAbsentDays();

                    if (absentDays == null) {
                        absentDays = new ArrayList<>();
                        u.getAttendance().setAbsentDays(absentDays);
                    }

                    if(absentDays.isEmpty()){
                        absentDays.add(date);
                    }

                    if(absentDays.contains(date)){
                        break;
                    }
                    else{
                        absentDays.add(date);
                    }
                }
            }
            try (FileWriter writer = new FileWriter("data/Database.json")) {
                gson.toJson(aUser, userListType, writer);
            } catch (JsonIOException e) {
                throw new RuntimeException(e);
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<LocalDate> viewPresentDates(int ID) throws IOException {
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
                    return u.getAttendance().presentDays;
                }
            }
        }catch (IOException e){
            System.out.println("Error in reading the file");
        }

        System.out.println("Check the ID you have entered : " + ID);
        return null;
    }

    @Override
    public List<LocalDate> viewAbsentDates (int ID) throws IOException{
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
                    return u.getAttendance().absentDays;
                }
            }
        }catch (IOException e){
            System.out.println("Error in reading the file");
            throw new IOException(e);
        }

        System.out.println("Check the ID you have entered : " + ID);
        return null;
    }

    public static void editAbsentAttendance(int ID, LocalDate date) throws IOException {
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
                if(u.getID() == ID){
                    found = true;

                    Attendance attendance = new Attendance();
                    if(attendance.absentDays.contains(date)){
                        attendance.absentDays.remove(date);
                        attendance.presentDays.add(date);

                        attendance.presentDays.sort(Comparator.naturalOrder());
                        attendance.absentDays.sort(Comparator.naturalOrder());
                    }
                }
            }

            if(!found){
                System.out.println("No such ID exists.");
            }
            try(FileWriter writer = new FileWriter("data/Database.json")){
                gson.toJson(aUser, writer);
            } catch (JsonIOException e) {
                System.out.println("Some unknow error occurred in writing to the file.");
            }
        }catch(IOException e){
            System.out.println("Some unknown error occurred in reading from the file.");
        }
    }
}
