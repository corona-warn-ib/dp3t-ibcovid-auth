package es.caib.dp3t.ibcovid.auth.service;

import es.caib.dp3t.ibcovid.auth.service.model.LoginRQSrvDto;
import es.caib.dp3t.ibcovid.auth.service.model.LoginTokenSrvDto;

public interface AuthService {

    LoginTokenSrvDto login(final LoginRQSrvDto srvRequest);

}
