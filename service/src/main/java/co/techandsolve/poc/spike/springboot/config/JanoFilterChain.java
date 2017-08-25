package co.techandsolve.poc.spike.springboot.config;

import java.util.HashMap;
import java.util.Map;

/**
 * Esta interfaz basicamente se encarga de definir los filtros o las condiciones
 * para proteger la API
 *
 *
 * Created by Raul A. Alzate <raul.alzate@techandsolve.com>  on 16/08/2017.
 */
interface JanoFilterChain {
    static Map<String, String> getDefinitionMap() {
        Map<String, String> filterChainDefinitionMap = new HashMap<>();
        filterChainDefinitionMap.put("/jano/auth", "noSessionCreation, anon");
        filterChainDefinitionMap.put("/task/**", "noSessionCreation, restAutnFilter");
        filterChainDefinitionMap.put("/tasks/**", "noSessionCreation, restAutnFilter");
        filterChainDefinitionMap.put("/index", "noSessionCreation, resAutzFilter");
        filterChainDefinitionMap.put("/application/**", "noSessionCreation, anon");

        return filterChainDefinitionMap;
    }
}
