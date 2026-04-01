package y.cloud.java.salary_statement_models;

import y.cloud.java.dto_models.ProjectRequest;

import java.time.LocalDate;
import java.util.UUID;


public class Project {
    private UUID project_id;
    private String project_name;
    private LocalDate start_date;
    private LocalDate end_date;

    public Project() {}
    public Project(ProjectRequest project_req){
        project_name = project_req.getProjectName();
        start_date = project_req.getStartDate();
        end_date = project_req.getEndDate();
    }

    public UUID getId() {
        return project_id;
    }

    public void setId(UUID project_id) {
        this.project_id = project_id;
    }

    public String getProjectName() {
        return project_name;
    }

    public void setProjectName(String project_name) {
        this.project_name = project_name;
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
