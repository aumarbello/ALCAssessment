package com.example.ahmed.alcassessment.presentation.splash;

import android.content.Intent;
import android.os.Bundle;

import com.example.ahmed.alcassessment.data.local.CardDAO;
import com.example.ahmed.alcassessment.presentation.base.BaseActivity;
import com.example.ahmed.alcassessment.presentation.cards.CardsActivity;

/**
 * Created by ahmed on 10/6/17.
 */

public class SplashActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);

        CardDAO dao = new CardDAO(this);
        dao.open();

        Intent intent = new Intent(this, CardsActivity.class);
        startActivity(intent);
        finish();
    }
}
