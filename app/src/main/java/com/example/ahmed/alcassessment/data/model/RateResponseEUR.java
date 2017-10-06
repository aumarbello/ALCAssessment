package com.example.ahmed.alcassessment.data.model;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

/**
 * Created by ahmed on 10/6/17.
 */
@AutoValue
public abstract class RateResponseEUR {
    public abstract Double EUR();

    public static Builder build(){
        return new AutoValue_RateResponseEUR.Builder();
    }

    public static TypeAdapter<RateResponseEUR> typeAdapter(Gson gson){
        return new AutoValue_RateResponseEUR.GsonTypeAdapter(gson);
    }

    @AutoValue.Builder
    public abstract static class Builder{
        public abstract Builder EUR(Double EUR);

        public abstract RateResponseEUR build();
    }
}