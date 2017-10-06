package com.example.ahmed.alcassessment.data.model;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

/**
 * Created by ahmed on 10/6/17.
 */
@AutoValue
public abstract class RateResponseUSD {
    public abstract Double USD();

    public static Builder build(){
        return new AutoValue_RateResponseUSD.Builder();
    }

    public static TypeAdapter<RateResponseUSD> typeAdapter(Gson gson){
        return new AutoValue_RateResponseUSD.GsonTypeAdapter(gson);
    }

    @AutoValue.Builder
    public abstract static class Builder{
        public abstract Builder USD(Double USD);

        public abstract RateResponseUSD build();
    }
}