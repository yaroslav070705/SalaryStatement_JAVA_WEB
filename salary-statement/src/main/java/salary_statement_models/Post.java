package salary_statement_models;

import java.util.UUID;


public class Post {
    private UUID post_id;
    private String post_name;
    private Double payout_value;

    public UUID getId() {
        return post_id;
    }

    public String getPostName() {
        return post_name;
    }

    public Double getPayoutValue() {
        return payout_value;
    }
}
