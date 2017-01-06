package com.example.yeo.practice.menu;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;

import com.example.yeo.practice.R;

import java.util.Locale;

// 퀴즈 메뉴에 대한 음성을 출력해주는 서비스

public class quiz_menu_service extends Service {
    private static final String TAG = "Menu_service";
    MediaPlayer init_quiz,vowel_quiz,final_quiz,number_quiz,alphabet_quiz,sentence_quiz,abbreviation_quiz,letter_quiz,word_quiz;
    MediaPlayer quiz_menu[]; //퀴즈 메뉴 음성변수들을 저장하는 배열 변수
    int quiz_menu_count=9;  // 퀴즈메뉴의 갯수
    int rawid[]; //퀴즈 메뉴의 음성 id를 저장하는 배열 변수
    public static int menu_page = 1;
    static boolean finish = false;
    int previous = 0;
    boolean progress =false;
    public quiz_menu_service() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate(){
        super.onCreate();

        //퀴즈메뉴의 음성변수들과 id를 배열변수에 저장
        quiz_menu = new MediaPlayer[] {init_quiz,vowel_quiz,final_quiz,number_quiz,alphabet_quiz,sentence_quiz,abbreviation_quiz,letter_quiz,word_quiz};
        rawid = new int[] {R.raw.initial_quiz, R.raw.vowel_quiz, R.raw.final_quiz, R.raw.num_quiz, R.raw.alphabet_quiz, R.raw.sentence_quiz, R.raw.abbreviation_quiz,
                R.raw.letter_quiz, R.raw.verb_quiz};

        for(int i = 0; i< quiz_menu_count; i++){

            quiz_menu[i] = MediaPlayer.create(this, rawid[i]);
            quiz_menu[i].setLooping(false);
        }

    }
    public void init(){ //음성파일 초기화 함수
        if(quiz_menu[previous].isPlaying()) {
            quiz_menu[previous].reset();
            quiz_menu[previous] = MediaPlayer.create(this, rawid[previous]);
        }
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startID){
        if(finish == false) {
            if (progress == false) {
                progress = true;
                previous = menu_page;
            } else if (progress == true) {
                init();
                previous = menu_page;
            }
            quiz_menu[menu_page].start();
        }
        else {
            init();
            finish = false;
        }
        return START_NOT_STICKY;
    }
}
