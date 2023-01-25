package server;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;



public class RequestJsonSerializer implements JsonSerializer<Request> {

    @Override
    public JsonElement serialize(Request request, Type type, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.add("key", context.serialize(request.type));
        object.add("value", context.serialize(request.value));
        return object;
    }

}