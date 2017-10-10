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
import android.widget.Toast;

import com.example.ahmed.alcassessment.R;

import butterknife.BindArray;
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

    @BindArray(R.array.cryptoCurrenciesText)
    String[] cryptoCurrenciesText;

    @BindArray(R.array.otherCurrenciesText)
    String[] otherCurrenciesText;

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
                        cryptoText.setText(cryptoCurrenciesText[0]);
                        break;
                    case 1:
                        cryptoText.setText(cryptoCurrenciesText[1]);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getActivity(),
                        "Select Choice CryptoCurrency Please",
                        Toast.LENGTH_SHORT).show();
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
                        otherText.setText(otherCurrenciesText[0]);
                        break;
                    case 1:
                        otherText.setText(otherCurrenciesText[1]);
                        break;
                    case 2:
                        otherText.setText(otherCurrenciesText[2]);
                        break;
                    case 3:
                        otherText.setText(otherCurrenciesText[3]);
                        break;
                    case 4:
                        otherText.setText(otherCurrenciesText[4]);
                        break;
                    case 5:
                        otherText.setText(otherCurrenciesText[5]);
                        break;
                    case 6:
                        otherText.setText(otherCurrenciesText[6]);
                        break;
                    case 7:
                        otherText.setText(otherCurrenciesText[7]);
                        break;
                    case 8:
                        otherText.setText(otherCurrenciesText[8]);
                        break;
                    case 9:
                        otherText.setText(otherCurrenciesText[9]);
                        break;
                    case 10:
                        otherText.setText(otherCurrenciesText[10]);
                        break;
                    case 11:
                        otherText.setText(otherCurrenciesText[11]);
                        break;
                    case 12:
                        otherText.setText(otherCurrenciesText[12]);
                        break;
                    case 13:
                        otherText.setText(otherCurrenciesText[13]);
                        break;
                    case 14:
                        otherText.setText(otherCurrenciesText[14]);
                        break;
                    case 15:
                        otherText.setText(otherCurrenciesText[15]);
                        break;
                    case 16:
                        otherText.setText(otherCurrenciesText[16]);
                        break;
                    case 17:
                        otherText.setText(otherCurrenciesText[17]);
                        break;
                    case 18:
                        otherText.setText(otherCurrenciesText[18]);
                        break;
                    case 19:
                        otherText.setText(otherCurrenciesText[19]);
                         break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getActivity(),
                        "Select Currency to Convert CryptoCurrency to Please",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
