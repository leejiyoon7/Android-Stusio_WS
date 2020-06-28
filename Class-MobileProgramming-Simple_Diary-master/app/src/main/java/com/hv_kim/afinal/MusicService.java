package com.hv_kim.afinal;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.SeekBar;

/**
 * Created by hb-kim on 2018-06-07.
 */

public class MusicService extends Service{

    MediaPlayer mPlayer;

    @Override
    public void onCreate() {
        super.onCreate();
    }
    @Override
    public void onDestroy() {
        mPlayer.stop();
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.snow);
        mPlayer.setLooping(true);
        mPlayer.start();

        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

}
