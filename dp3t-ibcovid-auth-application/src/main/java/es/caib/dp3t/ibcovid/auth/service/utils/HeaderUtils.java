package es.caib.dp3t.ibcovid.auth.service.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@NoArgsConstructor(access = AccessLevel.NONE)
public class HeaderUtils {

    public static String extractBearerToken(final String authorizationHeader) {
        return StringUtils.removeStartIgnoreCase(authorizationHeader, "Bearer ");
    }

}
