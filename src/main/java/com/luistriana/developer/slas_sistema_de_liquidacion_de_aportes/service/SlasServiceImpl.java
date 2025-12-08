package com.luistriana.developer.slas_sistema_de_liquidacion_de_aportes.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.luistriana.developer.slas_sistema_de_liquidacion_de_aportes.constants.ConstantesSeguridadSocial;
import com.luistriana.developer.slas_sistema_de_liquidacion_de_aportes.exception.datosInvalidosException;
import com.luistriana.developer.slas_sistema_de_liquidacion_de_aportes.model.AportesFondoSolidarioPensionesFSP;
import com.luistriana.developer.slas_sistema_de_liquidacion_de_aportes.model.RiesgoLaboralARL;
import com.luistriana.developer.slas_sistema_de_liquidacion_de_aportes.model.dtos.request.LiquidacionRequest;
import com.luistriana.developer.slas_sistema_de_liquidacion_de_aportes.model.dtos.response.LiquidacionResponse;

/**
 * Implementación del servicio de liquidación de aportes a seguridad social.
 * <p>
 * Esta clase proporciona la lógica de negocio para calcular los aportes obligatorios
 * y voluntarios a seguridad social para trabajadores independientes en Colombia
 * (contrato de prestación de servicios).
 * </p>
 *
 * <p><strong>Aportes calculados:</strong></p>
 * <ul>
 *   <li>Obligatorios: Salud (12.5%), Pensión (16%), FSP (según rango)</li>
 *   <li>Voluntarios: ARL (según nivel de riesgo) y CCF (0.6% o 2%)</li>
 * </ul>
 *
 * @author Luis Miguel Triana Rueda
 * @version 1.0
 * @since 2025-01-01
 * @see SlasService
 * @see LiquidacionRequest
 * @see LiquidacionResponse
 */
@Service
public class SlasServiceImpl implements SlasService {

    /**
     * Calcula la liquidación completa de aportes a seguridad social.
     * <p>
     * Este método realiza las siguientes operaciones:
     * </p>
     * <ol>
     *   <li>Valida la consistencia de los datos de entrada</li>
     *   <li>Calcula el IBC (Ingreso Base de Cotización)</li>
     *   <li>Calcula aportes obligatorios: Salud, Pensión y FSP</li>
     *   <li>Calcula aportes voluntarios: ARL y CCF (si aplican)</li>
     *   <li>Retorna el total y detalle de todos los aportes</li>
     * </ol>
     *
     * @param request objeto con los datos del trabajador (ingresos, aportes voluntarios)
     * @return objeto con el detalle completo de la liquidación
     * @throws datosInvalidosException si los datos de entrada son inconsistentes o inválidos
     * @see #validarConsistencia(LiquidacionRequest)
     * @see #porcentajeIBC(double)
     */
    @Override
    public LiquidacionResponse calculoSlas(LiquidacionRequest request) {

        validarConsistencia(request);
        // variables de validaciones

        double arl = 0.0;
        double ccf = 0.0;

        double ibc = porcentajeIBC(request.getIngresos());
        double pension = porcentajePension(ibc);
        double salud = porcentajeSalud(ibc);

        if (request.getAporteARL()) {
            arl = porcentajeARL(request.getNivelRiesgo(), ibc);
        }
        double fsp = porcentajeFsp(ibc);

        if (request.getAportaCCF()) {
            ccf = porcentajeCcf(ibc, request.getPorcentajeCCF());
        }

        double total = pension + salud + arl + fsp + ccf;
        LiquidacionResponse response = new LiquidacionResponse(ibc, salud, pension, fsp, arl, ccf, total);
        return response;

    }

    /**
     * Calcula el Ingreso Base de Cotización (IBC) aplicando límites legales.
     * <p>
     * El IBC se calcula como el 40% del ingreso mensual del trabajador independiente,
     * con las siguientes restricciones:
     * </p>
     * <ul>
     *   <li>Mínimo: 1 SMMLV (Salario Mínimo Mensual Legal Vigente)</li>
     *   <li>Máximo: 25 SMMLV</li>
     * </ul>
     *
     * @param ingreso ingreso mensual bruto del trabajador
     * @return IBC calculado y redondeado, respetando límites mínimo y máximo
     * @see ConstantesSeguridadSocial#MIN_CALCULO_IBC
     * @see ConstantesSeguridadSocial#MAX_CALCULO_IBC
     */
    private double porcentajeIBC(double ingreso) {

        // calculo de ibc
        double ibc = ingreso * 0.40;

        if (ibc < ConstantesSeguridadSocial.MIN_CALCULO_IBC)
            return ConstantesSeguridadSocial.SMMLV;

        if (ibc > ConstantesSeguridadSocial.MAX_CALCULO_IBC)
            return ConstantesSeguridadSocial.MAX_CALCULO_IBC;

        // operacion del metodo

        return Math.round(ibc);
    }

    /**
     * Calcula el aporte obligatorio a salud.
     * <p>
     * Aplica el porcentaje legal del 12.5% sobre el IBC.
     * </p>
     *
     * @param ibc Ingreso Base de Cotización previamente calculado
     * @return monto del aporte a salud, redondeado
     * @see ConstantesSeguridadSocial#SALUD
     */
    private double porcentajeSalud(double ibc) {

        double salud = ibc * ConstantesSeguridadSocial.SALUD;
        return Math.round(salud);
    }

    /**
     * Calcula el aporte obligatorio a pensión.
     * <p>
     * Aplica el porcentaje legal del 16% sobre el IBC.
     * </p>
     *
     * @param ibc Ingreso Base de Cotización previamente calculado
     * @return monto del aporte a pensión, redondeado
     * @see ConstantesSeguridadSocial#PENSION
     */
    private double porcentajePension(double ibc) {
        double pension = ibc * ConstantesSeguridadSocial.PENSION;

        return Math.round(pension);
    }

