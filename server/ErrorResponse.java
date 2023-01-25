package server;


import com.google.gson.JsonElement;

public class ErrorResponse extends Response {


    ErrorResponse(JsonElement response, JsonElement reason) {
        super(response);
        this.reason = reason;
    }

    //String value;
    JsonElement reason;
}
