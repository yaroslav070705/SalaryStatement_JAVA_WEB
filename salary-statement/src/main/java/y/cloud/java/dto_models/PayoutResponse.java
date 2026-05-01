package y.cloud.java.dto_models;

import y.cloud.java.salary_statement_models.Payout;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.UUID;

public class PayoutResponse {
    private UUID employee_id;
    private UUID payout_type_id;
    private LocalDate date;
    private Double value;

    public PayoutResponse() {}
    public PayoutResponse(Payout payout) {
        this.employee_id = payout.getId().getEmployeeId();
        this.payout_type_id = payout.getId().getPayoutTypeId();
        this.date = payout.getDate();
        this.value = payout.getValue();
    }
    public PayoutResponse(UUID employee_id, UUID payout_type_id, LocalDate date, Double value) {
        this.employee_id = employee_id;
        this.payout_type_id = payout_type_id;
        this.date = date;
        this.value = value;
    }

    public UUID getEmployeeId() {
        return employee_id;
    }
    public UUID getPayoutTypeId() {
        return payout_type_id;
    }
    public LocalDate getDate() {
        return date;
    }
    public Double getValue() {
        return value;
    }

    public void setEmployeeId(UUID employee_id) {
        this.employee_id = employee_id;
    }
    public void setPayoutTypeId(UUID payout_type_id) {
        this.payout_type_id = payout_type_id;
    }
    public void setDate(LocalDate date) { this.date = date; }
    public void setValue(Double value) {
        this.value = value;
    }
}
