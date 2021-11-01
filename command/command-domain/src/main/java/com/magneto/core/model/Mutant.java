package com.magneto.core.model;

import com.magneto.core.exception.ArgumentValidator;

public class Mutant {
    private String[] dna;
    private boolean isMutant;

    public Mutant(String[] dna) {
        ArgumentValidator.validateMandatory(dna);
        ArgumentValidator.validateDuplicates(dna);
        ArgumentValidator.validateLengthSequence(dna);
        for (String sequence : dna) {
            ArgumentValidator.validateMinimumLengthSequence(sequence);
            ArgumentValidator.validateCharactersSequence(sequence);
        }
        this.dna = dna;
    }

    public String[] getDna() {
        return dna;
    }

    public boolean isMutant() {
        return isMutant;
    }

    public void setIsMutant(boolean isMutant) {
        this.isMutant = isMutant;
    }
}
