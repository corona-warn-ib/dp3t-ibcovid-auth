package es.caib.dp3t.ibcovid.auth.service.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class LoginRQSrvDto {
    String username;
    String password;

}
