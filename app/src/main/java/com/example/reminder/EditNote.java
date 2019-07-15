package com.example.reminder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class EditNote extends AppCompatActivity {

    private EditText editText;
    private Button addButton;
    private RadioGroup radioGroup;
    private RadioButton low;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        setTitle(R.string.Title);
        editText=findViewById(R.id.ed);
        radioGroup=findViewById(R.id.rg_priority);
        low=findViewById(R.id.rb_low);
        low.setChecked(true);
        addButton=findViewById(R.id.bt_add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

}
