package com.likemilk.cho20_service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.Log;

public class MyService extends Service {

    boolean isLive = false;
    // 서비스가 정지될때 쓰레드가 같이 정지될수있게 만드는 변수. 스위치. 무한루프가 종료될 변수하나 무조건 만들어놓아야한다.
    // 왜냐하면 디바이스를 껏다키지않는이상 서비스인 스레드는 계속 돌고있다 . 그상태에서 종료가안되고 이스레드는 계속실행되는대
    // 다시 서비스를 실행시키면 서비스는 실행되고있는대 또 하나 더 실행되고있는것이다.
    int data=0;

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
     //이 메서드는 서비스를 접근할때 시작되는 메서드 , 서비스에 접속할때 이 메서드에 접속하게 되어있다.
     //서비스에 작업을 하고있는데 얼마나 작업이 되고있는지 만약 노래를듣는대 어떤노래가 재생되고있고 얼마나 재생이 되었는지 확인하기 위함.
     //그러나 화면Activity과 서비스는 서로 접근이 불가능하지만 이때는 ipc 를 이용해야한다 IPC 는 짱짱맨 중요


        //throw new UnsupportedOperationException("Not yet implemented"); 초기에는 return 이 지정되어있지않기때문에. 또는 aidl 이 정의되어있지않기때문에
        // 메세지를 not yet implemented 라는 메세지를 갖고 있다.

        return ipc;

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        isLive = true;
        TestThread t = new TestThread();
        t.start();
        return super.onStartCommand(intent, flags, startId);
    }

    class  TestThread extends Thread{
        public void run(){
            while(isLive){
                SystemClock.sleep(100);
                data++;
                Log.d("msg","data: "+data);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 부모클래스들을 호출해주는것은 무조건 위에 있어야한다.
        isLive = false;
    }
    Test.Stub ipc = new Test.Stub(){
            //이 객체를 생성을 할때 이 메서드를 구현해주면 Stub를 구해줄수 있다.
            @Override
       public int getData() throws RemoteException {
            return data;
                //스레드가 돌면서 1씩 증가했던 데이터 변수를 반환한다.
                // 여기서 구현하기때문에 여기안에서는 변수나 메서드를 호출하는것은 자연스럽게 된다.
                // 이렇게하면 액티비티쪽에서 여기있는 메서드를 호출하게 될것이다. 이 파일test.aidl안에서는 변수나 메서드를 사용하는 코드를 작성하면된다.
        }
    };
}
