package com.zukron.kalkulator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tvHistory, tvCalculateAndResult;
    private Button btnNumber0, btnNumber1, btnNumber2, btnNumber3, btnNumber4, btnNumber5, btnNumber6, btnNumber7, btnNumber8, btnNumber9;
    private Button btnCSymbol, btnTimeSymbol, btnDivideSymbol, btnMinusSymbol, btnPlusSymbol, btnEqualSymbol;
    private Button btnBackspace, btnDot;
    private String symbols = "";
    private String text = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvHistory = findViewById(R.id.tv_history);
        tvCalculateAndResult = findViewById(R.id.tv_calculate_and_result);

        btnNumber0 = findViewById(R.id.btn_number_0);
        btnNumber0.setOnClickListener(this);
        btnNumber1 = findViewById(R.id.btn_number_1);
        btnNumber1.setOnClickListener(this);
        btnNumber2 = findViewById(R.id.btn_number_2);
        btnNumber2.setOnClickListener(this);
        btnNumber3 = findViewById(R.id.btn_number_3);
        btnNumber3.setOnClickListener(this);
        btnNumber4 = findViewById(R.id.btn_number_4);
        btnNumber4.setOnClickListener(this);
        btnNumber5 = findViewById(R.id.btn_number_5);
        btnNumber5.setOnClickListener(this);
        btnNumber6 = findViewById(R.id.btn_number_6);
        btnNumber6.setOnClickListener(this);
        btnNumber7 = findViewById(R.id.btn_number_7);
        btnNumber7.setOnClickListener(this);
        btnNumber8 = findViewById(R.id.btn_number_8);
        btnNumber8.setOnClickListener(this);
        btnNumber9 = findViewById(R.id.btn_number_9);
        btnNumber9.setOnClickListener(this);

        btnCSymbol = findViewById(R.id.btn_c_symbol);
        btnCSymbol.setOnClickListener(this);
        btnTimeSymbol = findViewById(R.id.btn_time_symbol);
        btnTimeSymbol.setOnClickListener(this);
        btnDivideSymbol = findViewById(R.id.btn_divide_symbol);
        btnDivideSymbol.setOnClickListener(this);
        btnMinusSymbol = findViewById(R.id.btn_minus_symbol);
        btnMinusSymbol.setOnClickListener(this);
        btnPlusSymbol = findViewById(R.id.btn_plus_symbol);
        btnPlusSymbol.setOnClickListener(this);

        btnBackspace = findViewById(R.id.btn_backspace);
        btnBackspace.setOnClickListener(this);
        btnDot = findViewById(R.id.btn_dot);
        btnDot.setOnClickListener(this);

        btnEqualSymbol = findViewById(R.id.btn_equal_symbol);
        btnEqualSymbol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculate(tvCalculateAndResult.getText().toString());
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_number_0:
                text += "0";
                break;
            case R.id.btn_number_1:
                text += "1";
                break;
            case R.id.btn_number_2:
                text += "2";
                break;
            case R.id.btn_number_3:
                text += "3";
                break;
            case R.id.btn_number_4:
                text += "4";
                break;
            case R.id.btn_number_5:
                text += "5";
                break;
            case R.id.btn_number_6:
                text += "6";
                break;
            case R.id.btn_number_7:
                text += "7";
                break;
            case R.id.btn_number_8:
                text += "8";
                break;
            case R.id.btn_number_9:
                text += "9";
                break;
            case R.id.btn_time_symbol:
                text = calculateSymbol(text, 'x');
                break;
            case R.id.btn_divide_symbol:
                text = calculateSymbol(text, '/');
                break;
            case R.id.btn_minus_symbol:
                text = calculateSymbol(text, '-');
                break;
            case R.id.btn_plus_symbol:
                text = calculateSymbol(text, '+');
                break;
            case R.id.btn_c_symbol:
                clear();
                break;
            case R.id.btn_backspace:
                text = backspace(text);
                break;
            case R.id.btn_dot:
                text = dot(text);
                break;
        }

        // jika hasil infinity
        if (tvCalculateAndResult.getText().toString().equals("Infinity")) {
            clear();
        }

        tvCalculateAndResult.setText(text);
    }

    private void clear() {
        this.symbols = "";
        this.text = "";
        tvHistory.setText("");
    }

    private String dot(String text) {
        int length = text.length();
        char strEnd = text.charAt(length - 1);

        if (strEnd == 'x' || strEnd == '/' || strEnd == '+' || strEnd == '-') {
            text += "0.";
        } else {
            text += ".";
        }

        return text;
    }

    /***
     * berfungsi untuk memotong simbol yang double dan simbol yang bertumpuk pada perhitungan
     */
    private String calculateSymbol(String text, char x) {
        if (!TextUtils.isEmpty(tvCalculateAndResult.getText())) {
            int length = text.length();
            char strEnd = text.charAt(length - 1);

            if (strEnd == 'x' || strEnd == '/' || strEnd == '+' || strEnd == '-') {
                text = text.substring(0, (length - 1));
            }

            // collect symbol for calculate
            symbols += x;

            return text + x;
        }
        return "";
    }

    private String backspace(String text) {
        if (TextUtils.isEmpty(tvCalculateAndResult.getText())) {
            text = "";
        } else {
            int length = text.length();
            text = text.substring(0, (length - 1));
        }

        return text;
    }

    private void calculate(String text) {
        if (!TextUtils.isEmpty(tvCalculateAndResult.getText())) {
            String[] textArray = text.split("[x/+-]");
            int textArrayLength = textArray.length;
            int symbolLength = symbols.length();
            double[] numbers = new double[textArrayLength];
            double result = 0;

            // parsing
            for (int i = 0; i < textArrayLength; i++) {
                numbers[i] = Double.parseDouble(textArray[i]);
            }

            for (int i = 0; i < symbolLength; i++) {
                if (symbols.charAt(i) == '/') {
                    result = numbers[i] / numbers[i + 1];
                    numbers[i + 1] = result;
                }
                if (symbols.charAt(i) == 'x') {
                    result = numbers[i] * numbers[i + 1];
                    numbers[i + 1] = result;
                }
                if (symbols.charAt(i) == '+') {
                    result = numbers[i] + numbers[i + 1];
                    numbers[i + 1] = result;
                }
                if (symbols.charAt(i) == '-') {
                    result = numbers[i] - numbers[i + 1];
                    numbers[i + 1] = result;
                }
            }

            String strResult;
            // cari .0 angka dibelakang, jika tidak hanya akan memparsing saja
            if (Double.toString(result).matches(".*[.0]$")) {
                strResult = Double.toString(result).replace(".0", "");
            } else {
                strResult = String.valueOf(result);
            }

            // create tv history
            tvHistory.setText(text);

            // clear all symbol and text change to result
            this.symbols = "";
            this.text = strResult;
            tvCalculateAndResult.setText(strResult);
        }
    }
}