package co.techandsolve.poc.spike.springboot.shiro;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 16/08/2017.
 */
interface ShiroFilterChain {
    static Map<String, String> getDefinitionMap() {
        Map<String, String> filterChainDefinitionMap = new HashMap<>();
        filterChainDefinitionMap.put("/**", "anon");
        filterChainDefinitionMap.put("/things", "authc,roles[admin]");

        return filterChainDefinitionMap;
    }
}
