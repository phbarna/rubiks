package rubiks;

import jdk.net.SocketFlow;

/**
 * Perhaps an overkill, but want to get a neat way of returning success or failure from cube validation
 */
enum StatusCode {OK, FAIL}
public class CubeError {

    private StatusCode STATUS = StatusCode.OK; // default
    private String MESSAGE = "OK"; // default

    public CubeError withError(String message) {
        MESSAGE = message;
        STATUS = StatusCode.FAIL;
        return this;
    }

    public String getMESSAGE() {
        return MESSAGE;
    }

    public StatusCode getSTATUS() {
        return STATUS;
    }

}
