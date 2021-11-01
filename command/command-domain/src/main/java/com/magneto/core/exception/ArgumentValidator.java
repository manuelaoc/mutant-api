package com.magneto.core.exception;

import java.util.HashSet;
import java.util.Set;

public class ArgumentValidator {

    public static final int MINIMUM_LENGTH = 4;

    private ArgumentValidator() {
    }

    public static void validateMandatory(String[] dna) {
        if (dna == null || dna.length == 0) {
            throw new MutantException("El ADN no puede ser nulo o vac\u00EDo");
        }
    }

    public static void validateDuplicates(String[] dna) {
        Set<String> dnaSet = new HashSet<>();
        for (String sequence : dna) {
            if (!dnaSet.add(sequence)) {
                throw new MutantException("El ADN no puede contener secuencias duplicadas");
            }
        }
    }

    public static void validateMinimumLengthSequence(String sequence) {
        if (sequence.trim().split("").length < MINIMUM_LENGTH) {
            throw new MutantException(String.format("Las secuencias del ADN deben contener m\u00EDnimo %d caracteres", MINIMUM_LENGTH));
        }
    }

    public static void validateCharactersSequence(String sequence) {
        if (!sequence.matches("^[ATCG]*$")) {
            throw new MutantException("Las secuencias del ADN solo pueden contener las letras A, T, C y G");
        }
    }

    public static void validateLengthSequence(String[] dna) {
        int lengthSequence = dna[0].length();
        for (String sequence : dna) {
            if (sequence.length() != lengthSequence) {
                throw new MutantException("Las secuencias del ADN deben tener la misma longitud");
            }
        }
    }
}
