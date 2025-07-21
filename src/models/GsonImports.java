package models;

import attendance.durationAdapter;
import attendance.localDateAdapter;
import attendance.localTimeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

public class GsonImports {
    public static Gson createGson(){

        return new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new localDateAdapter())
                .registerTypeAdapter(LocalTime.class, new localTimeAdapter())
                .registerTypeAdapter(Duration.class, new durationAdapter())
                .setPrettyPrinting().create();
    }
}
