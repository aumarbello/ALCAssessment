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
import android.widget.ImageView;
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
        args.putDouble(DOUBLE_TAG, card.getCurrentRate());
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

    @BindView(R.id.dash)
    ImageView dash;

    private Unbinder unbinder;
    private static final String DOUBLE_TAG = "CONVERSION_DIALOG_DOUBLE";
    private static final String FROM_TAG = "FROM_CURRENCY";
    private static final String TO_TAG = "TO_CURRENCY";
    private String from;
    private String to;
    private String originalRelativeExchangeString;
    private String swapRelativeExchangeString;
    private double originalExchangeRate;
    private double swapExchangeRate;
    private String originalExchangedAmount;
    private String swapExchangedAmount;
    private boolean isOriginal;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle stateIn){
        View view = View.inflate(getActivity(), R.layout.conversion_dialog, null);
        unbinder = ButterKnife.bind(this, view);
        isOriginal = true;

        from = getArguments().getString(FROM_TAG);
        to = getArguments().getString(TO_TAG);

        setUpFields();

        Log.d(TO_TAG, "Value to - " + to);
        Log.d(FROM_TAG, "Value from - " + from);

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

                double convertedCurrency = dRate * exchangeRate();

                exchangedView.setText(exchangeString(convertedCurrency));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Convert Currency Amount")
                .setView(view)
                .setNegativeButton("Clear", null)
                .setNeutralButton("Swap", null)
                .setPositiveButton("OK", null);
        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(dialogInterface -> {
            Button neutralButton = ((AlertDialog) dialogInterface).getButton
                    (DialogInterface.BUTTON_NEUTRAL);

            Button negativeButton = ((AlertDialog) dialogInterface).getButton
                    (DialogInterface.BUTTON_NEGATIVE);
            neutralButton.setOnClickListener(negativeView -> {
                isOriginal = !isOriginal;
                conversion_box.setText("");
                setUpViews();
            });
            negativeButton.setOnClickListener(buttonView -> {
                conversion_box.setText("");
                conversion_box.requestFocus();
                Log.d("Clear", "Received call to clear");
            });
        });
        return dialog;
    }

    private void setUpFields() {
        originalExchangeRate = getArguments().getDouble(DOUBLE_TAG);
        swapExchangeRate = 1 / originalExchangeRate;

        originalRelativeExchangeString = String.format(Locale.ENGLISH, "1 %s : %.2f %s",
                getCryptoSymbol(from), originalExchangeRate, getSymbol(to));
        swapRelativeExchangeString = String.format(Locale.ENGLISH, "1 %s : %f %s",
                getSymbol(to), swapExchangeRate, getCryptoSymbol(from));

        originalExchangedAmount = String.format(Locale.ENGLISH, "%s  %.2f", getSymbol(to), 0.0);
        swapExchangedAmount = String.format(Locale.ENGLISH, "%s  %.2f", getCryptoSymbol(from), 0.0);
    }

    private double exchangeRate(){
        if (isOriginal){
            return originalExchangeRate;
        }else
            return swapExchangeRate;
    }

    private String exchangeString(double amount){
        if (isOriginal){
        return String.format(Locale.ENGLISH,
                "%s %.2f", getSymbol(to), amount);
        }else {
            return String.format(Locale.ENGLISH,
                    "%s %.2f", getCryptoSymbol(from), amount);
        }
    }

    private void setUpViews(){
        if (isOriginal){
            convertFrom.setText(getCryptoSymbol(from));
            convertTo.setText(getSymbol(to));

            conversion_rate.setText(originalRelativeExchangeString);

            exchangedView.setText(originalExchangedAmount);
        }else {
            convertFrom.setText(getSymbol(to));
            convertTo.setText(getCryptoSymbol(from));

            conversion_rate.setText(swapRelativeExchangeString);

            exchangedView.setText(swapExchangedAmount);
        }
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

//    private void swapViews(){
//        View firstView = currencySymbols.getChildAt(0);
//        View secondView = currencySymbols.getChildAt(1);
//        View thirdView = currencySymbols.getChildAt(2);
//        currencySymbols.removeAllViews();
//
//        //todo recalculate exchange rate, up date exchange rate view, and converted amount view
//
//        currencySymbols.addView(thirdView);
//        currencySymbols.addView(secondView);
//        currencySymbols.addView(firstView);
//
//        if (isOriginal){
//
//        }
//    }
}
