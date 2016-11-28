package com.likemilk.cho22_servicenitification;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

/*
보통 노티피케이션은 사용자가 작업이 끝나면 노티피케이션이 끝나는대 이번에는 그것들의 관계에대해서

노티피케이션은 뭔가 진행된다거나 바뀌는걸 보일때.
그럴때 노티피케이션에는 파라그래프를 표시하는 단위를 표시하기도 한다. 숫자값이 변경되는것에 따라
같이 파라그래프가 바뀐다.
프로그래스바 를 이용한다.
지금은 노티피케이션에서 진행바를 진행시키는것을 많이 사용하기도한다.
프로그래스바의 종류 진행중을 알리기위한. 또는 진행완료까지 파라그래프를 증가시키는 프로그래스바
두가지가 있다.
*/

//서비스가 종료가되면프로그래스바를 중지를 시키는걸로한다.
//메세지를 실행을하면 액티비티를 띄우는걸로.
public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void proNotification(View view){
        //버튼에대한 리스너
        Intent intent = new Intent(this,TestService.class);
        startService(intent);
        finish();
        //서비스를 가동시키고 자기 자신의 액티비티를 끄겟다._
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
