package com.example.ahmed.alcassessment.data.model;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.ahmed.alcassessment.utils.DBSchema;

/**
 * Created by ahmed on 10/6/17.
 */

public class CardWrapper extends CursorWrapper {
    public CardWrapper(Cursor cursor) {
        super(cursor);
    }

    public Card getCard(){
        Card card = new Card();
        String from = getString(getColumnIndex(DBSchema.currencyFrom));
        String to = getString(getColumnIndex(DBSchema.currencyTo));
        float currentRate = getFloat(getColumnIndex(DBSchema.currentRate));
        String uuid = getString(getColumnIndex(DBSchema.UUID));
        card.setAll(from, to, currentRate, uuid);
        return card;
    }
}
