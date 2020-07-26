package es.caib.dp3t.ibcovid.auth.service.provider;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Optional;

public interface JwtAuthTokenProvider {

    String create(final Authentication auth);

    boolean requiresAuthentication(final String header);

    boolean validate(final String token);

    Optional<UsernamePasswordAuthenticationToken> validateAndGetCredentials(final String token);

    String getUsername(final String token);

    Collection<SimpleGrantedAuthority> getRoles(final String token);

}
