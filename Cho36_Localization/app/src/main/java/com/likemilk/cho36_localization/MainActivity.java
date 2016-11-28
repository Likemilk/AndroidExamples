package com.likemilk.cho36_localization;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
// 디바이스는 xhdpi 인대 이미지가 hdpi 에는 있고 xhdpi 는없을대 자기랑 가장 근접한 폴더에서 이미지를 찾는다.
// xhdpi 디바이스에 hdpi와 xxhdpi가 가장근접한대 그중에서 해상도가 높은 이미지는 깨질수있으므로 자기보다
// 해상도가 한단계 낮은 쪽에서 이미지를 찾는다.
// hdpi 가 10x10 이라면 xhdpi 는 15에 15로 만들어주어야한다. 약 150퍼 정도가 차이가난다.
// 최근 디바이스들은 hdpi 이미지와 xhdpi 급 이미지들 둘다 준비한다. 그 이유는 요즘 xhdpi의 해상도를 지원하는 디바이스들이 많다.
// 게임은 2D게임마저도  3D 엔진을이용해서만든다 그이유는 3d 엔진이 직접 행렬연산으로 그래픽에 맞춰주기때문이다.
// 3D 게임 개발툴을이용해서 2D 게임을 만드는 게임툴이있다 Cocos2d-x 바로..
// unity 로 2d게임 컨탠츠를 만들수도 있다.
// 언어별로 폴더를 붙이면된다.
// 언어별로나눈다는것이 문자열이다.
// 첫번째로 text!
// values 폴더에다가 언어별로 나눌수있다.
// 이미지에 문자열이 그려져있을때 각 국가별로 준비한다
// 문자열은 string.xml 에다가 불러다가 쓰는방식이 가장 좋다.
// 그저 values 폴더만
// 레이아웃 xml에서는 세팅함 레이아웃은 다국어로 분리하지말자.

//res ->android resource -> layout-> activity_main -> Orientation ->screen orientation  portrait 는 세로 landscape 가로

//내가 해당한 문자열이 values에 없으면 기본 string.xml에 설정되어있는
///

public class MainActivity extends ActionBarActivity {
    TextView text1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text1 = (TextView)findViewById(R.id.textView);
        //화면 회전에 따른 복원작업 실시

        if(savedInstanceState!=null){

            //복원작업시 필요한 데이터를 bundle 에 셋팅;
            //oncreate 메서드가 호출되기전에 호출되는 메서드이다.
            //여기의 파라매터인 번들객체는 onCreate()메서드안에 파라메터로 넘겨지게된다.
            // 액티비티 화면을 처음으로 만들때는 onCreate 객체의 파라메터가 널값이 들어가게된다
            // 그다음에 onSaveInstanceState 가 실행되고 난이후에는 이 메서드에서 가공된 Bundle 객체가 들어가게된다

            String str1 = savedInstanceState.getString("str1");
            text1.setText(str1);
            //화면복원작업이 필요한코드들은 여기안에다가 다 ~~~넣어야한다.
        }

    }


    public void btn_method(View view){
        text1.setText("문자열입니다.");
        //ctrl+11 을 누르게되면  가로보기가되는데 가로보기모드가 시작되면서 다시 onCreate()를 실행한다. 그렇기떄문애ㅔ
        // 문자열입니다라는 글자는 지워지게되고 다시 새로만든것이된다. 그런대 만약 이렇게되면 안대쟈냐? 그래서 복원작업을 할거야
        //protected void onSaveInstanceState(Bundle outState)  바로 이거지 화면이 바뀔때 호출되는 메서드지.

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //복원작업시 필요한 데이터를 bundle 에 셋팅;
        //oncreate 메서드가 호출되기전에 호출되는 메서드이다.
        //여기의 파라매터인 번들객체는 onCreate()메서드안에 파라메터로 넘겨지게된다.
        // 액티비티 화면을 처음으로 만들때는 onCreate 객체의 파라메터가 널값이 들어가게된다
        // 그다음에 onSaveInstanceState 가 실행되고 난이후에는 이 메서드에서 가공된 Bundle 객체가 들어가게된다
        //
        outState.putString("str1",text1.getText().toString());
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
