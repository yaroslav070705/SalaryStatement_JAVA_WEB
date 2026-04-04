package tests;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import y.cloud.java.App;
import y.cloud.java.dto_models.PayoutTypeRequest;

import java.util.Objects;
import java.util.UUID;

@SpringBootTest(classes = App.class)
public class PayoutTypeDAOTest extends DaoTestSupport {

    @Test
    public void insertPayoutTypeDAOTest() {
        PayoutTypeRequest req = new PayoutTypeRequest();
        req.setPayoutType("PayoutTypeDAO Insert");

        UUID id = payout_type_dao.insert(req);

        assert Objects.nonNull(payout_type_dao.findById(id));
    }

    @Test
    public void updatePayoutTypeDAOTest() {
        PayoutTypeRequest req = new PayoutTypeRequest();
        req.setPayoutType("PayoutTypeDAO Update");
        UUID id = payout_type_dao.insert(req);
        req.setPayoutType("PayoutTypeDAO Updated");
        req.setPayoutTypeId(id);

        assert Objects.equals(payout_type_dao.update(req).getPayoutType(), "PayoutTypeDAO Updated");
    }

    @Test
    public void findAllPayoutTypeDAOTest() {
        PayoutTypeRequest req = new PayoutTypeRequest();
        req.setPayoutType("PayoutTypeDAO FindAll");
        payout_type_dao.insert(req);

        assert !payout_type_dao.findAll().isEmpty();
    }
}
