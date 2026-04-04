package y.cloud.java.dao_models;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import y.cloud.java.salary_statement_models.*;

import y.cloud.java.dto_models.EmployeeRequest;
import y.cloud.java.models_utils.NotStated;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.time.LocalDate;

@Repository
public class EmployeeDAO implements EmployeeInterfaceDAO{

    @PersistenceContext
    private EntityManager entityManager;

    private CriteriaBuilder cb;

    @PostConstruct
    public void init() {
        cb = entityManager.getCriteriaBuilder();
    }

    @Override
    public Employee findById(UUID id) {
        return entityManager.find(Employee.class, id);
    }

    @Override
    public List<Employee> findByParams(EmployeeRequest req) {
        CriteriaQuery<Employee> query = cb.createQuery(Employee.class);
        Root<Employee> root = query.from(Employee.class);
        List<Predicate> predicates = new ArrayList<>();

        if(!req.getName().isEmpty()) {
            predicates.add(cb.equal(root.get("name"), req.getName()));
        }
        if(!req.getSurname().isEmpty()) {
            predicates.add(cb.equal(root.get("surname"), req.getSurname()));
        }
        if(!req.getMiddleName().isEmpty()) {
            predicates.add(cb.equal(root.get("middle_name"), req.getMiddleName()));
        }
        if(!req.getBirthDate().equals(NotStated.REL.value())) {
            predicates.add(cb.equal(root.get("birth_date"), req.getBirthDate()));
        }
        if(req.getWorkExperience() != (int)NotStated.PRIMITIVE.value()) {
            predicates.add(cb.equal(root.get("work_experience"), req.getWorkExperience()));
        }
        if(!req.getPostId().equals((UUID)NotStated.ID.value())) {
            predicates.add(cb.equal(root.get("post_id").get("post_id"), req.getPostId()));
        }

        predicates.add(cb.equal(root.get("fired"), req.getFired()));

        query.select(root).where(cb.and(predicates.toArray(new Predicate[0])));

        return entityManager.createQuery(query).getResultList();
    }

    /*public Employee findByParams(EmployeeRequest req) {
        CriteriaQuery<Employee> query = cb.createQuery(Employee.class);
        Root<Employee> root = query.from(Employee.class);

        for(Field field : EmployeeRequest.class.getDeclaredFields()) {
            if(!field.getType().isPrimitive() && field.get(req) != NotStated.REL.value()) {

            }
        }
    }*/

    @Override
    public List<Employee> findAll() {
        return entityManager
                .createQuery("SELECT e FROM Employee e", Employee.class)
                .getResultList();
    }

    @Override
    public List<Employee> findAllEmployeeByProject(UUID project_id) {
        return entityManager
                .createQuery(
                        "SELECT ps.employee_id FROM ProjectSetup ps " +
                                "WHERE ps.project_id.project_id = :project_id",
                        Employee.class
                )
                .setParameter("project_id", project_id)
                .getResultList();
    }

    @Transactional
    @Override
    public UUID insert(EmployeeRequest emp_req) {
        Employee emp = new Employee(emp_req);
        emp.setPostId(entityManager.find(Post.class, emp_req.getPostId()));
        entityManager.persist(emp);

        EmployeePostHistory eph = new EmployeePostHistory();
        Post post = entityManager.find(Post.class, emp.getPostId());

        eph.setId(emp, post);
        eph.setStartDate(LocalDate.now());
        eph.setEndDate(null);
        entityManager.persist(eph);

        return emp.getId();
    }

    @Transactional
    @Override
    public Employee update(EmployeeRequest emp_req) {
        Employee cur_emp = entityManager.find(Employee.class, emp_req.getEmployeeId());
        if(emp_req.getPostId() != cur_emp.getPostId()) {
            UUID prev_post_id = entityManager.find(Employee.class, emp_req.getEmployeeId()).getPostId();
            EmployeePostHistory prev_emp_post_history = entityManager.find(EmployeePostHistory.class,
                                                                           new EmployeePostHistoryPK(emp_req.getEmployeeId(), prev_post_id));
            LocalDate local_date = LocalDate.now();
            prev_emp_post_history.setEndDate(local_date);
        }

        cur_emp.setName(emp_req.getName());
        cur_emp.setSurname(emp_req.getSurname());
        cur_emp.setMiddleName(emp_req.getMiddleName());
        cur_emp.setBirtDate(emp_req.getBirthDate());
        cur_emp.setWorkExperience(emp_req.getWorkExperience());

        return cur_emp;
    }

    @Transactional
    @Override
    public void fire(UUID id) {
        Employee emp = entityManager.find(Employee.class, id);
        emp.setFired(true);

        EmployeePostHistory emp_post_history = entityManager.find(EmployeePostHistory.class, new EmployeePostHistoryPK(emp.getId(), emp.getPostId()));
        emp_post_history.setEndDate(LocalDate.now());
    }

    @Transactional
    @Override
    public void updatePost(UUID employee_id, UUID post_id) {
        Employee emp = findById(employee_id);
        if (emp.getPostId().equals(post_id)) {
           return;
        }

        EmployeePostHistory eph = entityManager.find(EmployeePostHistory.class,
                new EmployeePostHistoryPK(employee_id, emp.getPostId()));

        Post post = entityManager.find(Post.class, post_id);
        emp.setPostId(post);

        eph.setEndDate(LocalDate.now());

        eph = new EmployeePostHistory();
        eph.setStartDate(LocalDate.now());
        eph.setEndDate(null);
        eph.setId(emp, post);
        entityManager.persist(eph);
    }

    @Transactional
    @Override
    public EmployeePostHistory findEmployeePostHistoryById(EmployeePostHistoryPK id) {
        return entityManager.find(EmployeePostHistory.class, id);
    }

    @Transactional
    @Override
    public List<EmployeePostHistory> findAllEmployeePostHistory(UUID employee_id) {
        CriteriaQuery<EmployeePostHistory> query = cb.createQuery(EmployeePostHistory.class);
        Root<EmployeePostHistory> root = query.from(EmployeePostHistory.class);

        query.select(root).where(cb.equal(root.get("employee_id").get("employee_id"), employee_id));
        return entityManager.createQuery(query).getResultList();
    }

    @Transactional
    @Override
    public List<EmployeeRoleHistory> findAllEmployeeRoleHistory(UUID employee_id) {
        CriteriaQuery<EmployeeRoleHistory> query = cb.createQuery(EmployeeRoleHistory.class);
        Root<EmployeeRoleHistory> root = query.from(EmployeeRoleHistory.class);

        query.select(root).where(cb.equal(root.get("employee_id").get("employee_id"), employee_id));
        return entityManager.createQuery(query).getResultList();
    }

    @Transactional
    @Override
    public List<Payout> findAllEmployeeBonus(UUID employee_id) {
        CriteriaQuery<Payout> query = cb.createQuery(Payout.class);
        Root<Payout> root = query.from(Payout.class);
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(cb.equal(root.get("employee_id").get("employee_id"), employee_id));
        predicates.add(cb.equal(root.get("payout_type_id").get("payout_type"), "Bonus"));
        query.select(root).where(cb.and(predicates.toArray(new Predicate[0])) );
        return entityManager.createQuery(query).getResultList();
    }

    @Transactional
    @Override
    public List<Payout> findAllPayouts(UUID employee_id) {
        CriteriaQuery<Payout> query = cb.createQuery(Payout.class);
        Root<Payout> root = query.from(Payout.class);

        query.select(root).where(cb.equal(root.get("employee_id").get("employee_id"), employee_id));
        return entityManager.createQuery(query).getResultList();
    }


}
