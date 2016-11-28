package com.likemilk.cho17_handler;

import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

//메인스레드는 화면에 관련된 스레드고 5초이상걸리는 작업을 하면안된다.
// 신규스레드는
// Async Task 는 메인에서처리하는 메서드 신규에서 처리하는 스레드로 나누어져있다.
// 스래드와 핸들러의 구조로 나누어서 스레드를 구현햇다면 Async Task 는 그것을 지원한다.
// doInBackGround 이것은 5초이상걸리는 작업을 해도된다 단. 화면에관련된작업을 처리하면 안된다.
// onFree 이것은 가장 먽 호출 on Post 다 끝나고나서 마지막에
// do in Background 이것이 화면에 관한처리를 .
// 스레드와 핸들러의 조합 으로 제공되고 그렇게 구현해야한다.
//

public class MainActivity extends ActionBarActivity {
    TextView text1;
    Handler h;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text1 = (TextView)findViewById(R.id.textView);
        h=new Handler();
        TestThread t = new TestThread();
        //이상태에서 start를 하게되면 일반스레드가 가동되는것..
        //h.post(t);
        //이렇게하면 스레드의 런메서드를 메인 스래드가 처리해주는 것이 된다.
        h.postDelayed(t,100);
        //두번째 인자는 100millisecond 후에 처리해달라는것임.
        // 스래드를 핸들러로 보내서 처리하겠다.

    }
    //스레드 클래스는 런메서드에 핸들러를 이용해서 메인스래드한태 처리해달라고 요청할수있다.
    //이것은 작업 한번당 5초 이상이 걸리는것이면안된다. 절대 5초이상 넘어가면안된다.
    // MainThread에게 작업을 요청하는 Thread [핸들러를 이용한다.] 네트워크에 관련된 코드를 넣으면안된다

    class TestThread extends Thread{
        //네트워크를 할때 스레드와 핸들러로 처리를 할것이다.
        //AsyncTask?
        public void run(){
            long time=System.currentTimeMillis();
            text1.setText("\n현재시간 : " + time);

            //h.post(this);
            //이렇게하면 스레드의 런메서드를 메인 스래드가 처리해주는 것이 된다.
            h.postDelayed(this,100);
            //두번째 인자는 100millisecond 후에 처리해달라는것임.

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



