package com.likemilk.cho20_service;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {
// IPC 를 구현해야함 근대 겁나 복잡해서 AIDL 를 제공한다.
// AIDL 를 사용하면 자바파일이 만들어지게된다. 그래서 그것을 참조하여서 사용함
// AIDL 로 인터페이스를 만든다고 생각하면 된다. 인터페이스를 만든다고생각하면된다
// app - new - AIDL
// 사실상 서비스와 엑티비티 상호적이게 하는방법 서비스에 접속하는 방법이라 생각하면된다
//

    Intent serviceIntent;
    Test ipc;
    // 주의사항 접속하고 이 액티비티 접속 종료하기전에 서비스가  중단될수도있다.
    // 이 액티비티에 종료되기전에 서비스에 접속을 종료해줘야한다.
    // 해주지않을경우에는 액티비티 쪽에서 오류가 난다.
    // 그러나 계속해서 반복하게 될경우 서비스가 중단되버리게 된다.
    ServiceConn conn;
    // 여기다가 선언해주는이유는 접속을 해제할대 이녀석이 필요하기 때문이다.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        serviceIntent = new Intent(this,MyService.class);
        startService(serviceIntent);
        //서비스를 중단시킬때는 service stop 을 사용해야되는대 어짜피 메모리청소가 발생함
        //바인드 오토크리에이트를 넣으면 서비스가 가동중이 아닐때 가동을시키고 그 다음에 접속을 하는 구조이다.

        Intent bind_intent = new Intent(this,MyService.class);
        //서비스 인텐트 생성
        conn = new ServiceConn();
        bindService(bind_intent,conn,BIND_AUTO_CREATE);
        //이렇게 하면 접속이 된다!
        //서비스 접속
        // BIND_A 서비스가 운영되지않고있을때 서비스가 접속하게 하는 객체.

//계속 반복을 할땐 접속할때 호출하고 계속 사용하다가 더이상 사용안한다 싶으면 unbind를 해주면된다.
// 이것은 엑티비티에서 서비스를 제어하거나 작업을 할때 하는것이다.


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

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unbindService(conn);
        //서비스 접속을 해제 시키겟다.
        stopService(serviceIntent);
        //서비스를 중지전에
        // 여기까지 작성하게된다면 startService()와
        //startService(serviceIntent); 영원히 끝나지 않는 앱을만들겠다! 하면 ... ㅇㅇ  주석을 해제하면됨.


    }

    class ServiceConn implements ServiceConnection {

        //서비스에 접속할 때 리턴받는 ipc 객체를 받아주는 클래스
        // 서비스에 접속이 성공이되면 이객ㅊ
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //lBinder 이 객체로 우리가 만들었던 Test f라는 aidl 값을 넘겨받을 수 있다.
            ipc = Test.Stub.asInterface(service);
            //test  서비스안에 만들어놓은 메서드를 호출하는것은 사실상 불가능하다.
            //Test 클래스안에있는 겟 데이터가
            // 메인엑티비티가 직접 호출하는것이아니라 Test클래스에 getData를 호출시켜서 service 에 있는 데이터를 얻을 수 있는것이다.
            //Test.java 를 보면 getData 메서드가 있는데 그 그것에 거쳐서 호출하는것이다. 직접호출하는것이 아니다!

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            ipc = null;
        }
    }
    public void getData(View view){
        // MyService ->Test.java[interface ]->MainActivity 반드시 ipc 를 통해서
        //  서비스에서 굉장히 오래걸리는 작업이 있다면 엑티비티에서 진행을 하지말고 노티피케이션을 통해서 알릴수있도록 하는것이 좋다.
        // 액티비티를 종료시키고 서비스를 진행시키는것.

        try {
            TextView text1 = (TextView) findViewById(R.id.textView);
            text1.setText("data : "+ ipc.getData());// 직빵으로 이렇게 하게되면 에러가 뜨게됩니다! 그 이유는! ipc 를
            // 사용한다는 것은 예외의 소지를 갖고 있기 때문이긔

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}

/*
안드로이드의 4대 구성요소

컨텐츠 프로바이더
서비스
브로드 캐스터
액티비티

화면이 없는 실행단위를 서비스라고한다.메모리청소 발생작업이 일어나면 그 때 제거가된다.
서비스가 메모리상에서 제거되면 onDestroy() 메서드가 실행되어서 다시 실행시킨다.

4.0 이하의 버전은 개발자가 직접 GC 처리를 해주어야하는데 안해주고 끝낸다.
그러나 4.1부터는 안드로이드 OS 가 직접 메모리 청소를 시켜주는 기능을 넣었다.

GC를 소멸시키고 싶으면 null값을 넣거나 지역변수는 그냥 내비두면 알아서 소멸시킨다.

브로드캐스트 리시버 : 어떠한 사건이 발생하면 그것에대해 반응하는것.
 껏다 켜도 서비스는 영원히 돌아간다 왠만한 서비스들 은 죄다 이렇게 만들어놓는다. 부팅이 완료되면
서비스를 바로 돌린다.

서비스는 화면이 없기떄문에 화면에 관련된 작업을 할 수 없다.

보통 서비스는 스래드를 돌리는 용도로 사용을한다.

그리고 여기서 IPC 를 같이 볼것이다. 액티비티에서 서비스에서 쓰는 요소를 가져다주고싶다
 액티비티와 서비스는 서로의 영역을 침범할수 없다 그러나 IPC 를 이용해서 서로 상호적인 관계를 만들어 줄 수 있다.

메모리청소 앱 아무~~~~~~~~~~~~~효과 없당. ㅋㄷㅋㄷ ..... ㅅㅂ!?

사용자들이 뭔가 다운을 받을때나 왠만한 스레드는 서비스에서 돌린다.
다운로드가 오래걸리면 그냥 꺼버린다. 다운로드는 액티비티에 띄워놓지않고 서비스로 돌린다. 노래 재생하는건 서비스로 돌린다.

보통 채팅같은 프로그램이나 구글에서 푸쉬메세지나... 그런것들은 메세지가 언제날라올지 모르기때문에 항상 세션을 유지시켜야하기떄문에
그런것들은 서비스에서 돌리기도한다. 그러나 무조건 서비스를 돌리는건아니다 . 브로드캐스트 리시버를 돌리기도한다.

안드로이드의 기능은 manifestㅇ ㅔ.


서비스를 만들떼
app -> new -> Service -> service []
Exported 와 Enabled 채크 Exported 채크가 안되어있으면 해당 구성요소를 구동시키는것을 차단시킴 그러므로 Exported 를 차단하면안된다.
그러나 다른 어플리케이션이 사용하지않는이상은...

*/


