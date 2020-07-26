package es.caib.dp3t.ibcovid.auth.service.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Data
@Builder
public class BasicUserAuthenticationSrvDto implements Authentication {
    private static final long serialVersionUID = -7008860392856711102L;

    private String name;
    private String username;
    private String email;
    private List<GrantedAuthority> authorities;
    private boolean authenticated;

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

}
