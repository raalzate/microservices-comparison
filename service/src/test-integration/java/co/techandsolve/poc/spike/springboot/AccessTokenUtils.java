package co.techandsolve.poc.spike.springboot;

/**
 * Utilizar el proyecto:
 *
 * https://vostpmde37.proteccion.com.co:10443/RALZATE/auth-demo-angular1.git
 *
 * para generar el token.
 *
 * Created by Raul A. Alzate <raul.alzate@techandsolve.com>  on 23/08/2017.
 */
public class AccessTokenUtils {

    private static final String TOKEN_JWT = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6IlZXVkljMVdEMVRrc2JiMzAxc2FzTTVrT3E1USIsImtpZCI6IlZXVkljMVdEMVRrc2JiMzAxc2FzTTVrT3E1USJ9.eyJhdWQiOiI2OWY0NmE5NS01NWNlLTQxMmQtOGNmYy04YzhiMGVmMThkYmMiLCJpc3MiOiJodHRwczovL3N0cy53aW5kb3dzLm5ldC9mNDdkY2NlNC0wMmU3LTRkODgtYTY1OS1hMmQwYmJlMTcwZTcvIiwiaWF0IjoxNTAzNjc5Mzk4LCJuYmYiOjE1MDM2NzkzOTgsImV4cCI6MTUwMzY4MzI5OCwiYWlvIjoiQVNRQTIvOEVBQUFBTWRyNzFLTnZrT1hIMkpONjR1M0hsWWowRys2UmxHRjVPRUdmYWZSbXZhVT0iLCJhbXIiOlsicHdkIl0sImZhbWlseV9uYW1lIjoiRmxvcmV6IiwiZ2l2ZW5fbmFtZSI6IkRhbmllbCIsImluX2NvcnAiOiJ0cnVlIiwiaXBhZGRyIjoiMTkwLjAuOC4xMzQiLCJuYW1lIjoiRGFuaWVsIEZsb3JleiIsIm5vbmNlIjoiNzFiMWIwMDctMzMwYS00MTU5LWI0OWItMjE0MjVkM2E0NDMxIiwib2lkIjoiMDUzYjllODItNjQ0Zi00Y2M4LWJlNzUtNWU0ZTYyNTUyYmEzIiwib25wcmVtX3NpZCI6IlMtMS01LTIxLTk5MTA5NDU4My0yMjM4MzMxMDk5LTQyODQzMjIwMDItMTExNzEzIiwic3ViIjoiUU1mM3FHU2U5dkZjUkhxRjJ5azZvQlVLVVVGX21XN3F1S2hIZ1hLRWJjQSIsInRpZCI6ImY0N2RjY2U0LTAyZTctNGQ4OC1hNjU5LWEyZDBiYmUxNzBlNyIsInVuaXF1ZV9uYW1lIjoiREZMT1JFWkBwcm90ZWNjaW9uLmNvbS5jbyIsInVwbiI6IkRGTE9SRVpAcHJvdGVjY2lvbi5jb20uY28iLCJ2ZXIiOiIxLjAifQ.n-yqbGJb2wwjG1zZxliy0D_s9Amks_XVGaw4M6ae6auGDrGhJO9dN9iQ5i8DtL2c7kzKX1P9MaAd68l9mh2PXXejlrDcqx56jKp_bxOOu9wX886ep8C-rhnSRoJ45MADYJ5vBj99k396D4NSAaa7QbZKSld2K6o9hQ6lnxW5_plwJWvR71zuEhgcdVc4AcUNIhtGEDG3J5F4LBsHGylKQ7yHHbVSHnpCEQZXXMvNfNGifZcybI4H2Qvou8wfLCib6wd4TDHUVvnCEXBwqJEuC16f4rUEqra7bpf2var0TZsnpNlIap5NrDmJTmxtRjSJ9ELquJKaqCjCuE5HaarPkA";

    public static String getToken() {
        return String.format("Bearer %s", TOKEN_JWT);
    }
}
