package com.luistriana.developer.slas_sistema_de_liquidacion_de_aportes.model;

public enum AportesFondoSolidarioPensionesFSP {
    HASTA_4_SMMLV(0.0),
    MAS_DE_4_HASTA_16SMMLV(1.0),
    MAS_DE_16_HASTA_17SMMLV(1.2),
    MAS_DE_17_HASTA_18SMMLV(1.4),
    MAS_DE_18_HASTA_19SMMLV(1.6),
    MAS_DE_19_HASTA_20(1.8),
    MAS_DE_20(2.0);

    // variable propia para cada enum
    private final Double porcentaje;

    // constructor privado propio de la clase
    private AportesFondoSolidarioPensionesFSP(Double porcentaje) {
        this.porcentaje = porcentaje;
    }

    // getter para llamar el valor
    public Double getPorcentaje() {
        return porcentaje;
    }

    // metodo estatico para validar segun su min y max segun el smmlv
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
//TODO: BUSCAR QUE ES Early Return Pattern Y EN DONDE ENCONTRAR MAS INFORMACION SOBRE ESOS PATRONES
}
