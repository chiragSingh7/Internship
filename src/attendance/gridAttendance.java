package attendance;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import models.GsonImports;
import models.User;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Math.ceil;

public class gridAttendance {
    private final String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
//    private final int year = LocalDate.now().getYear();
//    private int month = LocalDate.now().getMonthValue();
//    private int day = LocalDate.now().getDayOfMonth();
//
//    public int getYear(){
//        return this.year;
//    }
//
//    public int getMonth(){
//        return this.month;
//    }
//
//    public int getDay(){return this.day;}
//
//    public String[] getMonths(){
//        return this.months;
//    }
    public static int weeksOfMonth(){
        LocalDate today = LocalDate.now();

        int totalDays = today.lengthOfMonth();
        int firstWeek = today.withDayOfMonth(1).getDayOfWeek().getValue();
        int daysLeft = totalDays - (8-firstWeek);

        return (int) ceil(daysLeft/7.00) + 1;
    }

    public static void createAttendance(String mail) throws IOException {
        try(FileReader reader = new FileReader("data/Database.json")){
            Gson gson = GsonImports.createGson();

            Type userListType = new TypeToken<ArrayList<User>>(){}.getType();
            List<User> allUsers = gson.fromJson(reader, userListType);

            boolean found = false;
            for(User user : allUsers){
                if(user.getEmail().equalsIgnoreCase(mail)){
                    found = true;

                    int i,j=1;
                    int weeks = weeksOfMonth();
                    LocalDate today = LocalDate.now();
                    int dayOfWeek = today.getDayOfWeek().getValue();

                    List<List<Character>> attendanceGrid = user.getAttendance().getAttendanceGrid();

//                    if((dayOfWeek > 5 && today.lengthOfMonth() == 31) || (dayOfWeek > 6 && today.lengthOfMonth() == 30)){
//                        weeks = 7;
//                    }

                    if (attendanceGrid == null || attendanceGrid.isEmpty()) {
                        attendanceGrid = new ArrayList<>();

                        attendanceGrid.add(new ArrayList<>(Arrays.asList('M','T','W','T','F','S','S')));

                        for(i=1 ; i<=weeks ; i++){
                            List<Character> row = new ArrayList<>();
                            for (int d=0 ; d<7 ; d++){
                                row.add('X');
                            }
                            attendanceGrid.add(row);
                        }
                        user.getAttendance().setAttendanceGrid(attendanceGrid);
                    }

                    if(today.getDayOfMonth() == 1){
                        j += dayOfWeek-1;
                    }

                    for(i=1 ; i<weeks+1 ; i++){
                        for( ; j<7 ; j++){
                            if(((i-1)*7 + j) <=today.lengthOfMonth()){
                                attendanceGrid.get(i).add('X');
                            }
                        }
                        j=0;
                    }
                }
            }

            if(!found){
                System.out.println("ID not found");
            }
        }catch (IOException e){
            throw new IOException("Error while reading the file");
        }
    }

    public static List<List<Character>> showAttendance(String mail) throws IOException {
        try(FileReader reader = new FileReader("data/Database.json")){
            Gson gson = GsonImports.createGson();

            Type userListType = new TypeToken<ArrayList<User>>(){}.getType();
            List<User> allUsers = gson.fromJson(reader, userListType);

            boolean found = false;
            for(User user : allUsers){
                if(user.getEmail().equalsIgnoreCase(mail)) {
                    return user.getAttendance().getAttendanceGrid();
                }
            }

            if(!found){
                System.out.println("ID not found");
            }
        }catch (IOException e){
            throw new IOException("Error in reading the file");
        }
        return null;
    }

