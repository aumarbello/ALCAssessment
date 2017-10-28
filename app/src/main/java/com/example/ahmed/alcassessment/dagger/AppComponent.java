package com.example.ahmed.alcassessment.dagger;

import com.example.ahmed.alcassessment.presentation.cards.CardsActivity;
import com.example.ahmed.alcassessment.presentation.settings.SettingsActivity;
import com.example.ahmed.alcassessment.presentation.splash.SplashActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by ahmed on 10/6/17.
 */
@Singleton
@Component(modules = {AppModule.class, NetModule.class})
public interface AppComponent {
    void inject(SplashActivity activity);
    void inject(CardsActivity cardsActivity);
    void inject(SettingsActivity settingsActivity);
}
