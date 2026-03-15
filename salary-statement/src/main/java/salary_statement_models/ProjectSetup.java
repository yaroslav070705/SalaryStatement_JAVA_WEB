package salary_statement_models;

import java.util.UUID;


public class ProjectSetup {
    private Employee employee_id;
    private Project project_id;
    private Role role_id;

    public UUID[] getId() {
        return new UUID[]{employee_id.getId(), project_id.getId(), role_id.getId()};
    }
}
