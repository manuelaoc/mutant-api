package com.magneto.core.dao;

import com.magneto.core.model.StatsDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StatsMapper implements RowMapper<StatsDto> {

    @Override
    public StatsDto mapRow(ResultSet resultSet, int rowNum) throws SQLException {

        int countMutantDna = resultSet.getInt("count_mutant_dna");
        int countHumanDna = resultSet.getInt("count_human_dna");
        double ratio = countHumanDna != 0 ? (double) countMutantDna / countHumanDna : 0d;

        return new StatsDto(countMutantDna, countHumanDna, ratio);
    }

}