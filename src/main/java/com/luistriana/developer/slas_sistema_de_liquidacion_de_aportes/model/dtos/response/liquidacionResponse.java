package com.luistriana.developer.slas_sistema_de_liquidacion_de_aportes.model.dtos.response;

public record LiquidacionResponse(double ibc, double salud, double pension, double fsp, double arl, double ccf, double total) {
/**
 * en esta clase vamos a enviar todos los datos que pueden llegar a lanzar las operaciones de la peticion 
 */
}
