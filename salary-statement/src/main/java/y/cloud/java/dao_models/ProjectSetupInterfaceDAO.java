package y.cloud.java.dao_models;

import y.cloud.java.dto_models.ProjectSetupRequest;
import y.cloud.java.salary_statement_models.Employee;
import y.cloud.java.salary_statement_models.ProjectSetup;
import y.cloud.java.salary_statement_models.ProjectSetupPK;

import java.util.List;
import java.util.UUID;

interface ProjectSetupInterfaceDAO extends BaseInterfaceDAO<ProjectSetup, ProjectSetupRequest, ProjectSetupPK> {
    List<ProjectSetup> findByParams(ProjectSetupRequest req);
    List<Employee> getProjectEmployees(UUID project_id);
    public void delete(ProjectSetupRequest req);
}
