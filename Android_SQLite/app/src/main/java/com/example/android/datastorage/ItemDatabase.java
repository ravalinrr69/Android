package com.example.android.datastorage;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by ravalin on 3/9/18.
 */

public class ItemDatabase extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "products.db";
    public static final String ID = "Id";
    public static final String TABLE_NAME = "items";
    public static final String COL_ITEM_NAME = "ItemName";
    public static final String COL_ITEM_DESCRIPTION = "Description";
    public static final String COL_ITEM_PRICE = "ItemPrice";
    public static final String COL_ITEM_REVIEW = "ItemReview";

    SQLiteDatabase sqLiteDatabase;

    public ItemDatabase(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table items (ItemName TEXT, Description TEXT, ItemPrice TEXT, ITEMREVIEW TEXT)");
    }

    //Check whether the table exists or not and create it
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //Method to insert data into the table
    public boolean insertData(String itemName, String itemDesc, String itemPrice, String itemReview) throws SQLException {

        sqLiteDatabase = this.getWritableDatabase();
        Log.d("sqlitedatabase", "*****value: *****" + sqLiteDatabase);
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_ITEM_NAME, itemName);
        contentValues.put(COL_ITEM_DESCRIPTION, itemDesc);
        contentValues.put(COL_ITEM_PRICE, itemPrice);
        contentValues.put(COL_ITEM_REVIEW, itemReview);
        Log.d("contentValues", "lsit" + contentValues);
        long result = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        Log.d("insertion", "result (true/false): " + result);

        if (result == -1)
            return false;
        else
            return true;
    }
}
