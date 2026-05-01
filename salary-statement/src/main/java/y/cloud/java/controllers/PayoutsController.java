package y.cloud.java.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import y.cloud.java.dao_models.PayoutTypeDAO;
import y.cloud.java.dto_models.PayoutTypeResponse;
import y.cloud.java.salary_statement_models.PayoutType;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/payouts")
public class PayoutsController {
    @Autowired
    private PayoutTypeDAO payout_types_dao;

    @GetMapping
    public List<PayoutTypeResponse> getAllPayoutTypes() {
        List<PayoutType> types = payout_types_dao.findAll();
        List<PayoutTypeResponse> responses = new ArrayList<>();

        for(PayoutType payout_type : types) {
            PayoutTypeResponse resp = new PayoutTypeResponse(payout_type);
            responses.add(resp);
        }

        return responses;
    }
}
