package com.example.ahmed.alcassessment;

import android.app.Application;

import com.example.ahmed.alcassessment.dagger.AppComponent;
import com.example.ahmed.alcassessment.dagger.AppModule;
import com.example.ahmed.alcassessment.dagger.DaggerAppComponent;

/**
 * Created by ahmed on 10/6/17.
 */

public class App extends Application {
    private AppComponent appComponent;

    public AppComponent getComponent(){
        return appComponent;
    }

    @Override
    public void onCreate(){
        super.onCreate();

        appComponent = init(this);
    }

    protected AppComponent init(App app) {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(app))
                .build();
    }
}
