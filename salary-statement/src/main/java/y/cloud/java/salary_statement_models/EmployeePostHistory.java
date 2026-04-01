package y.cloud.java.salary_statement_models;

import java.time.LocalDate;
import java.util.UUID;


public class EmployeePostHistory {
    private Employee employee_id;
    private Post post_id;
    private LocalDate start_date;
    private LocalDate end_date;

    public EmployeePostHistoryPK getId() {
        return new EmployeePostHistoryPK(employee_id.getId(), post_id.getId());
    }

    public void setId(Employee employee_id, Post post_id) {
        this.employee_id = employee_id;
        this.post_id = post_id;
    }

    public LocalDate getStartDate() {
        return start_date;
    }

    public void setStartDate(LocalDate start_date) {
        this.start_date = start_date;
    }

    public LocalDate getEndDate() {
        return end_date;
    }

    public void setEndDate(LocalDate end_date) {
        this.end_date = end_date;
    }
}
