package com.magneto.core.repository;

import com.magneto.core.model.Mutant;

public interface MutantRepository {

    /**
     * Permite guardar la información de los ADN verificados
     *
     * @param mutant objeto con la informacion el ADN y la verificación de si el humano es o no mutante
     */
    void save(Mutant mutant);

    /**
     * Permite validar si ya existe registrado un ADN igual al que se está enviando
     *
     * @param dna ADN a verificar
     * @return
     */
    Boolean existDna(String[] dna);
}
