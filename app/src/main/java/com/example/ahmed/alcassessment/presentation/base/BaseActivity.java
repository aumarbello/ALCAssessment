package com.example.ahmed.alcassessment.presentation.base;

import android.support.v7.app.AppCompatActivity;

import com.example.ahmed.alcassessment.App;
import com.example.ahmed.alcassessment.dagger.AppComponent;

/**
 * Created by ahmed on 10/6/17.
 */

public class BaseActivity extends AppCompatActivity {
    @Override
    public void overridePendingTransition(int in, int out){
        super.overridePendingTransition(0, 0);
    }

    protected AppComponent getComponent(){
        return ((App)getApplication()).getComponent();
    }
}
