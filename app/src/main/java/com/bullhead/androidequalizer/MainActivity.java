package com.bullhead.androidequalizer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.bullhead.equalizer.EqualizerFragment;

public class MainActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mediaPlayer = MediaPlayer.create(this, R.raw.top_rock);
        int sessionId = mediaPlayer.getAudioSessionId();
        mediaPlayer.setLooping(true);
        EqualizerFragment equalizerFragment = EqualizerFragment.newBuilder()
                .setAccentColor(Color.parseColor("#4caf50"))
                .setAudioSessionId(sessionId)
                .build();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.eqFrame, equalizerFragment)
                .commit();

        mediaPlayer.start();


        IntentFilter iF = new IntentFilter();
        iF.addAction("com.android.music.playstatechanged");
        iF.addAction("com.htc.music.playstatechanged");
        iF.addAction("fm.last.android.playstatechanged");
        iF.addAction("com.sec.android.app.music.playstatechanged");
        iF.addAction("com.nullsoft.winamp.playstatechanged");
        iF.addAction("com.amazon.mp3.playstatechanged");
        iF.addAction("com.miui.player.playstatechanged");
        iF.addAction("com.real.IMP.playstatechanged");
        iF.addAction("com.sonyericsson.music.playstatechanged");
        iF.addAction("com.rdio.android.playstatechanged");
        iF.addAction("com.samsung.sec.android.MusicPlayer.playstatechanged");
        iF.addAction("com.andrew.apollo.playstatechanged");
        iF.addAction("gonemad.dashclock.music.playstatechanged");
        iF.addAction("com.piratemedia.musicmod.playstatechanged");
        iF.addAction("com.tbig.playerpro.playstatechanged");
        iF.addAction("org.abrantix.rockon.rockonnggl.playstatechanged");
        iF.addAction("com.maxmpz.audioplayer.playstatechanged");
        iF.addAction("com.doubleTwist.androidPlayer.playstatechanged");
        iF.addAction("com.lge.music.playstatechanged");
        registerReceiver(mReceiver, iF);
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Uri mAudioUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            String selection = MediaStore.Audio.Media.TITLE + " == \"" + intent.getStringExtra("track") + "\"";
            String[] STAR = {"*"};

            Cursor cursor = getContentResolver().query(mAudioUri, STAR, selection, null, null);

            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();

                if (cursor.getColumnIndex(MediaStore.Audio.Media.TITLE) != -1) {
                    String fullpath = cursor.getString(cursor
                            .getColumnIndex(MediaStore.Audio.Media.DATA));
                    Toast.makeText(getApplicationContext(), "song path: " + fullpath, Toast.LENGTH_LONG).show();
                }
            }
        }
    };

    @Override
    protected void onPause() {
        try {
//            mediaPlayer.pause();
        } catch (Exception ex) {
            //ignore
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
//            mediaPlayer.start();
        } catch (Exception ex) {
            //ignore
        }
    }
}
