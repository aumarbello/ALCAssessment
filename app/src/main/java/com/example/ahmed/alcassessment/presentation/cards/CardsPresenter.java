package com.example.ahmed.alcassessment.presentation.cards;

import com.example.ahmed.alcassessment.data.local.CardDAO;
import com.example.ahmed.alcassessment.data.model.Card;

import java.util.List;

/**
 * Created by ahmed on 10/6/17.
 */

public class CardsPresenter {
    private CardDAO cardDAO;
    private CardsActivity activity;

    public CardsPresenter(CardDAO cardDAO){
        this.cardDAO = cardDAO;
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

    }

    public void updateAllCardsDetails(List<Card> cardList){

    }
}
