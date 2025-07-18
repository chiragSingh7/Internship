package attendance;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.Duration;

public class durationAdapter extends TypeAdapter<Duration> {

    @Override
    public void write(JsonWriter out, Duration value) throws IOException {
        if (value == null) {
            out.nullValue();
        } else {
            out.value(value.toString());  // ISO-8601 format: "PT2H30M"
        }
    }

    @Override
    public Duration read(JsonReader in) throws IOException {
        String durationStr = in.nextString(); // e.g., "PT2H30M"
        return Duration.parse(durationStr);
    }
}
