package salary_statement_models;

import java.util.UUID;
import java.io.Serializable;

public class PayoutPK implements Serializable {
    private UUID employee_id;
    private UUID payout_type_id;
}