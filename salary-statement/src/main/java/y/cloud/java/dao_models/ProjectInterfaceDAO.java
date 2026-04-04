package y.cloud.java.dao_models;

import y.cloud.java.dto_models.ProjectRequest;
import y.cloud.java.salary_statement_models.Project;

import java.util.List;
import java.util.UUID;

interface ProjectInterfaceDAO extends BaseInterfaceDAO<Project, ProjectRequest, UUID>{
    List<Project> findByParams(ProjectRequest req);
}
