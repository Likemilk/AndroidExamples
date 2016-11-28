package com.likemilk.cho13_fragmentlist;

import android.support.v4.app.FragmentActivity;
import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

//ActionBarActivity 가 아니라 Activity 로 바꿔준다.
//그렇게되면 fatal error 가 뜨게되는대 파란색으로 뜬애러와 회색으로 뜬 애러는 서로 다르다
// 파란링크는 내가 작성한 코드에서 애러가 난거고 링크는 그에 따른 파일이 잘못됬을때 뜨는 애러이다.
//  이때 에러문의 맨 첫번째줄에 끝에 에러명이 나오게된다  FATAL EXCEPTION 이라규 ㅋ
// 사실상 에러메세지만으로는 알수는 없지만 안드로이드 스튜디오에서는 혹시~ 그 에러가 이거 아니냐? 라고 가이드라인을
// 보여준다.
// 안드로이드에서는 뷰밖에 배치할수밖에없다 . 그러나 우리는 뷰를 배치한것이 아니라 프래그먼트를 배치한것이다.
// 우리는 이것을 프래그먼트를 액티비티 즉 뷰로 바꿔주어야한다.

// https://developer.android.com/reference/android/support/v7/app/ActionBarActivity.html
// 여기 홈페이지를 보면 상속관꼐를 봐보면 ActionBarActivity는 FragmentActivity를 상속받고있다.
// 그렇기때문에 ActionBarActivity로 사용해도된다.
// ActionBarActivity를 상속받고 있는 예제가 참 많은대 관계없다.
// 이렇게 한 이유는 구글은 프래그먼트를 사용하는것을 적극적으로 사용하기를 바라는것이기때문이다.
// 어플의 질을 높이기위해 또는 화면전환을 할때 GC 의 생성을 줄이기위해 프로세스의 양을 줄이기위해! 여러모로 다다익선 미라클 익선


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
