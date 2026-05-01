package y.cloud.java.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import y.cloud.java.dao_models.ProjectDAO;
import y.cloud.java.dao_models.ProjectSetupDAO;
import y.cloud.java.dao_models.RoleDAO;
import y.cloud.java.dto_models.*;
import y.cloud.java.salary_statement_models.Employee;
import y.cloud.java.salary_statement_models.Project;
import y.cloud.java.salary_statement_models.ProjectSetup;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/projects")
public class ProjectsController {
    @Autowired
    private ProjectSetupDAO project_setup_dao;

    @Autowired
    private ProjectDAO project_dao;

    @Autowired
    private RoleDAO role_dao;

    @GetMapping
    public List<ProjectResponse> getProjects() {
        List<Project> projects = project_dao.findAll();
        List<ProjectResponse> responses = new ArrayList<>();

        for(Project project : projects) {
            ProjectResponse resp = new ProjectResponse(project);
            responses.add(resp);
        }

        return responses;
    }

    @GetMapping("/{id}")
    public List<ProjectSetupResponse> getEmployeeProjects(@PathVariable("id") UUID id) {
        ProjectSetupRequest req = new ProjectSetupRequest();
        req.setEmployeeId(id);

        List<ProjectSetup> project_setups = project_setup_dao.findByParams(req);
        List<ProjectSetupResponse> responses = new ArrayList<>();

        for(ProjectSetup ps : project_setups) {
            ProjectSetupResponse resp = new ProjectSetupResponse(ps);
            resp.setRoleName(role_dao.findById(resp.getRoleId()).getRoleName());
            resp.setProjectName(project_dao.findById(resp.getProjectId()).getProjectName());
            responses.add(resp);
        }

        return responses;
    }

    @GetMapping("/{id}/employees")
    public List<EmployeeResponse> getProjectEmployees(@PathVariable("id") UUID project_id) {
        List<Employee> employees_list = project_setup_dao.getProjectEmployees(project_id);
        List<EmployeeResponse> responses = new ArrayList<>();

        for(Employee employee : employees_list) {
            EmployeeResponse resp = new EmployeeResponse(employee);
            responses.add(resp);
        }

        return responses;
    }
}
