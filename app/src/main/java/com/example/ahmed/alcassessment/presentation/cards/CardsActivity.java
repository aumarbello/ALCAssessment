package com.example.ahmed.alcassessment.presentation.cards;

import android.os.Bundle;

import com.example.ahmed.alcassessment.presentation.base.BaseActivity;

import javax.inject.Inject;

/**
 * Created by ahmed on 10/6/17.
 */

public class CardsActivity extends BaseActivity {
    @Inject
    CardsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        getComponent().inject(this);
        presenter.AttachView(this);



    }
}
