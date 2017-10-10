package com.example.ahmed.alcassessment.dagger;

import com.example.ahmed.alcassessment.data.remote.ExchangeService;
import com.example.ahmed.alcassessment.utils.AppConstants;
import com.example.ahmed.alcassessment.utils.GsonAdapterFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ahmed on 10/6/17.
 */
@Module
public class NetModule {
    @Provides
    @Singleton
    Gson providesGson(){
        return new GsonBuilder()
                .registerTypeAdapterFactory(GsonAdapterFactory.create())
                .create();
    }

    @Provides
    @Singleton
    GsonConverterFactory providesConverterFactory(Gson gson){
        return GsonConverterFactory.create(gson);
    }

    @Provides
    @Singleton
    OkHttpClient providesClient(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();
    }

    @Provides
    @Singleton
    Retrofit providesRetrofit(OkHttpClient client, GsonConverterFactory factory){
        return new Retrofit.Builder()
                .client(client)
                .addConverterFactory(factory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(AppConstants.BASE_URL)
                .build();
    }

    @Provides
    @Singleton
    ExchangeService providesExchangeService(Retrofit retrofit){
        return retrofit.create(ExchangeService.class);
    }
}
