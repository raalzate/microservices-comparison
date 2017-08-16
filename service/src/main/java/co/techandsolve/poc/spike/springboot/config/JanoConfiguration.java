package co.techandsolve.poc.spike.springboot.config;

import co.com.proteccion.jano.SecuredApplication;
import co.com.proteccion.jano.shiro.filter.ResourceAuthorizationFilter;
import co.com.proteccion.jano.shiro.filter.RestAPIAuthenticationFilter;
import co.com.proteccion.jano.shiro.realm.AzureAuthenticationRealm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 14/08/2017.
 */
@Configuration
public class JanoConfiguration {

    @Autowired
    private SecuredApplication securedApplication;

    @Bean(name = "securityManager")
    public DefaultWebSecurityManager securityManager() {
        final DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(getAzureAuthenticationRealm());
        ThreadContext.bind(securityManager);
        return securityManager;
    }


    @Bean(name = "realm")
    @DependsOn("lifecycleBeanPostProcessor")
    public AzureAuthenticationRealm getAzureAuthenticationRealm() {
        AzureAuthenticationRealm azureAuthenticationRealm = new AzureAuthenticationRealm();
        azureAuthenticationRealm.setSecuredApplication(securedApplication);
        return azureAuthenticationRealm;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }


    @Bean(name = "shiroFilterFactoryBean")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean() {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager());
        shiroFilterFactoryBean.setFilters(getFilters());
        shiroFilterFactoryBean.setFilterChainDefinitionMap(JanoFilterChain.getDefinitionMap());
        return shiroFilterFactoryBean;
    }


    private Map<String, Filter> getFilters() {
        Map<String, Filter> filters = new HashMap<>();
        RestAPIAuthenticationFilter restAPIAuthenticationFilter = new RestAPIAuthenticationFilter();
        ResourceAuthorizationFilter resourceAuthorizationFilter = new ResourceAuthorizationFilter();

        restAPIAuthenticationFilter.setSecuredApplication(securedApplication);
        restAPIAuthenticationFilter.setValidacionExplicitaDeIPs(false);
        resourceAuthorizationFilter.setSecuredApplication(securedApplication);

        filters.put("restAutnFilter", restAPIAuthenticationFilter);
        filters.put("resAutzFilter", resourceAuthorizationFilter);
        return filters;
    }
}
