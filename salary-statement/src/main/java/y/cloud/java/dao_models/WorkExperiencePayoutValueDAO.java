package y.cloud.java.dao_models;

import y.cloud.java.dto_models.WorkExperiencePayoutValueRequest;

import y.cloud.java.salary_statement_models.WorkExperiencePayoutValue;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
public class WorkExperiencePayoutValueDAO implements WorkExperiencePayoutValueInterfaceDAO{
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public WorkExperiencePayoutValue findById(UUID id) {
        return entityManager.find(WorkExperiencePayoutValue.class, id);
    }

    @Override
    public List<WorkExperiencePayoutValue> findAll() {
        return entityManager.createQuery("SELECT w FROM WorkExperiencePayoutValue w", WorkExperiencePayoutValue.class).getResultList();
    }

    @Transactional
    @Override
    public UUID insert(WorkExperiencePayoutValueRequest req) {
        WorkExperiencePayoutValue work_exp = new WorkExperiencePayoutValue(req);
        entityManager.persist(work_exp);

        return work_exp.getId();
    }

    @Transactional
    @Override
    public WorkExperiencePayoutValue update(WorkExperiencePayoutValueRequest work_exp_req) {
        WorkExperiencePayoutValue work_exp = entityManager.find(WorkExperiencePayoutValue.class, work_exp_req.getExperienceId());
        work_exp.setWorkExperience(work_exp_req.getWorkExperience());
        work_exp.setValue(work_exp_req.getValue());

        return work_exp;
    }
}
