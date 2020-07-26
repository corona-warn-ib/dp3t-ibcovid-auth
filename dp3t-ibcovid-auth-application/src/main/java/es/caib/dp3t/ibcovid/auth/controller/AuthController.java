package es.caib.dp3t.ibcovid.auth.controller;

import es.caib.dp3t.ibcovid.auth.controller.config.RouteConstants;
import es.caib.dp3t.ibcovid.auth.controller.model.LoginTokenDto;
import es.caib.dp3t.ibcovid.auth.controller.model.UserBasicDetailsDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import es.caib.dp3t.ibcovid.auth.controller.mapper.LoginTokenDtoMapper;
import es.caib.dp3t.ibcovid.auth.controller.model.LoginRQDto;
import es.caib.dp3t.ibcovid.auth.service.AuthService;
import es.caib.dp3t.ibcovid.auth.service.model.LoginRQSrvDto;
import es.caib.dp3t.ibcovid.auth.service.model.LoginTokenSrvDto;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = RouteConstants.AUTH_PATH, produces = {MediaType.APPLICATION_JSON_VALUE})
@Log4j2
@Api(tags = "Authentication operations")
public class AuthController {

    private final AuthService authService;

    public AuthController(final AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = "/login")
    @ApiOperation(value = "Performs the user login")
    public ResponseEntity<LoginTokenDto> login(@Valid @RequestBody final LoginRQDto request) {
        log.debug("BEGIN - login: params={}", request);

        final LoginRQSrvDto srvRequest = LoginTokenDtoMapper.INSTANCE.requestToSrvRequest(request);
        final LoginTokenSrvDto srvResponse = authService.login(srvRequest);
        final LoginTokenDto response = LoginTokenDtoMapper.INSTANCE.srvDtoToDto(srvResponse);

        log.debug("END - login: response={}", response);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/authorize")
    @ApiOperation(value = "Performs the user authorization and returns the user basic details")
    public ResponseEntity<UserBasicDetailsDto> authorize(@RequestHeader("Authorization") final String authorization) {
        log.debug("BEGIN - authorize params={}", authorization);

        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final UserBasicDetailsDto userBasicDetails = UserBasicDetailsDto.builder()
                .username((String) authentication.getPrincipal())
                .roles(authentication.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .build();

        log.debug("END - authorize: response={}", userBasicDetails);
        return ResponseEntity.ok(userBasicDetails);
    }

}
