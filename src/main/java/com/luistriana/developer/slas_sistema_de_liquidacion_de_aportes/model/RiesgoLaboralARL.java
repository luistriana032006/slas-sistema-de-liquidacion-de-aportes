package com.luistriana.developer.slas_sistema_de_liquidacion_de_aportes.model;

public enum RiesgoLaboralARL {

    NIVEL_I(0.522),
    NIVEL_II(1.044),
    NIVEL_III(2.436),
    NIVEL_IV(4.350),
    NIVEL_V(6.960);

    // variable propia para cada enum
    private final Double porcentaje;

    // constructor privado propio de la clase
    private RiesgoLaboralARL(Double porcentaje) {
        this.porcentaje = porcentaje;
    }

    // getter para llamar el valor
    public Double getPorcentaje() {
        return porcentaje;
    }

}
