package com.likemilk.cho21_broadcastreceiver;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity {

    /*
    브로드 캐스트 리시버란 사건이 발생되었을대 처리되는것. OS가 알고있는 이름으로하는게 좋고 내가 정의한 게 있으면 내가 정의한걸로
    A라는 브로드캐스트 리시버가 있고 B 라는 브로드캐스트 리시버가 있고
    AN 이라는 브로드캐스터가 방송을때리면 A와 B에도 방송이된다.
    [A에는 br B에는 br AN 은 br 송신자. ]
    브로드 캐스트 리시버는 화면이 없는녀석이여서 화면에 무언가 할 수 없다.  문자가 스미싱이 이 원리로 사용될 수 도 있다.
    이름들이 굉장히 많다. 이름들은 Developer 사이트에서 검색하면된다 구글에서 검색하는 그대로~ 가져오면된다.
    문자메세지가 수신되면 브로드케스팅을 하는걸로 사용될 수도 있다.

    app->new ->other -> 브로드캐스트 리시버
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
