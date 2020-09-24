package com.example.mydiary;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class DiaryAddActivity extends AppCompatActivity {

    DiaryDAO dao;
    Uri selectedImageUri;
    ImageButton dpicture;

    private DatePickerDialog.OnDateSetListener callbackMethod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diary_add);

        dao = DiaryDAO.open(this);

        final EditText dtitle = findViewById(R.id.add_title);
        final TextView ddate = findViewById(R.id.add_date);
        final Spinner dweather = findViewById(R.id.add_weather);
        final EditText dcontent = findViewById(R.id.add_content);
        dpicture = findViewById(R.id.imageButton);

        ActionBar ab = getSupportActionBar();
        ab.setTitle("Add New Diary");

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.emotionlist, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dweather.setAdapter(adapter);

        dpicture.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent. setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, 200);
            }
        });

        Button saveBtn = findViewById(R.id.save_btn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Diary d = new Diary();
                d.setTitle(dtitle.getText().toString());
                d.setDate(ddate.getText().toString());
                d.setWeather(dweather.getSelectedItem().toString());
                d.setPicturelink(changeUri(selectedImageUri.toString()));
                d.setContent(dcontent.getText().toString());
                dao.addDiary(d);
                finish();
            }
        });

        callbackMethod = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                ddate.setText((year+"-"+month+"-"+dayOfMonth));
            }
        };

    }

    public String changeUri(String uri){

        Cursor c = getContentResolver().query(Uri.parse(uri),null,null,null,null);
        c.moveToNext();
        String path=c.getString(c.getColumnIndex(MediaStore.MediaColumns.DATA));
        c.close();

        return path;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            dpicture.setImageURI(selectedImageUri);
        }
    }

    public void OnClickHandler(View view) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(this, callbackMethod,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

}
