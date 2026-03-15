package salary_statement_models;

import java.time.LocalDate;
import java.util.UUID;


public class Project {
    private UUID project_id;
    private String project_name;
    private LocalDate start_date;
    private LocalDate end_date;

    public UUID getId() {
        return project_id;
    }

    public boolean setId(UUID project_id) {
        if(project_id == null){
            return false;
        }

        this.project_id = project_id;

        return true;
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
}
