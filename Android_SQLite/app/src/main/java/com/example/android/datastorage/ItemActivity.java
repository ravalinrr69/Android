package com.example.android.datastorage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ItemActivity extends AppCompatActivity {

    ItemDatabase db;
    EditText itemName, itemDesc, itemPrice, itemReview;
    Button addItemBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_item);

        db = new ItemDatabase(this);
        itemName = (EditText) findViewById(R.id.editText1);
        itemDesc = (EditText) findViewById(R.id.item_desc_et);
        itemPrice = (EditText) findViewById(R.id.item_price_et);
        itemReview = (EditText) findViewById(R.id.item_review_et);
        addItemBtn = (Button) findViewById(R.id.button2);

    }

    //method to insert records to db
    public void add(View v) {
        boolean isInserted = db.insertData(itemName.getText().toString(), itemDesc.getText().toString(),
                itemPrice.getText().toString(), itemReview.getText().toString());

        //Verifying whether the record is inserted to db or not
        if (isInserted == true)
            Toast.makeText(ItemActivity.this, "Data Inserted: ", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(ItemActivity.this, "Data not Inserted: ", Toast.LENGTH_LONG).show();
    }

}
