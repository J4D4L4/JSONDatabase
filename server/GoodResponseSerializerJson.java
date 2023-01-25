package server;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import server.GoodResponse;
import server.Request;

import java.lang.reflect.Type;



public class GoodResponseSerializerJson implements JsonSerializer<GoodResponse> {

    @Override
    public JsonElement serialize(GoodResponse response, Type type, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.add("response", context.serialize(response.response));
        object.add("value", context.serialize(response.value));
        return object;
    }

}