package com.example.ahmed.alcassessment.presentation.cards;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

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

    public CardHolder(View itemView, CardsActivity activity) {
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

    void bindCard(Card card){
        this.card = card;
        cryptCurrencySymbol.setText(getSymbol(card.getFrom()));
        otherCurrencySymbol.setText(getSymbol(card.getTo()));

        cryptCurrencyText.setText(card.getFrom());
        otherCurrencyText.setText(card.getTo());

        currentRate.setText(card.getCurrentRate() + "");
    }

    void setListeners(){
        syncCard.setOnClickListener(view -> {
            activity.syncCard(card);
            cardIsSyncing.setVisibility(View.VISIBLE);
        });

        deleteCard.setOnClickListener(view -> {
            activity.deleteCard(card);
            activity.updateAfterDelete();
        });

        itemView.setOnClickListener(view -> activity.openConversionDialog(card));
    }

    private String getSymbol(String currencyText){
        return "@";
    }
}
