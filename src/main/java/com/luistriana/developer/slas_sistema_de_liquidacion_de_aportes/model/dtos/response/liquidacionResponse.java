package com.luistriana.developer.slas_sistema_de_liquidacion_de_aportes.model.dtos.response;

/**
 * DTO (Data Transfer Object) de respuesta con el detalle de liquidación calculada.
 * <p>
 * Este record contiene todos los valores calculados de los aportes a seguridad social,
 * incluyendo el IBC (Ingreso Base de Cotización), los aportes obligatorios y voluntarios,
 * y el total a pagar.
 * </p>
 *
 * <p><strong>Valores incluidos:</strong></p>
 * <ul>
 *   <li>ibc: Ingreso Base de Cotización (40% del ingreso con límites)</li>
 *   <li>salud: Aporte obligatorio a salud (12.5% del IBC)</li>
 *   <li>pension: Aporte obligatorio a pensión (16% del IBC)</li>
 *   <li>fsp: Fondo de Solidaridad Pensional (según tabla progresiva)</li>
 *   <li>arl: Aporte voluntario a ARL (si aplica, según nivel de riesgo)</li>
 *   <li>ccf: Aporte voluntario a CCF (si aplica, 0.6% o 2%)</li>
 *   <li>total: Suma de todos los aportes</li>
 * </ul>
 *
 * <p>
 * Todos los valores están redondeados y expresados en pesos colombianos (COP).
 * </p>
 *
 * @param ibc Ingreso Base de Cotización calculado
 * @param salud Aporte a salud (obligatorio)
 * @param pension Aporte a pensión (obligatorio)
 * @param fsp Fondo de Solidaridad Pensional (obligatorio según rango)
 * @param arl Aporte a ARL (voluntario)
 * @param ccf Aporte a CCF (voluntario)
 * @param total Total de todos los aportes
 *
 * @author Luis Miguel Triana Rueda
 * @version 1.0
 * @since 2025-01-01
 */
public record LiquidacionResponse(double ibc, double salud, double pension, double fsp, double arl, double ccf, double total) {
}
