package com.example.yeo.practice.master_practice;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.example.yeo.practice.Braille_data.dot_word;
import com.example.yeo.practice.Display_Practice.Braille_long_display;
import com.example.yeo.practice.Display_Practice.Braille_long_practice;
import com.example.yeo.practice.MainActivity;
import com.example.yeo.practice.Menu_info;
import com.example.yeo.practice.R;
import com.example.yeo.practice.WHclass;


public class Word_service extends Service {
    /*
단어 연습에서 출력되는 음성파일을 관리하는 서비스 클래스
 */
    private static final String TAG = "Menu_service";
    MediaPlayer  wordfinish, toilet, love, exit, enterance, subway, korea, computer, dot, mom, dad, seoul,
                 right, left, direction, seat, smartphone, stair, handphone, bookstore;
    MediaPlayer Word[] ;//음성파일을 저장하는 배열 변수
    int setting[] = new int[dot_word.wordcount];
    int rawid[];//음성파일의 주소를 저장하는 배열 변수
    static public boolean finish = false;//점자 학습의 종료를 알리는 변수
    static int menu_page = 1;
    int previous=0;
    boolean progress = false;
    public Word_service() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {

        super.onCreate();
        wordfinish = MediaPlayer.create(this, R.raw.wordfinish);

        Word = new MediaPlayer[]{toilet, love, exit, enterance, subway, korea, computer, dot, mom,
                dad, mom, seoul, right, left, direction, seat, smartphone, stair, handphone, bookstore};
        // 선언된 음성 변수들을 배열 변수에 저장

        rawid = new int[]{R.raw.word_toilet, R.raw.word_love, R.raw.word_exit, R.raw.word_entrance, R.raw.word_subway, R.raw.word_korea, R.raw.word_computer, R.raw.word_dot
                , R.raw.word_mom, R.raw.word_dad, R.raw.word_seoul, R.raw.word_right, R.raw.word_left, R.raw.word_direction, R.raw.word_seat, R.raw.word_smartphone, R.raw.word_stair
                , R.raw.word_handphone, R.raw.word_bookstore};
        // 음성파일의 id 주소를 배열변수에 저장

        for (int i = 0; i < dot_word.wordcount; i++) {
            setting[i] = 0;
        }
    }
    public void init(){//사용한 음성파일을 재 설정해주는 함수
        if(Word[previous].isPlaying()) {
            Word[previous].reset();
            Word[previous] = MediaPlayer.create(this, rawid[previous]);
        }
    }
    public int onStartCommand(Intent intent, int flags, int startID){
        if(finish == false) {
            if(WHclass.sel== Menu_info.MENU_NOTE){
                if(Braille_long_practice.pre_reference2==true){
                    init();
                    Braille_long_practice.pre_reference2=false;
                }
                else {
                    if (setting[MainActivity.master_braille_db.master_db_manager.getReference_index(MainActivity.master_braille_db.master_db_manager.My_Note_page)] == 0) {
                        Word[MainActivity.master_braille_db.master_db_manager.getReference_index(MainActivity.master_braille_db.master_db_manager.My_Note_page)] =
                                MediaPlayer.create(this, rawid[MainActivity.master_braille_db.master_db_manager.getReference_index(MainActivity.master_braille_db.master_db_manager.My_Note_page)]);
                        Word[MainActivity.master_braille_db.master_db_manager.getReference_index(MainActivity.master_braille_db.master_db_manager.My_Note_page)].setLooping(false);
                        setting[MainActivity.master_braille_db.master_db_manager.getReference_index(MainActivity.master_braille_db.master_db_manager.My_Note_page)] = 1;
                    }
                    if (progress == false) {
                        progress = true;
                        previous = MainActivity.master_braille_db.master_db_manager.getReference_index(MainActivity.master_braille_db.master_db_manager.My_Note_page);
                    } else if (progress == true) {
                        init();
                        previous = MainActivity.master_braille_db.master_db_manager.getReference_index(MainActivity.master_braille_db.master_db_manager.My_Note_page);
                    }
                    Word[MainActivity.master_braille_db.master_db_manager.getReference_index(MainActivity.master_braille_db.master_db_manager.My_Note_page)].start();
                    Braille_long_practice.pre_reference2=true;
                }
            }
            else {
                if (setting[Braille_long_display.page] == 0) {
                    Word[Braille_long_display.page] = MediaPlayer.create(this, rawid[Braille_long_display.page]);
                    Word[Braille_long_display.page].setLooping(false);
                    setting[Braille_long_display.page] = 1;
                }
                if (progress == false) {
                    progress = true;
                    previous = Braille_long_display.page;
                } else if (progress == true) {
                    init();
                    previous = Braille_long_display.page;
                }
                Word[Braille_long_display.page].start();
            }

        }
        else {
            init();
            wordfinish.start();
            finish = false;
            wordfinish.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    wordfinish.reset();
                    wordfinish = MediaPlayer.create(Word_service.this, R.raw.letterfinish);
                }
            });
        }
        return START_NOT_STICKY;
    }
}