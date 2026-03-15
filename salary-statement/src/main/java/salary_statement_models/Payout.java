package salary_statement_models;

import java.time.LocalDate;
import java.util.UUID;


public class Payout {
    private Employee employee_id;
    private PayoutType payout_type_id;
    private LocalDate date;
    private Double value;

    public UUID[] getId() {
        return new UUID[]{employee_id.getId(), payout_type_id.getId()};
    }

    public LocalDate getDate() {
        return date;
    }

    public Double getValue() {
        return value;
    }
}
