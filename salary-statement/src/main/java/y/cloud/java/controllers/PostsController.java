package y.cloud.java.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import y.cloud.java.dao_models.EmployeePostHistoryDAO;
import y.cloud.java.dao_models.PostDAO;
import y.cloud.java.dto_models.EmployeePostHistoryRequest;
import y.cloud.java.dto_models.EmployeePostHistoryResponse;
import y.cloud.java.salary_statement_models.EmployeePostHistory;
import y.cloud.java.salary_statement_models.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/posts")
public class PostsController {
    @Autowired
    private PostDAO post_dao;

    @Autowired
    private EmployeePostHistoryDAO post_history_dao;

    @GetMapping
    public List<Post> getAllPosts() {
        return post_dao.findAll();
    }

    @GetMapping("/history/{id}")
    public List<EmployeePostHistoryResponse> getEmployeePostHistory(@PathVariable("id") UUID employee_id) {
        EmployeePostHistoryRequest req = new EmployeePostHistoryRequest(employee_id,
                                                                 null,
                                                               null,
                                                                null);
        List<EmployeePostHistory> emp_post_hist_list = post_history_dao.findByParams(req);
        List<EmployeePostHistoryResponse> responses = new ArrayList<>();
        for(EmployeePostHistory eph : emp_post_hist_list) {
            EmployeePostHistoryResponse resp = new EmployeePostHistoryResponse(eph);
            resp.setPostName(post_dao.getPostName(resp.getPostId()));
            responses.add(resp);
        }

        return responses;
    }
}
