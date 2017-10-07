package com.example.ahmed.alcassessment.dagger;

import android.app.Application;
import android.content.Context;

import com.example.ahmed.alcassessment.data.local.CardDAO;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ahmed on 10/6/17.
 */
@Module
public class AppModule {
    private Application application;

    public AppModule(Application application){
        this.application = application;
    }

    @Provides
    @Singleton
    Context providesContext(){
        return application;
    }
}
