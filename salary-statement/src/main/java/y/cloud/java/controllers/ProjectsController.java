package y.cloud.java.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import y.cloud.java.dao_models.EmployeeRoleHistoryDAO;
import y.cloud.java.dao_models.ProjectDAO;
import y.cloud.java.dao_models.ProjectSetupDAO;
import y.cloud.java.dao_models.RoleDAO;
import y.cloud.java.dto_models.*;
import y.cloud.java.salary_statement_models.Employee;
import y.cloud.java.salary_statement_models.Project;
import y.cloud.java.salary_statement_models.ProjectSetup;

import java.time.LocalDate;
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

    @Autowired
    private EmployeeRoleHistoryDAO role_history_dao;

    @GetMapping
    public List<ProjectResponse> getProjects(@RequestParam(required = false) String name,
                                             @RequestParam(required = false)@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start_date,
                                             @RequestParam(required = false)@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end_date) {

        ProjectRequest req = new ProjectRequest(null,name,start_date,end_date);
        List<Project> projects = project_dao.findByParams(req);
        List<ProjectResponse> responses = new ArrayList<>();

        for(Project project : projects) {
            ProjectResponse resp = new ProjectResponse(project);
            responses.add(resp);
        }

        return responses;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponse> getProject(@PathVariable("id") UUID id) {
        Project project = project_dao.findById(id);
        if (project == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new ProjectResponse(project));
    }

    @PutMapping("/{id}")
    public void updateProject(@PathVariable("id") UUID id, @RequestBody ProjectRequest req) {
        if (req.getProjectName() == null || req.getProjectName().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid request");
        }

        req.setProjectId(id);
        project_dao.update(req);
    }

    @GetMapping("/employee/{id}")
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

    @PostMapping("/{id}/employees")
    public void addProjectEmployee(@PathVariable("id") UUID project_id, @RequestBody ProjectSetupRequest req) {
        if (req.getEmployeeId() == null || req.getRoleId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid request");
        }

        req.setProjectId(project_id);
        project_setup_dao.insert(req);

        EmployeeRoleHistoryRequest history_req = new EmployeeRoleHistoryRequest(
                req.getEmployeeId(),
                project_id,
                req.getRoleId(),
                LocalDate.now(),
                null
        );
        role_history_dao.insert(history_req);
    }

    @PostMapping
    public void addProject(@RequestBody ProjectRequest req) {
        if(req.getProjectName() == null ||
                req.getProjectName().isEmpty() ||
                req.getStartDate() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid request");
        }

        project_dao.insert(req);
    }

    @DeleteMapping("/{id}/{employee_id}")
    public  void delProjectEmployee(@PathVariable("id")UUID project_id,
                                    @PathVariable("employee_id")UUID employee_id) {
        ProjectSetupRequest req = new ProjectSetupRequest(employee_id, project_id, null);
        project_setup_dao.delete(req);
    }
}
