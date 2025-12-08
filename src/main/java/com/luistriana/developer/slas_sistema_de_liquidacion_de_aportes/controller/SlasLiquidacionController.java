package com.luistriana.developer.slas_sistema_de_liquidacion_de_aportes.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luistriana.developer.slas_sistema_de_liquidacion_de_aportes.model.dtos.request.LiquidacionRequest;
import com.luistriana.developer.slas_sistema_de_liquidacion_de_aportes.model.dtos.response.LiquidacionResponse;
import com.luistriana.developer.slas_sistema_de_liquidacion_de_aportes.service.SlasService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/slas")
@Tag(name = "Liquidación de Aportes ", description =" e")
public class SlasLiquidacionController {

    private SlasService slas;

    public SlasLiquidacionController(SlasService slas) {
        this.slas = slas;
    }

    // metodo que recibe la peticion

    @PostMapping("/cotizacion")
    @Operation(
        summary = "Calcular aportes de seguridad social",
        description = "Calcula IBC y aportes obligatorios (salud, pensión, FSP) y opcionales (ARL, CCF) para trabajadores independientes"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cálculo exitoso"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos o inconsistentes")
    })
    public LiquidacionResponse verAportes(@Valid @RequestBody LiquidacionRequest liquidacion) {
        return slas.calculoSlas(liquidacion);
    }

}