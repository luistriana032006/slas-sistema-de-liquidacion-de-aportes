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

        // al ibc le vamos a calcular el porcentaje
        // recibimos el nivel de riesgo de arl

        // TODO: SIMPLIFICAR ESTOS CALCULOS PARA QUITAR VARIABLES TEMPORALES
        double porcentajeRiesgo = nivel.getPorcentaje();

        double porcentajeArl = ibc * porcentajeRiesgo;

        return Math.round(porcentajeArl);
    }

    // calculo a CCF
    private double porcentajeCcf(double ibc, double porcentaje) {
        final double EPSILON = 0.0001;

        // PASO1. convertir el porcentaje que entra en decimal

        double porcentajeDecimal = porcentaje / 100;

        // paso 2. validar si coincide con nuestras constantes
        if (Math.abs(porcentajeDecimal - ConstantesSeguridadSocial.PORCENTAJE_CCF_BAJO) < EPSILON) {
            return Math.round(ibc) * ConstantesSeguridadSocial.PORCENTAJE_CCF_BAJO;
        }

        if (Math.abs(porcentajeDecimal - ConstantesSeguridadSocial.PORCENTAJE_CCF_ALTO) < EPSILON) {
            return Math.round(ibc) * ConstantesSeguridadSocial.PORCENTAJE_CCF_ALTO;
        }
        throw new datosInvalidosException(
                " Porcentaje CCF invalido  " + porcentaje + "%  datos permitidos 0.6%  o 2%");

    }

    // metodo para consistencia de los datos

    private void validarConsistencia(LiquidacionRequest request) {

        // validaciones de ingreso 

        if (request.getIngresos() <0){
            throw new datosInvalidosException("el ingreso"+request.getIngresos()+" debe ser mayor a cero");
        }
        // validaciones para CCF
        /**
         * que pasa si en aporte ccf envian un true pero en el porcentaje envian null,
         */
        if (Boolean.TRUE.equals(request.getAportaCCF()) && request.getPorcentajeCCF() == null) {
            throw new datosInvalidosException(" no puede enviar un null en el porcentaje del request");
        }

        /**
         * que pasa si en aporte ccf enviand un false pero en el porcentaje envian un
         * dato
         */
        if (Boolean.TRUE.equals(request.getPorcentajeCCF() != null) && request.getAportaCCF() ) {
            throw new datosInvalidosException(" no puede enviar un false si va a enviar un porcentaje a cotizacion");
        }

        // validaciones apra ARL

        /**
         * que pasa si en aporteARL envian un true y no envian un nivel de riesgo
         * 
         */
        if (Boolean.TRUE.equals(request.getAporteARL()) && request.getNivelRiesgo() == null) {
            throw new datosInvalidosException(
                    "no se puede hacer la peticion por que no envio nada en el nivel de riesgo");
        }

        /**
         * que pasa si en aporte ARL envian un flase y envina un nivel de riesgo
         * 
         */
        if (request.getNivelRiesgo() != null && Boolean.TRUE.equals(request.getAporteARL() )) {
            throw new datosInvalidosException("No puede enviar un nivel de riesgo si aporteARL es false");
        }
    }

}
