package y.cloud.java.salary_statement_models;

import y.cloud.java.dto_models.WorkExperiencePayoutValueRequest;

import java.util.UUID;


public class WorkExperiencePayoutValue {
    private UUID experience_id;
    private int work_experience;
    private Double value;

    public WorkExperiencePayoutValue() {}
    public WorkExperiencePayoutValue(WorkExperiencePayoutValueRequest req) {
        this.work_experience = req.getWorkExperience();
        this.value = req.getValue();
    }

    public UUID getId() {
        return experience_id;
    }

    public void setId(UUID experience_id) {
        this.experience_id = experience_id;
    }

    public int getWorkExperience() {
        return work_experience;
    }

    public void setWorkExperience(int work_experience) {
        this.work_experience = work_experience;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
