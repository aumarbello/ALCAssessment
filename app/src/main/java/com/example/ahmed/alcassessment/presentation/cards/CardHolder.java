package com.example.ahmed.alcassessment.presentation.cards;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ahmed.alcassessment.R;
import com.example.ahmed.alcassessment.data.model.Card;

/**
 * Created by ahmed on 10/8/17.
 */

class CardHolder extends RecyclerView.ViewHolder {
    private TextView cryptCurrencySymbol;
    private TextView otherCurrencySymbol;
    private TextView cryptCurrencyText;
    private TextView otherCurrencyText;
    private TextView currentRate;
    private ImageView syncCard;
    private ImageView deleteCard;
    private ProgressBar cardIsSyncing;
    private CardsActivity activity;
    private Card card;
    private String[] crytoSymbols;
    private String[] otherSymbols;

    CardHolder(View itemView, CardsActivity activity) {
        super(itemView);
        this.activity = activity;
        cryptCurrencySymbol = itemView.findViewById(R.id.cryptoCurrency);
        otherCurrencySymbol = itemView.findViewById(R.id.otherCurrency);

        cryptCurrencyText = itemView.findViewById(R.id.textCryptCurrency);
        otherCurrencyText = itemView.findViewById(R.id.textOtherCurrency);

        currentRate = itemView.findViewById(R.id.currentRate);

        syncCard = itemView.findViewById(R.id.sync_rates);
        deleteCard = itemView.findViewById(R.id.delete_card);

        cardIsSyncing = itemView.findViewById(R.id.rate_in_sync);

        crytoSymbols = activity.getResources().getStringArray(R.array.crytoCurrencies);
        otherSymbols = activity.getResources().getStringArray(R.array.otherCurrencies);
    }

    void bindCard(Card card, int position){
        this.card = card;
        cryptCurrencySymbol.setText(getCryptoSymbol(card.getFrom()));
        otherCurrencySymbol.setText(getSymbol(card.getTo()));

        cryptCurrencyText.setText(card.getFrom());
        otherCurrencyText.setText(card.getTo());
        Log.d("Holder", "Current rate - " + card.getCurrentRate());
        if (card.getCurrentRate() == 0.0){
            cardIsSyncing.setVisibility(View.VISIBLE);
            currentRate.setVisibility(View.INVISIBLE);
        }else if (card.getCurrentRate() == 123){
            currentRate.setTextSize(10);
            currentRate.setText(R.string.faied_to_retrieve);
        }else {
            String rate = "1 " +
                    getCryptoSymbol(card.getFrom()) +
                    ": " +
                    card.getCurrentRate() + " " +
                    getSymbol(card.getTo());

            currentRate.setText(rate);
        }
        setListeners(position);
    }

   private void setListeners(int position){
        syncCard.setOnClickListener(view -> {
            activity.syncCard(card);
            cardIsSyncing.setVisibility(View.VISIBLE);
        });

        deleteCard.setOnClickListener(view -> {
            activity.deleteCard(card);
            activity.updateAfterDelete();
        });

        itemView.setOnClickListener(view -> {
            activity.openConversionDialog(card);
            Toast.makeText(activity, "Position of clicked item - " + position,
                    Toast.LENGTH_SHORT).show();
        });
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
            case "Zimbabwe Dollar":
                return otherSymbols[19];
        }
        return "";
    }
}
