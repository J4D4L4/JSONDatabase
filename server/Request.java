package server;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Request {

    public String type;
    public JsonElement key;
    public JsonElement value;

    public Request(String type, JsonElement key){
        this.type = type;
        this.key = key;
    }

    public Request(String type, JsonElement key, JsonElement value){
        this.type = type;
        this.key = key;
        this.value = value;
    }

    public Request(String type){
        this.type = type;
    }


}
