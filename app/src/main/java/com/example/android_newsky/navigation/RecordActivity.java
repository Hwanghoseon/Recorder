package com.example.android_newsky.navigation;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.content.pm.PackageManager;

import com.example.android_newsky.R;

import java.io.File;
import java.io.IOException;

public class RecordActivity extends AppCompatActivity {
    MediaPlayer Player;
    MediaRecorder recorder;

    String path ="sdcard/recorder.mp4";

    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
    }

    public void onButton1Clicked(View v) {
        Log.d("RecordActivity", "시작 버튼 클릭됨!");

        try{
            KillPlayer();

            Player = new MediaPlayer();
            Player.setDataSource(path);
            Player.prepare();
            Player.start();

        } catch (IOException e) {
            e.printStackTrace();
        }

        Toast.makeText(getApplicationContext(), "재생을 시작합니다!", Toast.LENGTH_LONG).show();
    }

    private void KillPlayer() {
        if(Player != null) {
            Player.release();
            Player = null;
        }
    }

    public void onButton2Clicked(View v) {
        Log.d("RecordActivity", "일시정지 버튼 클릭됨!");

        if(Player != null && Player.isPlaying()) {
            position = Player.getCurrentPosition();
            Player.pause();;
        }
        Toast.makeText(getApplicationContext(), "재생을 일시정지 합니다!", Toast.LENGTH_LONG).show();
    }

    public void onButton3Clicked(View v) {
        Log.d("RecordActivity", "재시작 버튼 클릭됨!");

        if(Player != null && !Player.isPlaying()) {
            Player.start();
            Player.seekTo(position);
        }
        Toast.makeText(getApplicationContext(), "재생을 재시작합니다!", Toast.LENGTH_LONG).show();
    }

    public void onButton4Clicked(View v) {
        Log.d("RecordActivity", "중지 버튼 클릭됨!");

        if(Player != null && Player.isPlaying()) {
            Player.stop();
        }
        Toast.makeText(getApplicationContext(), "재생을 중지합니다!", Toast.LENGTH_LONG).show();
    }

    public void onButton5Clicked(View v) {
        try {
            if(recorder != null) {
                recorder.stop();
                recorder.release();
                recorder = null;
            }

            File file= Environment.getExternalStorageDirectory();
//갤럭시 S4기준으로 /storage/emulated/0/의 경로를 갖고 시작한다.
            String path=file.getAbsolutePath()+"/"+"recoder.mp3";

            recorder = new MediaRecorder();

            try {
                recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
                recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            recorder.setOutputFile(path);;
            recorder.prepare();
            recorder.start();

            Toast.makeText(getApplicationContext(), "녹음을 시작합니다!", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onButton6Clicked(View v) {
        if(recorder != null) {
            recorder.stop();
            recorder.release();
            recorder = null;

            Toast.makeText(getApplicationContext(), "녹음을 중지합니다!", Toast.LENGTH_LONG).show();
        }
    }

    // 앱이 종료가 되었을 때도
    @Override
    protected void onDestroy() {
        super.onDestroy();

        KillPlayer();
    }

    @Override
    public  boolean onCreateOptionsMenu(Menu menu) {
        // getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_setting) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}