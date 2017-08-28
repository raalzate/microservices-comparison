# Seed - Protección Microservicio


El siguiente proyecto consiste en desarrollar una arquitectura de referencia que integre el sistema de seguridad de protección y con la arquitectura estudiada para las aplicación de Protección. La arquitectura debe cumplir con el manifiesto Reactivo y con el diseño de aplicaciones autocontenidas para una arquitectura por microservicios. 

### Dependencias:

  - Jano-security
  - Spring Boot
  - Flywaydb
 
### Comandos para Gradle:
  - **bootRun**: Corre la aplicación.
  - **test**: Corre los test unitarios.
  - **testIntegration**: Corre los test de integración (tener en cuenta que es necesario cambiar el token de autenticación, para esto existe una aplicación que ayuda a generar este token).
  - **jacocoTestCoverageVerification**: verificar la cobertura de codigo y el número de líneas permitidas.

## Proyecto demo Auth

Usar para generar Token y datos para las pruebas integrales.

https://vostpmde37.proteccion.com.co:10443/RALZATE/auth-demo-angular1.git

## Crear keysotre para SSL

 keytool -genkey -alias tomcat -storetype PKCS12 -keyalg RSA -keysize 2048 -keystore keystore.p12 -validity 3650
 