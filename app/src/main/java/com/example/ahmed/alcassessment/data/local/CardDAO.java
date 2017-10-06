package com.example.ahmed.alcassessment.data.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.ahmed.alcassessment.data.model.Card;
import com.example.ahmed.alcassessment.data.model.CardWrapper;
import com.example.ahmed.alcassessment.utils.DBSchema;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by ahmed on 10/6/17.
 */

public class CardDAO {
    private SQLiteDatabase database;
    private SQLHelper helper;

    @Inject
    public CardDAO(Context context){
        helper = new SQLHelper(context);
    }

    public void open(){
        database = helper.getWritableDatabase();
    }

    public void close(){
        database.close();
    }

    private ContentValues getValues(Card card){
        ContentValues values = new ContentValues();
        values.put(DBSchema.currencyFrom, card.getFrom());
        values.put(DBSchema.currencyTo, card.getTo());
        values.put(DBSchema.currentRate, card.getCurrentRate());
        values.put(DBSchema.UUID, card.getUuid().toString());
        return values;
    }

    public void addCard(Card card){
        database.insert(DBSchema.tableName, null, getValues(card));
    }

    public void updateCard(Card card){
        database.update(DBSchema.tableName, getValues(card),
                card.getUuid().toString(), new String[]{DBSchema.UUID});
    }

    private Cursor query(String whereClause, String[] args){
        return database.query(
                DBSchema.tableName, null, whereClause, args, null, null, null
        );
    }

    public List<Card> getAllCards(){
        Cursor cursor = query(null, null);
        cursor.moveToFirst();
        List<Card> cardList = new ArrayList<>(cursor.getCount());
        while (!cursor.isAfterLast()){
            CardWrapper wrapper = new CardWrapper(cursor);
            Card card = wrapper.getCard();
            cardList.add(card);
            cursor.moveToNext();
        }
        return cardList;
    }
}
