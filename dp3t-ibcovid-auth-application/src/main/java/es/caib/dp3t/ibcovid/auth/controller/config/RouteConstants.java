package es.caib.dp3t.ibcovid.auth.controller.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.NONE)
public final class RouteConstants {
    public static final String BASE_PATH = "/v1";
    public static final String ADMIN_PATH = BASE_PATH + "/admin";

    public static final String AUTH_PATH = BASE_PATH + "/auth";
    public static final String LOGIN_PATH = AUTH_PATH + "/login";
    public static final String AUTHORIZE_PATH = AUTH_PATH + "/authorize";

    public static final String ACTUATOR_BASE_PATH = "/actuator";
    public static final String H2_CONSOLE_PATH = "/h2-console";

}
