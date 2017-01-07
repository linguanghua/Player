package com.junxu.player;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

public class MainActivity extends AppCompatActivity {
    private GestureDetector mGestureDetector;
    private VideoView vv;
    private FrameLayout vFrameLayout;
    private ListView lv;
    private List<Map<String, String>> mVideoDatas;
    private long duration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (!LibsChecker.checkVitamioLibs(this)) {
            return;
        }
        vv = (VideoView) findViewById(R.id.vv);
        vv.setVideoPath("http://192.168.1.103:8082/DianMing/mp4/mm.mp4");
        vFrameLayout = (FrameLayout) findViewById(R.id.ll);
        MediaController mc = new MediaController(this, true, vFrameLayout);
        vv.setMediaController(mc);
        mc.setVisibility(View.GONE);

        vv.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                vv.start();
                duration = vv.getDuration();
            }
        });
        if(savedInstanceState!=null){
            long currentPosition = savedInstanceState.getLong("currentPosition");
            vv.seekTo(currentPosition);
        }

        mGestureDetector = new GestureDetector(MainActivity.this, new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                long currentPosition = vv.getCurrentPosition();
                 /*向左滑*/
                if (e1.getX() - e2.getX() > 120) {

                    if (currentPosition < 10000) {
                        currentPosition = 0;
                        vv.seekTo(currentPosition);
                    } else {
                        vv.seekTo(currentPosition - 10000);
                    }

                } else if (e2.getX() - e1.getX() > 120) {
              /*向右滑*/
                    if (currentPosition + 10000 > duration) {
                        currentPosition = duration;
                        vv.seekTo(currentPosition);
                    } else {
                        vv.seekTo(currentPosition + 10000);
                    }

                }
                return false;
            }
        });

        lv = (ListView) findViewById(R.id.lv);
        initDatas();

        Adapter adapter = new Adapter(this, mVideoDatas);

        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putLong("currentPosition", vv.getCurrentPosition());
        super.onSaveInstanceState(outState);
    }

    private void initDatas() {
        mVideoDatas = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Map<String, String> map = new HashMap<>();
            map.put("videoFileName", "视频" + i);
            map.put("videoFileTime", "02:56" + i);
            mVideoDatas.add(map);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }
}
