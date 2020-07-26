package es.caib.dp3t.ibcovid.auth.controller.config.security;

import es.caib.dp3t.ibcovid.auth.controller.config.RouteConstants;
import es.caib.dp3t.ibcovid.auth.service.provider.JwtAuthTokenProvider;
import es.caib.dp3t.ibcovid.auth.controller.filter.JWTAuthorizationFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Configuration
@Profile({"pre | pro"})
public class ProWebConfig extends BaseWebConfig {

    private final JwtAuthTokenProvider jwtAuthTokenProvider;

    public ProWebConfig(final JwtAuthTokenProvider jwtAuthTokenProvider) {
        this.jwtAuthTokenProvider = jwtAuthTokenProvider;
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(antPatternUrlStartsWith(RouteConstants.ADMIN_PATH)).authenticated()
                .antMatchers(RouteConstants.AUTHORIZE_PATH).authenticated()
                .antMatchers(RouteConstants.LOGIN_PATH).permitAll()
                .anyRequest().denyAll().and()
                .addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtAuthTokenProvider))
                .cors().and()
                .csrf().disable()
                .formLogin().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

}
