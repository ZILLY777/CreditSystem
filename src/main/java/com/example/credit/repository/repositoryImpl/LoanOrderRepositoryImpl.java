package com.example.credit.repository.repositoryImpl;

import com.example.credit.constants.OrderStatusEnum;
import com.example.credit.entity.tables.LoanOrder;
import com.example.credit.repository.repositoryInterfaces.LoanOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@RequiredArgsConstructor
@Repository
public class LoanOrderRepositoryImpl implements LoanOrderRepository {

    private final String FIND_ALL = "select * from loan_order";

    private static final String FIND_ORDER_BY_USER_ID = "SELECT * FROM loan_order WHERE user_id = ?";

    private static final String FIND_ORDER_BY_OFFER_ID = "SELECT * FROM loan_order WHERE order_id = ?";

    private static final String FIND_ORDER_BY_USER_ID_AND_TARIFF_ID = "SELECT * FROM loan_order WHERE user_id = ? AND tariff_id = ? ORDER BY id DESC";

    private static final String FIND_ORDER_BY_USER_ID_AND_ORDER_ID = "SELECT * FROM loan_order WHERE user_id = ? AND order_id = ?";

    private static final String SAVE_ORDER_QUERY = "INSERT INTO loan_order(order_id, user_id, tariff_id, credit_rating, status, time_insert, time_update) VALUES(?, ?, ?, ?, ?, ?, ?)";

    private static final String DELETE_ORDER_BY_USER_ID_AND_ORDER_ID = "DELETE FROM loan_order WHERE user_id = ? AND order_id = ?";

    private static final String FIND_ORDERS_BY_ORDER_STATUS = "SELECT * FROM loan_order WHERE status = ?";

    private static final String UPDATE_ORDER_BY_OFER_ID = "UPDATE loan_order SET status = ?, time_update = ? WHERE order_id = ?";
    private final JdbcTemplate jdbcTemplate;

    RowMapper<LoanOrder> loanOrderMapper = (rs, rowNum) -> new LoanOrder(
            rs.getLong(1),
            UUID.fromString(rs.getString(2)),
            rs.getLong(3),
            rs.getLong(4),
            rs.getBigDecimal(5),
            OrderStatusEnum.valueOf(rs.getString(6)),
            rs.getTimestamp(7),
            rs.getTimestamp(8)
    );

    @Override
    public List<LoanOrder> findAll() {
        return jdbcTemplate.query(FIND_ALL, loanOrderMapper);
    }

    @Override
    public LoanOrder save(LoanOrder loanOrder) {
        UUID uuid = UUID.randomUUID();
        Random rand = new Random();
        BigDecimal randomValue = BigDecimal.valueOf(0.10 + (0.80) * rand.nextDouble()).setScale(2, RoundingMode.HALF_UP);
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(SAVE_ORDER_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, String.valueOf(uuid));
            ps.setLong(2, loanOrder.getUserId());
            ps.setLong(3, loanOrder.getTariffId());
            ps.setBigDecimal(4, randomValue);
            ps.setString(5, String.valueOf(loanOrder.getStatus()));
            ps.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
            ps.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
            return ps;
        });
        loanOrder.setOrderId(uuid);
        return loanOrder;
    }

    @Override
    public List<LoanOrder> findOrdersByUserId(long id) {
        return jdbcTemplate.query(FIND_ORDER_BY_USER_ID, loanOrderMapper, id);
    }

    @Override
    public Optional<LoanOrder> findPaymentByUserIdAndTariffId(long userId, long offerId ) {
        return jdbcTemplate.query(FIND_ORDER_BY_USER_ID_AND_TARIFF_ID, loanOrderMapper, userId, offerId).stream().findFirst();
    }

    @Override
    public Optional<LoanOrder> findPaymentByUserIdAndOrderId(long userId, String orderId) {
        return jdbcTemplate.query(FIND_ORDER_BY_USER_ID_AND_ORDER_ID, loanOrderMapper, userId, orderId).stream().findFirst();
    }

    @Override
    public Optional<LoanOrder> findOrderByOrderId(String tariffId) {
        return jdbcTemplate.query(FIND_ORDER_BY_OFFER_ID, loanOrderMapper, tariffId).stream().findFirst();
    }

    @Override
    public boolean deleteOrder(long userId, String orderId) {
        return jdbcTemplate.update(DELETE_ORDER_BY_USER_ID_AND_ORDER_ID, userId, orderId) > 0;
    }

    @Override
    public List<LoanOrder> findOrdersByOrderStatus(String status) {
        return jdbcTemplate.query(FIND_ORDERS_BY_ORDER_STATUS, loanOrderMapper, status);
    }

    @Override
    public void updateOrderStatusByOrderId(String status, Timestamp updateTime, String orderId){
        jdbcTemplate.update(UPDATE_ORDER_BY_OFER_ID, status, updateTime, orderId);
    }


}
