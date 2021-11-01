package com.magneto.core.dao;

import com.magneto.core.model.StatsDto;

public interface StatsDao {
    /**
     * Permite consultar los datos necesarios para generar las estadísticas de las verificaciones de ADN
     *
     * @return datos para generar las estadisticas
     */
    StatsDto getData();
}
