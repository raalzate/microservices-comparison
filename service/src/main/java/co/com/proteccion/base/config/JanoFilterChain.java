package co.com.proteccion.base.config;

import java.util.HashMap;
import java.util.Map;

/**
 * Esta interfaz basicamente se encarga de definir los filtros o las condiciones
 * para proteger la API
 * <p>
 * <p>
 * Created by Raul A. Alzate <raul.alzate@techandsolve.com>  on 16/08/2017.
 */
interface JanoFilterChain {
    static Map<String, String> getDefinitionMap() {
        Map<String, String> filterChainDefinitionMap = new HashMap<>();

        //TODO: el equipo de desarrollo debe indicar la relacion de las URL base que se deben asegurar
        //TODO: el detalle se debe configurar en el archivo janoRestrictions.json o en el modelo de datos

        filterChainDefinitionMap.put("/task/**", "noSessionCreation, restAutnFilter");
        filterChainDefinitionMap.put("/tasks/**", "noSessionCreation, restAutnFilter");
        filterChainDefinitionMap.put("/index", "noSessionCreation, resAutzFilter");
        filterChainDefinitionMap.put("/application/**", "noSessionCreation, anon");

        return filterChainDefinitionMap;
    }
}
