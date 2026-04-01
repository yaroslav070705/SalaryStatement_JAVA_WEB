package y.cloud.java.salary_statement_models;

import y.cloud.java.dto_models.PostRequest;

import java.util.UUID;


public class Post {
    private UUID post_id;
    private String post_name;
    private Double payout_value;

    public Post() {}
    public Post(PostRequest req) {
        this.post_name = req.getPostName();
        this.payout_value =req.getPayoutValue();
    }

    public UUID getId() {
        return post_id;
    }

    public void setId(UUID post_id){
        this.post_id = post_id;
    }

    public String getPostName() {
        return post_name;
    }

    public void setPostName(String post_name) {
        this.post_name = post_name;
    }

    public Double getPayoutValue() {
        return payout_value;
    }
    public void setPayoutValue(Double payout_value) {
        this.payout_value = payout_value;
    }
}
