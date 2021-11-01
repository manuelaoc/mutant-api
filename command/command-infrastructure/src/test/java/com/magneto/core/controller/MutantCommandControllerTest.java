package com.magneto.core.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.magneto.core.ApplicationMock;
import com.magneto.core.command.MutantCommand;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ApplicationMock.class)
@WebMvcTest(MutantCommandController.class)
public class MutantCommandControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Verifica el ADN mutante enviado, realiza el guardado y retorna true")
    public void verifyAndSaveMutantDNASubmittedCorrectlyTest() throws Exception {
        // arrange
        String[] dna = new String[]{"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
        MutantCommand mutantCommand = new MutantCommand(dna);

        // act - assert
        this.mockMvc.perform(post("/mutant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(mutantCommand)))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    @DisplayName("Verifica el ADN no mutante enviado, realiza el guardado y retorna error 403")
    public void verifyAndSaveNonMutantDNASubmittedCorrectlyTest() throws Exception {
        // arrange
        String[] dna = new String[]{"TTCTGA", "CAGTAC", "TTATGT", "AGAAGG", "CCACTA", "TCACTG"};
        MutantCommand mutantCommand = new MutantCommand(dna);

        // act - assert
        this.mockMvc.perform(post("/mutant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(mutantCommand)))
                .andExpect(status().isForbidden())
                .andExpect(content().string("El ADN enviado no pertene a un mutante"));
    }

    @Test
    @DisplayName("Valida que al consumir la petición sin la información necesaria se retorne 400")
    public void validateWrongUrlTest() throws Exception {
        //Arrange - act - assert
        mockMvc.perform(post("/mutant")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Verifica que se lance la excepción correctamente cuando el ADN enviado es null")
    public void verifyNullDNASubmittedCorrectlyTest() throws Exception {
        // arrange
        MutantCommand mutantCommand = new MutantCommand(null);

        // act - assert
        this.mockMvc.perform(post("/mutant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(mutantCommand)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("El ADN no puede ser nulo o vac\u00EDo"));
    }

    @Test
    @DisplayName("Verifica que se lance la excepción correctamente cuando el ADN enviado es vacío")
    public void verifyEmptyDNASubmittedCorrectlyTest() throws Exception {
        // arrange
        MutantCommand mutantCommand = new MutantCommand(new String[]{});

        // act - assert
        this.mockMvc.perform(post("/mutant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(mutantCommand)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("El ADN no puede ser nulo o vac\u00EDo"));
    }

    @Test
    @DisplayName("Verifica que se lance la excepción correctamente cuando alguna secuencia del ADN enviado esta duplicada")
    public void verifyDuplicateSequenceDNASubmittedCorrectlyTest() throws Exception {
        // arrange
        String[] dna = new String[]{"TTCTGA", "TTCTGA", "TTATGT", "TTTAGC", "AGACTA", "TCCCTA"};
        MutantCommand mutantCommand = new MutantCommand(dna);

        // act - assert
        this.mockMvc.perform(post("/mutant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(mutantCommand)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("El ADN no puede contener secuencias duplicadas"));
    }

    @Test
    @DisplayName("Verifica que se lance la excepción correctamente cuando las secuencias del ADN enviado son de diferente tamaño")
    public void verifyLengthSequenceSubmittedCorrectlyTest() throws Exception {
        // arrange
        String[] dna = new String[]{"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCAA"};
        MutantCommand mutantCommand = new MutantCommand(dna);

        // act - assert
        this.mockMvc.perform(post("/mutant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(mutantCommand)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Las secuencias del ADN deben tener la misma longitud"));
    }

    @Test
    @DisplayName("Verifica que se lance la excepción correctamente cuando la longitud de las secuencias del ADN enviado es menor a 4")
    public void verifyLengthSequenceLessThan4SubmittedCorrectlyTest() throws Exception {
        // arrange
        String[] dna = new String[]{"ATG ", "CAG ", "TTA ", "AGA ", "CCC ", "TCA "};
        MutantCommand mutantCommand = new MutantCommand(dna);

        // act - assert
        this.mockMvc.perform(post("/mutant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(mutantCommand)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Las secuencias del ADN deben contener m\u00EDnimo 4 caracteres"));
    }

    @Test
    @DisplayName("Verifica que se lance la excepción correctamente cuando las secuencias del ADN enviado tienen caracteres diferentes a los permitidos")
    public void verifyCharactersNotAllowedSequenceSubmittedCorrectlyTest() throws Exception {
        // arrange
        String[] dna = new String[]{"ATGCGA", "ATGCGB", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
        MutantCommand mutantCommand = new MutantCommand(dna);

        // act - assert
        this.mockMvc.perform(post("/mutant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(mutantCommand)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Las secuencias del ADN solo pueden contener las letras A, T, C y G"));
    }

}
