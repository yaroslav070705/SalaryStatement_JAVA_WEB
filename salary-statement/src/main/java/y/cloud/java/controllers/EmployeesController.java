package y.cloud.java.controllers;

import net.bytebuddy.implementation.bytecode.constant.DefaultValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import y.cloud.java.dao_models.EmployeeDAO;
import y.cloud.java.dao_models.PayoutDAO;
import y.cloud.java.dao_models.PostDAO;
import y.cloud.java.dto_models.EmployeeRequest;
import y.cloud.java.dto_models.EmployeeResponse;
import y.cloud.java.dto_models.PayoutRequest;
import y.cloud.java.dto_models.PayoutResponse;
import y.cloud.java.salary_statement_models.Employee;
import y.cloud.java.salary_statement_models.Payout;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/employees")
public class EmployeesController {
    @Autowired
    private EmployeeDAO emp_dao;

    @Autowired
    private PostDAO post_dao;

    @Autowired
    private PayoutDAO payout_dao;

    @GetMapping
    public List<EmployeeResponse> getAllEmployees(@RequestParam(required = false) String name,
                                                  @RequestParam(required = false) String surname,
                                                  @RequestParam(required = false) String middle_name,
                                                  @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate birth_date,
                                                  @RequestParam(required = false) UUID post_id,
                                                  @RequestParam(required = false) boolean is_bonused) {
        EmployeeRequest req = new EmployeeRequest();
        req.setName(name);
        req.setSurname(surname);
        req.setMiddleName(middle_name);
        req.setBirthDate(birth_date);
        req.setPostId(post_id);
        req.setWorkExperience(-1);

        List<Employee> emp_list = emp_dao.findByParams(req);
        List<EmployeeResponse> resp_list = new ArrayList<>();

        for(Employee emp : emp_list) {
            EmployeeResponse resp = new EmployeeResponse(emp);
            resp.setPostName(post_dao.getPostName(emp.getPostId()));
            resp_list.add(resp);
        }

        return resp_list;
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> getEmployee(@PathVariable("id") UUID id) {
        Employee employee = emp_dao.findById(id);
        if (employee == null) {
            return ResponseEntity.notFound().build();
        }

        EmployeeResponse resp = new EmployeeResponse(employee);
        if (employee.getPostId() != null) {
            resp.setPostName(post_dao.getPostName(employee.getPostId()));
        }

        return ResponseEntity.ok(resp);
    }

    @GetMapping("/{id}/payouts")
    public List<PayoutResponse> getEmployeePayouts(@PathVariable("id") UUID id,
                                                   @RequestParam(required = false) UUID payout_type_id,
                                                   @RequestParam(defaultValue = "0") Double max_value,
                                                   @RequestParam(defaultValue = "0") Double min_value,
                                                   @RequestParam(required = false) LocalDate end_date,
                                                   @RequestParam(required = false) LocalDate start_date) {
        PayoutRequest req = new PayoutRequest(id, null, null, null);

        if (payout_type_id != null) {
            req.setPayoutTypeId(payout_type_id);
        }

        List<Payout> payouts = payout_dao.findByParams(req);
        List<PayoutResponse> responses = new ArrayList<>();

        for(Payout payout : payouts) {
            PayoutResponse resp = new PayoutResponse(payout);
            responses.add(resp);
        }

        return responses;
    }

    @PostMapping
    public void addEmployee(@RequestBody EmployeeRequest req) {
        emp_dao.insert(req);
    }

    @PostMapping("/{id}/fire")
    public void fireEmployee(@PathVariable("id") UUID id) {
        emp_dao.fire(id);
    }

    @PutMapping("/{id}")
    public void updateEmployee(@PathVariable("id") UUID id, @RequestBody EmployeeRequest req) {
        req.setEmployeeId(id);
        if(req.getName() == null || req.getSurname() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid request");
        }

        emp_dao.update(req);
    }
}
