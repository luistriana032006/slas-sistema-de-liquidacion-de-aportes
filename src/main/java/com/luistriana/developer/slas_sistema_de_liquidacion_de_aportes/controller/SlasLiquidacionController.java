package com.luistriana.developer.slas_sistema_de_liquidacion_de_aportes.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luistriana.developer.slas_sistema_de_liquidacion_de_aportes.model.dtos.request.LiquidacionRequest;
import com.luistriana.developer.slas_sistema_de_liquidacion_de_aportes.model.dtos.response.LiquidacionResponse;
import com.luistriana.developer.slas_sistema_de_liquidacion_de_aportes.service.SlasService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/slas")
public class SlasLiquidacionController {

    private SlasService slas;

    public SlasLiquidacionController(SlasService slas) {
        this.slas = slas;
    }

    // metodo que recibe la peticion

    @PostMapping("/cotizacion")
    public LiquidacionResponse verAportes(@Valid @RequestBody LiquidacionRequest liquidacion) {
        return slas.calculoSlas(liquidacion);
    }

}