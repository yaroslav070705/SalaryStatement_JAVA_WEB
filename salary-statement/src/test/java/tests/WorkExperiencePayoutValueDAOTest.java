package tests;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import y.cloud.java.App;
import y.cloud.java.dto_models.WorkExperiencePayoutValueRequest;

import java.util.Objects;
import java.util.UUID;

@SpringBootTest(classes = App.class)
public class WorkExperiencePayoutValueDAOTest extends DaoTestSupport {

    @Test
    public void insertWorkExperiencePayoutValueDAOTest() {
        WorkExperiencePayoutValueRequest req = new WorkExperiencePayoutValueRequest();
        req.setWorkExperience(3);
        req.setValue(3000.0);

        UUID id = work_experience_payout_value_dao.insert(req);

        assert Objects.nonNull(work_experience_payout_value_dao.findById(id));
    }

    @Test
    public void updateWorkExperiencePayoutValueDAOTest() {
        WorkExperiencePayoutValueRequest req = new WorkExperiencePayoutValueRequest();
        req.setWorkExperience(4);
        req.setValue(4000.0);
        UUID id = work_experience_payout_value_dao.insert(req);

        req.setExperienceId(id);
        req.setWorkExperience(6);
        req.setValue(6000.0);

        assert Objects.equals(work_experience_payout_value_dao.update(req).getValue(), 6000.0);
    }

    @Test
    public void findAllWorkExperiencePayoutValueDAOTest() {
        WorkExperiencePayoutValueRequest req = new WorkExperiencePayoutValueRequest();
        req.setWorkExperience(8);
        req.setValue(8000.0);
        work_experience_payout_value_dao.insert(req);

        assert !work_experience_payout_value_dao.findAll().isEmpty();
    }
}
