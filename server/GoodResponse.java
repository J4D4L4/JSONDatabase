package server;

public class GoodResponse extends Response {

    GoodResponse(String response, String value) {
        super(response);
        this.value = value;
    }

    String value;
    //String reason;
}
