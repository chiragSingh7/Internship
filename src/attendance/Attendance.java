package attendance;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import models.User;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Attendance implements attendanceMethods{
    private List<LocalDate> presentDays;
    private List<LocalDate> absentDays;

    public Attendance(){
        this.presentDays = new ArrayList<>();
        this.absentDays = new ArrayList<>();
    }

    @Override
    public void markPresent(LocalDate date){
        if(presentDays.isEmpty()){
            presentDays.add(date);
            System.out.println("Attendance marked for " + date);
        }
        else{
            if(presentDays.contains(date)){
                System.out.println("Attendance already marked for the day " + date);
                return;
            }

            LocalDate lastDate = Collections.max(presentDays);
            if(date.isBefore(lastDate)){
                System.out.println("Cannot mark present for past dates");
            }
            else{
                presentDays.add(date);
                System.out.println("Attendance marked for " + date);
            }
        }
    }

//    public void markAbsent(LocalDate date){
//
//    }

    @Override
    public List<LocalDate> viewPresentDates(int ID) throws IOException {
        try(FileReader reader = new FileReader("data/Database.json")){
            Gson gson = new Gson();

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
            Gson gson = new Gson();

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
