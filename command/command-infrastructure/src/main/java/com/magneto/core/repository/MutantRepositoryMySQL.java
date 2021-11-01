package com.magneto.core.repository;

import com.magneto.core.model.Mutant;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Arrays;

@Repository
public class MutantRepositoryMySQL implements MutantRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public MutantRepositoryMySQL(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public void save(Mutant mutant) {
        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("dna", Arrays.toString(mutant.getDna()));
        paramSource.addValue("isMutant", mutant.isMutant());
        this.namedParameterJdbcTemplate.update("INSERT INTO mutant VALUES(:dna, :isMutant)", paramSource);
    }

    @Override
    public Boolean existDna(String[] dna) {
        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("dna", Arrays.toString(dna));
        return this.namedParameterJdbcTemplate.queryForObject("SELECT count(1) FROM mutant WHERE dna = :dna", paramSource, Boolean.class);
    }
}
