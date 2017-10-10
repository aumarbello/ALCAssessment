package com.example.ahmed.alcassessment.presentation.cards;

import android.util.Log;

import com.example.ahmed.alcassessment.data.local.CardDAO;
import com.example.ahmed.alcassessment.data.model.Card;
import com.example.ahmed.alcassessment.data.remote.ExchangeService;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ahmed on 10/6/17.
 */

public class CardsPresenter {
    private CardDAO cardDAO;
    private CardsActivity activity;
    private ExchangeService service;
    private static final String TAG = "CardPresenter";

    @Inject
    public CardsPresenter(CardDAO cardDAO, ExchangeService service){
        this.cardDAO = cardDAO;
        this.service = service;
    }

    void AttachView(CardsActivity activity){
        cardDAO.open();
        this.activity = activity;
    }

    public List<Card> getAllCards(){
        List<Card> cardList = cardDAO.getAllCards();
        Log.d(TAG, "Size of list from db - " + cardList.size());
        //load cards in back round and update ui
        return cardList;
    }



    public void addCard(Card card){
        cardDAO.addCard(card);
    }

    public void updateCard(Card card){
        cardDAO.updateCard(card);
    }

    public void updateCardDetails(Card card){
        //call service and determine latest exchange rate
    }

    public void deleteCard(Card card){
        cardDAO.deleteCard(card);
    }

    public void updateAllCardsDetails(List<Card> cardList){
        //do the above for all cards
    }

    void getRateForCard(Card card) {
        String crypt;

        if (card.getFrom().equals("Bitcoin"))
            crypt = "BTC";
        else
            crypt = "ETH";

        switch (card.getTo()) {
            case "Dollar":
                service.getRateInUSD(crypt, "USD")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                //success
                                rateResponseUSD -> {
                                    card.setCurrentRate(rateResponseUSD.USD().floatValue());
                                    Log.d(TAG, "Received response - " + rateResponseUSD.USD());
                                    String result = getResult(rateResponseUSD.USD());
                                    Log.d(TAG, "Card value - " + card.getCurrentRate());
                                    activity.showExchangeRateForCard(card);
                                },
                                //error
                                throwable -> {
                                    Log.d(TAG, "Error", throwable);
                                    activity.showExchangeRateForCardError(card);
                                });
                break;
            case "Euro":
                service.getRateInEUR(crypt, "EUR")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                //success
                                rateResponseUSD -> {
                                    card.setCurrentRate(rateResponseUSD.EUR().floatValue());
                                    Log.d(TAG, "Received response - " + rateResponseUSD.EUR());
                                    String result = getResult(rateResponseUSD.EUR());
                                    Log.d(TAG, "Card value - " + card.getCurrentRate());
                                    activity.showExchangeRateForCard(card);
                                },
                                //error
                                throwable -> {
                                    Log.d(TAG, "Error", throwable);
                                    activity.showExchangeRateForCardError(card);
                                });
                break;
            default:
                service.getRateInUSD(crypt, "USD")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                //success
                                rateResponseUSD -> {
                                    //todo get comparative exchange rate and use it to convert to the current currency
                                    card.setCurrentRate(rateResponseUSD.USD().floatValue());
                                    Log.d(TAG, "Received response - " + rateResponseUSD.USD());
                                    String result = getResult(rateResponseUSD.USD());
                                    Log.d(TAG, "Card value - " + card.getCurrentRate());
                                    activity.showExchangeRateForCard(card);
                                },
                                //error
                                throwable -> {
                                    Log.d(TAG, "Error", throwable);
                                    activity.showExchangeRateForCardError(card);
                                });
                break;
        }


    }

    private String getResult(double rate){
        return rate + " test";
    }

    public void close() {
        cardDAO.close();
    }
}
