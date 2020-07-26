package es.caib.dp3t.ibcovid.auth.service.provider.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.caib.dp3t.ibcovid.auth.common.exception.AuthErrorCodes;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;
import es.caib.dp3t.ibcovid.auth.common.config.AppProperties;
import es.caib.dp3t.ibcovid.auth.common.exception.IBCovidAuthException;
import es.caib.dp3t.ibcovid.auth.common.utils.DateUtils;
import es.caib.dp3t.ibcovid.auth.service.provider.JwtAuthTokenProvider;
import es.caib.dp3t.ibcovid.auth.service.utils.CertificateStore;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.jackson2.SimpleGrantedAuthorityMixin;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
class JwtAuthTokenProviderImpl implements JwtAuthTokenProvider {
    private static final String ISSUER = "es.caib.dp3t.ibcovid-auth";
    private static final String TOKEN_PREFIX = "Bearer ";
    private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.RS256;

    private final AppProperties appProperties;
    private final ObjectMapper objectMapper;
    private final CertificateStore certificateStore;

    public JwtAuthTokenProviderImpl(final AppProperties appProperties,
                                    final ObjectMapper objectMapper,
                                    final CertificateStore certificateStore) {
        this.appProperties = appProperties;
        this.objectMapper = objectMapper;
        this.certificateStore = certificateStore;
    }

    @Override
    public String create(final Authentication auth) {
        final String username = auth.getName();
        final List<String> roles = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        final Claims claims = Jwts.claims();
        try {
            claims.put("authorities", objectMapper.writeValueAsString(roles));
        } catch (final JsonProcessingException e) {
            throw new IBCovidAuthException(AuthErrorCodes.GENERAL_ERROR, "Error while deserializing");
        }

        final Instant issueDate = Instant.now();
        final Instant expiryDate = issueDate.plus(appProperties.getJwtTimeToLive(), ChronoUnit.MINUTES);

        return Jwts.builder()
                .setIssuer(ISSUER)
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(Date.from(issueDate))
                .setExpiration(Date.from(expiryDate))
                .signWith(SIGNATURE_ALGORITHM, certificateStore.getPrivateKey())
                .compact();
    }

    @Override
    public boolean requiresAuthentication(final String header) {
        return StringUtils.startsWith(header, TOKEN_PREFIX);
    }

    @Override
    public boolean validate(final String token) {
        try {
            final Claims claims = getClaims(token);
            return StringUtils.equals(claims.getIssuer(), ISSUER)
                    && DateUtils.currentUTCDate().before(claims.getExpiration());
        } catch (final Exception e) {
            return false;
        }
    }

    @Override
    public Optional<UsernamePasswordAuthenticationToken> validateAndGetCredentials(final String token) {
        UsernamePasswordAuthenticationToken authentication;
        try {
            final Claims claims = getClaims(token);
            authentication = new UsernamePasswordAuthenticationToken(
                    getUsername(claims), getCredentials(claims), getRoles(claims));
        } catch (final Exception e) {
            authentication = null;
        }

        return Optional.ofNullable(authentication);
    }

    @Override
    public String getUsername(final String token) {
        return getUsername(getClaims(token));
    }

    @Override
    public Collection<SimpleGrantedAuthority> getRoles(final String token) {
        return getRoles(getClaims(token));
    }

    private Claims getClaims(final String token) {
        return Jwts.parser()
                .setSigningKey(certificateStore.getPublicKey())
                .parseClaimsJws(resolve(token))
                .getBody();
    }

    private String resolve(final String token) {
        if (StringUtils.startsWith(token, TOKEN_PREFIX)) {
            return token.replace(TOKEN_PREFIX, "");
        }
        return null;
    }

    private Object getCredentials(final Claims claims) {
        return null;
    }

    private String getUsername(final Claims claims) {
        return claims.getSubject();
    }

    private Collection<SimpleGrantedAuthority> getRoles(final Claims claims) {
        return deserializeRoles(claims.get("authorities"));
    }

    private Collection<SimpleGrantedAuthority> deserializeRoles(final Object roles) {
        try {
            return Arrays.stream(objectMapper.addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthorityMixin.class)
                    .readValue(roles.toString().getBytes(), String[].class))
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        } catch (final IOException e) {
            throw new IBCovidAuthException(AuthErrorCodes.GENERAL_ERROR, "Error while deserializing");
        }
    }

}
