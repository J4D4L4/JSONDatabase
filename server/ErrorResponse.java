package server;

public class ErrorResponse extends Response {

    ErrorResponse(String response, String reason) {
        super(response);
        this.reason = reason;
    }

    //String value;
    String reason;
}
