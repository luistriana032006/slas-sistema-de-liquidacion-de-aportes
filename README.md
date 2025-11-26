# SLAS - Sistema de Liquidación de Aportes a Seguridad Social

> **Estado del Proyecto:** 🚧 En Desarrollo Activo

Sistema backend desarrollado en Spring Boot para el cálculo y liquidación automatizada de aportes a la seguridad social en Colombia, incluyendo salud, pensión, ARL y parafiscales.

## Descripción

SLAS es una aplicación REST API que facilita el cálculo preciso de las contribuciones a la seguridad social según la normativa colombiana vigente. El sistema permite calcular aportes basándose en los ingresos mensuales del trabajador, aplicando las tarifas y límites establecidos por ley.

### Características Principales (En Desarrollo)

- ✅ Cálculo de aportes a salud (12.5%)
- ✅ Cálculo de aportes a pensión (16%)
- ✅ Cálculo de aportes a Riesgos Laborales (ARL) según nivel de riesgo
- ✅ Cálculo de Fondo de Solidaridad Pensional (FSP)
- 🚧 Gestión de usuarios y empleados
- 🚧 Cálculo de Caja de Compensación Familiar (CCF)
- 🚧 Generación de reportes y certificados
- 🚧 API REST completa

## Tecnologías

- **Java 21**
- **Spring Boot 3.5.8**
  - Spring Web
  - Spring Validation
- **Lombok** (reducción de código boilerplate)
- **Maven** (gestión de dependencias)

## Requisitos Previos

Antes de ejecutar el proyecto, asegúrate de tener instalado:

- Java JDK 21 o superior
- Maven 3.6+
- IDE recomendado: IntelliJ IDEA o Eclipse

## Instalación

1. **Clonar el repositorio**
   ```bash
   git clone https://github.com/tu-usuario/slas-sistema-de-liquidacion-de-aportes.git
   cd slas-sistema-de-liquidacion-de-aportes
   ```

2. **Compilar el proyecto**
   ```bash
   mvn clean install
   ```

3. **Ejecutar la aplicación**
   ```bash
   mvn spring-boot:run
   ```

La aplicación estará disponible en `http://localhost:8080`

## Estructura del Proyecto

```
src/main/java/com/luistriana/developer/slas_sistema_de_liquidacion_de_aportes/
├── constants/
│   └── ConstantesSeguridadSocial.java    # Constantes y tarifas legales
├── model/
│   ├── Usuario.java                       # Modelo de usuario (en desarrollo)
│   ├── AportesFondoSolidarioPensionesFSP.java  # Enum para FSP
│   ├── RiesgoLaboralARL.java             # Enum para niveles de riesgo ARL
│   └── dtos/
│       ├── request/
│       │   └── LiquidacionRequest.java   # DTO de entrada
│       └── response/
│           └── LiquidacionResponse.java  # DTO de salida
├── service/                               # Servicios de negocio (próximamente)
├── controller/                            # Controladores REST (próximamente)
└── SlasSistemaDeLiquidacionDeAportesApplication.java
```

## Modelo de Datos

### LiquidacionRequest

Request DTO para calcular liquidaciones:

```java
{
  "ingresosMensual": 5000000.0,
  "aporteARL": true,
  "aportaCCF": true,
  "nivelRiesgo": "RIESGO_III",
  "porcentajeCCF": 0.04
}
```

### Niveles de Riesgo ARL

- `RIESGO_I`: 0.522% - Actividades administrativas, financieras
- `RIESGO_II`: 1.044% - Comercio, algunos servicios
- `RIESGO_III`: 2.436% - Manufactura, talleres
- `RIESGO_IV`: 4.350% - Construcción, transporte
- `RIESGO_V`: 6.960% - Minería, alto riesgo

## Constantes del Sistema

El sistema utiliza las siguientes constantes basadas en la legislación colombiana:

| Constante | Valor | Descripción |
|-----------|-------|-------------|
| SMMLV | $1,423,500 | Salario Mínimo Mensual Legal Vigente |
| Salud | 12.5% | Aporte obligatorio a salud |
| Pensión | 16% | Aporte obligatorio a pensión |
| IBC Mínimo | 1 SMMLV | Ingreso Base de Cotización mínimo |
| IBC Máximo | 25 SMMLV | Ingreso Base de Cotización máximo |


## Contribución

Este es un proyecto personal en desarrollo. Las sugerencias y comentarios son bienvenidos.

## Licencia

Este proyecto está en desarrollo. La licencia se definirá próximamente.

## Notas Importantes

⚠️ **Este proyecto está en desarrollo activo.** Las funcionalidades y la API pueden cambiar sin previo aviso.

⚠️ Los valores de las constantes deben actualizarse anualmente según la normativa vigente.

## Contacto

Para consultas o sugerencias sobre el proyecto, puedes abrir un issue en el repositorio.

---

**Última actualización:** Noviembre 2025
