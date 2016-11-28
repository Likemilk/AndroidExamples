package com.likemilk.cho34_resource3;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

//이미지를 drawable 의 hdpi 에다가 이미지를 넣는다.
//drawable->resource file->
//보통에는 drawable 에다가 집어넣는다 .그리고 각각의 해상도 별 폴더별로 해당한다
//그리고 보통의 이미지는 drawable의 각각 설정되어있는 폴더로 넣어준다 .
// xml 은 drawable 안에다가 넣는다.
// xml 을 작성한후에 layout에다가 배경화면을 내가만든 xml 파일로 지정해준다.
//drawable->resource->layer.xml 생성
//layer.xml 에다가 여러가지 이미지를 설정한다.
//레이아웃엑티비티에 이미지뷰를 만들고 src 를 layet.xml을 넣는다.


//drawable->resource file-> select.xml

/*

카카오톡을보면 말풍선이 줄수에따라서 늘어난다
이미지는 하나만 준비를 해놓고  줄수에따라 nine patch image 기법을 사용하여서 늘린다
nine patch image 기법이다 9개의 구분을 나누어서 구분을 지정하는것임.
9개의 파트를나눠서 늘어나는부분 안늘어나는부분 늘어나느부분안늘어나는부분을 정하는것이다.

appdata->android->sdk->tools->draw9 patch.bat 을 실행시킨다.
쉬프트 누르고 영역클릭하면 지워진다
최대한 필요한부부~~운만~
요개요개 말이지 같은비율로 늘어남 ㅋ
음 ..늘어난다기보다 늘어나보이게끔 픽샐을 똑같이 복사해줌 늘릴 양만큼
그래서 화질의 변화는 적다.
그리고 저장~ circle2.9.png


좌측 위쪽은 늘릴이미지

오른쪽과 아래쪽은 여백쪽 이미지


*/
public class MainActivity extends ActionBarActivity {

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

/*


*/
