package com.example.credit.repository.repositoryImpl;

import com.example.credit.entity.tables.Tariff;
import com.example.credit.repository.repositoryInterfaces.TariffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class TariffRepositoryImpl implements TariffRepository {

    private final String FIND_ALL = "select * from tariff";

    private static final String FIND_TARIFF_BY_ID = "SELECT * FROM tariff WHERE id = ?";

    private static final String SAVE_TARIFF_QUERY = "INSERT INTO tariff(type, interest_rate) VALUES (?,?)";

    private final JdbcTemplate jdbcTemplate;

    RowMapper<Tariff> tariffRowMapper = (rs, rowNum) -> new Tariff(
            rs.getLong(1),
            rs.getString(2),
            rs.getString(3)
    );

    @Override
    public List<Tariff> findAll() {
        return jdbcTemplate.query(FIND_ALL, tariffRowMapper);
    }

    @Override
    public Tariff save(Tariff tariff) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(SAVE_TARIFF_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, tariff.getType());
            ps.setString(2, tariff.getInterestRate());

            return ps;
        }, keyHolder);
        Long id = (Long) keyHolder.getKey();
        tariff.setId(id);
        return tariff;
    }

    @Override
    public Optional<Tariff> findTariffById(long id) {
        return jdbcTemplate.query(FIND_TARIFF_BY_ID, tariffRowMapper, id).stream().findFirst();
    }
}
