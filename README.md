# Arquitectura de Referencia de Protección S.A. Para construcción de Microservicios

Este repositorio es una plantilla para desarrollar aplicaciones estilo microservicios basado
en una una arquitectura basada en el manifiesto Reactivo y con preferencia hacia las aplicaciones 
autocontenidas. 

Puede ver la presentación de la Arquitectura de Referencia de Protección 2017 [Aquí](https://proteccion.sharepoint.com/sites/ArquitecturaEmpresarial/ADT/_layouts/15/guestaccess.aspx?docid=0bc183d75596a47629e98d4db1bc118ae&authkey=Ac4_k5J1lcAhS0cp70KpywQ).

## Dependencias:

La arquitectura como código, representada por este repositorio esta basado en los siguientes componentes:

  - Proyecto de Autorización de servicios web con **Jano-security**
  - Spring Boot para la creación de microservicios
  - Spring Reactor para la materialización del manifiesto Reactivo
 
### Comandos para Gradle:
  - **bootRun**: Corre la aplicación.
  - **test**: Corre los test unitarios.
  - **testIntegration**: Corre los test de integración (tener en cuenta que es necesario cambiar el token de autenticación, para esto existe una aplicación que ayuda a generar este token).
  - **jacocoTestCoverageVerification**: verificar la cobertura de codigo y el número de líneas permitidas.

## Proyecto demo Auth

Usar para generar Token y datos para las pruebas integrales.

https://vostpmde37.proteccion.com.co:10443/RALZATE/auth-demo-angular1.git