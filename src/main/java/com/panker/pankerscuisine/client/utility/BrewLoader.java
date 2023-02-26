package com.panker.pankerscuisine.client.utility;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraftforge.client.model.geometry.IGeometryLoader;

public enum BrewLoader implements IGeometryLoader<BrewGeometry> {
    INSTANCE;

    @Override
    public BrewGeometry read(JsonObject jsonObject,
                             JsonDeserializationContext deserializationContext)
            throws JsonParseException {
        return new BrewGeometry();
    }
}
