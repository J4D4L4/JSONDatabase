package server;

import com.google.gson.JsonElement;

public class  Response {



    Response(JsonElement response) {
        this.response = response;
    }

    public JsonElement response;

}

