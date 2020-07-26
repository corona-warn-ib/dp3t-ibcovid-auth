package es.caib.dp3t.ibcovid.auth.controller.filter;

import es.caib.dp3t.ibcovid.auth.service.provider.JwtAuthTokenProvider;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Log4j2
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private final JwtAuthTokenProvider jwtAuthTokenProvider;

    public JWTAuthorizationFilter(final AuthenticationManager authenticationManager,
                                  final JwtAuthTokenProvider jwtAuthTokenProvider) {
        super(authenticationManager);
        this.jwtAuthTokenProvider = jwtAuthTokenProvider;
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest request,
                                    final HttpServletResponse response,
                                    final FilterChain chain)
            throws IOException, ServletException {
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (!jwtAuthTokenProvider.requiresAuthentication(header)) {
            chain.doFilter(request, response);
            return;
        }

        final Optional<UsernamePasswordAuthenticationToken> authentication =
                jwtAuthTokenProvider.validateAndGetCredentials(header);
        if (authentication.isPresent()) {
            SecurityContextHolder.getContext().setAuthentication(authentication.get());
        } else {
            log.warn("Request not authenticated");
            SecurityContextHolder.clearContext();
        }

        chain.doFilter(request, response);
    }

}
