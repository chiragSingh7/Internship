package attendance;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface attendanceMethods {

    public List<LocalDate> viewPresentDates(int ID) throws IOException;

    public List<LocalDate> viewAbsentDates(int ID) throws IOException;

    public void markPresent(LocalDate date);

//    public void markAbsent(LocalDate date);

}
