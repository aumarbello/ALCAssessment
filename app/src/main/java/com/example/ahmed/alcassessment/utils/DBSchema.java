package com.example.ahmed.alcassessment.utils;

/**
 * Created by ahmed on 10/6/17.
 */

public final class DBSchema {
    public static final String tableName = "CARDS_TABLE";
    public static final String currencyFrom = "CURRENCY_FROM";
    public static final String currencyTo = "CURRENCY_TO";
    public static final String currentRate = "CURRENT_RATE";
    public static final String UUID = "CARD_ID";

    public static final String createTable = "CREATE TABLE IF NOT EXISTS " + tableName
            + "(" + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + currencyTo + " TEXT, "
            + currencyFrom + " TEXT, "
            + currentRate + " DOUBLE, "
            + UUID + " TEXT" +
            ")";
    public static final String dropTable = "DROP TABLE IF EXISTS " + tableName;
}
