package com.example.yeo.practice.menu;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;

import com.example.yeo.practice.R;

import java.util.Locale;

//숙련과정 메뉴 음성 출력 서비스

public class Menu_master_service extends Service {
    private static final String TAG = "Menu_service";
    MediaPlayer letter, word;
    static int menu_page = 1;
    static boolean finish = false;
    public Menu_master_service() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        letter = MediaPlayer.create(this, R.raw.letter); //글자연습
        word = MediaPlayer.create(this, R.raw.word); //단어연습
        //반복제거
        letter.setLooping(false);
        word.setLooping(false);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startID){
        switch(menu_page){
            case 1: //글자연습
                if(word.isPlaying()){
                    word.reset();
                    word=MediaPlayer.create(this,R.raw.word);
                }
                letter.start();
                break;
            case 2: //단어연습
                if(letter.isPlaying()){
                    letter.reset();
                    letter=MediaPlayer.create(this,R.raw.letter);
                }
                word.start();
                break;
        }
        return START_NOT_STICKY;
    }
}
