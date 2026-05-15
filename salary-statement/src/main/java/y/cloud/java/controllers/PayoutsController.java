package y.cloud.java.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
