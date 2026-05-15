package y.cloud.java.dao_models;

import y.cloud.java.dto_models.BonusPayoutValueRequest;
import y.cloud.java.dto_models.PayoutRequest;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import y.cloud.java.salary_statement_models.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class PayoutDAO implements PayoutInterfaceDAO {

    @PersistenceContext
    private EntityManager entityManager;

    CriteriaBuilder cb;

    @PostConstruct
    public void init() {
        cb = entityManager.getCriteriaBuilder();
    }

    @Override
    public Payout findById(PayoutPK id) {
        return entityManager.find(Payout.class, id);
    }

    @Override
    public List<Payout> findAll() {
        return entityManager
                .createQuery("SELECT p FROM Payout p", Payout.class)
                .getResultList();
    }

    @Override
    public List<BonusPayoutValue> findAllBonusPayouts() {
        return entityManager
                .createQuery("SELECT bpv FROM BonusPayoutValue bpv", BonusPayoutValue.class)
                .getResultList();
    }

    @Transactional
    @Override
    public UUID insertBonusPayout(BonusPayoutValueRequest req) {
        BonusPayoutValue bonus_payout = new BonusPayoutValue(req);
        entityManager.persist(bonus_payout);

        return bonus_payout.getPayoutTypeId();
    }

    @Transactional
    @Override
    public BonusPayoutValue updateBonusPayout(BonusPayoutValueRequest req) {
        BonusPayoutValue bonus_payout = entityManager.find(BonusPayoutValue.class, req.getPayoutTypeId());
        bonus_payout.setValue(req.getValue());

        return bonus_payout;
    }

    @Override
    public List<Payout> findByParams(PayoutRequest req) {
        CriteriaQuery<Payout> query = cb.createQuery(Payout.class);
        Root<Payout> root = query.from(Payout.class);
        List<Predicate> predicates = new ArrayList<>();

        if(req.getPayoutTypeId() != null) {
            predicates.add(cb.equal(root.get("payout_type_id").get("payout_type_id"), req.getPayoutTypeId()));
        }
        if(req.getEmployeeId() != null) {
            predicates.add(cb.equal(root.get("employee_id").get("employee_id"), req.getEmployeeId()));
        }

        query.select(root).where(cb.and(predicates.toArray(new Predicate[0])));

        return entityManager.createQuery(query).getResultList();
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
