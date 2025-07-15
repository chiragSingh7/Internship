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
import java.util.Scanner;

public class modifyDatabase {

    public static void addToDatabase(int ID, String name, String mail, String password) {
        try {
            File file = new File("data/Database.json");
            Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new localDateAdapter()).setPrettyPrinting().create();

            User u1 = new User(ID, name, mail, password);

            //check if the file is empty
            if (file.length() == 0) {
                //if yes then create a list and add the first user to list and write
                List<User> allUser = new ArrayList<>();
                allUser.add(u1);
                String myJson = gson.toJson(allUser);

                try (FileWriter writer = new FileWriter("data/Database.json")) {
                    writer.write(myJson);
                } catch (IOException e) {
                    throw new IOException(e);
                }
            } else {
                Type userListType = new TypeToken<ArrayList<User>>() {
                }.getType();
                List<User> aUser = new ArrayList<>();

                try (FileReader reader = new FileReader("data/Database.json")) {
                    // read the list from json file and add the new user
                    aUser = gson.fromJson(reader, userListType);
                } catch (IOException e) {
                    throw new IOException(e);
                }

                aUser.add(u1);

                try (FileWriter writer = new FileWriter("data/Database.json")) {
                    gson.toJson(aUser, writer);
                } catch (IOException e) {
                    throw new IOException(e);
                }
            }
        } catch (Exception e) {
            System.out.println("Something went wrong while dealing with database.");
            e.printStackTrace();
        }
    }

    public static void deleteFromDatabase(int ID) {
        try (FileReader reader = new FileReader("data/Database.json")) {
            Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new localDateAdapter()).setPrettyPrinting().create();

            Type userListType = new TypeToken<ArrayList<User>>() {
            }.getType();
            List<User> aUser = gson.fromJson(reader, userListType);

            boolean found = false;
            for (User u : aUser) {
                if (u.getID() == ID) {
                    aUser.remove(u);
                    found = true;
                    break;
                }
            }

            if (!found) {
                System.out.println("No user found with this ID");
            }

            try (FileWriter writer = new FileWriter("data/Database.json")) {
                gson.toJson(aUser, writer);

                System.out.println("User ID '" + ID + "' removed successfully");
            } catch (IOException e) {
                System.out.println("Error while writing file " + e.getMessage());
            }
        } catch (IOException e) {
            System.out.println("Error while reading file " + e.getMessage());
        }
    }

    public static void editToDatabaseForAdmin(int ID) {
        try (FileReader reader = new FileReader("data/Database.json")) {
            Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new localDateAdapter()).setPrettyPrinting().create();

            Type userListType = new TypeToken<ArrayList<User>>() {
            }.getType();
            List<User> aUser = gson.fromJson(reader, userListType);
            Scanner scanner = new Scanner(System.in);

            boolean found = false;
            for (User u : aUser) {
                if (u.getID() == ID) {
                    System.out.println("\nEditing the database for " + ID);
                    found = true;
                    boolean working = true;

                    while (working) {
                        System.out.println("\n------x------x------x------x------\n");
                        System.out.println("Select what you want to edit/update for " + ID);
                        System.out.println("1. Name");
                        System.out.println("2. Email");
                        System.out.println("3. Role");
                        System.out.println("4. Sub-Role");
                        System.out.println("0. Exit");
                        System.out.println("\n------x------x------x------x------\n");
                        System.out.println("Enter your choice : ");

                        int choice5 = 0;
                        boolean valid = false;

                        while (!valid) {
                            System.out.print("Enter your choice (1/2/3/4/0) : ");
                            String input = scanner.nextLine();

                            try {
                                choice5 = Integer.parseInt(input);
                                valid = true;
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid input. Please enter a number.");
                            }
                        }

                        switch (choice5) {
                            case 1:
                                System.out.println("Enter the name you want to change to for " + ID);
                                String flag = scanner.nextLine();
                                editName(ID, flag);
                                break;

                            case 2:
                                System.out.println("Enter the email you want to change to for " + ID);
                                flag = scanner.nextLine();
                                editEmail(ID, flag);
                                break;

                            case 3:
                                System.out.println("Enter the role you want to change to for " + ID);
                                flag = scanner.nextLine();
                                editRole(ID, flag);
                                break;

                            case 4:
                                System.out.println("Enter the Sub-Role you want to change to for " + ID);
                                flag = scanner.nextLine();
                                editSubRole(ID, flag);
                                break;

                            case 0:
                                working = false;
                                break;

                            default:
                                System.out.println("Enter a valid input (1/2/3/4/0) ");
                                break;
                        }
                    }
                }
            }

            if (!found) {
                System.out.println("No user found with the ID : " + ID);
            }
        } catch (IOException e) {
            System.out.println("Error in reading the file " + e.getMessage());
        }
    }

    public static void editToDatabaseForHR(int ID) {
        try (FileReader reader = new FileReader("data/Database.json")) {
            Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new localDateAdapter()).setPrettyPrinting().create();

            Type userListType = new TypeToken<ArrayList<User>>() {
            }.getType();
            List<User> aUser = gson.fromJson(reader, userListType);
            Scanner scanner = new Scanner(System.in);

            boolean found = false;
            for (User u : aUser) {
                if (u.getID() == ID) {
                    System.out.println("Editing the database for " + ID);
                    found = true;
                    boolean working = true;

                    while (working) {
                        System.out.println("\n------x------x------x------x------\n");
                        System.out.println("Select what you want to edit/update for " + ID);
                        System.out.println("1. Name");
                        System.out.println("2. Email");
                        System.out.println("0. Exit");
                        System.out.println("\n------x------x------x------x------\n");

                        int choice5 = 0;
                        boolean valid = false;

                        while (!valid) {
                            System.out.print("Enter your choice (1/2/0): ");
                            String input = scanner.nextLine();

                            try {
                                choice5 = Integer.parseInt(input);
                                valid = true;
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid input. Please enter a number.");
                            }
                        }

                        switch (choice5) {
                            case 1:
                                System.out.println("Enter the name you want to change to for " + ID);
                                String flag = scanner.nextLine();
                                editName(ID, flag);
                                break;

                            case 2:
                                System.out.println("Enter the email you want to change to for " + ID);
                                flag = scanner.nextLine();
                                editEmail(ID, flag);
                                break;

                            case 0:
                                working = false;
                                break;

                            default:
                                System.out.println("Enter a valid input (1/2/0) ");
                                break;
                        }
                    }
                }
            }

            if (!found) {
                System.out.println("No user found with the ID : " + ID);
            }
        } catch (IOException e) {
            System.out.println("Error in reading the file " + e.getMessage());
        }
    }


    public static void editName(int ID, String name){
        try(FileReader reader = new FileReader("data/Database.json")){
            Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new localDateAdapter()).setPrettyPrinting().create();

            Type userListType = new TypeToken<ArrayList<User>>(){}.getType();
            List<User> aUser = gson.fromJson(reader, userListType);

            boolean found = false;
            for(User u : aUser){
                if(u.getID() == ID){
                    u.setName(name);
                    found = true;
                    break;
                }
            }

            if(!found){
                System.out.println("No user found with the ID : " + ID);
            }
            System.out.println("Name changed successfully ");
            showUser.showUserDetailsToAdmin(ID);
            try(FileWriter writer = new FileWriter("data/Database.json")){
                gson.toJson(aUser, writer);
            }catch (IOException e){
                System.out.println("Error while writing file " + e.getMessage());
            }
        }catch(IOException e){
            System.out.println("Error while reading file " + e.getMessage());
        }
    }

    public static void editEmail(int ID, String mail){
        try(FileReader reader = new FileReader("data/Database.json")){
            Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new localDateAdapter()).setPrettyPrinting().create();

            Type userListType = new TypeToken<ArrayList<User>>(){}.getType();
            List<User> aUser = gson.fromJson(reader, userListType);

            boolean found = false;
            for(User u : aUser){
                if(u.getID() == ID){
                    u.setEmail(mail);
                    found = true;
                    break;
                }
            }

            if(!found){
                System.out.println("No user found with the ID : " + ID);
            }
            System.out.println("Email changed successfully ");
            showUser.showUserDetailsToAdmin(ID);
            try(FileWriter writer = new FileWriter("data/Database.json")){
                gson.toJson(aUser, writer);
            }catch (IOException e){
                System.out.println("Error while writing file " + e.getMessage());
            }
        }catch(IOException e){
            System.out.println("Error while reading file " + e.getMessage());
        }
    }

    public static void editRole(int ID, String role){
        try(FileReader reader = new FileReader("data/Database.json")){
            Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new localDateAdapter()).setPrettyPrinting().create();

            Type userListType = new TypeToken<ArrayList<User>>(){}.getType();
            List<User> aUser = gson.fromJson(reader, userListType);

            boolean found = false;
            for(User u : aUser){
                if(u.getID() == ID){
                    u.setRole(role);
                    found = true;
                    break;
                }
            }

            if(!found){
                System.out.println("No user found with the ID : " + ID);
            }
            System.out.println("Role changed successfully ");
            showUser.showUserDetailsToAdmin(ID);
            try(FileWriter writer = new FileWriter("data/Database.json")){
                gson.toJson(aUser, writer);

            }catch (IOException e){
                System.out.println("Error while writing file " + e.getMessage());
            }
        }catch(IOException e){
            System.out.println("Error while reading file " + e.getMessage());
        }
    }

    public static void editSubRole(int ID, String subRole){
        try(FileReader reader = new FileReader("data/Database.json")){
            Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new localDateAdapter()).setPrettyPrinting().create();

            Type userListType = new TypeToken<ArrayList<User>>(){}.getType();
            List<User> aUser = gson.fromJson(reader, userListType);

            boolean found = false;
            for(User u : aUser){
                if(u.getID() == ID){
                    u.setSubRole(subRole);
                    found = true;
                    break;
                }
            }

            if(!found){
                System.out.println("No user found with the ID : " + ID);
            }
            System.out.println("Sub-Role changed successfully ");
            showUser.showUserDetailsToAdmin(ID);
            try(FileWriter writer = new FileWriter("data/Database.json")){
                gson.toJson(aUser, writer);
            }catch (IOException e){
                System.out.println("Error while writing file " + e.getMessage());
            }
        }catch(IOException e){
            System.out.println("Error while reading file " + e.getMessage());
        }
    }
}
