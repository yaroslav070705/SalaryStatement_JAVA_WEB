package dao_models;

import dto_models.PayoutTypeRequest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import salary_statement_models.PayoutType;

import java.util.List;
import java.util.UUID;

@Repository
public class PayoutTypeDAO implements PayoutTypeInterfaceDAO{
    @PersistenceContext
    private EntityManager entityManager;

    public PayoutType findById(UUID id) {
        return entityManager.find(PayoutType.class, id);
    }

    public List<PayoutType> findAll() {
        return entityManager
                .createQuery("SELECT p FROM PayoutType p", PayoutType.class)
                .getResultList();
    }

    @Transactional
    @Override
    public void insert(PayoutTypeRequest req) {
        PayoutType entity = new PayoutType(req);

        entityManager.persist(entity);
    }

    @Transactional
    @Override
    public void update(PayoutTypeRequest req) {
        PayoutType entity = entityManager.find(PayoutType.class, req.getPayoutTypeId());

        entity.setPayoutType(req.getPayoutType());
    }

    @Transactional
    @Override
    public void delete(UUID id) {
    }
}
