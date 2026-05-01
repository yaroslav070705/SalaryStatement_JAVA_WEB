package y.cloud.java.dao_models;

import y.cloud.java.dto_models.EmployeeRoleHistoryRequest;
import y.cloud.java.salary_statement_models.EmployeeRoleHistory;
import y.cloud.java.salary_statement_models.EmployeeRoleHistoryPK;

import java.util.List;

public interface EmployeeRoleHistoryInterfaceDAO extends BaseInterfaceDAO<EmployeeRoleHistory, EmployeeRoleHistoryRequest, EmployeeRoleHistoryPK>{
    List<EmployeeRoleHistory> findByParams(EmployeeRoleHistoryRequest req);
}
