package com.luistriana.developer.slas_sistema_de_liquidacion_de_aportes.model;

/**
 * Enumeración de rangos del Fondo de Solidaridad Pensional (FSP).
 * <p>
 * Define los rangos de IBC expresados en SMMLV (Salarios Mínimos Mensuales Legales Vigentes)
 * y sus respectivos porcentajes de cotización al FSP, según la normativa colombiana.
 * </p>
 *
 * <p><strong>Tabla de rangos y porcentajes:</strong></p>
 * <ul>
 *   <li>Hasta 4 SMMLV: 0%</li>
 *   <li>Más de 4 hasta 16 SMMLV: 1%</li>
 *   <li>Más de 16 hasta 17 SMMLV: 1.2%</li>
 *   <li>Más de 17 hasta 18 SMMLV: 1.4%</li>
 *   <li>Más de 18 hasta 19 SMMLV: 1.6%</li>
 *   <li>Más de 19 hasta 20 SMMLV: 1.8%</li>
 *   <li>Más de 20 SMMLV: 2%</li>
 * </ul>
 *
 * @author Luis Miguel Triana Rueda
 * @version 1.0
 * @since 2025-01-01
 */
public enum AportesFondoSolidarioPensionesFSP {
    /** Hasta 4 SMMLV - Sin aporte (0%) */
    HASTA_4_SMMLV(0.0),

    /** Más de 4 hasta 16 SMMLV - 1% */
    MAS_DE_4_HASTA_16SMMLV(0.01),

    /** Más de 16 hasta 17 SMMLV - 1.2% */
    MAS_DE_16_HASTA_17SMMLV(0.012),

    /** Más de 17 hasta 18 SMMLV - 1.4% */
    MAS_DE_17_HASTA_18SMMLV(0.014),

    /** Más de 18 hasta 19 SMMLV - 1.6% */
    MAS_DE_18_HASTA_19SMMLV(0.016),

    /** Más de 19 hasta 20 SMMLV - 1.8% */
    MAS_DE_19_HASTA_20(0.018),

    /** Más de 20 SMMLV - 2% */
    MAS_DE_20(0.02);

    /**
     * Porcentaje de cotización al FSP para este rango.
     */
    private final Double porcentaje;

    /**
     * Constructor privado del enum.
     *
     * @param porcentaje porcentaje de cotización para este rango
     */
    private AportesFondoSolidarioPensionesFSP(Double porcentaje) {
        this.porcentaje = porcentaje;
    }

    /**
     * Obtiene el porcentaje de cotización para este rango.
     *
     * @return porcentaje de cotización al FSP
     */
    public Double getPorcentaje() {
        return porcentaje;
    }

    /**
     * Determina el rango de FSP correspondiente según el IBC en SMMLV.
     * <p>
     * Este método utiliza el patrón Early Return para evaluar los rangos
     * de menor a mayor y retornar el rango apropiado.
     * </p>
     *
     * @param ibcEnSMMLV IBC del trabajador expresado en SMMLV
     * @return el rango de FSP correspondiente
     */
    public static AportesFondoSolidarioPensionesFSP ObtenerRango(double ibcEnSMMLV) {
        if (ibcEnSMMLV <= 4)
            return HASTA_4_SMMLV;
        if (ibcEnSMMLV <= 16)
            return MAS_DE_4_HASTA_16SMMLV;
        if (ibcEnSMMLV <= 17)
            return MAS_DE_16_HASTA_17SMMLV;
        if (ibcEnSMMLV <= 18)
            return MAS_DE_17_HASTA_18SMMLV;
        if (ibcEnSMMLV <= 19)
            return MAS_DE_18_HASTA_19SMMLV;
        if (ibcEnSMMLV <= 20)
            return MAS_DE_19_HASTA_20;

        return MAS_DE_20;
    }
}
