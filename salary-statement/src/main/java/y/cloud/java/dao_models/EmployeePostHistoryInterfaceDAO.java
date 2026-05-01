package y.cloud.java.dao_models;

import y.cloud.java.dto_models.EmployeePostHistoryRequest;
import y.cloud.java.salary_statement_models.EmployeePostHistory;
import y.cloud.java.salary_statement_models.EmployeePostHistoryPK;

import java.util.List;

public interface EmployeePostHistoryInterfaceDAO extends
        BaseInterfaceDAO<EmployeePostHistory, EmployeePostHistoryRequest, EmployeePostHistoryPK> {
    List<EmployeePostHistory> findByParams(EmployeePostHistoryRequest req);
}
