package com.peter.moneyconvertor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private static final String[] NOTE_COIN_NAMES = {"€50 Note", "€20 Note", "€10 Note", "€5 " +
            "Note", "€2 Coin", "€1 Coin", "€0.50 Coin", "€0.20 Coin", "€0.10 Coin", "€0.05 Coin"};
    private static final int[] NOTE_COIN_CENT_VALUE = {5000, 2000, 1000, 500, 200, 100, 50, 20,
            10, 5};
    private EditText centsEntered;
    private TextView input_cents_validation, result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        centsEntered = (EditText) findViewById(R.id.centsEdit);
        final Button convert = (Button) findViewById(R.id.convertButton);
        result = (TextView) findViewById(R.id.result);

        // validation for cents input
        input_cents_validation = (TextView) findViewById(R.id.input_cents_validation);
        input_cents_validation.setVisibility(View.GONE);

        convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (centsEntered.getText().toString().equals("")) { // blank entry
                    input_cents_validation.setVisibility(View.VISIBLE);
                    input_cents_validation.setText(R.string.empty_value);
                    result.setText("");
                } else if (!isNumeric(centsEntered.getText().toString())) { // if not numeric
                    input_cents_validation.setVisibility(View.VISIBLE);
                    input_cents_validation.setText(R.string.non_numeric);
                    result.setText("");
                } else if (Integer.parseInt(centsEntered.getText().toString()) < 1) {   // if 0
                    // or negative
                    input_cents_validation.setVisibility(View.VISIBLE);
                    input_cents_validation.setText(R.string.invalid_value);
                    result.setText("");
                } else {
                    input_cents_validation.setVisibility(View.GONE);
                    String money = convertCents();
                    result.setText(money);
                }
            }
        });
    }

    public String convertCents() {
        int cents = Integer.parseInt(centsEntered.getText().toString());
        int NoteCoinCount;
        String conversion = "";
        for (int i = 0; i < NOTE_COIN_NAMES.length; i++) {
            // number of cents greater than i in array
            // i.e. 5000, 2000, 1000 etc
            if (cents >= NOTE_COIN_CENT_VALUE[i]) {
                NoteCoinCount = cents / NOTE_COIN_CENT_VALUE[i];
                conversion += NOTE_COIN_NAMES[i] + " x " + NoteCoinCount + ",\n";
                cents = cents % NOTE_COIN_CENT_VALUE[i];
            }
        }
        return conversion;
    }


    public static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
