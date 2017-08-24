package co.techandsolve.poc.spike.springboot;

/**
 * Created by admin on 23/08/2017.
 */
public class AccessTokenUtils {

    private static final String TOKEN_JWT = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6IlZXVkljMVdEMVRrc2JiMzAxc2FzTTVrT3E1USIsImtpZCI6IlZXVkljMVdEMVRrc2JiMzAxc2FzTTVrT3E1USJ9.eyJhdWQiOiI2OWY0NmE5NS01NWNlLTQxMmQtOGNmYy04YzhiMGVmMThkYmMiLCJpc3MiOiJodHRwczovL3N0cy53aW5kb3dzLm5ldC9mNDdkY2NlNC0wMmU3LTRkODgtYTY1OS1hMmQwYmJlMTcwZTcvIiwiaWF0IjoxNTAzNjAwOTE1LCJuYmYiOjE1MDM2MDA5MTUsImV4cCI6MTUwMzYwNDgxNSwiYWlvIjoiWTJGZ1lLZ0lmV1R2V1BwdDQrOFRuR1dsWjRwWndubGZNS3FlN0xVV3RKTTdYUi9MZXdRQSIsImFtciI6WyJwd2QiXSwiZmFtaWx5X25hbWUiOiJGbG9yZXoiLCJnaXZlbl9uYW1lIjoiRGFuaWVsIiwiaW5fY29ycCI6InRydWUiLCJpcGFkZHIiOiIxOTAuMC44LjEzNCIsIm5hbWUiOiJEYW5pZWwgRmxvcmV6Iiwibm9uY2UiOiIzNzIwMTJjMS0yZDJkLTQzOGYtOTlmMS03NWYwNWJhZjQxMDEiLCJvaWQiOiIwNTNiOWU4Mi02NDRmLTRjYzgtYmU3NS01ZTRlNjI1NTJiYTMiLCJvbnByZW1fc2lkIjoiUy0xLTUtMjEtOTkxMDk0NTgzLTIyMzgzMzEwOTktNDI4NDMyMjAwMi0xMTE3MTMiLCJzdWIiOiJRTWYzcUdTZTl2RmNSSHFGMnlrNm9CVUtVVUZfbVc3cXVLaEhnWEtFYmNBIiwidGlkIjoiZjQ3ZGNjZTQtMDJlNy00ZDg4LWE2NTktYTJkMGJiZTE3MGU3IiwidW5pcXVlX25hbWUiOiJERkxPUkVaQHByb3RlY2Npb24uY29tLmNvIiwidXBuIjoiREZMT1JFWkBwcm90ZWNjaW9uLmNvbS5jbyIsInZlciI6IjEuMCJ9.P9zm-2hhENvOBNleVdjdb-QnhyVyfm0zLZOAXvn-63vZRlF9uQTzjaZft5dX_HcYlEsVPz9Ta1WPuu5DWVVVS1hiT4QzBftj0NriA1wBqPdntErptVm5nwxC6HhswYFXX0GEa9oZkrp2pkweznB22fLz7V4SQwKtljQiPbMohp9KPG3awP80ROmGWr1cQcKyJlZm0UM6lAT8kFLZCqDluVr2iVaAGj31jDx2-e6Rsgh2ZUOW3WPha3tl9NUkPX5B6Lqp3OGYupTvVwFgZVPxj4xwFekFZ9QhB1JPOUe_fdUbY2bBpO3OboiU4pbvVcUMqVUCOpEjemJdLdDWa3IKXQ";

    public static String getToken() {
        return String.format("Bearer %s", TOKEN_JWT);
    }
}