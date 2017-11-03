package com.example.ahmed.alcassessment.presentation.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;

import com.example.ahmed.alcassessment.R;
import com.example.ahmed.alcassessment.data.local.Prefs;
import com.example.ahmed.alcassessment.presentation.base.BaseActivity;

import javax.inject.Inject;

/**
 * Created by ahmed on 10/12/17.
 */

public class SettingsActivity extends BaseActivity {
    @Inject
    Prefs prefs;


    private static String listSummary;
    private static String numberSummary;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getComponent().inject(this);

        //Summaries
        listSummary = prefs.getListType() == 0 ? "List" : "Grid";
        numberSummary = prefs.numberOfCards() + "";

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

            //updating summary based on the selected value
            ListPreference numberOfCards = (ListPreference) findPreference(getString
                    (R.string.preload_number_key));
            ListPreference listType = (ListPreference) findPreference(getString
                    (R.string.list_layout_key));

            listType.setSummary(listSummary);
            numberOfCards.setSummary(numberSummary);
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences preference, String key) {
            if (key.equals(getString(R.string.preload_number_key))){
                ListPreference numberOfCards = (ListPreference) findPreference(key);
                numberOfCards.setSummary(preference.getString(key, "8"));
            }else if (key.equals(getString(R.string.list_layout_key))){
                ListPreference listType = (ListPreference) findPreference(key);
                String summary = preference.getString(key, "1").equals("0") ?
                        "List" : "Grid";
                listType.setSummary(summary);
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
