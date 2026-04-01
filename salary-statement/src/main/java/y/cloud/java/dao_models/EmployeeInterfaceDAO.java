package y.cloud.java.dao_models;

import y.cloud.java.dto_models.EmployeeRequest;

import y.cloud.java.salary_statement_models.Employee;

import java.util.List;
import java.util.UUID;

interface EmployeeInterfaceDAO extends BaseInterfaceDAO<Employee, EmployeeRequest, UUID>{
    void fire(UUID id);
    List<Employee> findByParams(EmployeeRequest req);
}