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

    public UUID getId () {
        return employee_id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getMiddleName() {
        return middle_name;
    }

    public LocalDate getBirthDate() {
        return  birth_date;
    }

    public int getWorkExperience() {
        return work_experience;
    }

    public UUID getPostId() {
        return post_id.getId();
    }

    public boolean getFired () {
        return fired;
    }
}