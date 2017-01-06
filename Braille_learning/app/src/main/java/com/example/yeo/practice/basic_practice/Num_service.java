package com.example.yeo.practice.basic_practice;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.example.yeo.practice.Braille_data.dot_num;
import com.example.yeo.practice.Display_Practice.Braille_short_display;
import com.example.yeo.practice.Display_Practice.Braille_short_practice;
import com.example.yeo.practice.MainActivity;
import com.example.yeo.practice.Menu_info;
import com.example.yeo.practice.R;
import com.example.yeo.practice.WHclass;


public class Num_service extends Service {
    /*
     숫자 연습에서 출력되는 음성파일을 관리하는 서비스 클래스
     */
    private static final String TAG = "Num_service";
    MediaPlayer ns, zero0, one1, two2, three3, four4, five5, six6, seven7, eight8, nine9, ten10, twofive25, fourseven47, sixeight68, ninenine99;
    MediaPlayer numfinish;
    MediaPlayer Num[]; // 선언된 음성 변수들을 배열 변수에 저장
    int rawid[]; // 음성파일의 id 주소를 배열변수에 저장
    public static boolean finish = false;
    static int menu_page = 1;
    int previous=0;
    boolean progress = false;
    public Num_service() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate(){
        Num = new MediaPlayer[] {ns,zero0,one1,two2,three3,four4,five5,six6,seven7,eight8,nine9,ten10,twofive25,fourseven47,sixeight68,ninenine99};
        // 선언된 음성 변수들을 배열 변수에 저장

        rawid = new int[] {R.raw.num_sign,R.raw.zero,R.raw.one,R.raw.two,R.raw.three,R.raw.four,R.raw.five,R.raw.six,R.raw.seven,R.raw.eight,R.raw.nine,R.raw.ten
        ,R.raw.twofive,R.raw.fourseven,R.raw.sixeight,R.raw.ninenine};
        // 음성파일의 id 주소를 배열변수에 저장


        numfinish = MediaPlayer.create(this,R.raw.numfinish);
        numfinish.setLooping(false);

        for(int i = 0; i< dot_num.num_count; i++){
            Num[i] = MediaPlayer.create(this, rawid[i]);
            Num[i].setLooping(false);
        }
    }
    public void init(){ //사용한 음성파일을 재 설정해주는 함수
        if(Num[previous].isPlaying()) {
            Num[previous].reset();
            Num[previous] = MediaPlayer.create(this, rawid[previous]);
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
                    Num[MainActivity.basic_braille_db.basic_db_manager.getReference_index(MainActivity.basic_braille_db.basic_db_manager.My_Note_page)].start();
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
                Num[Braille_short_display.page].start();
            }

        }
        else {
            init();
            numfinish.start();
            finish = false;
            numfinish.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    numfinish.reset();
                    numfinish = MediaPlayer.create(Num_service.this, R.raw.numfinish);
                }
            });
        }
        return START_NOT_STICKY;
    }
}