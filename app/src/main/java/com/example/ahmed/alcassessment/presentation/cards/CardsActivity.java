package com.example.ahmed.alcassessment.presentation.cards;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.ahmed.alcassessment.R;
import com.example.ahmed.alcassessment.data.model.Card;
import com.example.ahmed.alcassessment.presentation.base.BaseActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ahmed on 10/6/17.
 */

public class CardsActivity extends BaseActivity implements AddCardDialog.CallBack {
    @Inject
    CardsPresenter presenter;

    @BindView(R.id.card_list)
    RecyclerView cardList;

    @BindView(R.id.swipe_to_re_sync)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.add_card)
    FloatingActionButton addCard;

    private CardAdapter adapter;
    private Unbinder unbinder;
    private List<Card> cards;
    private AddCardDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        getComponent().inject(this);
        presenter.AttachView(this);
        setContentView(R.layout.card_list);

        cards = presenter.getAllCards();

        unbinder = ButterKnife.bind(this);

        adapter = new CardAdapter(cards, this);
        dialog = AddCardDialog.getInstance();

        cardList.setLayoutManager(new GridLayoutManager(this, 2));
        cardList.setAdapter(adapter);

        addCard.setOnClickListener(view -> {
            dialog.show(getSupportFragmentManager(), "Add Card");
            Toast.makeText(this, "Adding", Toast.LENGTH_SHORT).show();
        });
    }

    public void syncCard(Card card) {
        Toast.makeText(this, "Syncing",
                Toast.LENGTH_SHORT).show();
    }

    public void deleteCard(Card card, int position) {
        Toast.makeText(this, "Deleting",
                Toast.LENGTH_SHORT).show();

        adapter.deleteCard(card);
        presenter.deleteCard(card);
        adapter.notifyItemRemoved(position);
    }

    public void openConversionDialog(Card card) {
        Snackbar.make(cardList, "Opening", Snackbar.LENGTH_SHORT);
    }

    private void updateAfterAdding(Card card){
        adapter.addCard(card);
        int position = adapter.getItemCount();
        adapter.notifyItemInserted(position);
        Log.d("Activity", "Call to add card to adapter");

    }

    @Override
    public void onDestroy(){
        super.onDestroy();

        unbinder.unbind();
        presenter.close();
    }

    @Override
    public void addCard(String from, String to) {
        Card card = new Card();
        card.setFrom(from);
        card.setTo(to);
        updateAfterAdding(card);

        presenter.getRateForCard(card);

        Toast.makeText(this, "Received currencies - " + from + " and - " + to,
                Toast.LENGTH_SHORT).show();
    }

   public void showExchangeRateForCard(Card card){
       presenter.addCard(card);
       int pos = adapter.getItemCount() - 1;
       adapter.notifyItemChanged(pos);
       Log.d("Activity", "Changing item at - " + pos);
    }

    public void showExchangeRateForCardError(Card card) {
        card.setCurrentRate(123);
        int pos = adapter.getItemCount() - 1;
        adapter.notifyItemChanged(pos);
    }
}
