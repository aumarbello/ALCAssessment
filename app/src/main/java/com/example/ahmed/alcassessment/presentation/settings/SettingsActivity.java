package com.example.ahmed.alcassessment.presentation.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;

import com.example.ahmed.alcassessment.R;
import com.example.ahmed.alcassessment.presentation.base.BaseActivity;

/**
 * Created by ahmed on 10/12/17.
 */

public class SettingsActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content ,new SettingsFragment())
                .commit();
    }

    public static class SettingsFragment
            extends PreferenceFragment
            implements SharedPreferences.OnSharedPreferenceChangeListener{
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            addPreferencesFromResource(R.xml.settings);
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences preference, String key) {
            if (key.equals(getString(R.string.preload_number_key))){
                ListPreference numberOfCards = (ListPreference) findPreference(key);
                numberOfCards.setSummary(preference.getString(key, "8"));
            }else if (key.equals(getString(R.string.list_layout_key))){
                ListPreference listType = (ListPreference) findPreference(key);
                listType.setSummary(preference.getString(key, "Grid"));
            }
        }

        @Override
        public void onResume() {
            super.onResume();

            getPreferenceScreen().getSharedPreferences()
                    .registerOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onPause() {
            super.onPause();

            getPreferenceScreen().getSharedPreferences()
                    .unregisterOnSharedPreferenceChangeListener(this);
        }
    }
}
