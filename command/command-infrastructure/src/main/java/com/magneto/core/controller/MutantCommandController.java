package com.magneto.core.controller;

import com.magneto.core.command.MutantCommand;
import com.magneto.core.usecase.VerifyMutantUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("mutant")
@Tag(name = "Verificacion ADN mutante", description = "Controlador para verificar si un humano es mutante")
public class MutantCommandController {

    private final VerifyMutantUseCase verifyMutantUseCase;

    @Autowired
    public MutantCommandController(VerifyMutantUseCase verifyMutantUseCase) {
        this.verifyMutantUseCase = verifyMutantUseCase;
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.OK)
    @Operation(summary = "Verificar el ADN enviado para saber si un humano es o no mutante")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se verifica el ADN y el humano es un mutante"),
            @ApiResponse(responseCode = "400", description = "No se esta enviando la informacion correcta para realizar la verificacion"),
            @ApiResponse(responseCode = "403", description = "Se verifica el ADN y el humano no es un mutante"),
            @ApiResponse(responseCode = "500", description = "Error interno en el servidor al realizar la verificacion")
    })
    public ResponseEntity<Boolean> verify(
            @Parameter(name = "mutantCommand", description = "Objeto MutantCommand con las propiedades necesarias para la verificacion del ADN", required = true)
            @RequestBody MutantCommand mutantCommand) {
        return new ResponseEntity<>(this.verifyMutantUseCase.execute(mutantCommand), HttpStatus.OK);
    }
}
