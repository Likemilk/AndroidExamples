package com.likemilk.cho29_preference;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.HashSet;
import java.util.Set;

/*
[프레퍼렌스/프레그먼트 제공] 를 쓰기위해 3.0 버전 이상부터 가능
화면을만들어서 환경설정 기능을 제공 이런건 내부단에서 구현이 가능하다

코드단으로 하는법을 먼저하겠다.

/*

코드를 통한 데이터1프리퍼런스는 얻는 프리퍼런스에 이름이 다르면  다 따로따로 관리된다.


xml 을 통한 프리퍼런스는 하나로 관리된다. 전부다 키값을 다르게 해줘야한다
프리퍼런스 에디터<SettingFragment.java > 이 영역이 따로 있다.
코드를 통해만들때 전부 따로 따로 .. 구현을 해주면된다.

내일은 Row 하고 Asset
 */

public class MainActivity extends ActionBarActivity {
//이버전은 4.0이다.
//버전을 바꾸고싶다면 build.gradle 에서 minimumSdkVerSion 을 변경하면된다. 컴파일할때 사용하겟다는 버전을 말한다.
    TextView text1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text1 = (TextView) findViewById(R.id.textView);
    }
    public void internal_save(View view){
        //내부 저장
        //preference 를 얻어오겟다.
        SharedPreferences pref = getSharedPreferences("data", Context.MODE_PRIVATE); //다른거 열어도 아무것도 안된다 이것은 무조건 PRIVATE 로 만 세팅해야한다.
        // 데이터 관리를 위한 도구를 추출
        SharedPreferences.Editor editor = pref.edit();
        // 데이터 셋팅
        editor.putBoolean("data1",true);
        editor.putFloat("data2", 12.34f);//put double 응ㄴ 없다.
        editor.putInt("data3", 100); // 키 벨류 형태로 넣어주면된다.
        editor.putLong("data4", 10000L); // 키 벨류 형태로 넣어주면된다.
        editor.putString("data5", "문자열 데이터"); // 키 벨류 형태로 넣어주면된다.
        //문자열 셋 (3.0~)
        HashSet<String> set = new HashSet<String>();
        set.add("문자열1");
        set.add("문자열2");
        set.add("문자열3");
        editor.putStringSet("data6", set); //문자열저장을 가진 set 형식의 자료형만된다. 이것은 support 라이브러리가없어서 하위버전에서 지원할 수 없다.
        //코드단에 돌아가는것에서는 버전가능한게 많지 않다.
        //------------------------세팅까지만 함
        editor.commit();// 세팅한걸 저장한다 commit !
        text1.setText("저장 완료");
    }
    //내부에 저장하겠다. 읽어오겠다.
    public void internal_load(View view){
        SharedPreferences pref = getSharedPreferences("data",Context.MODE_PRIVATE);
        //데이터를 가져온다

        boolean data1 = pref.getBoolean("data1",false);
        float data2 = pref.getFloat("data2", 0.0f);
        int data3 = pref.getInt("data3", 0);
        long data4 = pref.getLong("data4", 0L);
        String data5 = pref.getString("data5", "초기값");
        HashSet<String> data6 =(HashSet<String>) pref.getStringSet("data6",null); //이것을 반환받으면 set 으로 나온다 이것을 형변환으로 HashSet으로 한다.

        text1.setText("data1 : " + data1 + "\n");
        text1.append("data2 : " + data2 + "\n");
        text1.append("data3 : " + data3 + "\n");
        text1.append("data4 : " + data4 + "\n");
        text1.append("data5 : " + data5 + "\n");
        for(String str: data6){
            text1.append("data6 : "+str+"\n");
        }


    }
    public void pref_fragment(View view){
        FragmentManager manager = getFragmentManager();
        FragmentTransaction tran = manager.beginTransaction();
        SettingFragment f = new SettingFragment();
        //tran.add(R.id.container,f);//똑같은거 보이기하면 중복됨
        tran.replace(R.id.container,f);//바꾸기로 해줘야함
        tran.commit();
    }
    public void get_prefData(View view){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        String data1 = pref.getString("data1", "기본문자열");
        boolean data2 = pref.getBoolean("data2", false);
        boolean data3 = pref.getBoolean("data3",false);
        String data4 = pref.getString("data4","기본리스트");
        Set<String> data5 = pref.getStringSet("data5",null);
        String data6 = pref.getString("data6","벨소리");

        text1.setText("data1 :"+data1+"\n");
        text1.append("data2 :" + data2 + "\n");
        text1.append("data3 :" + data3 + "\n");
        text1.append("data4 :" + data4 + "\n");
        text1.append("data5 :" + data5 + "\n");
        text1.append("data6 :"+data6+"\n");
        text1.append("data6 :" + "\n");

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
