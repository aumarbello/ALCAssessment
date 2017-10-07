package com.example.ahmed.alcassessment.dagger;

import com.example.ahmed.alcassessment.data.remote.ExchangeService;
import com.example.ahmed.alcassessment.utils.AppConstants;
import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ahmed on 10/6/17.
 */
@Module
public class NetModule {
    @Provides
    @Singleton
    Gson providesGson(){
        return new Gson();
    }

    @Provides
    @Singleton
    GsonConverterFactory providesConverterFactory(Gson gson){
        return GsonConverterFactory.create(gson);
    }

    @Provides
    @Singleton
    OkHttpClient providesClient(){
        return new OkHttpClient();
    }

    @Provides
    @Singleton
    Retrofit providesRetrofit(OkHttpClient client, GsonConverterFactory factory){
        return new Retrofit.Builder()
                .client(client)
                .addConverterFactory(factory)
                .baseUrl(AppConstants.BASE_URL)
                .build();
    }

    @Provides
    @Singleton
    ExchangeService providesExchangeService(Retrofit retrofit){
        return retrofit.create(ExchangeService.class);
    }
}
