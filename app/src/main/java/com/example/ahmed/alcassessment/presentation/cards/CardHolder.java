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
            currentRate.setText("Failed to retrieve rate");
        }else {
            currentRate.setText(card.getCurrentRate() + "");
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
                return "ETH";
            default:
                return "BTC";
        }
    }

    private String getSymbol(String currencyText){
        switch (currencyText){
            case "Naira":
                return "NGN";
            case "Dollar":
                return "USD";
            case "Euro":
                return "EUR";
            case "British Pound":
                return "GBP";
            case "Chinese Yuan":
                return "CYN";
            case "Indian Rupees":
                return "INR";
            case "Israel Shekel":
                return "ILS";
            case "Laos Kip":
                return "LAK";
            case "Netherlands Antilles Guilder":
                return "ANG";
            case "Qatari Riyal":
                return "QAR";
            case "Russian Ruble":
                return "RUB";
            case "Brazilian Real":
                return "BRL";
            case "Switzerland Franc":
                return "CHF";
            case "Seychelles Rupee":
                return "SCR";
            case "South African Rand":
                return "ZAR";
            case "Sweden Krona":
                return "SEK";
            case "Taiwan New Dollar":
                return "TWD";
            case "Ukraine Hryvnia":
                return "UAH";
            case "Uruguay Peso":
                return "UYU";
            case "Zimbabwe Dollar":
                return "ZWD";
        }
        return "";
    }
}
