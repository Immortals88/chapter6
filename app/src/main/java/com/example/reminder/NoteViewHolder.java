package com.example.reminder;

import android.graphics.Color;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class NoteViewHolder extends RecyclerView.ViewHolder {
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT =
            new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss", Locale.ENGLISH);

    private CheckBox checkBox;
    private TextView contentText;
    private TextView dateText;
    private View deleteBtn;
    private final NoteOperation operator;


    public NoteViewHolder(@NonNull View itemView,NoteOperation operator) {

        super(itemView);
        this.operator=operator;
        checkBox = itemView.findViewById(R.id.checkbox);
        contentText = itemView.findViewById(R.id.text_content);
        dateText = itemView.findViewById(R.id.text_date);
        deleteBtn = itemView.findViewById(R.id.btn_delete);

    }

    public void bind(final mySchema note){
        contentText.setText(note.getText());
        dateText.setText(SIMPLE_DATE_FORMAT.format(note.getDate()));
        checkBox.setChecked(note.getState() == 0);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                note.setState(isChecked ? 0 : 1);
                operator.updateNote(note);
            }
        });
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operator.deleteNote(note);
            }
        });
        if (note.getState() == 0) {
            contentText.setTextColor(Color.GRAY);

        } else {
            contentText.setTextColor(Color.BLACK);
        }
        switch (note.getPriority()){
            case 3:
                itemView.setBackgroundColor(Color.RED);
                break;

            case 2:
                itemView.setBackgroundColor(Color.YELLOW);
                break;


        }

    }
}
