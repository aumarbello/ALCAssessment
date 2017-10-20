package com.example.ahmed.alcassessment.utils;

import com.google.gson.TypeAdapterFactory;
import com.ryanharter.auto.value.gson.GsonTypeAdapterFactory;

/**
 * Created by ahmed on 10/9/17.
 */
@GsonTypeAdapterFactory
public abstract class GsonAdapterFactory implements TypeAdapterFactory {
    public static TypeAdapterFactory create() {
        return new AutoValueGson_GsonAdapterFactory();
    }
}
