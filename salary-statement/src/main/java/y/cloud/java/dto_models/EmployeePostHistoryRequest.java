package y.cloud.java.dto_models;

import java.time.LocalDate;
import java.util.UUID;

public class EmployeePostHistoryRequest {
    private UUID employee_id;
    private UUID post_id;
    private LocalDate start_date;
    private LocalDate end_date;

    public EmployeePostHistoryRequest() {}
    public EmployeePostHistoryRequest(UUID employee_id, UUID post_id, LocalDate start_date, LocalDate end_date) {
        this.employee_id = employee_id;
        this.post_id = post_id;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public UUID getEmployeeId() {
        return employee_id;
    }

    public UUID getPostId() {
        return post_id;
    }

    public LocalDate getStartDate() {
        return start_date;
    }

    public LocalDate getEndDate() {
        return end_date;
    }

    public void setEmployeeId(UUID employee_id) {
        this.employee_id = employee_id;
    }

    public void setPostId(UUID post_id) {
        this.post_id = post_id;
    }

    public void setStartDate(LocalDate start_date) {
        this.start_date = start_date;
    }

    public void setEndDate(LocalDate end_date) {
        this.end_date = end_date;
    }
}
