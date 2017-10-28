package com.example.ahmed.alcassessment.presentation.cards;

import android.util.Log;

import com.example.ahmed.alcassessment.R;
import com.example.ahmed.alcassessment.data.local.CardDAO;
import com.example.ahmed.alcassessment.data.model.Card;
import com.example.ahmed.alcassessment.data.remote.ExchangeService;
import com.example.ahmed.alcassessment.utils.AppConstants;

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
    private CurrencyService currencyService;
    private static final String TAG = "CardPresenter";
    private String[] otherSymbols;

    @Inject
    public CardsPresenter(CardDAO cardDAO, ExchangeService service,
                          CurrencyService currencyService){
        this.cardDAO = cardDAO;
        this.service = service;
        this.currencyService = currencyService;
    }

    void AttachView(CardsActivity activity){
        cardDAO.open();
        this.activity = activity;
        otherSymbols = activity.getResources().getStringArray(R.array.otherCurrencies);
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
                        .subscribe(//success
                                rateResponseUSD -> {
                                    card.setCurrentRate(rateResponseUSD.USD());
                                    Log.d(TAG, "Received response - " + rateResponseUSD.USD());
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
                                    card.setCurrentRate(rateResponseUSD.EUR());
                                    Log.d(TAG, "Received response - " + rateResponseUSD.EUR());
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
                                    double firstValue = rateResponseUSD.USD();
//                                    card.setCurrentRate(rateResponseUSD.USD());
//                                    Log.d(TAG, "Received response - " + rateResponseUSD.USD());
//                                    Log.d(TAG, "Card value - " + card.getCurrentRate());
//                                    activity.showExchangeRateForCard(card);
                                    Log.d(TAG, "Original Currency - " + card.getTo());
                                    String symbol = getCurrencySymbol(card.getTo());
                                    Log.d(TAG, "Currency Symbol - " + symbol);
                                    currencyService.getExchangeRate(
                                            AppConstants.appId, "USD", symbol)
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(responseBody -> {
                                                String jsonMap = responseBody.string();
                                                Log.d(TAG, "Different currency json value - " + jsonMap);
                                                JSONObject object = new JSONObject(jsonMap);
                                                JSONObject innerObject = object.getJSONObject("rates");
                                                double exchangeRate = innerObject.getDouble(symbol);
                                                Log.d(TAG, "Exchange rate from inner json - " + exchangeRate);
                                                card.setCurrentRate(firstValue * exchangeRate);
                                                activity.showExchangeRateForCard(card);

                                            }, throwable -> {
                                                Log.d(TAG, "Error", throwable);
                                                activity.showExchangeRateForCardError(card);
                                            });
                                },
                                //error
                                throwable -> {
                                    Log.d(TAG, "Error", throwable);
                                    activity.showExchangeRateForCardError(card);
                                });
                break;
        }


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

    public void close() {
        cardDAO.close();
    }
}
