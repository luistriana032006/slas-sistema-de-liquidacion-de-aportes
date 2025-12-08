package com.luistriana.developer.slas_sistema_de_liquidacion_de_aportes.model;

/**
 * Enumeración de niveles de riesgo laboral para ARL (Administradora de Riesgos Laborales).
 * <p>
 * Define los cinco niveles de riesgo establecidos por la normativa colombiana,
 * cada uno con su porcentaje de cotización correspondiente.
 * </p>
 *
 * <p><strong>Niveles y porcentajes:</strong></p>
 * <ul>
 *   <li>NIVEL_I: 0.522% - Riesgo mínimo (ej: actividades administrativas)</li>
 *   <li>NIVEL_II: 1.044% - Riesgo bajo (ej: comercio, algunos servicios)</li>
 *   <li>NIVEL_III: 2.436% - Riesgo medio (ej: manufactura, algunos procesos industriales)</li>
 *   <li>NIVEL_IV: 4.350% - Riesgo alto (ej: construcción, minería de superficie)</li>
 *   <li>NIVEL_V: 6.960% - Riesgo máximo (ej: minería subterránea, manejo de explosivos)</li>
 * </ul>
 *
 * @author Luis Miguel Triana Rueda
 * @version 1.0
 * @since 2025-01-01
 */
public enum RiesgoLaboralARL {

    /** Nivel I - Riesgo mínimo (0.522%) */
    NIVEL_I(0.522),

    /** Nivel II - Riesgo bajo (1.044%) */
    NIVEL_II(1.044),

    /** Nivel III - Riesgo medio (2.436%) */
    NIVEL_III(2.436),

    /** Nivel IV - Riesgo alto (4.350%) */
    NIVEL_IV(4.350),

    /** Nivel V - Riesgo máximo (6.960%) */
    NIVEL_V(6.960);

    /**
     * Porcentaje de cotización asociado al nivel de riesgo.
     */
    private final Double porcentaje;

    /**
     * Constructor privado del enum.
     *
     * @param porcentaje porcentaje de cotización para este nivel de riesgo
     */
    private RiesgoLaboralARL(Double porcentaje) {
        this.porcentaje = porcentaje;
    }

    /**
     * Obtiene el porcentaje de cotización para este nivel de riesgo.
     *
     * @return porcentaje de cotización a ARL
     */
    public Double getPorcentaje() {
        return porcentaje;
    }

}
