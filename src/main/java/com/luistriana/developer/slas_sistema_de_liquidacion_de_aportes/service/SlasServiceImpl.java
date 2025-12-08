package com.luistriana.developer.slas_sistema_de_liquidacion_de_aportes.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.luistriana.developer.slas_sistema_de_liquidacion_de_aportes.constants.ConstantesSeguridadSocial;
import com.luistriana.developer.slas_sistema_de_liquidacion_de_aportes.exception.datosInvalidosException;
import com.luistriana.developer.slas_sistema_de_liquidacion_de_aportes.model.AportesFondoSolidarioPensionesFSP;
import com.luistriana.developer.slas_sistema_de_liquidacion_de_aportes.model.RiesgoLaboralARL;
import com.luistriana.developer.slas_sistema_de_liquidacion_de_aportes.model.dtos.request.LiquidacionRequest;
import com.luistriana.developer.slas_sistema_de_liquidacion_de_aportes.model.dtos.response.LiquidacionResponse;

@Service
public class SlasServiceImpl implements SlasService {

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
     * metodos propios para hacer el calculo correcto
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

    private double porcentajeSalud(double ibc) {

        double salud = ibc * ConstantesSeguridadSocial.SALUD;
        return Math.round(salud);
    }

    private double porcentajePension(double ibc) {
        double pension = ibc * ConstantesSeguridadSocial.PENSION;

        return Math.round(pension);
    }

    private double porcentajeFsp(double ibc) {
        // primer paso dividimos el ibc para saber cuantos SMMLV caben en el
        double ibcEnSMMLV = ibc / ConstantesSeguridadSocial.SMMLV;

        /**
         * creamos una variable que sea de tipo del emun de aportesFinancieros
         */
        AportesFondoSolidarioPensionesFSP rango = AportesFondoSolidarioPensionesFSP.ObtenerRango(ibcEnSMMLV);

        return Math.round(ibc) * rango.getPorcentaje();
    }

    // calculo de la ARl que depende que envien un true en el campo de que si quiee
    // enviar algo a la ARL
    private double porcentajeARL(RiesgoLaboralARL nivel, double ibc) {

        return Math.round(ibc * (nivel.getPorcentaje() / 100));
    }

    // calculo a CCF
    private double porcentajeCcf(double ibc, double porcentaje) {

        return Math.round(ibc * (porcentaje / 100));
    }

    // metodo para consistencia de los datos

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
