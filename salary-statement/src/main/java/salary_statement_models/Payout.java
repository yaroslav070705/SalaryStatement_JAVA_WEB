package salary_statement_models;

import dto_models.PayoutRequest;

import java.time.LocalDate;
import java.util.UUID;


public class Payout {
    private Employee employee_id;
    private PayoutType payout_type_id;
    private LocalDate date;
    private Double value;

    public Payout() {}
    public Payout(PayoutRequest payout_req) {
        date = payout_req.getDate();
        value = payout_req.getValue();;
    }

    public UUID[] getId() {
        return new UUID[]{employee_id.getId(), payout_type_id.getId()};
    }

    public void setId(Employee employee_id, PayoutType payout_type_id) {
        this.employee_id = employee_id;
        this.payout_type_id = payout_type_id;
    }

    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getValue() {
        return value;
    }

    public void  setValue(Double value) {
        this.value = value;
    }
}
