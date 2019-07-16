package com.example.reminder;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_EDIT=999;
    private RecyclerView recyclerView;
    private MyListAdapter notesAdapter;
    private CheckBox  checkBox;
    private myDataBase dataBase;
    private static RefreshNote  flush;
    private static UpdateNoteTask  updateNoteTask;
    private DeleteNoteTask mDeleteNoteTask;
    private boolean extra;
    private List<mySchema> a=new ArrayList<mySchema>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        extra=false;

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MainActivity.this, EditNote.class),REQUEST_EDIT);
            }
        });
        recyclerView=findViewById(R.id.rv_list);
        dataBase=myDataBase.getInstance(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,
                RecyclerView.VERTICAL, false));
        recyclerView.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        notesAdapter = new MyListAdapter(new NoteOperation() {
            @Override
            public void deleteNote(mySchema note) {
                MainActivity.this.deleteNote(note);
            }

            @Override
            public void updateNote(mySchema note) {
                MainActivity.this.updateNode(note);
            }
        });
        recyclerView.setAdapter(notesAdapter);
        checkBox=findViewById(R.id.cb_check);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                extra=b;
                show();
            }
        });
        show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_EDIT
                && resultCode == Activity.RESULT_OK) {
            show();
        }
    }

    private void updateNode(mySchema note) {
        if (dataBase == null) {
            return;
        }
        updateNoteTask = new UpdateNoteTask();
        updateNoteTask.execute(note);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        dataBase.close();

        super.onDestroy();
        dataBase = null;
    }

    public void show(){
        flush = new RefreshNote();
        flush.execute();
    }
    private class UpdateNoteTask extends AsyncTask<mySchema, Integer, Boolean> {
        @Override
        protected Boolean doInBackground(mySchema... notes) {
            return dataBase.noteDao().update(notes[0])> 0;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            super.onPostExecute(success);
            show();
        }
    }

    private class RefreshNote extends AsyncTask<Void, Integer, List<mySchema>> {
        @Override
        protected List<mySchema> doInBackground(Void... voids) {
            if(extra==false)
                return dataBase.noteDao().getNotes();
            else
                a=dataBase.noteDao().getNotes();
                a.addAll(dataBase.noteDao().getExtraNotes());
                return a;
        }

        @Override
        protected void onPostExecute(List<mySchema> notes) {
            super.onPostExecute(notes);
            notesAdapter.refresh(notes);
            recyclerView.setAdapter(notesAdapter);
        }
    }
    private void deleteNote(mySchema note) {
        if (dataBase == null) {
            return;
        }
        mDeleteNoteTask = new DeleteNoteTask();
        mDeleteNoteTask.execute(note);
    }
    private class DeleteNoteTask extends AsyncTask<mySchema, Integer, Boolean> {
        @Override
        protected Boolean doInBackground(mySchema... notes) {
            return dataBase.noteDao().deleteNote(notes[0]) > 0;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            super.onPostExecute(success);
            show();
        }
    }
}
