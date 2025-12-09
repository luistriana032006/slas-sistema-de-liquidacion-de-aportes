package com.luistriana.developer.slas_sistema_de_liquidacion_de_aportes.model.dtos.request;

import com.luistriana.developer.slas_sistema_de_liquidacion_de_aportes.model.RiesgoLaboralARL;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * DTO (Data Transfer Object) para recibir los datos de solicitud de
 * liquidación.
 * <p>
 * Esta clase contiene todos los datos necesarios para calcular los aportes
 * a seguridad social de un trabajador independiente.
 * </p>
 *
 * <p>
 * <strong>Campos obligatorios:</strong>
 * </p>
 * <ul>
 * <li>ingresosMensual: Debe ser positivo y no nulo</li>
 * <li>aporteARL: Indica si desea aportar a ARL (true/false)</li>
 * <li>aportaCCF: Indica si desea aportar a CCF (true/false)</li>
 * </ul>
 *
 * <p>
 * <strong>Campos condicionales:</strong>
 * </p>
 * <ul>
 * <li>nivelRiesgo: Requerido solo si aporteARL es true</li>
 * <li>porcentajeCCF: Requerido solo si aportaCCF es true (valores: 0.6 o
 * 2.0)</li>
 * </ul>
 *
 * @author Luis Miguel Triana Rueda
 * @version 1.0
 * @since 2025-01-01
 * @see RiesgoLaboralARL
 */
public class LiquidacionRequest {

    /**
     * Ingreso mensual bruto del trabajador independiente.
     * Debe ser un valor positivo mayor a cero.
     */
    @NotNull
    @Positive
    private Double ingresosMensual;

    /**
     * Indica si el trabajador desea realizar aporte voluntario a ARL.
     * Si es true, debe especificarse el nivelRiesgo.
     */
    @NotNull
    private Boolean aporteARL;

    /**
     * Indica si el trabajador desea realizar aporte voluntario a CCF.
     * Si es true, debe especificarse el porcentajeCCF.
     */
    @NotNull
    private Boolean aportaCCF;

    /**
     * Nivel de riesgo laboral para el cálculo de ARL.
     * Este campo es opcional y solo se requiere si aporteARL es true.
     * Valores permitidos: NIVEL_I, NIVEL_II, NIVEL_III, NIVEL_IV, NIVEL_V.
     */
    private RiesgoLaboralARL nivelRiesgo;

    /**
     * Porcentaje de cotización a CCF.
     * Este campo es opcional y solo se requiere si aportaCCF es true.
     * Valores permitidos: 0.6 (aporte básico) o 2.0 (aporte completo).
     */
    private Double porcentajeCCF;

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

    /**
     * Constructor con todos los parámetros.
     *
     * @param ingresosMensual ingreso mensual bruto del trabajador
     * @param aporteARL       indica si aporta a ARL
     * @param aportaCCF       indica si aporta a CCF
     * @param nivelRiesgo     nivel de riesgo laboral (opcional, requerido si
     *                        aporteARL es true)
     * @param porcentajeCCF   porcentaje CCF (opcional, requerido si aportaCCF es
     *                        true)
     */
    public LiquidacionRequest(Double ingresosMensual, Boolean aporteARL, Boolean aportaCCF,
            RiesgoLaboralARL nivelRiesgo, Double porcentajeCCF) {
        this.ingresosMensual = ingresosMensual;
        this.aporteARL = aporteARL;
        this.aportaCCF = aportaCCF;
        this.nivelRiesgo = nivelRiesgo;
        this.porcentajeCCF = porcentajeCCF;
    }

    /**
     * Constructor sin argumentos requerido para deserialización JSON.
     */
    public LiquidacionRequest() {
    }

}
