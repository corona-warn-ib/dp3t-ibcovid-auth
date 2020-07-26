package es.caib.dp3t.ibcovid.auth.client;

import es.caib.dp3t.ibcovid.auth.client.model.LoginRQDto;
import es.caib.dp3t.ibcovid.auth.client.model.UserBasicDetailsDto;
import feign.HeaderMap;
import feign.Headers;
import feign.RequestLine;
import es.caib.dp3t.ibcovid.auth.client.model.LoginTokenDto;

import java.util.Map;

@Headers({"Accept: application/json", "Content-Type: application/json"})
public interface IBCovidAuthClient {

    @RequestLine("POST /auth/login")
    LoginTokenDto login(final LoginRQDto body);

    @RequestLine("GET /auth/authorize")
    UserBasicDetailsDto authorize(final @HeaderMap Map<String, Object> headers);

}
