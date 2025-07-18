package attendance;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalTime;

public class localTimeAdapter extends TypeAdapter<LocalTime> {

    @Override
    public void write(JsonWriter out, LocalTime value) throws IOException {
        if (value == null) {
            out.nullValue();
        } else {
            out.value(value.toString());  // e.g., "15:45:30"
        }
    }

    @Override
    public LocalTime read(JsonReader in) throws IOException {
        String timeStr = in.nextString();
        return LocalTime.parse(timeStr); // parses "HH:mm[:ss[.SSS]]"
    }
}
