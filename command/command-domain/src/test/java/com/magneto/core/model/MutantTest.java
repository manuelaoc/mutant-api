package com.magneto.core.model;

import com.magneto.core.exception.MutantException;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

public class MutantTest {

    @Test
    @DisplayName("Valida que el modelo se cree correctamente al validar la información enviada")
    public void validateModelCreationCorrectlyTest() {
        Mutant mutant = new Mutant(new String[]{"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"});
        Assertions.assertNotNull(mutant.getDna());
    }

    @Test
    @DisplayName("Valida que al enviar un null o un vacío se lance una excepción al momento de crear el modelo")
    public void validateMandatoryDataCorrectlyTest() {
        Throwable nullException = Assertions.assertThrows(MutantException.class, () -> new Mutant(null));
        Assertions.assertEquals("El ADN no puede ser nulo o vac\u00EDo", nullException.getMessage());

        Throwable emptyException = Assertions.assertThrows(MutantException.class, () -> new Mutant(new String[]{}));
        Assertions.assertEquals("El ADN no puede ser nulo o vac\u00EDo", emptyException.getMessage());
    }

    @Test
    @DisplayName("Valida que al enviar una secuencia duplicada dentro del ADN enviado se lance una excepción")
    public void validateDuplicateDataCorrectlyTest() {
        Throwable exception = Assertions.assertThrows(MutantException.class, () -> new Mutant(new String[]{"ATGCGA", "ATGCGA", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"}));
        Assertions.assertEquals("El ADN no puede contener secuencias duplicadas", exception.getMessage());
    }

    @Test
    @DisplayName("Valida que al enviar secuencias con diferente longitud se lance una excepción")
    public void validateLengthSequenceCorrectlyTest() {
        Throwable exception = Assertions.assertThrows(MutantException.class, () -> new Mutant(new String[]{"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCAA"}));
        Assertions.assertEquals("Las secuencias del ADN deben tener la misma longitud", exception.getMessage());
    }

    @Test
    @DisplayName("Valida que al enviar una secuencia con menos de 4 caracteres se lance una excepción")
    public void validateMinimumLengthSequenceCorrectlyTest() {
        Throwable exception = Assertions.assertThrows(MutantException.class, () -> new Mutant(new String[]{"ATG ", "CAG ", "TTA ", "AGA ", "CCC ", "TCA "}));
        Assertions.assertEquals("Las secuencias del ADN deben contener m\u00EDnimo 4 caracteres", exception.getMessage());
    }

    @Test
    @DisplayName("Valida que al enviar una secuencia con caracteres diferentes a A, T, C y G se lance una excepción")
    public void validateCharactersSequenceCorrectlyTest() {
        Throwable exception = Assertions.assertThrows(MutantException.class, () -> new Mutant(new String[]{"ATGCGA", "ATGCGB", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"}));
        Assertions.assertEquals("Las secuencias del ADN solo pueden contener las letras A, T, C y G", exception.getMessage());
    }
}