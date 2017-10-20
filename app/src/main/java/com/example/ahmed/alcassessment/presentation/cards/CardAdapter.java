package com.example.ahmed.alcassessment.presentation.cards;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ahmed.alcassessment.R;
import com.example.ahmed.alcassessment.data.model.Card;

import java.util.List;

/**
 * Created by ahmed on 10/8/17.
 */

class CardAdapter extends RecyclerView.Adapter<CardHolder> {
    private List<Card> cardList;
    private CardsActivity activity;
    private TextView itemRate;
    private ProgressBar rateSync;

    CardAdapter(List<Card> cardList, CardsActivity activity){
        this.cardList = cardList;
        this.activity = activity;
    }
    @Override
    public CardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        View view = inflater.inflate(R.layout.card_list_item, parent, false);
        return new CardHolder(view, activity);
    }

    @Override
    public void onBindViewHolder(CardHolder holder, int position) {
        Card card = cardList.get(position);
        itemRate = holder.currentRate;
        rateSync = holder.cardIsSyncing;
        holder.bindCard(card, position);
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    void addCard(Card card){
        Log.d("Adapter", "Size of list currently - " + cardList.size());
        cardList.add(card);
    }

    void deleteCard(Card card){
        cardList.remove(card);
    }

    public void setCardList(List<Card> cardList) {
        this.cardList = cardList;
    }

    void noChangeInExchangeRate(Card card, boolean isSame) {
        itemRate.setVisibility(View.VISIBLE);

//        itemRate.setText(card.getCurrentRate());
        rateSync.setVisibility(View.GONE);
        if (isSame){
            Toast.makeText(activity, "No change in exchange rate",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
