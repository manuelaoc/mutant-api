package com.magneto.core.controller;

import com.magneto.core.model.StatsDto;
import com.magneto.core.usecase.GenerateStatsUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("stats")
@Tag(name = "Estadisticas", description = "Controlador para obtener las estadisticas de las verificaciones de ADN")
public class StatsQueryController {

    private final GenerateStatsUseCase generateStatsUseCase;

    @Autowired
    public StatsQueryController(GenerateStatsUseCase generateStatsUseCase) {
        this.generateStatsUseCase = generateStatsUseCase;
    }

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    @Operation(summary = "Obtener las estadisticas sobre los datos de las verificaciones realizadas a los ADN")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se retornan las estadisticas de las verificaciones de ADN", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Error interno en el servidor al realizar la verificacion", content = {@Content(mediaType = "application/json")})
    })
    public ResponseEntity<StatsDto> getStats() {
        return new ResponseEntity<>(this.generateStatsUseCase.execute(), HttpStatus.OK);
    }
}
