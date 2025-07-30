package attendance;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.reflect.TypeToken;
import models.GsonImports;
import models.User;

import java.io.*;
import java.lang.reflect.Type;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import static attendance.gridAttendance.checkHoliday;

public class Attendance implements attendanceMethods{
    private List<LocalDate> presentDays;
    private List<LocalDate> absentDays;
    private List<LocalDate> pending;
    private List<List<Character>> attendanceGrid;

    public void setAttendanceGrid(List<List<Character>> attendanceGrid){
        this.attendanceGrid = attendanceGrid;
    }

    public void setPresentDays(List<LocalDate> presentDays){
        this.presentDays = presentDays;
    }

    public void setAbsentDays(List<LocalDate> absentDays){
        this.absentDays = absentDays;
    }

    public void setPending(List<LocalDate> pending){this.pending = pending;}

    public List<List<Character>> getAttendanceGrid(){
        return this.attendanceGrid;
    }

    public List<LocalDate> getPresentDays(){
        return this.presentDays;
    }

    public List<LocalDate> getAbsentDays(){
        return this.absentDays;
    }

    public List<LocalDate> getPending(){return this.pending;}

    public Attendance(){
        this.presentDays = new ArrayList<>();
        this.absentDays = new ArrayList<>();
        this.pending = new ArrayList<>();
        this.attendanceGrid = new ArrayList<>();
    }

