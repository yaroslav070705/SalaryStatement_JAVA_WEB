package tests;

import org.springframework.beans.factory.annotation.Autowired;
import y.cloud.java.dao_models.EmployeeDAO;
import y.cloud.java.dao_models.EmployeePostHistoryDAO;
import y.cloud.java.dao_models.EmployeeRoleHistoryDAO;
import y.cloud.java.dao_models.PayoutDAO;
import y.cloud.java.dao_models.PayoutTypeDAO;
import y.cloud.java.dao_models.PostDAO;
import y.cloud.java.dao_models.ProjectDAO;
import y.cloud.java.dao_models.ProjectSetupDAO;
import y.cloud.java.dao_models.RoleDAO;
import y.cloud.java.dao_models.RolePayoutValueDAO;
import y.cloud.java.dao_models.WorkExperiencePayoutValueDAO;

public abstract class DaoTestSupport {
    @Autowired
    protected EmployeeDAO employee_dao;
    @Autowired
    protected EmployeePostHistoryDAO employee_post_history_dao;
    @Autowired
    protected EmployeeRoleHistoryDAO employee_role_history_dao;
    @Autowired
    protected PayoutDAO payout_dao;
    @Autowired
    protected PayoutTypeDAO payout_type_dao;
    @Autowired
    protected PostDAO post_dao;
    @Autowired
    protected ProjectDAO project_dao;
    @Autowired
    protected ProjectSetupDAO project_setup_dao;
    @Autowired
    protected RoleDAO role_dao;
    @Autowired
    protected RolePayoutValueDAO role_payout_value_dao;
    @Autowired
    protected WorkExperiencePayoutValueDAO work_experience_payout_value_dao;
}