    public static void calculateAttendance(String mail) throws IOException {
        try(FileReader reader = new FileReader("data/Database.json")){
            Gson gson = GsonImports.createGson();

            Type userListType = new TypeToken<ArrayList<User>>(){}.getType();
            List<User> allUsers = gson.fromJson(reader, userListType);

            boolean found = false;
            for(User user : allUsers){
                if(user.getEmail().equalsIgnoreCase(mail)){
                    found = true;

                    int i,j=1;
                    int weeks = weeksOfMonth();
                    LocalDate today = LocalDate.now();
                    int dayOfWeek = today.getDayOfWeek().getValue();

                    List<LocalDate> presentDays = user.getAttendance().getPresentDays();
                    List<LocalDate> absentDays = user.getAttendance().getAbsentDays();
                    List<LocalDate> pending = user.getAttendance().getPending();
                    List<List<Character>> attendanceGrid = user.getAttendance().getAttendanceGrid();

//                    if((dayOfWeek > 5 && today.lengthOfMonth() == 31) || (dayOfWeek > 6 && today.lengthOfMonth() == 30)){
//                        weeks = 7;
//                    }

                    if(attendanceGrid == null){
                        createAttendance(mail);
                    }

                    if(today.getDayOfMonth() == 1){
                        j += dayOfWeek-1;
                    }

                    for(i=1 ; i<weeks+1 ; i++){
                        for( ; j<8 ; j++){
                            LocalDate date = LocalDate.now();

                            if(((i-1)*7 + j) <=today.lengthOfMonth() && ((i-1)*7 + j)>0 ){
                                date = LocalDate.of(today.getYear(), today.getMonthValue(),((i-1)*7 +j));
                            }

                            if(presentDays.contains(date) && ((i-1)*7 + j) <=today.lengthOfMonth()){
                                attendanceGrid.get(i).set(j, 'P');
                            }else if(absentDays.contains(date) && ((i-1)*7 + j) <=today.lengthOfMonth()){
                                attendanceGrid.get(i).set(j, 'A');
                            }else if(pending.contains(date) && ((i-1)*7 + j) <=today.lengthOfMonth()){
                                attendanceGrid.get(i).set(j, '_');
                            } else if (checkHoliday(date) && ((i-1)*7 + j) <=today.lengthOfMonth()){
                                attendanceGrid.get(i).set(j, 'H');
                            } else if (((i-1)*7 + j) <=today.lengthOfMonth()){
                                attendanceGrid.get(i).set(j, 'X');
                            }
                        }
                        j=1;
                    }
                }
            }

            if(!found){
                System.out.println("ID not found");
            }
            try(FileWriter writer = new FileWriter("data/Database.json")){
                gson.toJson(allUsers, userListType, writer);
            }catch (IOException e){
                throw new IOException("Error while writing to the file");
            }
        }catch (IOException e){
            throw new IOException("Error while reading the file");
        }
    }

    public static boolean checkHoliday(LocalDate date){
        int dayOfWeek = date.getDayOfWeek().getValue();
        return dayOfWeek > 5;
    }

    public static void printAttendanceGrid(String mail) throws IOException {
        try(FileReader reader = new FileReader("data/Database.json")){
            Gson gson = GsonImports.createGson();

            Type userListType = new TypeToken<ArrayList<User>>(){}.getType();
            List<User> allUsers = gson.fromJson(reader, userListType);

            boolean found = false;
            for(User user : allUsers){
                if(user.getEmail().equalsIgnoreCase(mail)){
                    found = true;

                    List<List<Character>> attendanceGrid = user.getAttendance().getAttendanceGrid();

                    int n = attendanceGrid.size();
                    int m = 0;
                    
                    if(!attendanceGrid.isEmpty()){
                        m = attendanceGrid.get(0).size();
                    }else{
                        System.out.println("Attendance grid is empty");
                    }

                    System.out.println("\n");
                    for(int k=0 ; k<1 ; k++){
                        for(int j=0 ; j<m ; j++){
                            System.out.print(attendanceGrid.get(k).get(j) + " ");
                        }
                    }

                    System.out.print("\n");
                    int spaces = LocalDate.now().withDayOfMonth(1).getDayOfWeek().getValue();

                    for(int i=1 ; i<2 ; i++){
                        for(int j=0 ; j<spaces-1 ; j++){
                            System.out.print("  ");
                        }
                    }

                    for(int i=1 ; i<n ; i++){
                        for(int j= spaces-1; j<m ; j++){
                            System.out.print(attendanceGrid.get(i).get(j) + " ");
                        }
                        System.out.print("\n");
                        spaces = 1;
                    }
                }
            }

            if(!found){
                System.out.println("Email not found ");
            }
        } catch (IOException e) {
            throw new IOException("Error while reading the file");
        }
    }
}
