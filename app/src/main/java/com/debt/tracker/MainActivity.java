package com.debt.tracker;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private EditText etName, etAmount;
    private TextView tvRecords;
    private Button btnAddDebt, btnClear;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.etName);
        etAmount = findViewById(R.id.etAmount);
        tvRecords = findViewById(R.id.tvRecords);
        btnAddDebt = findViewById(R.id.btnAddDebt);
        btnClear = findViewById(R.id.btnClear);

        sharedPreferences = getSharedPreferences("DebtPrefs", MODE_PRIVATE);
        loadRecords();

        btnAddDebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString().trim();
                String amount = etAmount.getText().toString().trim();

                if (name.isEmpty() || amount.isEmpty()) {
                    Toast.makeText(MainActivity.this, "يرجى كتابة الاسم والمبلغ!", Toast.LENGTH_SHORT).show();
                    return;
                }

                String currentData = sharedPreferences.getString("records", "");
                String newEntry = "👤 " + name + " : " + amount + " ريال\n-----------------------\n";
                String updatedData = newEntry + currentData;

                sharedPreferences.edit().putString("records", updatedData).apply();
                
                etName.setText("");
                etAmount.setText("");
                loadRecords();
                Toast.makeText(MainActivity.this, "تم حفظ الدين بنجاح", Toast.LENGTH_SHORT).show();
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences.edit().remove("records").apply();
                loadRecords();
                Toast.makeText(MainActivity.this, "تم مسح جميع السجلات", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadRecords() {
        String records = sharedPreferences.getString("records", "");
        if (records.isEmpty()) {
            tvRecords.setText("لا توجد ديون مسجلة حالياً.");
        } else {
            tvRecords.setText(records);
        }
    }
}

