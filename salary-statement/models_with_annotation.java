package salary_statement_models;

import jakarta.persistence.*;
import java.util.UUID;
import java.time.LocalDate;
import java.lang.String;

@Entity(name="Employees")
public static class Employee {
    @Id
    private UUID employee_id;

    private String name;
    private String surname;
    private String middle_name;
    private LocalDate birth_date;
    private int work_experience;
    private UUID post_id;
    private boolean fired;

    public Employee(){}
}


@Entity(name="Posts")
public static class Post {
    @Id
    private UUID post_id;

    private String post_name;
    private BigDecimal payout_value;
}


public static class EmployeePostHistoryPK {
    @Id
    private UUID employee_id;
    @id
    private UUID post_id;
}

@Entity(name="Employees_Posts_History")
@IdClass(EmployeePostHistoryPK.class)
public static class EmployeePostHistory {
    @Id
    private UUID employee_id;
    @id
    private UUID post_id;

    private LocalDate start_date;
    private LocalDate end_date;
}


@Entity(name="Payout_Types")
public static class PayoutType {
    @Id
    private UUID payout_type_id;

    private String payout_type;
}


public static class PayoutPK {
    @Id
    private UUID employee_id;
    @Id
    private UUID payout_type_id;
}

@Entity(name="Payouts")
@IdClass(PayoutPK.class)
public static class Payout {
    @Id
    private UUID employee_id;
    @Id
    private UUID payout_type_id;

    private LocalDate date;
    private BigDecimal value;
}


@Entity(name="Roles")
public static class Role {
    @Id
    private UUID role_id;

    private String role_name;
}


@Entity(name="Projects")
public static class Project {
    @Id
    private UUID project_id;

    private String project_name;
    private LocalDate start_date;
    private LocalDate end_date;
}


public static class ProjectSetupPK {
    @Id
    private UUID employee_id;
    @Id
    private UUID project_id;
}

@Entity(name="Projects_Setup")
@IdClass(ProjectSetupPK.class)
public static class ProjectSetup {
    @Id
    private UUID employee_id;
    @Id
    private UUID project_id;

    private UUID role_id;
}


public static class RolePayoutValuePK {
    @Id
    private UUID project_id;
    @Id
    private UUID role_id;
}

@Entity(name="Role_Payout_Value")
@IdClass(RolePayoutValuePK.class)
public static class RolePayoutValue {
    @Id
    private UUID project_id;
    @Id
    private UUID role_id;

    private BigDecimal value;
}


public static EmployeeRoleHistoryPK {
    @Id
    private UUID employee_id;
    @Id
    private UUID project_id;
}

@Entity(name="Employees_Roles_History")
@IdClass(EmployeeRoleHistoryPK.class)
public static class EmployeeRoleHistory {
    @Id
    private UUID employee_id;
    @Id
    private UUID project_id;
    @Id
    private UUID role_id;

    private LocalDate start_date;
    private LocalDate end_date;
}


@Entity(name="Work_Experience_Payout_Value")
public static class WorkExperiencePayoutValue {
    @Id
    private UUID experience_id;

    private int work_experience;
    private BigDecimal value;
}