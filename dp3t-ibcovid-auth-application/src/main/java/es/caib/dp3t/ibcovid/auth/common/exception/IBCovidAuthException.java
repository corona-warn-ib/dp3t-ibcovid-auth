package es.caib.dp3t.ibcovid.auth.common.exception;

import lombok.Getter;

import java.text.MessageFormat;

@Getter
public class IBCovidAuthException extends RuntimeException {
    private static final long serialVersionUID = 5272778568987056354L;

    private final AuthErrorCodes error;

    public IBCovidAuthException(final AuthErrorCodes error, final Object... params) {
        super(MessageFormat.format(error.getMessage(), params));
        this.error = error;
    }

    public IBCovidAuthException(final Throwable cause, final AuthErrorCodes error, final Object... params) {
        super(MessageFormat.format(error.getMessage(), params), cause);
        this.error = error;
    }

}
