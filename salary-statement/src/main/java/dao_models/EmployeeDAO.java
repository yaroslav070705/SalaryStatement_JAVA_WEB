package dao_models;

import salary_statement_models.Employee;
import salary_statement_models.EmployeePostHistory;
import salary_statement_models.EmployeePostHistoryPK;
import salary_statement_models.Post;

import dto_models.EmployeeRequest;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.time.LocalDate;

@Repository
public class EmployeeDAO implements EmployeeInterfaceDAO{

    @PersistenceContext
    private EntityManager entityManager;

    public Employee findById(UUID id) {
        return entityManager.find(Employee.class, id);
    }

    public List<Employee> findAll() {
        return entityManager
                .createQuery("SELECT e FROM Employee e", Employee.class)
                .getResultList();
    }

    @Transactional
    @Override
    public void insert(EmployeeRequest emp_req) {
        Employee emp = new Employee(emp_req);
        emp.setPostId(entityManager.find(Post.class, emp_req.getPostId()));
        entityManager.persist(emp);

        EmployeePostHistory eph = new EmployeePostHistory();
        Post post = entityManager.find(Post.class, emp.getPostId());
        eph.setId(emp, post);
        eph.setStartDate(LocalDate.now());
        eph.setEndDate(null);
        entityManager.persist(eph);
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