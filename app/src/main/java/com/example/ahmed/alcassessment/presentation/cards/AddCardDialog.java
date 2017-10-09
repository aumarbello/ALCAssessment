package com.example.ahmed.alcassessment.presentation.cards;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ahmed.alcassessment.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ahmed on 10/9/17.
 */

public class AddCardDialog extends DialogFragment {
    interface CallBack{
        void addCard(String from, String to);
    }

    public AddCardDialog(){

    }

    public static AddCardDialog getInstance(){
        return new AddCardDialog();
    }

    private CallBack callBack;
    private Unbinder unbinder;

    @BindView(R.id.cryptoDropDown)
    Spinner cryptoDropDown;
    @BindView(R.id.otherDropDown)
    Spinner otherDropDown;

    @BindView(R.id.cryptoDropDownText)
    TextView cryptoText;
    @BindView(R.id.otherDropDownText)
    TextView otherText;

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);

        callBack = (CallBack) getActivity();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle bundle){
        View view = View.inflate(getActivity(), R.layout.add_card, null);
        unbinder = ButterKnife.bind(this, view);
        setUpSpinners();

        return new AlertDialog.Builder(getActivity())
                .setTitle("Add Exchange Card")
                .setView(view)
                .setPositiveButton("OK", (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                    callBack.addCard(cryptoText.getText().toString(),
                            otherText.getText().toString());
                })
                .create();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();

        unbinder.unbind();
    }

    private void setUpSpinners(){
        ArrayAdapter<CharSequence> cryptoAdapter = ArrayAdapter.
                createFromResource(getActivity(), R.array.crytoCurrencies,
                        android.R.layout.simple_spinner_item);

        cryptoAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);

        cryptoDropDown.setAdapter(cryptoAdapter);
        cryptoDropDown.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long l) {
                switch (position){
                    case 0:
                        cryptoText.setText("Bitcoin");
                        break;
                    case 1:
                        cryptoText.setText("Ether");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<CharSequence> otherAdapter = ArrayAdapter.
                createFromResource(getActivity(),
                        R.array.otherCurrencies,
                        android.R.layout.simple_spinner_item);
        otherAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        otherDropDown.setAdapter(otherAdapter);
        otherDropDown.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long l) {
                switch (position){
                    case 0:
                        otherText.setText("Naira");
                        break;
                    case 1:
                        otherText.setText("Dollar");
                        break;
                    case 2:
                        otherText.setText("Euro");
                        break;
                    case 3:
                        otherText.setText("British Pound");
                        break;
                    case 4:
                        otherText.setText("Chinese Yuan");
                        break;
                    case 5:
                        otherText.setText("Indian Rupee");
                        break;
                    case 6:
                        otherText.setText("Israel Shekel");
                        break;
                    case 7:
                        otherText.setText("Laos Kip");
                        break;
                    case 8:
                        otherText.setText("Netherlands Antilles Guilder");
                        break;
                    case 9:
                        otherText.setText("Qatar Riyal");
                        break;
                    case 10:
                        otherText.setText("Russian Ruble");
                        break;
                    case 11:
                        otherText.setText("Brazil Real");
                        break;
                    case 12:
                        otherText.setText("Switzerland Franc");
                        break;
                    case 13:
                        otherText.setText("Seychelles Rupee");
                        break;
                    case 14:
                        otherText.setText("South African Rand");
                        break;
                    case 15:
                        otherText.setText("Sweden Krona");
                        break;
                    case 16:
                        otherText.setText("Taiwan New Dollar");
                        break;
                    case 17:
                        otherText.setText("Ukraine Hryvnia");
                        break;
                    case 18:
                        otherText.setText("Uruguay Peso");
                        break;
                    case 19:
                        otherText.setText("Zimbabwe Dollar");
                         break;
                    //todo add cases for other currencies
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
