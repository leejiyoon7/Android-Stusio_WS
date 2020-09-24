package com.example.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
public class DiaryActivity extends AppCompatActivity {

    final String TAG = getClass().getSimpleName();
    List<Diary> diarys;
    ListView dlistview;
    DiaryDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        //파일 접근권한 부여
        if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "권한 설정 완료");
        } else {
            Log.d(TAG, "권한 설정 요청");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DiaryAddActivity.class);
                startActivity(intent);
            }
        });

        dao = DiaryDAO.open(this);
        diarys = dao.getAll();

        dlistview = findViewById(R.id.item_view);
        DiaryAdapter adapter = new DiaryAdapter(this,R.layout.list_diary, diarys);
        dlistview.setAdapter(adapter);

        dlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                View diaryview= (View) View.inflate(MainActivity.this,R.layout.watch_diary, null);

                AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);
                TextView text = (TextView) diaryview.findViewById(R.id.textView2);
                ImageView picOFDay = diaryview.findViewById(R.id.imageView);
                String content = diarys.get(position).getContent();
                Uri link = Uri.parse(diarys.get(position).getPicturelink());

                text.setText(content);
                picOFDay.setImageURI(link);

                dlg.setView(diaryview);
                dlg.setPositiveButton("확인",null);
                dlg.show();
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //db 전체 삭제후 반영
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.delete_all) {
            dao.deleteAll();
            onResume();
        }
        return super.onOptionsItemSelected(item);
    }

    //db 반영하여 표시
    @Override
    public void onResume() {
        super.onResume();
        DiaryAdapter adapter = (DiaryAdapter) dlistview.getAdapter();
        adapter.diarys = dao.getAll();
        this.diarys = adapter.diarys;
        adapter.notifyDataSetChanged();
        Toolbar toolbar = findViewById(R.id.toolbar);

        if(dao.emotionLast().equals("매우기쁨")){
            toolbar.setBackgroundColor(Color.parseColor("#afe3ff"));
        }else if(dao.emotionLast().equals("기쁨")){
            toolbar.setBackgroundColor(Color.parseColor("#1612e6"));
        }else if(dao.emotionLast().equals("우울")){
            toolbar.setBackgroundColor(Color.parseColor("#f5d20a"));
        }else if(dao.emotionLast().equals("매우우울")){
            toolbar.setBackgroundColor(Color.parseColor("#0af51e"));
        }else{
            toolbar.setBackgroundColor(Color.parseColor("#EFAAAA"));
        }
    }
}