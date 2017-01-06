package com.example.yeo.practice.basic_practice;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.example.yeo.practice.Braille_data.dot_abbreviation;
import com.example.yeo.practice.Display_Practice.Braille_short_display;
import com.example.yeo.practice.Display_Practice.Braille_short_practice;
import com.example.yeo.practice.MainActivity;
import com.example.yeo.practice.Menu_info;
import com.example.yeo.practice.R;
import com.example.yeo.practice.WHclass;


public class abbreviation_service extends Service {
    /*
     약자 및 약어 연습에서 출력되는 음성파일을 관리하는 서비스 클래스
     */
    private static final String TAG = "abbreviation_service";
    MediaPlayer ga, na, da, ma, ba, sa, ja, ka, ta, pa, ha, uk, un,wool,ul, yun, yul, young, ok, on, yong, woon, woo, eun, eul, in, fortis_siot, gut,so,but,and,so2,bytheway,and2,so3;
    MediaPlayer Abbreviation[]; //음성파일을 저장하는 배열 변수
    int rawid[]; //음성파일의 주소를 저장하는 배열 변수
    MediaPlayer abbreviationfinish; //점자 학습의 종료를 알리는 변수

    public static boolean finish = false;
    static int menu_page = 1;
    int previous=0;
    boolean progress = false;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate(){
        abbreviationfinish = MediaPlayer.create(this, R.raw.abbreviationfinish);
        abbreviationfinish.setLooping(false);

        Abbreviation = new MediaPlayer[] {ga, na, da, ma, ba, sa, ja, ka, ta, pa, ha, uk, un,wool,ul, yun, yul, young, ok, on, yong, woon, woo, eun, eul, in, fortis_siot, gut,so,but,and,so2,bytheway,and2,so3};
        // 선언된 음성 변수들을 배열 변수에 저장

        rawid = new int[]{R.raw.ga,R.raw.na,R.raw.da,R.raw.ma,R.raw.ba,R.raw.sa,R.raw.ja,R.raw.ka,R.raw.ta,R.raw.pa,R.raw.ha,R.raw.uk,R.raw.un
                ,R.raw.ul,R.raw.yun,R.raw.yul,R.raw.young,R.raw.ok,R.raw.on,R.raw.yong,R.raw.woon,R.raw.wool,R.raw.eun,R.raw.eul
                ,R.raw.in,R.raw.abbreviation_siot,R.raw.gut,R.raw.gut,R.raw.gut,R.raw.gut,R.raw.gut,R.raw.gut,R.raw.gut,R.raw.gut};
        // 음성파일의 id 주소를 배열변수에 저장

        for(int i = 0; i< dot_abbreviation.abbreviation_count; i++){
            Abbreviation[i] = MediaPlayer.create(this, rawid[i]);
            Abbreviation[i].setLooping(false);
        }
    }
    public void init(){ //사용한 음성파일을 재 설정해주는 함수
        if(Abbreviation[previous].isPlaying()) {
            Abbreviation[previous].reset();
            Abbreviation[previous] = MediaPlayer.create(this, rawid[previous]);
        }

    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startID){
        if(finish == false) {
            if(WHclass.sel== Menu_info.MENU_NOTE){
                if(Braille_short_practice.pre_reference==true){
                    init();
                    Braille_short_practice.pre_reference=false;
                }
                else {
                    if (progress == false) {
                        progress = true;
                        previous = MainActivity.basic_braille_db.basic_db_manager.getReference_index(MainActivity.basic_braille_db.basic_db_manager.My_Note_page);
                    } else if (progress == true) {
                        init();
                        previous = MainActivity.basic_braille_db.basic_db_manager.getReference_index(MainActivity.basic_braille_db.basic_db_manager.My_Note_page);
                    }
                    Abbreviation[MainActivity.basic_braille_db.basic_db_manager.getReference_index(MainActivity.basic_braille_db.basic_db_manager.My_Note_page)].start();
                    Braille_short_practice.pre_reference=true;
                }
            }
            else {
                if (progress == false) {
                    progress = true;
                    previous = Braille_short_display.page;
                } else if (progress == true) {
                    init();
                    previous = Braille_short_display.page;
                }
                Abbreviation[Braille_short_display.page].start();
            }

        }
        else {
            init();
            abbreviationfinish.start();
            finish = false;
            abbreviationfinish.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    abbreviationfinish.reset();
                    abbreviationfinish = MediaPlayer.create(abbreviation_service.this, R.raw.abbreviationfinish);
                }
            });
        }
        return START_NOT_STICKY;
    }
}