package salary_statement_models;

import java.time.LocalDate;
import java.util.UUID;


public class Employee {
    private UUID employee_id;
    private String name;
    private String surname;
    private String middle_name;
    private LocalDate birth_date;
    private int work_experience;
    private Post post_id;
    private boolean fired;

    public Employee(){}
}