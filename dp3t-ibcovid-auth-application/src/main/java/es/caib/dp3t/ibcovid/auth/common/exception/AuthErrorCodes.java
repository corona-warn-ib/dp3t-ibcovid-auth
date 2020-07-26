package es.caib.dp3t.ibcovid.auth.common.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum AuthErrorCodes {
    GENERAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "An internal error has occurred: {0}"),
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "Validation error: {0}"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "Unauthorized access"),
    FORBIDDEN(HttpStatus.FORBIDDEN, "Access Denied");

    private final HttpStatus httpStatus;
    private final String message;

    public String getCode() {
        return name();
    }

}
