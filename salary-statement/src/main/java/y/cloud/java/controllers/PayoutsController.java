package y.cloud.java.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import y.cloud.java.dao_models.*;
import y.cloud.java.dto_models.*;
import y.cloud.java.salary_statement_models.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/payouts")
public class PayoutsController {
    @Autowired
    private PayoutTypeDAO payout_types_dao;

    @Autowired
    private PostDAO post_dao;

    @Autowired
    private WorkExperiencePayoutValueDAO work_exp_dao;

    @Autowired
    private RolePayoutValueDAO role_payout_dao;

    @Autowired
    private PayoutDAO payout_dao;

    @Autowired
    private ProjectDAO project_dao;

    @Autowired
    private RoleDAO role_dao;

    @GetMapping
    public List<PayoutTypeResponse> getAllPayoutTypes() {
        List<PayoutType> types = payout_types_dao.findAll();
        List<PayoutTypeResponse> responses = new ArrayList<>();

        for(PayoutType payout_type : types) {
            PayoutTypeResponse resp = new PayoutTypeResponse(payout_type);
            responses.add(resp);
        }

        return responses;
    }

    @GetMapping("/policies/roles/{project_id}")
    public List<RolePayoutValueResponse> getRolesPayoutsByProject(@PathVariable("project_id") UUID project_id) {
        RolePayoutValueRequest req = new RolePayoutValueRequest();
        req.setProjectId(project_id);
        List<RolePayoutValue> values = role_payout_dao.findByParams(req);
        List<RolePayoutValueResponse> responses = new ArrayList<>();

        for(RolePayoutValue value : values) {
            RolePayoutValueResponse resp = new RolePayoutValueResponse(value);
            resp.setRoleName(role_dao.findById(resp.getRoleId()).getRoleName());
            resp.setProjectName(project_dao.findById(project_id).getProjectName());
            responses.add(resp);
        }

        return responses;
    }

    @PostMapping("/policies/roles")
    public void addRolesPayoutsByProject(@RequestBody RolePayoutValueRequest req) {
        if (req.getProjectId() == null || req.getRoleId() == null
                || req.getValue() == null || req.getValue() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid request");
        }

        PayoutTypeRequest type_req = new PayoutTypeRequest();
        type_req.setPayoutType("По проектам");
        payout_types_dao.insert(type_req);

        role_payout_dao.insert(req);
    }

    @PutMapping("/policies/roles/{project_id}/{role_id}")
    public void updateRolesPayoutsByProject(@PathVariable("project_id") UUID project_id,
                                            @PathVariable("role_id") UUID role_id,
                                            @RequestBody RolePayoutValueRequest req) {
        if (req.getValue() == null || req.getValue() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid request");
        }

        req.setProjectId(project_id);
        req.setRoleId(role_id);
        role_payout_dao.update(req);
    }

    @GetMapping("/policies/posts")
    public List<PostResponse> getPostsPayoutsPolicies() {
        List<Post> posts = post_dao.findAll();
        List<PostResponse> responses = new ArrayList<>();

        for(Post post : posts) {
            PostResponse resp = new PostResponse(post);
            responses.add(resp);
        }

        return responses;
    }

    @PostMapping("/policies/posts")
    public void addPostsPayoutsPolicies(@RequestBody PostRequest req) {
        if (req.getPostName() == null || req.getPostName().isEmpty()
                || req.getPayoutValue() == null || req.getPayoutValue() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid request");
        }

        post_dao.insert(req);
    }

    @PutMapping("/policies/posts/{post_id}")
    public void updatePostsPayoutsPolicies(@PathVariable("post_id") UUID post_id,
                                           @RequestBody PostRequest req) {
        if (req.getPostName() == null || req.getPostName().isEmpty()
                || req.getPayoutValue() == null || req.getPayoutValue() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid request");
        }

        req.setPostId(post_id);
        post_dao.update(req);
    }

    @GetMapping("/policies/work_experience")
    public List<WorkExperiencePayoutValueResponse> getWorkExperiencePayoutsPolicies() {
        List<WorkExperiencePayoutValue> values = work_exp_dao.findAll();
        List<WorkExperiencePayoutValueResponse> responses = new ArrayList<>();

        for(WorkExperiencePayoutValue value : values) {
            WorkExperiencePayoutValueResponse resp = new WorkExperiencePayoutValueResponse(value);
            responses.add(resp);
        }

        return responses;
    }

    @PostMapping("/policies/work_experience")
    public void addWorkExperiencePayoutsPolicies(@RequestBody WorkExperiencePayoutValueRequest req) {
        if (req.getValue() == null || req.getValue() <= 0 || req.getWorkExperience() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid request");
        }

        PayoutTypeRequest type_req = new PayoutTypeRequest();
        type_req.setPayoutType("Стаж");
        payout_types_dao.insert(type_req);

        work_exp_dao.insert(req);
    }

    @PutMapping("/policies/work_experience/{experience_id}")
    public void updateWorkExperiencePayoutsPolicies(@PathVariable("experience_id") UUID experience_id,
                                                    @RequestBody WorkExperiencePayoutValueRequest req) {
        if (req.getValue() == null || req.getValue() <= 0 || req.getWorkExperience() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid request");
        }

        req.setExperienceId(experience_id);
        work_exp_dao.update(req);
    }

    @GetMapping("/policies/bonuses")
    public List<BonusPayoutValueResponse> getBonusPayoutsPolicies() {
        List<BonusPayoutValue> values = payout_dao.findAllBonusPayouts();
        List<BonusPayoutValueResponse> responses = new ArrayList<>();

        for(BonusPayoutValue value : values) {
            BonusPayoutValueResponse resp = new BonusPayoutValueResponse(value);
            resp.setPayoutType(payout_types_dao.findById(resp.getPayoutTypeId()).getPayoutType());
            responses.add(resp);
        }

        return responses;
    }

    @PostMapping("/policies/bonuses")
    public void addBonusPayoutsPolicies(@RequestBody BonusPayoutValueRequest req) {
        if (req.getPayoutType() == null || req.getPayoutType().isEmpty()
                || req.getValue() == null || req.getValue() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid request");
        }

        PayoutTypeRequest type_req = new PayoutTypeRequest();
        type_req.setPayoutType(req.getPayoutType());
        UUID payout_type_id = payout_types_dao.insert(type_req);

        req.setPayoutTypeId(payout_type_id);
        payout_dao.insertBonusPayout(req);
    }

    @PutMapping("/policies/bonuses/{payout_type_id}")
    public void updateBonusPayoutsPolicies(@PathVariable("payout_type_id") UUID payout_type_id,
                                           @RequestBody BonusPayoutValueRequest req) {
        if (req.getValue() == null || req.getValue() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid request");
        }

        req.setPayoutTypeId(payout_type_id);
        payout_dao.updateBonusPayout(req);
    }
}
