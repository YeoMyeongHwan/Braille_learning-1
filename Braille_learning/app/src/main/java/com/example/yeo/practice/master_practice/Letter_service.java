package com.example.yeo.practice.master_practice;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.example.yeo.practice.Braille_data.dot_letter;
import com.example.yeo.practice.Display_Practice.Braille_long_display;
import com.example.yeo.practice.Display_Practice.Braille_long_practice;
import com.example.yeo.practice.Display_Practice.Braille_short_display;
import com.example.yeo.practice.Display_Practice.Braille_short_practice;
import com.example.yeo.practice.MainActivity;
import com.example.yeo.practice.Menu_info;
import com.example.yeo.practice.R;
import com.example.yeo.practice.WHclass;


public class Letter_service extends Service {
/*
글자 연습에서 출력되는 음성파일을 관리하는 서비스 클래스
 */
    private static final String TAG = "Menu_service";
    MediaPlayer letterfinish, ham, kal, ang, hwa, dae, han, gang, nam, s, il, jak, eom, bba, sam, gu, bak, su, dok, ri, gi, si, gak, jang, hak, gyo, sil,
            big, ryun, gue, min, kuk,chui;
    MediaPlayer Letter[] ; //음성파일을 저장하는 배열 변수
    int setting[] = new int[dot_letter.lettercount];
    int rawid[];//음성파일의 주소를 저장하는 배열 변수
    public static boolean finish = false; //점자 학습의 종료를 알리는 변수
    static int menu_page = 1;
    int previous=0;
    boolean progress = false;

    public Letter_service() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {

        super.onCreate();
        letterfinish=MediaPlayer.create(this, R.raw.letterfinish);

        Letter = new MediaPlayer[] {ham,kal,ang,hwa,dae,han,gang,nam,s,il,jak,eom,bba,sam,gu,bak,su,dok,ri,gi,si,gak,jang,hak,
                gyo,sil,chui,big,ryun,gue,min,kuk};
        // 선언된 음성 변수들을 배열 변수에 저장

        rawid = new int[] { R.raw.letter_ham,R.raw.letter_kal,R.raw.letter_ang,R.raw.letter_hwa,R.raw.letter_dae,R.raw.letter_han,R.raw.letter_gang,R.raw.letter_nam,R.raw.letter_s,R.raw.letter_il,R.raw.letter_jak,R.raw.letter_eom,R.raw.letter_bba
                ,R.raw.letter_sam,R.raw.letter_gu,R.raw.letter_bak,R.raw.letter_su,R.raw.letter_dok,R.raw.letter_ri,R.raw.letter_gi,R.raw.letter_si,R.raw.letter_gak,R.raw.letter_jang,R.raw.letter_hak
                ,R.raw.letter_gyo,R.raw.letter_sil,R.raw.letter_chui,R.raw.letter_big,R.raw.letter_ryun,R.raw.letter_gue,R.raw.letter_min,R.raw.letter_kuk};
        // 음성파일의 id 주소를 배열변수에 저장

        for(int i = 0; i< dot_letter.lettercount; i++){
           setting[i] = 0;
        }


    }
    public void init(){ //사용한 음성파일을 재 설정해주는 함수
        if(Letter[previous].isPlaying()) {
            Letter[previous].reset();
            Letter[previous] = MediaPlayer.create(this, rawid[previous]);
        }
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startID){
        if(finish == false) {
            if(WHclass.sel== Menu_info.MENU_NOTE){
                if(Braille_long_practice.pre_reference2==true){
                    init();
                    Braille_long_practice.pre_reference2=false;
                }
                else {
                    if (setting[MainActivity.master_braille_db.master_db_manager.getReference_index(MainActivity.master_braille_db.master_db_manager.My_Note_page)] == 0) {
                        Letter[MainActivity.master_braille_db.master_db_manager.getReference_index(MainActivity.master_braille_db.master_db_manager.My_Note_page)] =
                                MediaPlayer.create(this, rawid[MainActivity.master_braille_db.master_db_manager.getReference_index(MainActivity.master_braille_db.master_db_manager.My_Note_page)]);
                        Letter[MainActivity.master_braille_db.master_db_manager.getReference_index(MainActivity.master_braille_db.master_db_manager.My_Note_page)].setLooping(false);
                        setting[MainActivity.master_braille_db.master_db_manager.getReference_index(MainActivity.master_braille_db.master_db_manager.My_Note_page)] = 1;
                    }
                    if (progress == false) {
                        progress = true;
                        previous = MainActivity.master_braille_db.master_db_manager.getReference_index(MainActivity.master_braille_db.master_db_manager.My_Note_page);
                    } else if (progress == true) {
                        init();
                        previous = MainActivity.master_braille_db.master_db_manager.getReference_index(MainActivity.master_braille_db.master_db_manager.My_Note_page);
                    }
                    Letter[MainActivity.master_braille_db.master_db_manager.getReference_index(MainActivity.master_braille_db.master_db_manager.My_Note_page)].start();
                    Braille_long_practice.pre_reference2=true;
                }
            }
            else {
                if (setting[Braille_long_display.page] == 0) {
                    Letter[Braille_long_display.page] = MediaPlayer.create(this, rawid[Braille_long_display.page]);
                    Letter[Braille_long_display.page].setLooping(false);
                    setting[Braille_long_display.page] = 1;
                }
                if (progress == false) {
                    progress = true;
                    previous = Braille_long_display.page;
                } else if (progress == true) {
                    init();
                    previous = Braille_long_display.page;
                }
                Letter[Braille_long_display.page].start();
            }

        }
        else {
            init();
            letterfinish.start();
            finish = false;
            letterfinish.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    letterfinish.reset();
                    letterfinish = MediaPlayer.create(Letter_service.this, R.raw.letterfinish);
                }
            });
        }
        return START_NOT_STICKY;
    }
}
