package com.example.ahmed.alcassessment.presentation.base;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by ahmed on 10/6/17.
 */

public class BaseActivity extends AppCompatActivity {
    @Override
    public void overridePendingTransition(int in, int out){
        super.overridePendingTransition(0, 0);
    }
}
