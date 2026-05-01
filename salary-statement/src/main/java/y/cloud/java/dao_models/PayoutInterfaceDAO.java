package y.cloud.java.dao_models;

import y.cloud.java.dto_models.PayoutRequest;
import y.cloud.java.salary_statement_models.Payout;
import y.cloud.java.salary_statement_models.PayoutPK;

import java.util.List;

interface PayoutInterfaceDAO extends BaseInterfaceDAO<Payout, PayoutRequest, PayoutPK> {
    List<Payout> findByParams(PayoutRequest req);
}