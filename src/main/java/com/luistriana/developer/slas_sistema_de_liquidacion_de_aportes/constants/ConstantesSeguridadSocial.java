package com.luistriana.developer.slas_sistema_de_liquidacion_de_aportes.constants;

/**
 * Clase de utilidad que contiene las constantes del sistema de seguridad social colombiano.
 * <p>
 * Esta clase no está diseñada para ser instanciada. Contiene únicamente constantes públicas
 * estáticas relacionadas con los cálculos de aportes a seguridad social.
 * </p>
 *
 * <p><strong>Constantes incluidas:</strong></p>
 * <ul>
 *   <li>Valores del SMMLV (Salario Mínimo Mensual Legal Vigente)</li>
 *   <li>Límites para el cálculo del IBC</li>
 *   <li>Porcentajes de cotización obligatoria (Salud y Pensión)</li>
 *   <li>Porcentajes de cotización voluntaria (CCF)</li>
 *   <li>Límites para el FSP</li>
 * </ul>
 *
 * @author Luis Miguel Triana Rueda
 * @version 1.0
 * @since 2025-01-01
 */
public class ConstantesSeguridadSocial {

    /**
     * Salario Mínimo Mensual Legal Vigente (SMMLV) para el año 2025.
     * Valor: $1,423,500 COP
     */
    public static final double SMMLV = 1_423_500;

    /**
     * Límite mínimo para el cálculo del IBC.
     * Equivale a 1 SMMLV.
     * Valor: $1,423,500 COP
     */
    public static final double MIN_CALCULO_IBC = 1_423_500;

    /**
     * Límite máximo para el cálculo del IBC.
     * Equivale a 25 SMMLV.
     * Valor: $35,587,500 COP
     */
    public static final double MAX_CALCULO_IBC = 35_587_500;

    /**
     * Porcentaje base para calcular el IBC de trabajadores independientes.
     * El IBC se calcula como el 40% del ingreso mensual.
     * Valor: 0.40 (40%)
     */
    public static final double IBC = 0.40;

    /**
     * Porcentaje de cotización obligatoria a salud.
     * Valor: 0.125 (12.5%)
     */
    public static final double SALUD = 0.125;

    /**
     * Porcentaje de cotización obligatoria a pensión.
     * Valor: 0.16 (16%)
     */
    public static final double PENSION = 0.16;

    /**
     * Límite mínimo en SMMLV para el inicio de cotización al FSP.
     * Por debajo de este valor no se aplica FSP.
     * Valor: 4.0 SMMLV
     */
    public static final double LIMITE_FSP_MINIMO_SMMLV = 4.0;

    /**
     * Porcentaje de cotización voluntaria básica a CCF.
     * Valor: 0.006 (0.6%)
     */
    public static final double PORCENTAJE_CCF_BAJO = 0.006;

    /**
     * Porcentaje de cotización voluntaria completa a CCF.
     * Valor: 0.02 (2.0%)
     */
    public static final double PORCENTAJE_CCF_ALTO = 0.02;

    /**
     * Constructor privado para prevenir la instanciación.
     * Esta es una clase de utilidad que solo debe contener constantes estáticas.
     *
     * @throws UnsupportedOperationException si se intenta instanciar
     */
    private ConstantesSeguridadSocial() {
        throw new UnsupportedOperationException("Esta es una clase de utilidad y no debe ser instanciada");
    }

}
