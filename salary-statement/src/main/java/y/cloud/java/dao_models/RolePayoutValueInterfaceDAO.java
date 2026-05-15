package y.cloud.java.dao_models;

import y.cloud.java.dto_models.RolePayoutValueRequest;
import y.cloud.java.salary_statement_models.RolePayoutValue;
import y.cloud.java.salary_statement_models.RolePayoutValuePK;

import java.util.List;

public interface RolePayoutValueInterfaceDAO extends BaseInterfaceDAO<RolePayoutValue, RolePayoutValueRequest, RolePayoutValuePK>{
    List<RolePayoutValue> findByParams(RolePayoutValueRequest req);
}
