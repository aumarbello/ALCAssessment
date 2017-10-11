package com.example.ahmed.alcassessment.presentation.cards;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ahmed.alcassessment.R;
import com.example.ahmed.alcassessment.data.model.Card;

import java.util.Locale;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ahmed on 10/10/17.
 */

public class ConversionDialog extends DialogFragment {
    public ConversionDialog(){

    }

    public static ConversionDialog getInstance(Card card){
        Bundle args = new Bundle();
        args.putFloat(DOUBLE_TAG, card.getCurrentRate());
        args.putString(FROM_TAG, card.getFrom());
        args.putString(TO_TAG, card.getTo());

        ConversionDialog dialog = new ConversionDialog();
        dialog.setArguments(args);
        return dialog;
    }

    @BindView(R.id.convertFrom)
    TextView convertFrom;

    @BindView(R.id.convertTo)
    TextView convertTo;

    @BindView(R.id.conversion_rate)
    TextView conversion_rate;

    @BindView(R.id.conversion_box)
    EditText conversion_box;

    @BindArray(R.array.crytoCurrencies)
    String[] crytoSymbols;

    @BindArray(R.array.otherCurrencies)
    String[] otherSymbols;

    @BindView(R.id.exchanged_value)
    TextView exchangedView;

    private Unbinder unbinder;
    private static final String DOUBLE_TAG = "CONVERSION_DIALOG_DOUBLE";
    private static final String FROM_TAG = "FROM_CURRENCY";
    private static final String TO_TAG = "TO_CURRENCY";
    private String from;
    private String to;
    private double cardRate;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle stateIn){
        View view = View.inflate(getActivity(), R.layout.conversion_dialog, null);
        cardRate = (double) getArguments().getFloat(DOUBLE_TAG);
        from = getArguments().getString(FROM_TAG);
        to = getArguments().getString(TO_TAG);

        unbinder = ButterKnife.bind(this, view);
        setUpViews();

        conversion_box.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s,
                                          int start,
                                          int before,
                                          int count) {
            }

            @Override
            public void onTextChanged(CharSequence s,
                                      int start,
                                      int before,
                                      int count) {
                String amount = conversion_box.getText().toString().equals("") ?
                        "0" : conversion_box.getText().toString();
                Double dRate = Double.valueOf(amount);

                double convertedCurrency = dRate * cardRate;

                String exchangedString = String.format(Locale.ENGLISH,
                        "%s %.2f", getSymbol(to), convertedCurrency);
                exchangedView.setText(exchangedString);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Convert Currency Amount")
                .setView(view)
                .setNeutralButton("Clear", null)
                .setPositiveButton("OK", null);
        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(dialogInterface -> {
            Button button = ((AlertDialog) dialogInterface).getButton
                    (DialogInterface.BUTTON_NEUTRAL);
            button.setOnClickListener(buttonView -> {
                conversion_box.setText("");
                conversion_box.requestFocus();
                Log.d("Clear", "Received call to clear");
            });
        });
        return dialog;
    }

    private void setUpViews(){
        convertFrom.setText(getCryptoSymbol(from));
        convertTo.setText(getSymbol(to));
        String theRate = String.format(Locale.ENGLISH, "1 %s : %.2f %s",
                getCryptoSymbol(from), cardRate, getSymbol(to));
        conversion_rate.setText(theRate);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();

        unbinder.unbind();
    }

    private String getCryptoSymbol(String currencyText){
        switch (currencyText){
            case "Ether":
                return crytoSymbols[1];
            default:
                return crytoSymbols[0];
        }
    }

    private String getSymbol(String currencyText){
        switch (currencyText){
            case "Naira":
                return otherSymbols[0];
            case "Dollar":
                return otherSymbols[1];
            case "Euro":
                return otherSymbols[2];
            case "British Pound":
                return otherSymbols[3];
            case "Chinese Yuan":
                return otherSymbols[4];
            case "Indian Rupees":
                return otherSymbols[5];
            case "Israel Shekel":
                return otherSymbols[6];
            case "Laos Kip":
                return otherSymbols[7];
            case "Netherlands Antilles Guilder":
                return otherSymbols[8];
            case "Qatari Riyal":
                return otherSymbols[9];
            case "Russian Ruble":
                return otherSymbols[10];
            case "Brazilian Real":
                return otherSymbols[11];
            case "Switzerland Franc":
                return otherSymbols[12];
            case "Seychelles Rupee":
                return otherSymbols[13];
            case "South African Rand":
                return otherSymbols[14];
            case "Sweden Krona":
                return otherSymbols[15];
            case "Taiwan New Dollar":
                return otherSymbols[16];
            case "Ukraine Hryvnia":
                return otherSymbols[17];
            case "Uruguay Peso":
                return otherSymbols[18];
            case "Turkish Lira":
                return otherSymbols[19];
        }
        return "";
    }
}
