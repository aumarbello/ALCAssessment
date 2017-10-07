package com.example.ahmed.alcassessment.presentation.cards;

import com.example.ahmed.alcassessment.data.local.CardDAO;
import com.example.ahmed.alcassessment.data.model.Card;
import com.example.ahmed.alcassessment.data.remote.ExchangeService;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by ahmed on 10/6/17.
 */

public class CardsPresenter {
    private CardDAO cardDAO;
    private CardsActivity activity;
    private ExchangeService service;

    @Inject
    public CardsPresenter(CardDAO cardDAO, ExchangeService service){
        this.cardDAO = cardDAO;
        this.service = service;
    }

    void AttachView(CardsActivity activity){
        this.activity = activity;
    }

    public List<Card> getAllCards(){
        //load cards in back round and update ui
        return null;
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

    public void updateAllCardsDetails(List<Card> cardList){
        //do the above for all cards
    }
}
