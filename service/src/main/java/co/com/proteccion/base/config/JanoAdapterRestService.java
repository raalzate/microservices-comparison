package co.com.proteccion.base.config;

import co.com.proteccion.jano.core.model.UserLogin;
import co.com.proteccion.jano.shiro.service.resources.AuthResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

/**
 * Clase para adaptar los servicios de Jano para la autenticación por servicio rest
 *
 * Created by Raul A. Alzate <raul.alzate@techandsolve.com>  on 23/08/2017.
 */

@RestController
public class JanoAdapterRestService extends AuthResource {

    @Autowired
    private HttpServletRequest request;

    @GetMapping(value = "/jano/auth")
    ResponseEntity<?> login(@RequestParam(value = "token", required = false) String token) {
        Response response = super.loginWithTokenJWT(token, request);
        return ResponseEntity.ok(response.getEntity());
    }

    @PostMapping(value = "/jano/auth")
    ResponseEntity<?> loginByUser(@RequestBody UserLogin userLogin) {
        Response response = super.loginWithObjectLogin(userLogin, request);
        return ResponseEntity.ok(response.getEntity());
    }
}
