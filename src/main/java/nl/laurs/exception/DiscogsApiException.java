package nl.laurs.exception;

/**
 * @author: maarten
 */
public class DiscogsApiException extends Exception {
    public static final int API_REQUEST_NOT_OK = 1;

    private int errorCode;

    public DiscogsApiException(int errorCode) {
        this.errorCode = errorCode;
    }

    public DiscogsApiException(int errorCode, Exception cause) {
        super("Discogs api error " + errorCode + " occured: ", cause);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
