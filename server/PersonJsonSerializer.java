package server;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class PersonJsonSerializer implements JsonSerializer<Person> {

    @Override
    public JsonElement serialize(Person person, Type type, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.add("key", context.serialize(person.getId()));
        object.add("name", context.serialize(person.getName()));
        object.add("car", context.serialize(person.car));
        object.add("rocket", context.serialize(person.rocket));
        return object;
    }

}