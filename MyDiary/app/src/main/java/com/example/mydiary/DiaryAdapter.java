package com.example.mydiary;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;
import java.util.Objects;

public class DiaryAdapter extends ArrayAdapter {
    Context context;
    int resId;
    List<Diary> diarys;
    DiaryDAO dao = DiaryDAO.open(getContext());

    public DiaryAdapter(@NonNull Context context,int resource, @NonNull List<Diary> objects) {
        super(context,resource,objects);
        this.context = context;
        this.resId = resource;
        this.diarys = objects;
    }

    @Override
    public int getCount() {
        return diarys.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final int pos = position;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(resId, null);
        }
        final Diary diary = diarys.get(position);
        ImageView diaryImage = convertView.findViewById(R.id.diary_img);
        TextView diaryTitle = convertView.findViewById(R.id.title);
        TextView diaryDate = convertView.findViewById(R.id.date);
        TextView diaryWeather = convertView.findViewById(R.id.weather);
        ImageView share = convertView.findViewById(R.id.share);

        diaryTitle.setText(diary.getTitle());
        diaryDate.setText(diary.getDate());
        diaryWeather.setText(diary.getWeather());
        diaryImage.setImageURI(Uri.parse(diary.getPicturelink()));

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(context.getApplicationContext(), v);
                popup.getMenuInflater().inflate(R.menu.item_menu,
                        popup.getMenu());
                popup.show();
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            //텍스트를 받아와서 공유
                            case R.id.share_txt:
                                Intent intent = new Intent(Intent.ACTION_SEND);
                                intent.setType("text/plain");
                                intent.putExtra(Intent.EXTRA_TEXT,diary.getContent());
                                Intent chooser = Intent.createChooser(intent,"공유");
                                context.startActivity(chooser);
                                break;

                                //이름과 날짜를 받아와서 db 삭제
                                case R.id.delete:
                                    Toast.makeText(context.getApplicationContext(), " Delete Clicked at position " + " : " + pos, Toast.LENGTH_LONG).show();
                                    dao.delete(diary.getTitle(), diary.getDate());
                                    diarys.remove(diary);
                                    notifyDataSetChanged();
                                    break;

                                    default:
                                        break;
                        }
                        return true;
                    }
                });
            }
        });

        return convertView;
    }



}
