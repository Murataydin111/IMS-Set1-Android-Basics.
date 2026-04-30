package com.example.lab1helloworld;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class CalculatorActivity extends AppCompatActivity {

    TextView display;

    double previousResult = 0;
    String currentOperator = "";
    String currentInput = "";

    boolean justPressedEquals = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        display = findViewById(R.id.display);

        setupButtons();
    }

    private void setupButtons() {

        int[] buttons = {
                R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3,
                R.id.btn4, R.id.btn5, R.id.btn6,
                R.id.btn7, R.id.btn8, R.id.btn9,
                R.id.btnAdd, R.id.btnSub, R.id.btnMul, R.id.btnDiv,
                R.id.btnEquals, R.id.btnClear, R.id.btnPow
        };

        for (int id : buttons) {
            Button btn = findViewById(id);
            btn.setOnClickListener(v -> handleInput(btn.getText().toString()));
        }
    }

    private void handleInput(String value) {

        // sayı girilirse
        if (value.matches("[0-9]")) {
            if (justPressedEquals) reset();

            currentInput += value;
            display.setText(currentInput);
        }

        // temizle
        else if (value.equals("C")) {
            reset();
        }

        // eşittir
        else if (value.equals("=")) {
            calculate();
            justPressedEquals = true;
        }

        // operatör
        else {
            if (currentInput.isEmpty()) return;

            calculate();
            currentOperator = value;
            justPressedEquals = false;
        }
    }

    private void calculate() {

        if (currentInput.isEmpty()) return;

        double number = Double.parseDouble(currentInput);

        switch (currentOperator) {
            case "+": previousResult += number; break;
            case "-": previousResult -= number; break;
            case "*": previousResult *= number; break;

            case "/":
                if (number == 0) {
                    display.setText("Error");
                    reset();
                    return;
                }
                previousResult /= number;
                break;

            case "num":
                previousResult = Math.pow(previousResult, number);
                break;

            default:
                previousResult = number;
        }

        display.setText(String.valueOf(previousResult));
        currentInput = "";
    }

    private void reset() {
        previousResult = 0;
        currentOperator = "";
        currentInput = "";
        display.setText("0");
        justPressedEquals = false;
    }
}