package server;

import com.google.gson.JsonElement;

public class GoodResponse extends Response {

    GoodResponse(JsonElement response, JsonElement value) {
        super(response);
        this.value = value;
    }

    public JsonElement value;
    //String reason;
}
