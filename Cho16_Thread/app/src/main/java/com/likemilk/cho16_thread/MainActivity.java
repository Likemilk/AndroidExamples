package com.likemilk.cho16_thread;

import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.Random;
// 쓰레드란? 멀티태스킹을 하기위해서.
// 쓰래드는 절대 동시성이 아니라 시 분 할 !
// Queue 방식으로 작업처리를함.
// 그러나 그 작업이 매우빠르기때문에 동시에 처리하는것처럼 느껴진다
// 진정한 병렬처리는 디바이스가 2개일경우에나 가능함..
// 프로그램을 종료시키는 이유 .시스템에 치명적인 에러를 일으킬수 있기때문이다.
// 자바 프로그래밍은 자바 해시코드 프로그래밍임.
// 우리가만드는 apk 는 달빅 vm 이 돌린다 .
// 안드로이드 os 는 apk 를 실행시크는게 아니라 os 는 달빅을 실행시키고 달빅은 apk 를 실행시키는 방식이다.
// 에러가 발생하면 android 이가 apk 를 종료시키는것이 아니라 달빅을 종료시키는것이다.
// 네트워크관련된 컨텐츠들은 thread 로 돌려야한다.
// 오류가 발생할 가능성이 있는 코드를 감시하기위해&운영하기위해 스래드를 사용한다.
// Main이란 이름으로 Thread를 운영한다.

//각 스레드 별 특징
// main쓰래드의 특징
//1, 5초 이상 걸리는 작업을 하면안된다.
//5.0에서는 일이 발생하지않지만 2.2에서나 3.0에서는 에러가발생하게된다.
//만약 5초이상 걸리는 작업을 실행하게되면 ANR 을 발생하게된다. [응답없음을 발생함 Application No Responed]
//
//2, g화면처리에 관련된 처리는 모두 메인스레드에서만 해야한다.
//일 반 Thread 의 특징
//5초이상 작업 가능
//화면처리가 안된다.
//진행바 처리를 하려하면일반스레드에선 빻 튕겨버린다.

// 5초이상걸리면서 화면작업을 해야할때..
//ex 화면에서 무언가 다운받을대.
//일반쓰레드에서 작업이 진행되면 메인스레드로 값을 맞춰서 화면을 진행시키게 해야한다.

//네트워크는 무조건 스레드로 처리해야함.

public class MainActivity extends ActionBarActivity {
//액티비티에서 작업이 끝난다고 하는경우 쓰레드로 작업하다가 작업이 끝나면
//작업이 끝났다는 결과를 화면에 띄운다. 5초 이상의 작업을 만드는 작업이 있다면 핸들러를 이용해서 작업을 함.[anr 이 발생하기 떄문에]
//안드로이드에서는 네트워크에 관련된건 전부 스레드를 이용해야한다.
//5 초이상걸리지않은작업을 메인스레드에 한번해달라고 부탁함. 한번작업을 하는데 5초이상걸리지않은 작업을 수행하고 메인스레드가 한가한시간에는
//화면처리를 한다. 그후에 다시 일반스레드는 메인스레드한태 다시 작업을 부탁함. 이것이 바로 스레드 작업이다.

    TextView text1,text2;
    DisplayHandler h;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //메인 스레드에서 처리하는것은 반드시 끝나야한다.
        text1 = (TextView) findViewById(R.id.textView);
        text2 = (TextView) findViewById(R.id.textView2);
        //화면 처리 담당 핸들러 생성하기
        h = new DisplayHandler();

        /*
        while(true){
            long time = System.currentTimeMillis();
            text2.setText("현재시간: "+time);
        }
        이렇게 메인스레드에서 5초이상걸리게되면 작업이 멈춘다.
        */


        //스래드 가동!
        ThreadClass t = new ThreadClass();
        t.start();

    }
    public void getTime(View view){
        //실행후 몇초지났니?
        long time = System.currentTimeMillis();  //절대값이다. 바뀌지앟는다.
        //현재시간을 ms 밀리세컨드 로 구한다
        text1.setText("현재시간: "+time);

    }
    class ThreadClass extends Thread{
        public void run(){
            while(true){
                long time = System.currentTimeMillis();
                SystemClock.sleep(30);
                //보통 슬립을 걸때는 Thread.sleep 으로 걸었는대 안드로이드 에서는 SystemClock.sleep 으로 슬립을 건다.
                //물론 두개의 기능은 같다.

                Log.d("msg",time +" ");
                //text2.setText("현재시간: "+ time );
                //화면 즉 view 에 관련된 처리를 해보겟슴다.
                //일반스레드에서 화면에 관한 처리를 하게되면 에러가발생한다. fatalError
                //쓰레드를 통한 서비스 운영이 작업이 끝난것에대한 메세지를 띄워야한다. 또는 진행Bar 라던가.


                //h.sendEmptyMessage(0);
                //0을 넘겨부럿다.

                Message msg=new Message();

                msg.what =1; //what에 뭘 넣든 상관없다 정수라면 그러나 이것을 사용할때는 이게 어떤작업인가? 작업을 분기위해 사용하라는것이다.
                Random r = new Random();
                msg.arg1 = r.nextInt();
                msg.arg2 = r.nextInt();
                // 정수값 2개를 넘길수있다~
                msg.obj = new Long(time);
                // 객체를 하나 넘길수있다~

                h.sendMessage(msg); //정수값2개 객체1개 그리고 what 1개
                //what 은 무조건 넘겨야한다.
                //객체를 넘길수있다는것은 다 ~~넘길수있다 HashMap 이든 ArrayList로 된 데이터는 Object 를 상속한 모두~!
                //매가변수가 Object 로 되어있다면 다아ㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏ 넘길수있다.!
            }
        }
    }
    //Thread 에서 화면처리가 필요할때 사용하는 요청 클래스
    class DisplayHandler extends Handler{
        //android os 패키지로 골라주어야한다.
        //ctrl+o 로[override 목록 표시] -> find handleMessage

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==0){
                //what 은  Message.java의 271 번줄을 확인한다. 또는 ThreadClass 참조한다..
                //Message 는 Parcelable 을 상속하고 있다. 값을 옮겨 받게 할 수 있는 기능이다.
                //sendE
                long time = System.currentTimeMillis();
                text2.setText("현재시간: "+ time );

            }else if(msg.what ==1) {
                text2.setText("msg.arg1: "+ msg.arg1+"\n");
                text2.append("msg.arg2: "+msg.arg2+"\n");
                text2.append("msg.obj: "+msg.obj+"\n");

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
