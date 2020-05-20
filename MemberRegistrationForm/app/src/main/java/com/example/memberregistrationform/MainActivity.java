package com.example.memberregistrationform;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    SeekBar age;
    TextView ageTxt;
    Spinner spinner;
    ImageButton photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        photo = findViewById(R.id.PhotoBtn);
        age = findViewById(R.id.Age_seekBar);
        ageTxt = findViewById(R.id.ageChange);

        registerForContextMenu(photo);

        age.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ageTxt.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        spinner = findViewById(R.id.JobSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.job_list,
                android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_new:
                toastMsg("New Member");
                break;
            case R.id.action_del:
                toastMsg("Delete Member");
                break;
            case R.id.action_search:
                toastMsg("Search Member");
                break;
            case R.id.action_list:
                toastMsg("List Member");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    void toastMsg(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();}

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_fcm,menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ctx_photo: toastMsg("Add Photo");break;
            case R.id.ctx_avatar: toastMsg("Add Avatar");break;
            case R.id.ctx_reset: toastMsg("Reset to default");break;
        }

        return super.onContextItemSelected(item);
    }
}
