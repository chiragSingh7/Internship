package attendance;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import models.GsonImports;
import models.User;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class gridAttendance {
//    private String[] month = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
//    private String[] year

    public List<List<Character>> showAttendance(int ID){
        try(FileReader reader = new FileReader("data/Database.json")){
            Gson gson = GsonImports.createGson();

            Type userListType = new TypeToken<ArrayList<User>>(){}.getType();
            List<User> allUsers = gson.fromJson(reader, userListType);

            boolean found = false;
            for(User user : allUsers){
                if(user.getID() == ID) {
                    found = true;
                    user.getGridAttendance();
                }
            }

            if(!found){
                System.out.println("ID not found");
            }
        }catch (IOException e){
            System.out.println("Error while reading the file");
        }
        return null;
    }

    public static void calculateAttendance(int ID){
        try(FileReader reader = new FileReader("data/Database.json")){
            Gson gson = GsonImports.createGson();

            Type userListType = new TypeToken<ArrayList<User>>(){}.getType();
            List<User> allUsers = gson.fromJson(reader, userListType);

            boolean found = false;
            for(User user : allUsers){
                if(user.getID() == ID){
                    found = true;

                    int i=0, j=0;
                    int weeks = 5;

                    LocalDate today = LocalDate.now();
                    if(today.getDayOfMonth() == 1){
                        int dayOfWeek = today.getDayOfWeek().getValue();
                        if((dayOfWeek > 5 && today.lengthOfMonth() == 31) || (dayOfWeek > 6 && today.lengthOfMonth() == 30)){
                            i += dayOfWeek;
                            weeks = 6;
                        } else{
                            i += dayOfWeek;
                        }
                    }

                    List<LocalDate> presentDays = user.getAttendance().getPresentDays();
                    List<LocalDate> absentDays = user.getAttendance().getAbsentDays();
                    List<LocalDate> pending = user.getAttendance().getPending();

                    List<List<Character>> attendanceGrid = user.getGridAttendance();

                    for( ; i<weeks ; i++){
                        for( ; j<8 ; j++){

                            if (j == 6 || j == 7){
                                attendanceGrid.get(i).add('H');
                            }

                            if(presentDays.contains(today.plusDays(1))){
                                attendanceGrid.get(i).add('P');
                            }else if(absentDays.contains(today.plusDays(1))){
                                attendanceGrid.get(i).add('A');
                            }else if(pending.contains(today.plusDays(1))){
                                attendanceGrid.get(i).add(' ');
                            } else{
                                attendanceGrid.get(i).add('X');
                            }
                        }
                    }
                }
            }

            if(!found){
                System.out.println("ID not found");
            }
        }catch (IOException e){
            System.out.println("Error while reading the file");
        }
    }

    public static boolean checkHoliday(LocalDate date){
        int dayOfWeek = date.getDayOfWeek().getValue();
        return dayOfWeek > 5;
    }
}
