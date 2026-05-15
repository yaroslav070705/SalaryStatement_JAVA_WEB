package y.cloud.java.dto_models;

import y.cloud.java.salary_statement_models.WorkExperiencePayoutValue;

import java.util.UUID;

public class WorkExperiencePayoutValueResponse {
    private UUID experience_id;
    private int work_experience;
    private Double value;

    public WorkExperiencePayoutValueResponse() {}
    public WorkExperiencePayoutValueResponse(WorkExperiencePayoutValue we_value) {
        this.experience_id = we_value.getId();
        this.value = we_value.getValue();
        this.work_experience = we_value.getWorkExperience();
    }

    public UUID getExperienceId() {
        return experience_id;
    }
    public int getWorkExperience() {
        return work_experience;
    }
    public Double getValue() {
        return value;
    }

    public void setExperienceId(UUID experience_id) { this.experience_id = experience_id; }
    public void setWorkExperience(int work_experience) {
        this.work_experience = work_experience;
    }
    public void setValue(Double value) {
        this.value = value;
    }
}
