package login;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import attendance.localDateAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import models.User;

public class userLogin {
    //ignore case for mail
    public static boolean checkMail(String mail) throws IOException {
        boolean found = false;

        try (FileReader reader = new FileReader("data/Database.json")) {
            Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new localDateAdapter()).setPrettyPrinting().create();

            Type userListType = new TypeToken<ArrayList<User>>() {
            }.getType();
            List<User> aUser = gson.fromJson(reader, userListType);

            for (User u : aUser) {
                if (u.getEmail().equals(mail)) {
                    found = true;
                    break;
                }
            }
        } catch (IOException e) {
            throw new IOException(e);
        }

        //return true if the mail is valid
        return found;
    }

    public static boolean checkPass(String mail, String password) throws IOException {
        boolean found = false;

        try (FileReader reader = new FileReader("data/Database.json")) {
            Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new localDateAdapter()).setPrettyPrinting().create();

            Type userListType = new TypeToken<ArrayList<User>>() {
            }.getType();
            List<User> aUser = gson.fromJson(reader, userListType);

            for (User u : aUser) {
                if (u.getEmail().equals(mail)) {
                    if (u.getPassword().equals(password)) {
                        found = true;
                    }
                }
            }
        } catch (IOException e) {
            throw new IOException(e);
        }
        //return true is password matches
        return found;
    }
}