package com.hv_kim.afinal;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    myDBHelper myHelper;
    SQLiteDatabase sqlDB;

    MediaPlayer mPlayer;

    Switch switch1;
    Intent intent;

    ArrayList<String> theDays = new ArrayList<String>();
    ArrayList<String> picPaths = new ArrayList<String>();
    ArrayList<String> texts = new ArrayList<String>();
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MODE_PRIVATE);

        ImageButton btnMap=findViewById(R.id.btnMap);
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mapintent = new Intent(getApplicationContext(), MapActivity.class);
                startActivity(mapintent);
            }
        });

        intent = new Intent(this, MusicService.class);
        switch1= (Switch) findViewById(R.id.switch1);
        switch1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (switch1.isChecked() == true) {
                    startService(intent);
                } else {
                    stopService(intent);
                }
            }
        });

/*        pbMP3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(b){
                    mPlayer.seekTo(i);
                }
                SimpleDateFormat timeFormat = new SimpleDateFormat("mm:ss");
                TextView tvTime = (TextView) findViewById(R.id.tvTime);
                tvTime.setText("진행률 : " + i + " %");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });*/
        myHelper = new myDBHelper(this);

        sqlDB = myHelper.getReadableDatabase();
        cursor = sqlDB.rawQuery("SELECT * FROM diaryTBL;", null);
        String theDay=null;
        String picPath=null;
        String text=null;
        while(cursor.moveToNext()) {  // 커서를 다음 행으로 이동시킨다.
            theDay = cursor.getString(0);  // 0번째 열의 데이터를 가져온다.
            theDays.add(theDay); // 동적배열에 가져온 데이터를 추가한다.
            Log.i("확인", "theDays 완료");
            picPath = cursor.getString(1);
            Log.i("확인", "picPath의 값: "+picPath);
            picPaths.add(picPath);
            text = cursor.getString(2);
            texts.add(text);
            Log.i("확인", "text의 값: "+text);


        }
        cursor.close();
        sqlDB.close();



        //create gridAdapter
       final GridView gridView = (GridView) findViewById(R.id.gridView);
        MyGridAdapter gridAdapter = new MyGridAdapter(this);
        gridView.setAdapter(gridAdapter);

        ImageButton btnCreate= findViewById(R.id.btnCreate);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), createDB.class);
                startActivity(intent);
            }
        });
        //item 클릭시 내용 대화상자로 보이기
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                View dialogView;
                dialogView = (View) View.inflate(MainActivity.this, R.layout.dialog_viewitem, null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);
                TextView text2 = (TextView) dialogView.findViewById(R.id.text2);
                ImageView picOftheDay2 = dialogView.findViewById(R.id.picOftheDay2);

                sqlDB=myHelper.getReadableDatabase();
                Cursor cursor;
                cursor = sqlDB.rawQuery("select * from diaryTBL;", null);
                cursor.moveToPosition(position);
                dlg.setTitle(cursor.getString(0));
                sqlDB.close();
                cursor.close();

                picOftheDay2.setImageURI(Uri.parse(picPaths.get(position)));

                text2.setText(texts.get(position).toString());
                Log.i("확인", "texts["+position+"] : "+texts.get(position));


                dlg.setView(dialogView);
                dlg.setPositiveButton("확인", null);
                dlg.show();
            }
        });
    }//onCreate문 끝남.

    public class MyGridAdapter extends BaseAdapter{
        Context context;


        public MyGridAdapter(Context c){
            context = c;
        }
        @Override
        public int getCount() {


            return theDays.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View gridView;
            if (view == null) {

                // get layout from mobile.xml
                gridView = inflater.inflate(R.layout.item, null);

                // set value into textview
                TextView textView = (TextView) gridView
                        .findViewById(R.id.textTheDay);
                textView.setText(theDays.get(i).toString());

                ImageView picView = gridView.findViewById(R.id.picOftheDay);

                sqlDB= myHelper.getReadableDatabase();
                cursor=sqlDB.rawQuery("select picpath from diaryTBL;", null);
                cursor.moveToPosition(i);
                picView.setImageURI(Uri.parse(cursor.getString(0)));

                Log.i("확인", "picPaths["+i+"] : "+cursor.getString(0));
                cursor.close();
                sqlDB.close();

            } else {
                gridView = (View) view;
            }
            gridView.setLayoutParams(new GridView.LayoutParams(400, 400));

            return gridView;
        }
    }

    public class myDBHelper extends SQLiteOpenHelper{
        public myDBHelper(Context context) {
            super(context, "diaryDB", null , 1);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL("create table diaryTBL (date CHAR(10) primary key, picpath CHAR(180), text CHAR(200));");
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS planTBL");
            onCreate(sqLiteDatabase);
        }
    }
}
