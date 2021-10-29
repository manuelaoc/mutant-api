package com.magneto.core.repository;

public interface MutantRepository {

    /**
     * Permite guardar la información de los ADN verificados
     *
     * @param dna objeto con la informacion del ADN para el guardado
     */
    void save(String[] dna);
}
