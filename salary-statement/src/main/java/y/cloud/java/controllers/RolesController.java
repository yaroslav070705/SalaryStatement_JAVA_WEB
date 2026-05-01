package y.cloud.java.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import y.cloud.java.dao_models.EmployeeRoleHistoryDAO;
import y.cloud.java.dao_models.RoleDAO;
import y.cloud.java.dto_models.EmployeeRoleHistoryRequest;
import y.cloud.java.dto_models.EmployeeRoleHistoryResponse;
import y.cloud.java.salary_statement_models.EmployeeRoleHistory;
import y.cloud.java.salary_statement_models.Role;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/roles")
public class RolesController {
    @Autowired
    private RoleDAO role_dao;

    @Autowired
    private EmployeeRoleHistoryDAO role_history_dao;

    @GetMapping
    public List<Role> getAll() { return role_dao.findAll(); }

    @GetMapping("/history/{employee_id}")
    public List<EmployeeRoleHistoryResponse> getEmployeeRolesHistory(@PathVariable UUID employee_id) {
        EmployeeRoleHistoryRequest req = new EmployeeRoleHistoryRequest(employee_id,
                null, null,
                null, null);
        List<EmployeeRoleHistory> emp_post_hist_list = role_history_dao.findByParams(req);
        List<EmployeeRoleHistoryResponse> responses = new ArrayList<>();

        for(EmployeeRoleHistory erh : emp_post_hist_list) {
            EmployeeRoleHistoryResponse resp = new EmployeeRoleHistoryResponse(erh);
            resp.setRoleName(role_dao.findById(resp.getRoleId()).getRoleName());
            responses.add(resp);
        }

        return responses;
    }

}
