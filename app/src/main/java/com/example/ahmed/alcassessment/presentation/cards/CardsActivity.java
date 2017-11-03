package com.example.ahmed.alcassessment.presentation.cards;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

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
        implements AddCardDialog.CallBack,
        ConversionDialog.Callback {
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

    @BindView(R.id.empty_list)
    TextView emptyList;

    @BindArray(R.array.cryptoCurrenciesText)
    String[] crytoCurrencies;

    @BindArray(R.array.otherCurrenciesText)
    String[] otherCurrencies;

    private CardAdapter adapter;
    private Unbinder unbinder;
    private AddCardDialog dialog;
    private int syncPosition;
    private boolean isSyncing;
    private double previousRate;
    private boolean isAddingRandomCards;
    private int randomCardCount;

    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        getComponent().inject(this);
        presenter.AttachView(this);
        setContentView(R.layout.card_list);
        overridePendingTransition(0, 0);
        List<Card> cards = presenter.getAllCards();

        unbinder = ButterKnife.bind(this);

        if (cards.isEmpty()){
           emptyList.setVisibility(View.VISIBLE);
        }

        adapter = new CardAdapter(cards, this);
        dialog = AddCardDialog.getInstance();

        if (prefs.getListType() == 0){
            cardList.setLayoutManager(new LinearLayoutManager(this));
        }else {
            cardList.setLayoutManager(new GridLayoutManager(this, 2));
        }

        cardList.setAdapter(adapter);

        addCard.setOnClickListener(view ->
                dialog.show(getSupportFragmentManager(), "Add Card"));

        prefOperations(cards);

        refreshLayout.setOnRefreshListener(() ->
                presenter.updateAllCardsDetails(cards));
    }

    private void prefOperations(List<Card> cards) {
        if (prefs.createRandomCard() && cards.isEmpty()){
            createCard();
            isAddingRandomCards = true;
        }

        if (prefs.refreshAll()){
            refreshLayout.setRefreshing(true);
            presenter.updateAllCardsDetails(cards);
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
    }

    public void deleteCard(Card card, int position) {
        adapter.deleteCard(card);
        presenter.deleteCard(card);
        adapter.notifyItemRemoved(position);

        if (adapter.getItemCount() == 0){
            emptyList.setVisibility(View.VISIBLE);
        }
    }

    public void openConversionDialog(Card card) {
        boolean addSwapButton  = prefs.twoWayExchange();

        ConversionDialog dialog = ConversionDialog.getInstance(card, addSwapButton);
        dialog.show(getSupportFragmentManager(), "Conversion Dialog");
    }

    private void updateAfterAdding(Card card){
        if (emptyList.getVisibility() == View.VISIBLE){
            emptyList.setVisibility(View.GONE);
        }
        adapter.addCard(card);
        int position = adapter.getItemCount();
        adapter.notifyItemInserted(position);

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
    }

   public void showExchangeRateForCard(Card card){
       if (isSyncing){
           presenter.updateCardDetailsDB(card);
           adapter.noChangeInExchangeRate(previousRate == card.getCurrentRate());
           adapter.notifyItemChanged(syncPosition);
           isSyncing = false;
       }else {
           presenter.addCard(card);
           int pos = adapter.getItemCount() - 1;
           adapter.notifyItemChanged(pos);
           Log.d("Activity", "Called adapter on change");
       }

       if (isAddingRandomCards && randomCardCount != prefs.numberOfCards()){
           createCard();
       }else
           isAddingRandomCards = false;
    }

    public void showExchangeRateForCardError(Card card) {
        if (isSyncing){
            card.setCurrentRate(1);
            adapter.notifyItemChanged(syncPosition);
            isSyncing = false;
        }else {
            presenter.addCard(card);
            card.setCurrentRate(1);
            int pos = adapter.getItemCount() - 1;
            adapter.notifyItemChanged(pos);
        }

        if (isAddingRandomCards && randomCardCount != prefs.numberOfCards()){
            createCard();
        }else
            isAddingRandomCards = false;
    }

    private void createCard() {
        int cryptoCurrency = new Random().nextInt(1);
        int otherCurrency = new Random().nextInt(19);

        String from = crytoCurrencies[cryptoCurrency];
        String to = otherCurrencies[otherCurrency];

        addCard(from, to);

        randomCardCount++;
    }

    @Override
    public void showExchangeSnackBar() {
      makeSnackBar(getString(R.string.exchange_rate_error_text));
    }

    void showNetworkBar(){
        makeSnackBar(getString(R.string.network_error_text));
    }

    private void makeSnackBar(String message){
        Snackbar.make(refreshLayout, message,
                Snackbar.LENGTH_SHORT).show();
    }

    void refreshAdapter(List<Card> allCards) {
        adapter.setCardList(allCards);
        adapter.notifyDataSetChanged();
        refreshLayout.setRefreshing(false);
    }
}
