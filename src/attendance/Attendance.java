package attendance;

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
import java.util.Collections;
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
            Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new localDateAdapter()).setPrettyPrinting().create();

            Type userListType = new TypeToken<ArrayList<User>>() {}.getType();
            List<User> aUser = gson.fromJson(reader, userListType);

            boolean found = false;
            for (User u : aUser) {
                if (u.getID() == ID) {
                    found = true;

                    List<LocalDate> presentDays = u.getAttendance().getPresentDays();

                    if (presentDays == null) {
                        presentDays = new ArrayList<>();
                        u.getAttendance().setPresentDays(presentDays);
                    }

                    if (presentDays.isEmpty()) {
                        presentDays.add(date);
                        System.out.println("Attendance successfully marked for " + date);
                    } else {
                        if (presentDays.contains(date)) {
                            System.out.println("️Attendance already marked for " + date);
                            return;
                        }

                        LocalDate lastDate = Collections.max(presentDays);
                        if (date.isBefore(lastDate)) {
                            System.out.println("Cannot mark present for past dates.");
                        } else {
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

            // Step 4: Write updated list back to the file
            try (FileWriter writer = new FileWriter("data/Databsae.json")) {
                gson.toJson(aUser, writer);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


//    public void markAbsent(LocalDate date){
//
//    }

    @Override
    public List<LocalDate> viewPresentDates(int ID) throws IOException {
        try(FileReader reader = new FileReader("data/Database.json")){
            Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new localDateAdapter()).setPrettyPrinting().create();

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
            Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new localDateAdapter()).setPrettyPrinting().create();

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

}
