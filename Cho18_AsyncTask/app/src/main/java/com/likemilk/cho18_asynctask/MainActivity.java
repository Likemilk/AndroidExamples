package com.likemilk.cho18_asynctask;

import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

//메인스레드는 화면에 관련된 스레드고 5초이상걸리는 작업을 하면안된다.
// 신규스레드는
// Async Task 는 메인에서처리하는 메서드 신규에서 처리하는 스레드로 나누어져있다.
// 스래드와 핸들러의 구조로 나누어서 스레드를 구현햇다면 Async Task 는 그것을 지원한다.
// doInBackGround 이것은 5초이상걸리는 작업을 해도된다 단. 화면에관련된작업을 처리하면 안된다.
// onFree 이것은 가장 먽 호출
// on Post 다 끝나고나서 마지막에
// do in Background 이것이 화면에 관한처리를 제공함.
// 스레드와 핸들러의 조합 으로 제공되고 그렇게 구현해야한다.
//
public class MainActivity extends ActionBarActivity {
    TextView text1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text1 = (TextView) findViewById(R.id.textView);
        SyncClass task = new SyncClass();
        task.execute(100,200);  // doInBackground 의 매개변수이다.

    }
    /*
    * 첫번째 제네릭: execute 메서드의 매개 변수 값       execute -> doInBackground 로 전해진다.
    *          doInBackground로 전달되는 값의 타입 [doInBackground 는 안드로이드 OS 자체가 직접 호출하는것.]
    * 두번째 제네릭: publishProgress 메서드의 매개변수 타입
    *          onProgressUpdate 로 전달되는 값의 타입
    * 세번째 제네릭: doInBackground 의 리턴타입
              onPostExecute로 전달되는 값의 타입

       두인 백그라운드가 끝날 이 값은 onPostExecute가 값을받는다.
       만약에 매개변수가 받는 타입이 없다면 제네릭 타입이 없어도 된다면 void 로 적어도 된다.
    * */


    class SyncClass extends AsyncTask<Integer,Long,String> {

        @Override
        protected void onPreExecute() {
            //extends 의 override 용
            //doInBackground 가동 전에 호출 한번만 실행한다 . 초기화작업이나 사전작업에 사용된다. 화면에 관련된 처리를 해도된다.
            super.onPreExecute();
            Toast.makeText(MainActivity.this,"onPreExecute() 가 호출되었습니다.",Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(String s) {
            //doInBackground 종료후에 호출된다 . AsyncTask 작업 완료시에 호출됨.
            //extends 의 override 용
            //제네릭 3
            super.onPostExecute(s);
            text1.append(s);
        }

        @Override
        protected void onProgressUpdate(Long... values) {//...은 가변형배열로 받음
            // doInBackGround 에서 publishProgress를 호출하면 동작한다.
            //extends 의 override 용
            //제네릭 2
            //메인스레드가 처리하는건 5초이상넘어가면안되고 화면처리가 가능하다.
            super.onProgressUpdate(values);

            text1.setText("현재시간: "+ values[0]);

        }

        @Override
        protected String doInBackground(Integer... params) {//...은 가변형배열로 받음 50개짜리의 정수가 들어오면 이것을 50개짜리의 배열로 받는다.
        //implements 메서드
        //제네릭 1
        // 이것은 일반스래드가 돌리기때문에 무한루프 나 5초이상걸리는건 가능 화면처리는 하면안된다. 중간중간에 화면처리가 필요할 경우에는 onProgressUpdate 가 호출된다.
        // 처리가 필요할때는 호출하는 매서드의 매개 변수타입이 바로 두번째 제네릭인 Long 이다.
        // 두인 백그라운드는 일반 스레드에서 처리하게된다.
            for(int i=0;i<20;i++){
                SystemClock.sleep(1000);
                params[0]++;
                params[1]++;
                Log.d("msg",params[0]+"  "+params[1]);

                //text1.setText("msg"+params[0]+" "+params[1]);// fatal 에러뜸.
                long time = System.currentTimeMillis();
                publishProgress(time);//Long 타입으로 publishProgress 을 이용해서 Long 타입으로 값을 보낸다.
            }
            return "작업이 완료되었습니다.";
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