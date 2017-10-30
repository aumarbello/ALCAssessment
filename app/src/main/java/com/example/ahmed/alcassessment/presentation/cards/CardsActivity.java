package com.example.ahmed.alcassessment.presentation.cards;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.ahmed.alcassessment.R;
import com.example.ahmed.alcassessment.data.local.Prefs;
import com.example.ahmed.alcassessment.data.model.Card;
import com.example.ahmed.alcassessment.presentation.base.BaseActivity;
import com.example.ahmed.alcassessment.presentation.settings.SettingsActivity;

import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ahmed on 10/6/17.
 */

public class CardsActivity extends BaseActivity
        implements AddCardDialog.CallBack {
    @Inject
    CardsPresenter presenter;

    @Inject
    Prefs prefs;

    @BindView(R.id.card_list)
    RecyclerView cardList;

    @BindView(R.id.swipe_to_re_sync)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.add_card)
    FloatingActionButton addCard;

    @BindArray(R.array.crytoCurrencies)
    String[] crytoSymbols;

    @BindArray(R.array.otherCurrencies)
    String[] otherSymbols;

    private CardAdapter adapter;
    private Unbinder unbinder;
    private AddCardDialog dialog;
    private int syncPosition;
    private boolean isSyncing;
    private double previousRate;

    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        getComponent().inject(this);
        presenter.AttachView(this);
        setContentView(R.layout.card_list);

        List<Card> cards = presenter.getAllCards();

        unbinder = ButterKnife.bind(this);

        adapter = new CardAdapter(cards, this);
        dialog = AddCardDialog.getInstance();

        if (prefs.getListType() == 0){
            cardList.setLayoutManager(new LinearLayoutManager(this));
        }else {
            cardList.setLayoutManager(new GridLayoutManager(this, 2));
        }

        cardList.setAdapter(adapter);

        addCard.setOnClickListener(view -> {
            dialog.show(getSupportFragmentManager(), "Add Card");
            Toast.makeText(this, "Adding", Toast.LENGTH_SHORT).show();
        });

        if (prefs.createRandomCard() && !cards.isEmpty()){
            createRandomCards(prefs.numberOfCards());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == R.id.settings){
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public void syncCard(Card card, int position) {
        syncPosition = position;
        isSyncing = true;
        previousRate = card.getCurrentRate();
        presenter.getRateForCard(card);
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
        boolean addSwapButton  = prefs.twoWayExchange();

        ConversionDialog dialog = ConversionDialog.getInstance(card, addSwapButton);
        dialog.show(getSupportFragmentManager(), "Conversion Dialog");
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
       if (isSyncing){
           presenter.updateCard(card);
           adapter.noChangeInExchangeRate(previousRate == card.getCurrentRate());
           adapter.notifyItemChanged(syncPosition);
           isSyncing = false;
       }else {
           presenter.addCard(card);
           int pos = adapter.getItemCount() - 1;
           adapter.notifyItemChanged(pos);
           Log.d("Activity", "Changing item at - " + pos);
       }
    }

    public void showExchangeRateForCardError(Card card) {
        if (isSyncing){
            card.setCurrentRate(123);
            adapter.notifyItemChanged(syncPosition);
            isSyncing = false;
        }else {
            card.setCurrentRate(123);
            int pos = adapter.getItemCount() - 1;
            adapter.notifyItemChanged(pos);
        }
    }

    private void createRandomCards(int cardCount) {
        for (int a  = 0; a < cardCount; a++){
            createCard();

            // TODO: 10/27/17 wait sometime before adding next

        }
    }

    private void createCard() {
        int cryptoCurrency = new Random().nextInt(1);
        int otherCurrency = new Random().nextInt(19);

        String from = crytoSymbols[cryptoCurrency];
        String to = otherSymbols[otherCurrency];

        addCard(from, to);
    }
}
