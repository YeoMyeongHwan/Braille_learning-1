package com.example.yeo.practice.menu;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.example.yeo.practice.R;

/*
대메뉴 화면 음성 출력 서비스
 */
public class Menu_main_service extends Service {
    private static final String TAG = "Menu_service";
    MediaPlayer basic,master,quiz, tutorial, explain_tutorial, explain_basic, explain_master, explain_quiz;
    public static int menu_page = 1;
    static boolean finish = false;
    public Menu_main_service() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        tutorial = MediaPlayer.create(this, R.raw.directions); //사용설명서
        basic = MediaPlayer.create(this, R.raw.basic); //기초과정
        master = MediaPlayer.create(this, R.raw.master); //숙련과정
        quiz =MediaPlayer.create(this, R.raw.quiz); // 퀴즈

        //반복제거
        basic.setLooping(false);
        master.setLooping(false);
        quiz.setLooping(false);
        tutorial.setLooping(false);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startID){
        switch(menu_page){
            case 1: //사용설명서
                if(quiz.isPlaying()){
                    quiz.reset();
                    quiz=MediaPlayer.create(this,R.raw.quiz);
                }
                if(basic.isPlaying()){
                    basic.reset();
                    basic=MediaPlayer.create(this,R.raw.basic);
                }
                tutorial.start();
                break;
            case 2: //기초과정
                if(tutorial.isPlaying()){
                    tutorial.reset();
                    tutorial=MediaPlayer.create(this,R.raw.directions);
                }
                if(master.isPlaying()){
                    master.reset();
                    master=MediaPlayer.create(this,R.raw.master);
                }
                basic.start();
                break;
            case 3: //숙련과정
                if(basic.isPlaying()){
                    basic.reset();
                    basic=MediaPlayer.create(this,R.raw.basic);
                }
                if(quiz.isPlaying()){
                    quiz.reset();
                    quiz=MediaPlayer.create(this,R.raw.quiz);
                }
                master.start();
                break;
            case 4: //퀴즈
                if(master.isPlaying()){
                    master.reset();
                    master=MediaPlayer.create(this,R.raw.master);
                }
                if(tutorial.isPlaying()){
                    tutorial.reset();
                    tutorial=MediaPlayer.create(this,R.raw.directions);
                }
                quiz.start();
                break;
            case 5:
                break;
        }
        return START_NOT_STICKY;

    }
}
