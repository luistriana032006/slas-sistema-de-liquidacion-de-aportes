package com.luistriana.developer.slas_sistema_de_liquidacion_de_aportes.service;

import org.springframework.stereotype.Service;

import com.luistriana.developer.slas_sistema_de_liquidacion_de_aportes.constants.ConstantesSeguridadSocial;
import com.luistriana.developer.slas_sistema_de_liquidacion_de_aportes.model.AportesFondoSolidarioPensionesFSP;
import com.luistriana.developer.slas_sistema_de_liquidacion_de_aportes.model.RiesgoLaboralARL;
import com.luistriana.developer.slas_sistema_de_liquidacion_de_aportes.model.dtos.request.LiquidacionRequest;
import com.luistriana.developer.slas_sistema_de_liquidacion_de_aportes.model.dtos.response.LiquidacionResponse;

@Service
public class SlasServiceImpl implements SlasService {

    @Override
    public LiquidacionResponse calculoSlas(LiquidacionRequest request) {

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

        return ibc;
    }

    private double porcentajeSalud(double ibc) {

        double salud = ibc * ConstantesSeguridadSocial.SALUD;
        return salud;
    }

    private double porcentajePension(double ibc) {
        double pension = ibc * ConstantesSeguridadSocial.PENSION;
        return pension;
    }

    private double porcentajeFsp(double ibc) {
        // primer paso dividimos el ibc para saber cuantos SMMLV caben en el
        double ibcEnSMMLV = ibc / ConstantesSeguridadSocial.SMMLV;

        /**
         * creamos una variable que sea de tipo del emun de aportesFinancieros
         */
        AportesFondoSolidarioPensionesFSP rango = AportesFondoSolidarioPensionesFSP.ObtenerRango(ibcEnSMMLV);

        return ibc * rango.getPorcentaje();
    }

    // calculo de la ARl que depende que envien un true en el campo de que si quiee
    // enviar algo a la ARL
    private double porcentajeARL(RiesgoLaboralARL nivel, double ibc) {

        // al ibc le vamos a calcular el porcentaje
        // recibimos el nivel de riesgo de arl

        // TODO: SIMPLIFICAR ESTOS CALCULOS PARA QUITAR VARIABLES TEMPORALES
        double porcentajeRiesgo = nivel.getPorcentaje();

        double porcentajeArl = ibc * porcentajeRiesgo;

        return porcentajeArl;
    }

    // calculo a CCF
    private double porcentajeCcf(double ibc, double porcentaje) {
        return ibc * (porcentaje / 100);
    }

    // metodo para consistencia de los datos 

    private void validarCosistencia(LiquidacionRequest request){

        

    }
}
