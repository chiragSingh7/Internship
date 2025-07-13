package attendance;

import java.time.LocalDate;
import java.util.List;

public class attendanceAdmin implements attendanceMethods{

    @Override
    public List<LocalDate> viewPresentDates() {
        return List.of();
    }

    @Override
    public List<LocalDate> viewAbsentDates() {
        return List.of();
    }

    @Override
    public void markPresent(LocalDate date) {

    }

    @Override
    public void markAbsent(LocalDate date) {

    }
}
