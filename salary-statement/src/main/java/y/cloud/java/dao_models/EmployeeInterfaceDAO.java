package y.cloud.java.dao_models;

import y.cloud.java.dto_models.EmployeeRequest;

import y.cloud.java.salary_statement_models.*;

import java.util.List;
import java.util.UUID;

interface EmployeeInterfaceDAO extends BaseInterfaceDAO<Employee, EmployeeRequest, UUID>{
    void fire(UUID id);
    List<Employee> findByParams(EmployeeRequest req);
    void updatePost(UUID employee, UUID post);
    EmployeePostHistory findEmployeePostHistoryById(EmployeePostHistoryPK id);
    List<EmployeePostHistory> findAllEmployeePostHistory(UUID employee_id);
    List<EmployeeRoleHistory> findAllEmployeeRoleHistory(UUID employee_id);
    List<Payout> findAllEmployeeBonus(UUID employee_id);
    List<Payout> findAllPayouts(UUID employee_id);
    List<Employee> findAllEmployeeByProject(UUID project_id);
}