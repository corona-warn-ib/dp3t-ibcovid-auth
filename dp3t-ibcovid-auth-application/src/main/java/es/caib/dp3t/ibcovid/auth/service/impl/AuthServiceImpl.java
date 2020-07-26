package es.caib.dp3t.ibcovid.auth.service.impl;

import es.caib.dp3t.ibcovid.auth.common.exception.AuthErrorCodes;
import es.caib.dp3t.ibcovid.auth.common.exception.IBCovidAuthException;
import es.caib.dp3t.ibcovid.auth.service.model.LoginRQSrvDto;
import es.caib.dp3t.ibcovid.auth.service.model.LoginTokenSrvDto;
import es.caib.dp3t.ibcovid.auth.service.provider.JwtAuthTokenProvider;
import lombok.extern.log4j.Log4j2;
import es.caib.dp3t.ibcovid.auth.service.AuthService;
import es.caib.dp3t.ibcovid.auth.service.provider.UserAuthProvider;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
class AuthServiceImpl implements AuthService {

    private final UserAuthProvider userAuthProvider;
    private final JwtAuthTokenProvider jwtAuthTokenProvider;

    public AuthServiceImpl(final UserAuthProvider userAuthProvider,
                           final JwtAuthTokenProvider jwtAuthTokenProvider) {
        this.userAuthProvider = userAuthProvider;
        this.jwtAuthTokenProvider = jwtAuthTokenProvider;
    }

    @Override
    @Transactional
    public LoginTokenSrvDto login(final LoginRQSrvDto srvRequest) {
        log.debug("BEGIN - login: params={}", srvRequest);

        final Authentication auth = userAuthProvider.loginUser(srvRequest.getUsername(), srvRequest.getPassword());

        if (auth == null || !auth.isAuthenticated()) {
            log.warn("User {} tried to login, but was not successful", srvRequest.getUsername());
            throw new IBCovidAuthException(AuthErrorCodes.FORBIDDEN);
        }

        final String token = jwtAuthTokenProvider.create(auth);

        final LoginTokenSrvDto response = LoginTokenSrvDto.builder()
                .token(token)
                .build();

        log.debug("END - login: response={}", response);
        return response;
    }

}
