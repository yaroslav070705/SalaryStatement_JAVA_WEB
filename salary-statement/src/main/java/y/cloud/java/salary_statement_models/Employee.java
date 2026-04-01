package y.cloud.java.salary_statement_models;

import y.cloud.java.dto_models.EmployeeRequest;

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

    public Employee(EmployeeRequest req){
        name = req.getName();
        surname = req.getSurname();
        middle_name = req.getMiddleName();
        birth_date = req.getBirthDate();
        work_experience = req.getWorkExperience();
        fired = req.getFired();
    }

    public UUID getId () {
        return employee_id;
    }

    public void setId(UUID employee_id){
        this.employee_id = employee_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname){
        this.surname = surname;
    }

    public String getMiddleName() {
        return middle_name;
    }

    public void setMiddleName(String middle_name){
        this.middle_name = middle_name;
    }

    public LocalDate getBirthDate() {
        return  birth_date;
    }

    public void setBirtDate(LocalDate birth_date) {
        this.birth_date = birth_date;
    }

    public int getWorkExperience() {
        return work_experience;
    }

    public void setWorkExperience(int work_experience){
        this.work_experience = work_experience;
    }

    public UUID getPostId() {
        return post_id.getId();
    }

    public void setPostId(Post post_id) {
        this.post_id = post_id;
    }

    public boolean getFired () {
        return fired;
    }

    public void setFired(boolean fired) {
        this.fired = fired;
    }
}