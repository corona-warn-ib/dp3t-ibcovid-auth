package es.caib.dp3t.ibcovid.auth.service.provider;

import org.springframework.security.core.Authentication;

public interface UserAuthProvider {

    Authentication loginUser(final String username, final String password);

}
