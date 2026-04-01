package dao_models;

import dto_models.EmployeeRequest;

import salary_statement_models.Employee;

import java.util.List;
import java.util.UUID;

interface EmployeeInterfaceDAO extends BaseInterfaceDAO<Employee, EmployeeRequest, UUID>{
    void fire(UUID id);
    List<Employee> findByParams(EmployeeRequest req);
}