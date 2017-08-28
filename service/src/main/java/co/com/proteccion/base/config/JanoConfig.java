package co.com.proteccion.base.config;

import co.com.proteccion.jano.core.SecuredApplication;
import co.com.proteccion.jano.core.datasource.FlatFileConnection;
import co.com.proteccion.jano.core.service.auth.EmpleadosIdentityRepository;
import co.com.proteccion.jano.shiro.filter.ResourceAuthorizationFilter;
import co.com.proteccion.jano.shiro.filter.RestAPIAuthenticationFilter;
import co.com.proteccion.jano.shiro.realm.EmpleadosAuthenticationRealm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Value;
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
 * Esta clase sustituye al archivo Shiro.ini.
 *
 * Es la encargada de realizar la configuracion de los Beans de Shiro.
 *
 * Configura los Beands, Realms y Filtros
 *
 * Created by Raul A. Alzate <raul.alzate@techandsolve.com>  on 14/08/2017.
 */
@Configuration
public class JanoConfig {

    private SecuredApplication securedApplication;

    @Bean(name = "securityManager")
    public DefaultWebSecurityManager securityManager() {
        final DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(empleadosAuthenticationRealm());
        ThreadContext.bind(securityManager);
        return securityManager;
    }


    @Bean(name = "realm")
    @DependsOn("lifecycleBeanPostProcessor")
    public EmpleadosAuthenticationRealm empleadosAuthenticationRealm() {
        EmpleadosAuthenticationRealm azureAuthenticationRealm = new EmpleadosAuthenticationRealm();
        azureAuthenticationRealm.setJanoIdentityRepository(getEmpleadosIdentityRepository());
        return azureAuthenticationRealm;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }


    @Bean(name = "shiroFilterFactoryBean")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(
            @Value("${securedApplication.clientId}") String clientId,
            @Value("${securedApplication.name}") String name,
            @Value("${securedApplication.tenant}") String tenant,
            @Value("${securedApplication.clientSecret}") String clientSecret) {

        securedApplication = new SecuredApplication();
        securedApplication.setName(name);
        securedApplication.setAllowOrigin("*");
        securedApplication.setClientId(clientId);
        securedApplication.setTenant(tenant);
        securedApplication.setClientSecret(clientSecret);
        securedApplication.setGenericDataSource(getFlatFileConnection());

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

        //TODO: El equipo de desarrollo debe relacionar otros filtros de Shiro o de Jano que pueda requerir la aplicacion

        return filters;
    }

    private EmpleadosIdentityRepository getEmpleadosIdentityRepository() {
        EmpleadosIdentityRepository empleadosIdentityRepository = new EmpleadosIdentityRepository();
        empleadosIdentityRepository.setSecuredApplication(securedApplication);
        return empleadosIdentityRepository;
    }


    private FlatFileConnection getFlatFileConnection() {
        FlatFileConnection flatFileConnection = new FlatFileConnection();
        String pathConfig = Optional.ofNullable(JanoConfig.class.getClassLoader().getResource("application.yml"))
                .filter(Objects::nonNull)
                .map(URL::getPath)
                .map(path -> path.replace("/application.yml", ""))
                .orElse(null);
        flatFileConnection.setFilePath(pathConfig);
        return flatFileConnection;
    }
}
