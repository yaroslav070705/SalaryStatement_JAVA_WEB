package dao_models;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import salary_statement_models.Employee;
import salary_statement_models.EmployeePostHistory;
import salary_statement_models.EmployeePostHistoryPK;
import salary_statement_models.Post;

import dto_models.EmployeeRequest;
import models_utils.NotStated;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.time.LocalDate;

@Repository
public class EmployeeDAO implements EmployeeInterfaceDAO{

    @PersistenceContext
    private EntityManager entityManager;

    private final CriteriaBuilder cb = entityManager.getCriteriaBuilder();

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
        if(req.getBirthDate() != NotStated.REL.value()) {
            predicates.add(cb.equal(root.get("birth_date"), req.getBirthDate()));
        }
        if(req.getWorkExperience() != (int)NotStated.PRIMITIVE.value()) {
            predicates.add(cb.equal(root.get("work_experience"), req.getWorkExperience()));
        }
        if(req.getPostId() != (UUID)NotStated.ID.value()) {
            predicates.add(cb.equal(root.get("post_id"), req.getPostId()));
        }

        predicates.add(cb.equal(root.get("fired"), req.getPostId()));

        query.select(root).where(cb.and(predicates));

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

    @Transactional
    @Override
    public UUID insert(EmployeeRequest emp_req) {
        Employee emp = new Employee(emp_req);
        emp.setPostId(entityManager.find(Post.class, emp_req.getPostId()));

        try {
            entityManager.persist(emp);
        } catch (Exception e) {
            emp.setId(null);
        }

        EmployeePostHistory eph = new EmployeePostHistory();
        Post post = null;
        try {
            post = entityManager.find(Post.class, emp.getPostId());
        } catch(Exception e) {
            System.out.print("No post with id");
        }

        eph.setId(emp, post);
        eph.setStartDate(LocalDate.now());
        eph.setEndDate(null);

        entityManager.persist(eph);


        return emp.getId();
    }

    @Transactional
    @Override
    public void update(EmployeeRequest emp_req) {
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
        cur_emp.setPostId(entityManager.find(Post.class, emp_req.getPostId()));
        cur_emp.setWorkExperience(emp_req.getWorkExperience());
        cur_emp.setFired(emp_req.getFired());
    }

    @Transactional
    @Override
    public void delete(UUID id) {
    }

    @Transactional
    @Override
    public void fire(UUID id) {
        Employee emp = entityManager.find(Employee.class, id);
        emp.setFired(true);

        EmployeePostHistory emp_post_history = entityManager.find(EmployeePostHistory.class, new EmployeePostHistoryPK(emp.getId(), emp.getPostId()));
        emp_post_history.setEndDate(LocalDate.now());
    }
}