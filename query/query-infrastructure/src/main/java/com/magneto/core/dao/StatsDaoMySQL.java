package com.magneto.core.dao;

import com.magneto.core.model.StatsDto;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class StatsDaoMySQL implements StatsDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public StatsDaoMySQL(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public StatsDto getData() {
        return this.namedParameterJdbcTemplate.query("SELECT SUM(CASE WHEN is_mutant = true THEN 1 ELSE 0 END) AS count_mutant_dna, SUM(CASE WHEN is_mutant = FALSE THEN 1 ELSE 0 END) AS count_human_dna FROM mutant", new StatsMapper()).get(0);
    }
}
