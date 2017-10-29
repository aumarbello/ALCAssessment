package com.example.ahmed.alcassessment.presentation.cards;

import android.util.Log;

import com.example.ahmed.alcassessment.R;
import com.example.ahmed.alcassessment.data.local.CardDAO;
import com.example.ahmed.alcassessment.data.model.Card;
import com.example.ahmed.alcassessment.data.remote.ExchangeService;
import com.example.ahmed.alcassessment.utils.NetworkUtil;

import org.json.JSONObject;

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
    private String[] otherSymbols;

    @Inject
    CardsPresenter(CardDAO cardDAO, ExchangeService service){
        this.cardDAO = cardDAO;
        this.service = service;
    }

    void AttachView(CardsActivity activity){
        cardDAO.open();
        this.activity = activity;
        otherSymbols = activity.getResources().getStringArray(R.array.otherCurrencies);
    }

    List<Card> getAllCards(){
        List<Card> cardList = cardDAO.getAllCards();
        Log.d(TAG, "Size of list from db - " + cardList.size());
        //todo load cards in back round and update ui
        return cardList;
    }



    void addCard(Card card){
        cardDAO.addCard(card);
    }

    void updateCard(Card card){
        cardDAO.updateCard(card);
    }

    public void updateCardDetails(Card card){
        //todo call service and determine latest exchange rate
    }

    void deleteCard(Card card){
        cardDAO.deleteCard(card);
    }

    public void updateAllCardsDetails(List<Card> cardList){
        //todo do the above for all cards
    }

    void getRateForCard(Card card) {
        if (!NetworkUtil.isNetworkConnected(activity)){
            //todo show dialog or snackBar to inform user of network connectivity status
            activity.showExchangeRateForCardError(card);
            return;
        }

        String crypt;

        if (card.getFrom().equals("Bitcoin"))
            crypt = "BTC";
        else
            crypt = "ETH";

        String currencyCode = getCurrencySymbol(card.getTo());
        service.getExchangeRate(crypt, currencyCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(//success
                        responseBody -> {
                            String responseString = responseBody.string();
                            JSONObject responseObject = new JSONObject
                                    (responseString);

                            card.setCurrentRate(responseObject.getDouble
                                    (currencyCode));

                            Log.d(TAG, "Received response - toString " +
                                    responseBody.toString());
                            Log.d(TAG, "Card value - .string()" +
                                    responseString);
                            activity.showExchangeRateForCard(card);
                        },
                        //error
                        throwable -> {
                            Log.d(TAG, "Error", throwable);
                            activity.showExchangeRateForCardError(card);
                        });

    }

    private String getCurrencySymbol(String to){
        switch (to){
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
            default:
                return "GBP";
        }
    }

    void close() {
        cardDAO.close();
    }
}
