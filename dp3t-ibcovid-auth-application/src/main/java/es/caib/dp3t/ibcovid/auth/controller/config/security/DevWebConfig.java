package es.caib.dp3t.ibcovid.auth.controller.config.security;

import es.caib.dp3t.ibcovid.auth.controller.filter.JWTAuthorizationFilter;
import es.caib.dp3t.ibcovid.auth.service.provider.JwtAuthTokenProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import static es.caib.dp3t.ibcovid.auth.controller.config.RouteConstants.ACTUATOR_BASE_PATH;
import static es.caib.dp3t.ibcovid.auth.controller.config.RouteConstants.ADMIN_PATH;
import static es.caib.dp3t.ibcovid.auth.controller.config.RouteConstants.AUTHORIZE_PATH;
import static es.caib.dp3t.ibcovid.auth.controller.config.RouteConstants.H2_CONSOLE_PATH;
import static es.caib.dp3t.ibcovid.auth.controller.config.RouteConstants.LOGIN_PATH;

@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Configuration
@Profile("dev")
public class DevWebConfig extends BaseWebConfig {

    private final JwtAuthTokenProvider jwtAuthTokenProvider;

    public DevWebConfig(final JwtAuthTokenProvider jwtAuthTokenProvider) {
        this.jwtAuthTokenProvider = jwtAuthTokenProvider;
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(antPatternUrlStartsWith(ADMIN_PATH)).authenticated()
                .antMatchers(AUTHORIZE_PATH).authenticated()
                .antMatchers(LOGIN_PATH,
                        antPatternUrlStartsWith(ACTUATOR_BASE_PATH),
                        antPatternUrlStartsWith(H2_CONSOLE_PATH)).permitAll()
                .anyRequest().denyAll().and()
                .addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtAuthTokenProvider))
                .cors().and()
                .csrf().disable()
                .headers().frameOptions().disable().and()
                .formLogin().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

}
