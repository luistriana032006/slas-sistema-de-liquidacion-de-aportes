package com.luistriana.developer.slas_sistema_de_liquidacion_de_aportes.exception;

/**
 * Excepción personalizada para indicar datos inválidos o inconsistentes en las solicitudes.
 * <p>
 * Esta excepción se lanza cuando los datos de entrada no cumplen con las reglas de negocio
 * o presentan inconsistencias, como:
 * </p>
 * <ul>
 *   <li>Ingresos menores o iguales a cero</li>
 *   <li>Indicar que aporta a CCF pero no especificar el porcentaje</li>
 *   <li>Enviar un porcentaje CCF sin indicar que desea aportar</li>
 *   <li>Porcentajes CCF diferentes a 0.6 o 2.0</li>
 *   <li>Indicar que aporta a ARL pero no especificar el nivel de riesgo</li>
 *   <li>Enviar un nivel de riesgo sin indicar que desea aportar a ARL</li>
 * </ul>
 *
 * <p>
 * Esta excepción extiende {@link RuntimeException}, por lo que es una excepción no verificada
 * (unchecked) y no requiere ser declarada en la firma del método.
 * </p>
 *
 * @author Luis Miguel Triana Rueda
 * @version 1.0
 * @since 2025-01-01
 * @see RuntimeException
 */
public class datosInvalidosException extends RuntimeException {

    /**
     * Constructor que crea una nueva excepción con el mensaje especificado.
     *
     * @param message mensaje descriptivo del error de validación
     */
    public datosInvalidosException(String message) {
        super(message);
    }
}