    /**
     * Calcula el aporte al Fondo de Solidaridad Pensional (FSP).
     * <p>
     * El FSP es un aporte obligatorio que varía según el IBC expresado en SMMLV:
     * </p>
     * <ul>
     *   <li>0% para IBC menor a 4 SMMLV</li>
     *   <li>1% para IBC entre 4 y 16 SMMLV</li>
     *   <li>1.2% para IBC entre 16 y 17 SMMLV</li>
     *   <li>1.4% para IBC entre 17 y 18 SMMLV</li>
     *   <li>1.6% para IBC entre 18 y 19 SMMLV</li>
     *   <li>1.8% para IBC entre 19 y 20 SMMLV</li>
     *   <li>2% para IBC mayor o igual a 20 SMMLV</li>
     * </ul>
     *
     * @param ibc Ingreso Base de Cotización previamente calculado
     * @return monto del aporte al FSP, redondeado
     * @see AportesFondoSolidarioPensionesFSP#ObtenerRango(double)
     */
    private double porcentajeFsp(double ibc) {
        // primer paso dividimos el ibc para saber cuantos SMMLV caben en el
        double ibcEnSMMLV = ibc / ConstantesSeguridadSocial.SMMLV;

        /**
         * creamos una variable que sea de tipo del emun de aportesFinancieros
         */
        AportesFondoSolidarioPensionesFSP rango = AportesFondoSolidarioPensionesFSP.ObtenerRango(ibcEnSMMLV);

        return Math.round(ibc) * rango.getPorcentaje();
    }

    /**
     * Calcula el aporte voluntario a ARL (Administradora de Riesgos Laborales).
     * <p>
     * El porcentaje de cotización varía según el nivel de riesgo de la actividad laboral:
     * </p>
     * <ul>
     *   <li>Nivel I: 0.522%</li>
     *   <li>Nivel II: 1.044%</li>
     *   <li>Nivel III: 2.436%</li>
     *   <li>Nivel IV: 4.350%</li>
     *   <li>Nivel V: 6.960%</li>
     * </ul>
     *
     * @param nivel nivel de riesgo laboral de la actividad
     * @param ibc Ingreso Base de Cotización previamente calculado
     * @return monto del aporte a ARL, redondeado
     * @see RiesgoLaboralARL
     */
    private double porcentajeARL(RiesgoLaboralARL nivel, double ibc) {

        return Math.round(ibc * (nivel.getPorcentaje() / 100));
    }

    /**
     * Calcula el aporte voluntario a CCF (Caja de Compensación Familiar).
     * <p>
     * Los porcentajes permitidos son:
     * </p>
     * <ul>
     *   <li>0.6% - Aporte básico</li>
     *   <li>2.0% - Aporte completo</li>
     * </ul>
     *
     * @param ibc Ingreso Base de Cotización previamente calculado
     * @param porcentaje porcentaje de cotización (0.6 o 2.0)
     * @return monto del aporte a CCF, redondeado
     */
    private double porcentajeCcf(double ibc, double porcentaje) {

        return Math.round(ibc * (porcentaje / 100));
    }

    /**
     * Valida la consistencia y coherencia de los datos de entrada.
     * <p>
     * Realiza las siguientes validaciones:
     * </p>
     * <ul>
     *   <li>El ingreso debe ser mayor a cero</li>
     *   <li>Si aporta a CCF, debe especificar porcentaje (0.6 o 2.0)</li>
     *   <li>Si no aporta a CCF, no debe enviar porcentaje</li>
     *   <li>El porcentaje CCF debe ser exactamente 0.6 o 2.0</li>
     *   <li>Si aporta a ARL, debe especificar nivel de riesgo</li>
     *   <li>Si no aporta a ARL, no debe enviar nivel de riesgo</li>
     * </ul>
     *
     * @param request objeto con los datos a validar
     * @throws datosInvalidosException si alguna validación falla
     */
    private void validarConsistencia(LiquidacionRequest request) {

        // validaciones de ingreso

 // Validación de ingreso
    if (request.getIngresos() <= 0) {  // Cambié < a <=
        throw new datosInvalidosException(
            "El ingreso debe ser mayor a cero. Recibido: " + request.getIngresos());
    }

    // Validación CCF: si aporta TRUE pero porcentaje es null
    if (request.getAportaCCF() && request.getPorcentajeCCF() == null) {
        throw new datosInvalidosException(
            "Si aporta a CCF, debe especificar porcentaje (0.6 o 2.0)");
    }

    // Validación CCF: si aporta FALSE pero envía porcentaje
    if (request.getPorcentajeCCF() != null && !request.getAportaCCF()) {
        throw new datosInvalidosException(
            "No puede enviar porcentaje si aporteVoluntarioCCF es false");
    }

    // Validación CCF: porcentaje debe ser 0.6 o 2.0
    if (request.getAportaCCF() && request.getPorcentajeCCF() != null) {
        double p = request.getPorcentajeCCF();
        if (p != 0.6 && p != 2.0) {
            throw new datosInvalidosException(
                "Porcentaje CCF debe ser 0.6 o 2.0, recibido: " + p);
        }
    }

    // Validación ARL: si aporta TRUE pero nivel es null
    if (request.getAporteARL() && request.getNivelRiesgo() == null) {
        throw new datosInvalidosException(
            "Si aporta a ARL, debe especificar nivel de riesgo");
    }

    // Validación ARL: si aporta FALSE pero envía nivel
    if (request.getNivelRiesgo() != null && !request.getAporteARL()) {
        throw new datosInvalidosException(
            "No puede enviar nivel de riesgo si aporteVoluntarioARL es false");
    }
    }

}
