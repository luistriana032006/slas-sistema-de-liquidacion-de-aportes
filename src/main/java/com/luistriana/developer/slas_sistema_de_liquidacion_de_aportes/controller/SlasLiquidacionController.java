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

/**
 * Controlador REST para la liquidación de aportes a seguridad social.
 * <p>
 * Proporciona endpoints para calcular los aportes obligatorios y voluntarios
 * de trabajadores independientes en Colombia bajo el régimen de contrato
 * de prestación de servicios.
 * </p>
 *
 * @author Luis Miguel Triana Rueda
 * @version 1.0
 * @since 2025-01-01
 * @see SlasService
 * @see LiquidacionRequest
 * @see LiquidacionResponse
 */
@RestController
@RequestMapping("/api/slas")
@Tag(name = "Liquidación de Aportes", description = "Endpoints para calcular aportes a seguridad social de trabajadores independientes")
public class SlasLiquidacionController {

    private SlasService slas;

    /**
     * Constructor con inyección de dependencias.
     *
     * @param slas servicio de liquidación de aportes
     */
    public SlasLiquidacionController(SlasService slas) {
        this.slas = slas;
    }

    /**
     * Endpoint para calcular la liquidación completa de aportes.
     * <p>
     * Recibe los datos del trabajador y retorna el detalle de todos los aportes
     * calculados (IBC, salud, pensión, FSP, ARL y CCF).
     * </p>
     *
     * <p><strong>Ejemplo de request:</strong></p>
     * <pre>
     * {
     *   "ingresos": 5000000,
     *   "aporteARL": true,
     *   "nivelRiesgo": "NIVEL_III",
     *   "aportaCCF": true,
     *   "porcentajeCCF": 2.0
     * }
     * </pre>
     *
     * @param liquidacion datos del trabajador (ingresos y aportes voluntarios)
     * @return detalle completo de la liquidación calculada
     * @throws datosInvalidosException si los datos son inválidos o inconsistentes (retorna HTTP 400)
     */
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