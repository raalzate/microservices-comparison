package co.techandsolve.poc.spike.springboot.config;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 16/08/2017.
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
