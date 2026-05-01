package y.cloud.java.dto_models;

import y.cloud.java.salary_statement_models.Project;

import java.time.LocalDate;
import java.util.UUID;

public class ProjectResponse {
    private UUID project_id;
    private String project_name;
    private LocalDate start_date;
    private LocalDate end_date;

    public ProjectResponse() {}
    public ProjectResponse(Project project) {
        this.project_id = project.getId();
        this.project_name = project.getProjectName();
        this.start_date = project.getStartDate();
        this.end_date = project.getEndDate();
    }
    public ProjectResponse(UUID project_id, String project_name, LocalDate start_date, LocalDate end_date) {
        this.project_id = project_id;
        this.project_name = project_name;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public UUID getProjectId() {
        return project_id;
    }
    public String getProjectName() {
        return project_name;
    }
    public LocalDate getStartDate() {
        return start_date;
    }
    public LocalDate getEndDate() {
        return end_date;
    }


    public void setProjectId(UUID project_id) {
        this.project_id = project_id;
    }
    public void setProjectName(String project_name) {
        this.project_name = project_name;
    }
    public void setStartDate(LocalDate start_date) {
        this.start_date = start_date;
    }
    public void setEndDate(LocalDate end_date) { this.end_date = end_date; }
}
