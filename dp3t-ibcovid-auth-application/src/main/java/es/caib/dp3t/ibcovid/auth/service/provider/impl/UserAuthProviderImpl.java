package es.caib.dp3t.ibcovid.auth.service.provider.impl;

import es.caib.dp3t.ibcovid.auth.data.UserRepository;
import es.caib.dp3t.ibcovid.auth.data.model.User;
import es.caib.dp3t.ibcovid.auth.service.model.BasicUserAuthenticationSrvDto;
import es.caib.dp3t.ibcovid.auth.service.provider.UserAuthProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
class UserAuthProviderImpl implements UserAuthProvider {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserAuthProviderImpl(final UserRepository userRepository, final PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication loginUser(final String username, final String password) {
        final User user = userRepository.findByUsernameAndActiveIsTrue(username).orElse(null);

        if (userIsAuthenticated(user, password)) {
            return BasicUserAuthenticationSrvDto.builder()
                    .name(user.getUsername())
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .authorities(getGrantedAuthorities(user))
                    .authenticated(true)
                    .build();
        } else {
            return null;
        }
    }

    private boolean userIsAuthenticated(final User user, final String password) {
        if (user != null) {
            return passwordEncoder.matches(password, user.getPassword());
        } else {
            return false;
        }
    }

    private List<GrantedAuthority> getGrantedAuthorities(final User user) {
        return user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

}
