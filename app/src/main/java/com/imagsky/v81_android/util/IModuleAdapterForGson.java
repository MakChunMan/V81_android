package com.imagsky.v81_android.util;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.imagsky.v81_android.domain.Module;

import java.lang.reflect.Type;

/**
 * A java adapter class for GSON to deserialize inheritance Module
 * Created by jasonmak on 30/1/2015.
 */
public class IModuleAdapterForGson implements JsonDeserializer<Module> {

    private static final String MODULE_TYPE = "moduleType";

    private static final String ClassPrefix = "com.imagsky.v81_android.domain";

    @Override
    public Module deserialize(JsonElement json, Type typeOfT,
                               JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject =  json.getAsJsonObject();
        JsonPrimitive prim = (JsonPrimitive) jsonObject.get(MODULE_TYPE);

        String className = prim.getAsString();
        Class<?> klass = null;
        try{
            klass = Class.forName(ClassPrefix + "." + className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new JsonParseException(e.getMessage());
        }
        return context.deserialize(jsonObject, klass);
    }
}
