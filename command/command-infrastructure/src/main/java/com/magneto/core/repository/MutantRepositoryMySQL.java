package com.magneto.core.repository;

import org.springframework.stereotype.Repository;

@Repository
public class MutantRepositoryMySQL implements MutantRepository {

    @Override
    public void save(String[] dna) {
        // this.namedParameterJdbcTemplate.update(sql, paramSource, keyHolder, new String[]{key});
    }
}
