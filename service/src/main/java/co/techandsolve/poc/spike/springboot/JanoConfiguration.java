package co.techandsolve.poc.spike.springboot;

import co.com.proteccion.jano.SecuredApplication;
import co.com.proteccion.jano.datasource.FlatFileConnection;
import co.com.proteccion.jano.shiro.filter.ResourceAuthorizationFilter;
import co.com.proteccion.jano.shiro.filter.RestAPIAuthenticationFilter;
import co.com.proteccion.jano.shiro.realm.AzureAuthenticationRealm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * Created by admin on 14/08/2017.
 */
@Configuration
public class JanoConfiguration {

    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilter() {
        SecuredApplication securedApp = getSecuredApplication();
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(securityManager(securedApp));
        factoryBean.setFilters(getFilters(securedApp));
        factoryBean.setFilterChainDefinitionMap(getFilterChainDefinitionMapping());
        return factoryBean;
    }

    @Bean(name = "securityManager")
    public DefaultWebSecurityManager securityManager(SecuredApplication securedApp) {
        final DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(getAzureAuthenticationRealm(securedApp));
        return securityManager;
    }


    @Bean(name = "realm")
    @DependsOn("lifecycleBeanPostProcessor")
    public AzureAuthenticationRealm getAzureAuthenticationRealm(SecuredApplication securedApp) {
        AzureAuthenticationRealm azureAuthenticationRealm = new AzureAuthenticationRealm();
        azureAuthenticationRealm.setSecuredApplication(securedApp);
        return azureAuthenticationRealm;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }


    private SecuredApplication getSecuredApplication() {
        SecuredApplication securedApplication = new SecuredApplication();
        securedApplication.setName("appSegura");
        securedApplication.setAllowOrigin("*");
        securedApplication.setClientId("3a8ba9aa-420c-4234-ad25-5d1d25dcb911");
        securedApplication.setTenant("f47dcce4-02e7-4d88-a659-a2d0bbe170e7");
        securedApplication.setClientSecret("W4gJjyUnI4Q1lCiUs1l3BLY85ReJcbHgW8wZ/EYLJik=");
        securedApplication.setGenericDataSource(getFlatFileConnection());
        return securedApplication;
    }

    private FlatFileConnection getFlatFileConnection(){
        FlatFileConnection flatFileConnection = new FlatFileConnection();
        String pathConfig = Optional.ofNullable(JanoConfiguration.class.getClassLoader().getResource("application.yml"))
                .filter(Objects::nonNull)
                .map(URL::getPath)
                .map(path -> path.replace("/application.yml", ""))
                .orElse(null);
        flatFileConnection.setFilePath(pathConfig);
        return flatFileConnection;
    }

    private Map<String,Filter> getFilters(SecuredApplication securedApp) {
        Map<String,Filter> filters = new HashMap<>();
        RestAPIAuthenticationFilter restAPIAuthenticationFilter = new RestAPIAuthenticationFilter();
        ResourceAuthorizationFilter resourceAuthorizationFilter = new ResourceAuthorizationFilter();

        restAPIAuthenticationFilter.setSecuredApplication(securedApp);
        restAPIAuthenticationFilter.setValidacionExplicitaDeIPs(false);
        resourceAuthorizationFilter.setSecuredApplication(securedApp);

        filters.put("restAutnFilter", restAPIAuthenticationFilter);
        filters.put("resAutzFilter", resourceAuthorizationFilter);
        return filters;
    }

    private Map<String,String> getFilterChainDefinitionMapping() {
        Map<String,String> filterChainDefinitionMapping = new HashMap<>();
        filterChainDefinitionMapping.put("/jano/auth", "noSessionCreation, anon");
        filterChainDefinitionMapping.put("/jano/**", "noSessionCreation, restAutnFilter");
        filterChainDefinitionMapping.put("/**", "noSessionCreation, restAutnFilter, resAutzFilter");
        return filterChainDefinitionMapping;
    }


}
