package com.example.yeo.practice;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Display;

import com.example.yeo.practice.MyNote.Basic_Braille_DB;
import com.example.yeo.practice.MyNote.Master_Braille_DB;
import com.example.yeo.practice.menu.Menu_Tutorial;
import com.example.yeo.practice.menu.Menu_main_service;
import com.example.yeo.practice.tutorial.Tutorial_basic_practice;
import com.example.yeo.practice.tutorial.Tutorial_dot_lecture;
import com.example.yeo.practice.tutorial.Tutorial_dot_practice;
import com.example.yeo.practice.tutorial.Tutorial_master_practice;
import com.example.yeo.practice.tutorial.Tutorial_quiz;
import com.example.yeo.practice.tutorial.Tutorial_service;
import com.example.yeo.practice.tutorial.Tutorial_tutorial;

import net.daum.mf.speech.api.TextToSpeechManager;

/*
Braile_learning Application이 시작되면 가장먼저 실행되는 Main 클래스

 */
public class MainActivity extends FragmentActivity {
    static public float width,height;
    final static int CODE = 1;
    static public Basic_Braille_DB basic_braille_db; // 나만의 기초 단어장을 위한 데이터베이스
    static public Master_Braille_DB master_braille_db; // 나만의 숙련 단어장을 위한 데이터베이스

    static public Braille_Text_To_Speech Braille_TTS = new Braille_Text_To_Speech();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        TextToSpeechManager.getInstance().initializeLibrary(getApplicationContext());

        width = size.x;   // 스마트폰 가로 해상도
        height = size.y;  // 스마트폰 세로 해상도
        WHclass.height = height;  //WHclass height에 세로 해상도 저장
        WHclass.width = width;  //WHclass width에 가로 해상도 저장
        WHclass.Touch_space = width * (float) 0.1; //터치 영역을 저장하는 메크로
        WHclass.Drag_space = width * (float) 0.2; //드래그 영역을 저장하는 메크로
        // WidthHeight WH = new WidthHeight(width,height);
        SharedPreferences sf= getSharedPreferences("save", 0);
        int i = sf.getInt("b", 0);

        basic_braille_db = new Basic_Braille_DB(getApplicationContext(),"BRAILLE.db",null,1); //BRAILLE 라는 이름을 가진 테이블
        master_braille_db = new Master_Braille_DB(getApplicationContext(),"BRAILLE2.db",null,1); //BRAILLE2 라는 이름을 가진 테이블



        switch(i){ //Database 에 저장된 값을 읽어들여, 시작지점을 결정함
            case 0:
                Intent i0 = new Intent(MainActivity.this, Menu_Tutorial.class);
                //Intent i0 = new Intent(MainActivity.this, Tutorial.class);
                startActivityForResult(i0,CODE);
                startService(new Intent(this, Menu_main_service.class)); //메뉴 음성 출력 서비스
                //startService(new Intent(this, Tutorial_service.class)); // 사용설명 서비스
                finish();
                //WHclass.db=1;
                break;
            case 1:
                Intent i1 = new Intent(MainActivity.this, Tutorial_tutorial.class);
                startActivityForResult(i1, 0);
                startService(new Intent(this, Tutorial_service.class));
                finish();
                break;
            case 2:
                Intent i2 = new Intent(MainActivity.this, Tutorial_basic_practice.class);
                startActivityForResult(i2, 0);
                finish();
                break;
            case 3:
                Intent i3 = new Intent(MainActivity.this, Tutorial_master_practice.class);
                WHclass.basicprogress[0] = 1;
                startActivityForResult(i3, 0);
                finish();
                break;
            case 4:
                Intent i4 = new Intent(MainActivity.this, Tutorial_quiz.class);
                startActivityForResult(i4, 0);
                WHclass.mainmenuprogress = true;
                finish();
                break;
            case 5:
                Intent i5 = new Intent(MainActivity.this, Tutorial_dot_lecture.class);
                startActivityForResult(i5, 0);
                startService(new Intent(this, Tutorial_service.class));
                finish();
                break;
            case 6:
                Intent i6 = new Intent(MainActivity.this, Tutorial_dot_practice.class);
                WHclass.sel = 9;
                startActivityForResult(i6, 0);
                startService(new Intent(this, Tutorial_service.class));
                finish();
                break;
            case 7:
                Intent i7 = new Intent(MainActivity.this, Menu_Tutorial.class);
                startActivityForResult(i7, 0);
                startService(new Intent(this, Tutorial_service.class));
                finish();
                break;
        }

        //Intent intent = new Intent(MainActivity.this, Menu_Tutorial.class); // 대 메뉴 사용설명서 화면
        //Intent intent = new Intent(MainActivity.this, Tutorial.class); //여자 스피커 사용설명 화면
        //startActivityForResult(intent,CODE);
        //startService(new Intent(this, Tutorial_service.class)); // 사용설명 서비스
        //startService(new Intent(this, Menu_main_service.class)); //메뉴 음성 출력 서비스
        finish();

    }

}

