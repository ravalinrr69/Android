package com.example.android.currencyconverter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends Activity {


    TextView textView;
    String printResult;
    EditText editText;
    String currencyType;
    EditText amountEditText;
    double finalValue;
    Button buttonEuro;
    String euroString;
    Button buttonRupee;
    String rupeeString;
    Button buttonPound;
    String poundString;

    double finalValueAfterConversionEuro;
    double finalValueAfterConversionRupee;
    double finalValueAfterConversionPound;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.converted_amount_tv);
        printResult = textView.getText().toString();
        editText = (EditText) findViewById(R.id.result_tv);
        currencyType = editText.getText().toString();

        buttonEuro = (Button) findViewById(R.id.euro_tv);
        euroString = buttonEuro.getText().toString();

        buttonPound = (Button) findViewById(R.id.pound_tv);
        poundString = buttonPound.getText().toString();

        buttonRupee = (Button) findViewById(R.id.indian_rupee_tv);
        rupeeString = buttonRupee.getText().toString();
    }

    public void displayEuroEt(View view) {
        editText.setText(euroString);
        Log.d("currency amount", "value= " + finalValue);
    }

    public void displayIndianRupeeEt(View view) {
        editText.setText(rupeeString);
        Log.d("currency amount", "value= " + finalValue);
    }

    public void displayBritishPoundEt(View view) {
        editText.setText(poundString);
        Log.d("currency amount", "value= " + finalValue);
    }

    public void closeApp(View view) {
        this.finish();
    }

    public void convert(View view) {
        currencyType = editText.getText().toString();
        Log.d("Inside convert method", "........" + currencyType);
        amountEditText = (EditText) findViewById(R.id.dollar_editText);
        String value = amountEditText.getText().toString();
        finalValue = Double.parseDouble(value);

        if (currencyType.equals("Euro")) {
            finalValueAfterConversionEuro = finalValue * 0.81;
            Log.d("dollar to euro", "value= " + finalValueAfterConversionEuro);
            printResult = "Dollar Amount $" + finalValue + " converted to " + finalValueAfterConversionEuro + " Euros";
            textView.setText(printResult);
        } else if (currencyType.equals("Indian Rupee")) {
            finalValueAfterConversionRupee = finalValue * 64.39;
            Log.d("dollar to euro", "value= " + finalValueAfterConversionRupee);
            printResult = "Dollar Amount $" + finalValue + " converted to " + finalValueAfterConversionRupee + " Indian Rupees";
            textView.setText(printResult);
        } else {
            finalValueAfterConversionPound = finalValue * 0.71;
            Log.d("dollar to euro", "value= " + finalValueAfterConversionPound);
            printResult = "Dollar Amount $" + finalValue + " converted to " + finalValueAfterConversionPound + " British Pounds";
            textView.setText(printResult);
        }

    }
}
