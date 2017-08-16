package co.techandsolve.poc.spike.springboot.config;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 16/08/2017.
 */
interface JanoFilterChain {
    static Map<String, String> getDefinitionMap() {
        Map<String, String> filterChainDefinitionMap = new HashMap<>();
        filterChainDefinitionMap.put("/**", "noSessionCreation, anon");
     //   filterChainDefinitionMap.put("/jano/auth", "noSessionCreation, anon");
      //  filterChainDefinitionMap.put("/things", "noSessionCreation, restAutnFilter");

        return filterChainDefinitionMap;
    }
}
