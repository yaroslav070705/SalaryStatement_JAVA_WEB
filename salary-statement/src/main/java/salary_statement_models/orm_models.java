

class EmployeePostHistoryPK {
    private UUID employee_id;
    private UUID post_id;
}




class PayoutPK {
    @Id
    private UUID employee_id;
    @Id
    private UUID payout_type_id;
}


class ProjectSetupPK {
    private UUID employee_id;
    private UUID project_id;
}




class RolePayoutValuePK {
    private UUID project_id;
    private UUID role_id;
}




class EmployeeRoleHistoryPK {
    private UUID employee_id;
    private UUID project_id;
}




