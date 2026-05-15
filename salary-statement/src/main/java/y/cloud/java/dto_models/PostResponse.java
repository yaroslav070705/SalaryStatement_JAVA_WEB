package y.cloud.java.dto_models;

import y.cloud.java.salary_statement_models.Post;

import java.util.UUID;

public class PostResponse {
    private UUID post_id;
    private String post_name;
    private Double payout_value;

    public PostResponse() {}
    public PostResponse(Post post) {
        this.post_id = post.getId();
        this.post_name = post.getPostName();
        this.payout_value = post.getPayoutValue();
    }

    public UUID getPostId() { return post_id; }
    public String getPostName() {
        return post_name;
    }
    public Double getPayoutValue() {
        return payout_value;
    }


    public void setPostId(UUID post_id) {
        this.post_id = post_id;
    }
    public void setPostName(String post_name) {
        this.post_name = post_name;
    }
    public void setPayoutValue(Double payout_value) {
        this.payout_value = payout_value;
    }
}
