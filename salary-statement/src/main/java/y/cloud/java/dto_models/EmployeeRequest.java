package y.cloud.java.dto_models;

import org.hibernate.jdbc.Expectation;

import java.time.LocalDate;
import java.util.UUID;

public class EmployeeRequest {

    private UUID employee_id;
    private String name;
    private String surname;
    private String middle_name;
    private LocalDate birth_date;
    private int work_experience;
    private UUID post_id;
    private boolean fired;

    public UUID getEmployeeId() {
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
        return birth_date;
    }
    public int getWorkExperience() {
        return work_experience;
    }
    public UUID getPostId() {
        return post_id;
    }
    public boolean getFired() {
        return fired;
    }



    public void setEmployeeId(UUID employee_id) {
        this.employee_id = employee_id;
    }
    public void setName(String name) { this.name = name; }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public void setMiddleName(String middle_name) {
        this.middle_name = middle_name;
    }
    public void setBirthDate(LocalDate birth_date) {
        this.birth_date = birth_date;
    }
    public void setWorkExperience(int work_experience) {
        this.work_experience = work_experience;
    }
    public void setPostId(UUID post_id) {
        this.post_id = post_id;
    }
    public void setFired(boolean fired) {
        this.fired = fired;
    }
}