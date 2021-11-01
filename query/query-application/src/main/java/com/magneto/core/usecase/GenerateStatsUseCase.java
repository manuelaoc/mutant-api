package com.magneto.core.usecase;

import com.magneto.core.dao.StatsDao;
import com.magneto.core.model.StatsDto;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class GenerateStatsUseCase {

    private final StatsDao statsDAO;

    public GenerateStatsUseCase(StatsDao statsDAO) {
        this.statsDAO = statsDAO;
    }

    public StatsDto execute() {
        return statsDAO.getData();
    }
}
