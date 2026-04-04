package y.cloud.java.dao_models;

import y.cloud.java.dto_models.PayoutRequest;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import y.cloud.java.salary_statement_models.Employee;
import y.cloud.java.salary_statement_models.Payout;
import y.cloud.java.salary_statement_models.PayoutPK;
import y.cloud.java.salary_statement_models.PayoutType;

import java.util.List;

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
    public PayoutPK insert(PayoutRequest payout_req) {
        Employee emp = entityManager.find(Employee.class, payout_req.getEmployeeId());
        PayoutType payout_type = entityManager.find(PayoutType.class, payout_req.getPayoutTypeId());

        Payout payout = new Payout(payout_req);
        payout.setId(emp, payout_type);
        entityManager.persist(payout);

        return payout.getId();
    }

    @Transactional
    @Override
    public Payout update(PayoutRequest payout_req) {
        Payout payout = entityManager.find(Payout.class, new PayoutPK(payout_req.getEmployeeId(), payout_req.getPayoutTypeId()));
        payout.setDate(payout_req.getDate());
        payout.setValue(payout_req.getValue());

        return payout;
    }

}