    @Override
    public void markPresent(int ID, LocalDate date) throws IOException{

        try (FileReader reader = new FileReader("data/Database.json")) {
            Gson gson = GsonImports.createGson();

            Type userListType = new TypeToken<ArrayList<User>>(){}.getType();
            List<User> allUsers = gson.fromJson(reader, userListType);

            boolean found = false;
            for (User u : allUsers) {
                if (u.getID() == ID) {
                    found = true;

                    List<LocalDate> presentDays = u.getAttendance().getPresentDays();
                    List<LocalDate> absentDays = u.getAttendance().getAbsentDays();
                    List<LocalDate> pending = u.getAttendance().getPending();

                    if (presentDays == null) {
                        presentDays = new ArrayList<>();
                        u.getAttendance().setPresentDays(presentDays);
                    }

                    if (presentDays.isEmpty()) {
                        pending.remove(date);
                        absentDays.remove(date);
                        presentDays.add(date);
                        System.out.println("Attendance successfully marked for " + date);
                    } else {
                        if(presentDays.contains(date)){
                            absentDays.remove(date);
                            pending.remove(date);
                            break;
                        }else{
                            presentDays.add(date);
                            pending.remove(date);
                            absentDays.remove(date);
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
                gson.toJson(allUsers, userListType, writer);
            }catch (IOException e){
                throw new IOException ("Error in marking present");
            }
        } catch (FileNotFoundException e){
            throw new FileNotFoundException("File not found ");
        } catch (IOException e) {
            throw new IOException("Error in reading file");
        }
    }

    @Override
    public void markAbsent(int ID, LocalDate date) throws IOException {
        try(FileReader reader = new FileReader("data/Database.json")){
            Gson gson = GsonImports.createGson();

            Type userListType = new TypeToken<ArrayList<User>>() {}.getType();
            List<User> allUsers = gson.fromJson(reader, userListType);

            for(User user : allUsers){
                if(user.getID() == ID){

                    List<LocalDate> absentDays = user.getAttendance().getAbsentDays();
                    List<LocalDate> presentDays = user.getAttendance().getPresentDays();
                    List<LocalDate> pending = user.getAttendance().getPending();

                    if (absentDays == null) {
                        absentDays = new ArrayList<>();
                        user.getAttendance().setAbsentDays(absentDays);
                    }

                    if(absentDays.isEmpty()){
                        absentDays.add(date);
                        pending.remove(date);
                        presentDays.remove(date);
                    }else{
                        if(absentDays.contains(date)){
                            pending.remove(date);
                            presentDays.remove(date);
                            break;
                        }
                        else{
                            absentDays.add(date);
                            pending.remove(date);
                            presentDays.remove(date);
                        }
                    }
                }
            }
            try (FileWriter writer = new FileWriter("data/Database.json")) {
                gson.toJson(allUsers, userListType, writer);
            } catch (IOException e) {
                throw new IOException("Error while marking absent");
            }
        } catch (FileNotFoundException e){
            throw new FileNotFoundException("File not found");
        } catch (IOException e) {
            throw new IOException("Error while reading the file");
        }
    }

    @Override
    public void markPending(int ID, LocalDate date) throws IOException{
        try(FileReader reader = new FileReader("data/Database.json")){
            Gson gson = GsonImports.createGson();

            Type userListType = new TypeToken<ArrayList<User>>(){}.getType();
            List<User> allUsers = gson.fromJson(reader, userListType);

            boolean found = false;
            for(User user : allUsers){
                if(user.getID() == ID){
                    found = true;
                    List<LocalDate> pending = user.getAttendance().getPending();

                    if(pending == null){
                        pending = new ArrayList<>();
                        user.getAttendance().setPending(pending);
                    }

                    if (!pending.isEmpty()) {
                        pending.clear();
                    }
                    pending.add(date);
                    break;
                }
            }

            if(!found){
                System.out.println("No user found with this ID");
            }
            try(FileWriter writer = new FileWriter("data/Database.json")){
                gson.toJson(allUsers, userListType, writer);
            }catch (IOException e){
                throw new IOException("Error while marking pending");
            }
        }catch (FileNotFoundException e){
            throw new FileNotFoundException("File not found");
        } catch(IOException e){
            throw new IOException("Error in reading the file");
        }
    }

    @Override
    public List<LocalDate> viewPresentDates(int ID) throws IOException {
        try(FileReader reader = new FileReader("data/Database.json")){
            Gson gson = GsonImports.createGson();

            Type userListType = new TypeToken<ArrayList<User>>(){}.getType();
            List<User> aUser = gson.fromJson(reader, userListType);

            for(User u : aUser){
                if(u.getID() == ID){
                    return u.getAttendance().presentDays;
                }
            }
        }catch (IOException e){
            throw new IOException("Error while displaying present dates");
        }

        System.out.println("Check the ID you have entered : " + ID);
        return null;
    }

    @Override
    public List<LocalDate> viewAbsentDates(int ID) throws IOException{
        try(FileReader reader = new FileReader("data/Database.json")){
            Gson gson = GsonImports.createGson();

            Type userListType = new TypeToken<ArrayList<User>>(){}.getType();
            List<User> aUser = gson.fromJson(reader, userListType);

            for(User u : aUser){
                if(u.getID() == ID){
                    return u.getAttendance().absentDays;
                }
            }
        }catch (IOException e){
            throw new IOException("Error while displaying absent dates");
        }

        System.out.println("Check the ID you have entered : " + ID);
        return null;
    }

    public List<LocalDate> viewPendingDates(int ID) throws IOException{
            try(FileReader reader = new FileReader("data/Database.json")){
                Gson gson = GsonImports.createGson();

                Type userListType = new TypeToken<ArrayList<User>>(){}.getType();
                List<User> aUser = gson.fromJson(reader, userListType);

                for(User u : aUser){
                    if(u.getID() == ID){
                        return u.getAttendance().pending;
                    }
                }
            }catch (IOException e){
                throw new IOException("Error while displaying pending dates");
            }

            System.out.println("Check the ID you have entered : " + ID);
            return null;
    }

    public static void viewAttendance(String mail) throws IOException{
        try(FileReader reader = new FileReader("data/Database.json")){
            Gson gson = GsonImports.createGson();

            Type userListType = new TypeToken<ArrayList<User>>(){}.getType();
            List<User> aUser = gson.fromJson(reader, userListType);

            boolean found = false;
            for(User u : aUser){
                if(u.getEmail().equalsIgnoreCase(mail)){
                    found = true;
                    System.out.println("Present days are : " + u.getAttendance().presentDays);
                    System.out.println("Absent days are : " + u.getAttendance().absentDays);
                    break;
                }
            }

            if(!found){
                System.out.println("Enter a valid ID");
            }
        } catch (FileNotFoundException e){
            throw new FileNotFoundException("File not found");
        }
        catch (IOException e) {
            throw new IOException("Error displaying attendance");
        }
    }

    public static void viewAttendanceOfUser(int ID) throws IOException {
        try(FileReader reader = new FileReader("data/Database.json")){
            Gson gson = GsonImports.createGson();

            Type userListType = new TypeToken<ArrayList<User>>(){}.getType();
            List<User> aUser = gson.fromJson(reader, userListType);

            boolean found = false;
            for(User u : aUser){
                if(u.getID() == ID){
                    found = true;
                    System.out.println("Present days are : " + u.getAttendance().presentDays);
                    System.out.println("Absent days are : " + u.getAttendance().absentDays);
                    break;
                }
            }

            if(!found){
                System.out.println("Enter a valid ID");
            }
        } catch (FileNotFoundException e){
            throw new FileNotFoundException("File not found");
        } catch (IOException e) {
            throw new IOException("Error reading the file");
        }
    }

    public static void selectEditAttendance(int ID, String mail) throws IOException {
        Scanner scanner = new Scanner(System.in);

        boolean working1 = true;
        while(working1){
            System.out.println("\n------x------x------x------x------\n");
            System.out.println("Select what you want to do : ");
            System.out.println("1. Mark an absent date as present");
            System.out.println("2. Mark a present date as absent");
            System.out.println("0. Exit");
            System.out.println("\n------x------x------x------x------\n");

            int choice6 = 0;
            boolean valid1 = false;

            while (!valid1) {
                System.out.print("Enter your choice (1/2/0) : ");
                String input = scanner.nextLine();

                try {
                    choice6 = Integer.parseInt(input);
                    valid1 = true;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number.\n");
                }
            }

            switch (choice6) {

                case 1:
                    System.out.println("\nEnter the date you want to change the attendance for in format(yyyy-MM-dd): ");
                    String date = scanner.nextLine();

                    try {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        LocalDate date1 = LocalDate.parse(date, formatter);
                        editAbsentAttendance(ID, date1);
                        viewAttendanceOfUser(ID);
                    } catch (DateTimeParseException e) {
                        System.out.println("Invalid date format. Please use yyyy-MM-dd");
                    }catch (IOException e){
                        System.out.println("Error while reading the file");
                    }
                    working1 = false;
                    break;

                case 2:
                    System.out.println("\nEnter the date you want to change the attendance for int format (yyyy-mm-dd) : ");
                    date = scanner.nextLine();

                    LocalDate date2 = null;
                    try{
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        date2 = LocalDate.parse(date, formatter);
                        editPresentAttendance(ID, date2);
                        viewAttendanceOfUser(ID);
                    }catch(DateTimeException e){
                        System.out.println("Invalid date format. Please use yyyy-mm-dd");
                    }catch (IOException e){
                        System.out.println("Error while reading the file");
                    }

                    working1 = false;

                    break;

                case 0:
                    working1 = false;
                    break;

                default:
                    System.out.println("Enter a valid choice (1/2/0) ");
                    break;
            }
        }
    }

    public static void editAbsentAttendance(int ID, LocalDate date) throws IOException {
        try(FileReader reader = new FileReader("data/Database.json")){
            Gson gson = GsonImports.createGson();

            Type userListType = new TypeToken<ArrayList<User>>(){}.getType();
            List<User> aUser = gson.fromJson(reader, userListType);

            boolean found = false;
            for(User u : aUser){
                if(u.getID() == ID){
                    found = true;

                    List<LocalDate> absentDays = u.getAttendance().getAbsentDays();
                    List<LocalDate> presentDays = u.getAttendance().getPresentDays();

                    if(!presentDays.contains(date)){
                        if(absentDays.isEmpty()){
                            presentDays.add(date);
                        }else{
                            if(absentDays.contains(date)){
                                absentDays.remove(date);
                                presentDays.add(date);

                                presentDays.sort(Comparator.naturalOrder());
                            }else {
                                if(checkHoliday(date)){
                                    boolean temp = true;
                                    while(temp){
                                        Scanner scanner = new Scanner(System.in);
                                        System.out.println("The mentioned date was a holiday. Confirm if you want to mark this date as present ?");
                                        System.out.println("Date : " + date + "," + " Day : " + date.getDayOfWeek());
                                        System.out.println("(YES/NO) ? ");

                                        //check if the input is right
                                        String input = scanner.nextLine();

                                        if(input.equalsIgnoreCase("YES")){
                                            presentDays.add(date);
                                            temp = false;
                                        }else if(input.equalsIgnoreCase("NO")){
                                            return;
                                        }else {
                                            System.out.println("Enter a valid input (YES/NO)");
                                        }
                                    }
                                }else{
                                    System.out.println("The date is before the User joined. Cannot be marked as absent or present.");
                                }
                            }
                        }
                    }else{
                        System.out.println("The date is already marked as present");
                        return;
                    }
                }
            }

            if(!found){
                System.out.println("No such ID exists.");
            }
            try(FileWriter writer = new FileWriter("data/Database.json")){
                gson.toJson(aUser, userListType, writer);
            } catch (JsonIOException e) {
                System.out.println("Some unknow error occurred in writing to the file.");
            }
        }catch(IOException e){
            System.out.println("Some unknown error occurred in reading from the file.");
        }
    }

    public static void editPresentAttendance(int ID, LocalDate date) {
        try(FileReader reader = new FileReader("data/Database.json")){
            Gson gson = GsonImports.createGson();

            Type userListType = new TypeToken<ArrayList<User>>(){}.getType();
            List<User> aUser = gson.fromJson(reader, userListType);

            boolean found = false;
            for(User u : aUser){
                if(u.getID() == ID){
                    found = true;

                    List<LocalDate> absentDays = u.getAttendance().getAbsentDays();
                    List<LocalDate> presentDays = u.getAttendance().getPresentDays();

                    if(!absentDays.contains(date)){
                        if(presentDays.isEmpty()){
                            absentDays.add(date);
                        }else{
                            if(presentDays.contains(date)){
                                presentDays.remove(date);
                                absentDays.add(date);

                                absentDays.sort(Comparator.naturalOrder());
                            }else {
                                if(checkHoliday(date)){
                                    boolean temp = true;
                                    while(temp){
                                        Scanner scanner = new Scanner(System.in);
                                        System.out.println("The mentioned date was a holiday. Confirm if you want to mark this date as absent ?");
                                        System.out.println("Date : " + date + "," + " Day : " + date.getDayOfWeek());
                                        System.out.println("Yes/No ? ");

                                        //check if the input is right
                                        String input = scanner.nextLine();

                                        if(input.equalsIgnoreCase("YES")){
                                            absentDays.add(date);
                                            temp = false;
                                        }else if(input.equalsIgnoreCase("NO")){
                                            return;
                                        }else {
                                            System.out.println("Enter a valid input (YES/NO)");
                                        }
                                    }
                                }else{
                                    System.out.println("The date is before the User joined. Cannot be marked as absent or present.");
                                }
                            }
                        }
                    }else{
                        System.out.println("The date is already marked as absent");
                        return;
                    }
                }
            }

            if(!found){
                System.out.println("No such ID exists.");
            }
            try(FileWriter writer = new FileWriter("data/Database.json")){
                gson.toJson(aUser, userListType, writer);
            } catch (JsonIOException e) {
                System.out.println("Some unknow error occurred in writing to the file.");
            }
        }catch(IOException e){
            System.out.println("Some unknown error occurred in reading from the file.");
        }
    }
}
