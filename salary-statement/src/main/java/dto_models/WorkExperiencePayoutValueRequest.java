package dto_models;

import java.util.UUID;

public class WorkExperiencePayoutValueRequest {
    private UUID experience_id;
    private int work_experience;
    private Double value;

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
