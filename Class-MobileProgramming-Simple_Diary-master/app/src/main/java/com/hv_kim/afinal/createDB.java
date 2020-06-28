package com.hv_kim.afinal;

/*저장버튼 누르고 DB에 저장하는 것이 안됨.
음악 서비스: seekbar랑 연동 안됨.
구글지도 추가해야함.
gridview클릭하면 대화상자로 내용 보이기
*/


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;

public class createDB extends Activity{

    myDBHelper myHelper;
    SQLiteDatabase sqlDB;

    TextView textChooseDate;
    LinearLayout layoutPic;
    EditText editText;
    String picPath;
    final int REQ_CODE_SELECT_IMAGE = 100;
    ImageButton btnAddPic;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_new_page);

        textChooseDate = (TextView) findViewById(R.id.textChooseDate);
        layoutPic = (LinearLayout) findViewById(R.id.layoutPic);
        editText = (EditText) findViewById(R.id.editText);
        ImageButton btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnAddPic = (ImageButton) findViewById(R.id.btnAddPic);
        Button btnSave = findViewById(R.id.btnSave);
        myHelper = new myDBHelper(this);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnAddPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQ_CODE_SELECT_IMAGE);
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sqlDB = myHelper.getWritableDatabase();
               sqlDB.execSQL("insert into diaryTBL values ('"+textChooseDate.getText()+"' , '"+ picPath +"', '"+editText.getText()+"');" );
                sqlDB.close();
                Toast.makeText(getBaseContext(), "DB에 저장되었습니다", Toast.LENGTH_SHORT).show();
                finish();
            }
        });


        textChooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                View dialogView;
                dialogView = (View) View.inflate(createDB.this, R.layout.dialog1, null);
                final DatePicker datePicker = dialogView.findViewById(R.id.datePicker);

                AlertDialog.Builder dlg = new AlertDialog.Builder(createDB.this);
                dlg.setTitle("날짜 선택");
                dlg.setView(dialogView);
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String dateY = Integer.toString(datePicker.getYear());
                        String dateM = Integer.toString(datePicker.getMonth());
                        String dateD = Integer.toString(datePicker.getDayOfMonth());
                        String text =dateY+"."+dateM+"."+dateD;
                        textChooseDate.setText(text);
                    }
                });
                dlg.setNegativeButton("취소", null);
                dlg.show();
            }
        });
    }   //onCreate문 끝나는 시점

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        btnAddPic.setVisibility(View.GONE);

        ImageView imgView = findViewById(R.id.imgView);
        imgView.setVisibility(View.VISIBLE);
        if(requestCode == REQ_CODE_SELECT_IMAGE)
            if(resultCode == Activity.RESULT_OK){
                picPath=changeUri(data.getData().toString());
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                imgView.setImageBitmap(bitmap);
                Log.i("확인", "picPath : "+ picPath);

        }

        super.onActivityResult(requestCode, resultCode, data);
    }
    public String changeUri(String uri){

        Cursor c = getContentResolver().query(Uri.parse(uri),null,null,null,null);

        c.moveToNext();
        String path=c.getString(c.getColumnIndex(MediaStore.MediaColumns.DATA));
        c.close();
        Log.i("확인", "path = "+path);
        return path;
    }


    public class myDBHelper extends SQLiteOpenHelper {
        public myDBHelper(Context context) {
            super(context, "diaryDB", null , 1);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {

        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
    }

}
