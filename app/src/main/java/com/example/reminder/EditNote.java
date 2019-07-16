package com.example.reminder;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Date;

public class EditNote extends AppCompatActivity {

    private myDataBase dataBase;
    private EditText editText;
    private Button addButton;
    private RadioGroup radioGroup;
    private RadioButton low;
    private addNode async;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        dataBase=myDataBase.getInstance(this);
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
                addButton.setClickable(false);
                String content = editText.getText().toString();
                if (content.isEmpty()) {
                    editText.setError("Empty text is not allowed");
                    addButton.setClickable(true);
                    return;
                }
                mySchema node = new mySchema();
                node.setText(content);
                node.setPriority(getSelectedPriority());
                node.setState(1);
                node.setDate((int)(System.currentTimeMillis()));
                async=new addNode();
                async.execute(node);

            }
        });

    }

    @Override
    protected void onDestroy() {
        dataBase.close();
        if (async != null && !async.isCancelled()) {
            async.cancel(true);
        }
        super.onDestroy();
    }

    private int getSelectedPriority() {
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.rb_high:
                return 3;
            case R.id.rb_middle:
                return 2;
            default:
                return 1;
        }
    }

    private class addNode extends AsyncTask<mySchema, Integer, Boolean>{

        @Override
        protected Boolean doInBackground(mySchema... mySchemas) {
            return dataBase.noteDao().InsertNote(mySchemas[0])>0;
        }

        @Override
        protected void onPostExecute(Boolean success){
            super.onPostExecute(success);
            if(success){
                Toast.makeText(EditNote.this,
                        "添加成功", Toast.LENGTH_SHORT).show();
                setResult(Activity.RESULT_OK);
            }
            else {
                Toast.makeText(EditNote.this,"添加失败", Toast.LENGTH_SHORT).show();
            }
            addButton.setClickable(true);
            finish();
        }
    }

}
