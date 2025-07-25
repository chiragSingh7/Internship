package attendance;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface attendanceMethods {

    void markPresent(int ID, LocalDate date) throws IOException;

    void markAbsent(int ID, LocalDate date) throws IOException;

    void markPending(int ID, LocalDate date) throws IOException;

    List<LocalDate> viewPresentDates(int ID) throws IOException;

    List<LocalDate> viewAbsentDates(int ID) throws IOException;

    List<LocalDate> viewPendingDates(int ID) throws IOException;
}
