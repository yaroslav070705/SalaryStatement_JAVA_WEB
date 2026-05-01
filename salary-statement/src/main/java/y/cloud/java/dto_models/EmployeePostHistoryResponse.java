package y.cloud.java.dto_models;

import y.cloud.java.salary_statement_models.EmployeePostHistory;

import java.time.LocalDate;
import java.util.UUID;

public class EmployeePostHistoryResponse {
    private UUID employee_id;
    private UUID post_id;
    private String post_name;
    private LocalDate start_date;
    private LocalDate end_date;

    public EmployeePostHistoryResponse() {}
    public EmployeePostHistoryResponse(EmployeePostHistory post_history) {
        this.employee_id = post_history.getId().getEmployeeId();
        this.post_id = post_history.getId().getPostId();
        this.end_date = post_history.getEndDate();
        this.start_date = post_history.getStartDate();
    }

    public UUID getEmployeeId() { return employee_id; }
    public UUID getPostId() { return post_id; }
    public String getPostName() { return post_name; }
    public LocalDate getStartDate() { return start_date; }
    public LocalDate getEndDate() { return end_date; }

    public void setEmployeeId(UUID employee_id) { this.employee_id = employee_id; }
    public void setPostId(UUID post_id) { this.post_id = post_id; }
    public void setPostName(String post_name) { this.post_name = post_name; }
    public void setStartDate(LocalDate start_date) { this.start_date = start_date; }
    public void setEndDate(LocalDate end_date) { this.end_date = end_date; }
}
