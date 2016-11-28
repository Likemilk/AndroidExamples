package com.likemilk.cho9_fragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
//2.2 로 사용하였기때문에 support 라이브러리를 사용.
//2.2 d에서는 오류는 안나지만 사용이 안되는 경우도 간혹 있다.
//5.0은 안드로이드 os 내부에 있는 클래스를 이용해서 컴파일하고 그 이전에는 직접 클래스파일을 apk 에 담아서 실행하는방식이었다.
//그리고 또한 5.0 에서는 에니메이션이 쟈라라랑 하고 나오면 이하버전에서는 안나오는경우도 있다.

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends ActionBarActivity {
    FirstFragment frag1;
    SecondFragment frag2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frag1 = new FirstFragment();
        frag2 = new SecondFragment();
    }

    public void show_first(View view){
        //이 방법은 xml 에서 onClick 환경에서 잡아준것이다.
        //왜 View view 를 넣었는가 ?
        //여기다가 View view 를 넣지않으면 fatal error 가 뜨면서 종료되게 된다.
        //Q  show_first View view 를 사용하는 적이없는대 왜 사용하였는가요 ?
        //A: 매개변수 View view 를 정한이유는 기본적으로 onClick 메서드에서는 View view 즉 무엇을 눌럿는지
        // 식별을 해야하는대 식별할 매개변수를
        //지정해주지않으면 무엇이 눌렷는지 식별이 불가하다
        // 안드로이드 os 에서는 이것을 필요로 하기때문에 View view 파라매터를 지정해주는것이다.
        // 즉 onClick 으로 사용되는 메서드는 View view 인자를 필요로 하다는 소리이다.


        FragmentManager manager = getSupportFragmentManager();//3.0~: getFragmentManager()
        FragmentTransaction tran = manager.beginTransaction();
        //타겟버전이 2.2 이 므로 서포트v4 엡펙트 로 임포트 해주어야한다.


        //tran.add(R.id.container,frag1); 에드상태에서 버튼을 여러번 눌렀을때는 fatal 에러가떳는대 replace 로 바꾸니까 fatal 에러가 안뜨고 잘뜸.
        //Q  add 를 쓰고 같은버튼을 여러번누를땐 튕겻는대 replace 로 하니까 고내찮아 졌어요 왜인가요 ?
        //A  Swing Component 에서도 마찬가지로 이미 생성되어있는객체는 두번다시 생성하지않는다 repaint()를 하지않는한. 그러나 안드로이드에서는
        // 이러한것을 용납하지않는 모양이다. 이미생성되어있는대 한번더 생성하면 fatal 에러로 튕겨버린다.
        tran.replace(R.id.container,frag1);
        //MainActivity에 있는 fragment태그인 container id 인 녀석을 frag1 로 바꾸겠다는 소리이다
        //1 R.java 로 선언되어있는 fragment 해쉬값
        //2, fragment 인 객체
        tran.addToBackStack("frag1");  // fragment 의 이벤트가 스텍방식으로 fragment 가 저장이되어서 레이아웃처럼 사용할 수 있다.
                                        // BactStack 을 엑티비티의 레이아웃처럼 사용가능하다.
                                        // 시작하자마자 처음부터는 백스택을 넣지않는다 . 그이유는..... ㅂㄷㅂㄷ 이전으로 돌아가려하는대 이전이아니라 나의 행적자체를
                                        // 쫒아가는것이라서 사용자들이 익숙하지않는 UI 를 구성하게된다.
        tran.commit();//적용!
    }
    public void show_second(View view){
        //이미 생성된 프레그먼트 객체에 같은 객체를 여러번 생성하는 명령어를 작성해도
        //하나만 작성하게 된다.
        FragmentManager manager = getSupportFragmentManager();//3.0~: getFragmentManager()
        FragmentTransaction tran = manager.beginTransaction();
        //만약 추가할 프레그먼트가 리니어레이아웃이라면 덮어지지않고 밑으로 내려가지만
        //이경우에는 replace 를 사용함 이미 붙어져있는경우가 없는경우라면 그냥 add 하고 같은 의미임.
        //이럴경우에는 프레그먼트ㅏ 새로 붙는다.
        //tran.add(R.id.container,frag2); 해당레이아웃안에 있는 레이아웃을 몽땅다 제거하고 저걸로 대체를 하겠다는 소리이다.
        tran.replace(R.id.container,frag2);
        //이미 R.id.container 에는 다른 프레그먼트가 추가되어있으므로 add 를 하게되면 애러가 뜬다.
        //그 이유는 자바 Swing 컴포넌트에서 알 수 있듯이 미리 생성되어있는건 한번 더 만들 필요가없기때문이다.
        // 즉 똑같은녀석을 더 만들이유가 어디있노 ..!
        tran.addToBackStack("frag2");
        tran.commit();//적용!
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
