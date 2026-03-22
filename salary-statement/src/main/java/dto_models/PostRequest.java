package dto_models;

import java.util.UUID;

public class PostRequest {
    private UUID post_id;
    private String post_name;
    private Double payout_value;

    public UUID getId() {
        return post_id;
    }

    public void setId(UUID post_id) {
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
