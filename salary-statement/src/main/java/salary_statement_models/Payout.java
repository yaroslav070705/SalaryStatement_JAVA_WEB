package salary_statement_models;

import java.time.LocalDate;


public class Payout {
    private Employee employee_id;
    private PayoutType payout_type_id;
    private LocalDate date;
    private Double value;
}
