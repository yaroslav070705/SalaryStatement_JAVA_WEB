package salary_statement_models;

import java.util.UUID;


public class WorkExperiencePayoutValue {
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
}
