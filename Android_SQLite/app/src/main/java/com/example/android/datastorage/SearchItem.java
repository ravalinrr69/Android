package com.example.android.datastorage;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SearchItem extends AppCompatActivity {

    EditText searchItem;
    TextView result;
    String itemName;
    SQLiteDatabase db;
    ItemDatabase helper = new ItemDatabase(this);
    StringBuffer buffer = new StringBuffer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_item);
        result = (TextView) findViewById(R.id.search_results_et);
    }

    public String getData(String name) {

        db = helper.getWritableDatabase();
        String[] columns = {ItemDatabase.COL_ITEM_NAME, ItemDatabase.COL_ITEM_DESCRIPTION, ItemDatabase.COL_ITEM_PRICE,
                ItemDatabase.COL_ITEM_REVIEW};
        String[] selectionArgs = {name};

        Cursor cursor = db.query(ItemDatabase.TABLE_NAME, columns, ItemDatabase.COL_ITEM_NAME + " =? ", selectionArgs, null, null, null);
        Log.d("inside cursor: ", "values = " + cursor);
        while (cursor.moveToNext()) {

           /* int index0 = cursor.getColumnIndex(ItemDatabase.COL_ITEM_NAME);
            int index1 = cursor.getColumnIndex(ItemDatabase.COL_ITEM_DESCRIPTION);
            int index2 = cursor.getColumnIndex(ItemDatabase.COL_ITEM_PRICE);
            int index3 = cursor.getColumnIndex(ItemDatabase.COL_ITEM_REVIEW);*/

            String itemName = cursor.getString(0);
            String itemDesc = cursor.getString(1);
            String itemPrice = cursor.getString(2);
            String itemReview = cursor.getString(3);

            Log.d("item", "name = " + itemName);
            Log.d("item", "desc = " + itemDesc);
            Log.d("item", "price = " + itemPrice);
            Log.d("item", "review = " + itemReview);

            buffer.append("Item Name: " + itemName + ", Item Description: " + itemDesc + ", Item Price: " + itemPrice
                    + ", Item Review: " + itemReview + "\n \n");
        }

        return buffer.toString();
    }

    public void searchItem(View view) {

        searchItem = (EditText) findViewById(R.id.search_by_name);
        itemName = searchItem.getText().toString();
        String resultSet = getData(itemName);
        String message = "No such item found";
        if (resultSet.isEmpty()) {
            result.setText(message);
        } else {
            result.setText(resultSet);
        }
    }
}
