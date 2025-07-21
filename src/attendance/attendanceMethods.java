package attendance;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface attendanceMethods {

    public void markPresent(int ID, LocalDate date) throws IOException;

    public void markAbsent(int ID, LocalDate date) throws IOException;

    List<LocalDate> viewPresentDates(int ID) throws IOException;

    List<LocalDate> viewAbsentDates(int ID) throws IOException;
}
