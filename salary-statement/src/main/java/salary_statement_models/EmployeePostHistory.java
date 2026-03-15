package salary_statement_models;

import java.time.LocalDate;
import java.util.UUID;


public class EmployeePostHistory {
    private Employee employee_id;
    private Post post_id;
    private LocalDate start_date;
    private LocalDate end_date;

    public UUID[] getId() {
        return new UUID[]{employee_id.getId(), post_id.getId()};
    }

    public LocalDate getStartDate() {
        return start_date;
    }

    public LocalDate getEndDate() {
        return end_date;
    }
}
