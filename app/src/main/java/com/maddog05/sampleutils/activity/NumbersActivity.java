package com.maddog05.sampleutils.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.AppCompatEditText;
import android.view.View;
import android.widget.Toast;

import com.maddog05.maddogutilities.number.Numbers;
import com.maddog05.sampleutils.R;

public class NumbersActivity extends AppCompatActivity {

    private AppCompatEditText numberEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers);

        numberEt = findViewById(R.id.et_numbers_input);

        findViewById(R.id.btn_one).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numberEt.getText().toString().trim().isEmpty())
                    showError();
                else
                    showOperationResult(Numbers.formatIntegerTwoNumbers(Integer.parseInt(numberEt.getText().toString())));
            }
        });
        findViewById(R.id.btn_two).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numberEt.getText().toString().trim().isEmpty())
                    showError();
                else
                    showOperationResult(Numbers.formatDoubleTwoDecimals(Double.parseDouble(numberEt.getText().toString())));
            }
        });
        findViewById(R.id.btn_three).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numberEt.getText().toString().trim().isEmpty())
                    showError();
                else
                    showOperationResult(Numbers.isLeapYear(Integer.parseInt(numberEt.getText().toString())) ? "true" : "false");
            }
        });
    }

    private void showError() {
        Toast.makeText(NumbersActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
    }

    private void showOperationResult(String text) {
        Toast.makeText(NumbersActivity.this, text, Toast.LENGTH_SHORT).show();
    }

}
