package com.luistriana.developer.slas_sistema_de_liquidacion_de_aportes.model.dtos.request;

import com.luistriana.developer.slas_sistema_de_liquidacion_de_aportes.model.RiesgoLaboralARL;

public class LiquidacionRequest {

    private Double ingresosMensual;
    private Boolean aporteARL;
    private Boolean aportaCCF;
    private RiesgoLaboralARL nivelRiesgo;
    private Double porcentajeCCF;

    public Double getIngresos() {
        return ingresosMensual;
    }

    public Double getIngresosMensual() {
        return ingresosMensual;
    }

    public void setIngresosMensual(Double ingresosMensual) {
        this.ingresosMensual = ingresosMensual;
    }

    public RiesgoLaboralARL getNivelRiesgo() {
        return nivelRiesgo;
    }

    public void setNivelRiesgo(RiesgoLaboralARL nivelRiesgo) {
        this.nivelRiesgo = nivelRiesgo;
    }

    public Double getPorcentajeCCF() {
        return porcentajeCCF;
    }

    public void setPorcentajeCCF(Double porcentajeCCF) {
        this.porcentajeCCF = porcentajeCCF;
    }

    public void setIngresos(Double ingresos) {
        this.ingresosMensual = ingresos;
    }

    public Boolean getAporteARL() {
        return aporteARL;
    }

    public void setAporteARL(Boolean aporteARL) {
        this.aporteARL = aporteARL;
    }

    public Boolean getAportaCCF() {
        return aportaCCF;
    }

    public void setAportaCCF(Boolean aportaCCF) {
        this.aportaCCF = aportaCCF;
    }

   

    public LiquidacionRequest(Double ingresosMensual, Boolean aporteARL, Boolean aportaCCF,
            RiesgoLaboralARL nivelRiesgo, Double porcentajeCCF) {
        this.ingresosMensual = ingresosMensual;
        this.aporteARL = aporteARL;
        this.aportaCCF = aportaCCF;
        this.nivelRiesgo = nivelRiesgo;
        this.porcentajeCCF = porcentajeCCF;
    }

    public LiquidacionRequest() {
    }

}
