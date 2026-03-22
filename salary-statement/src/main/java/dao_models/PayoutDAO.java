package dao_models;

import dto_models.PayoutRequest;
import salary_statement_models.*;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
import java.util.UUID;

@Repository
public class PayoutDAO implements PayoutInterfaceDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public Payout findById(PayoutPK id) {
        return entityManager.find(Payout.class, id);
    }

    public List<Payout> findAll() {
        return entityManager
                .createQuery("SELECT p FROM Payout p", Payout.class)
                .getResultList();
    }

    @Transactional
    @Override
    public void insert(PayoutRequest payout_req) {
        Employee emp = entityManager.find(Employee.class, payout_req.getEmployeeId());
        PayoutType payout_type = entityManager.find(PayoutType.class, payout_req.getPayoutTypeId());

        Payout payout = new Payout(payout_req);
        payout.setId(emp, payout_type);
        entityManager.persist(payout);
    }

    @Transactional
    @Override
    public void update(PayoutRequest payout_req) {
        Payout payout = entityManager.find(Payout.class, new PayoutPK(payout_req.getEmployeeId(), payout_req.getPayoutTypeId()));
        payout.setDate(payout_req.getDate());
        payout.setValue(payout_req.getValue());
    }

    @Transactional
    @Override
    public void delete(PayoutPK id) {
        Payout p = entityManager.find(Payout.class, id);
        entityManager.remove(p);
    }
}