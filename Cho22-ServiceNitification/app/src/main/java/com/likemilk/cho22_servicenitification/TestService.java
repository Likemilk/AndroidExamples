package com.likemilk.cho22_servicenitification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;

public class TestService extends Service {
    //exported ok //enabled ok
    //onbind 메서드는 사용 x
    int progress;
    // 진행값

    NotificationCompat.Builder builder;
    NotificationManager manager;
    public TestService() {
    }


    //ctrl+o  override 에서 필요한걸 골라온다.
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        progress=0;
        builder = new NotificationCompat.Builder(this);
        builder.setTicker("작업이 시작되었습니다");
        builder.setSmallIcon(R.drawable.ic_launcher);
        builder.setContentTitle(progress + "%");
        //builder.setContentText(); 내용은 사용하지않을것이고 프로그래스를 사용할것이다.
        builder.setProgress(100,progress,false);
        //1파라매터 : 최대값
        //2파라매터 : 현재값;
        //3파라메터
        //setProgress의 세번째 매개변수에다가 true 를 놓으면 작업중인것처럼 보이게 된다

        builder.setOngoing(true);
        //이것은 메세지가 진행중이니 지우지않겠다라는 뜻이다.
        //지금의 노티피케이션은 지워지는 메세지로 세팅되어있으므로 안지워지게 설정해준다.


        Notification noti = builder.build();
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(10,noti);
        // 첫번째 값 : 우선순위 또는 각각을 구별하는 id 값
        // 두번째 값 만들어진 noti 의 값.

        TestThread t = new TestThread();
        t.start();


        return super.onStartCommand(intent, flags, startId);
    }

    class TestThread extends Thread{
        //작업이 완료가되면 현제 설치중입니다 또는 진행중입니다 메세지를 띄우면서.

        public void run(){
        //다운로드 중 또는 작업 진행중을 나타낼것이다.
            while(progress<=100){
                SystemClock.sleep(100);
                builder.setContentTitle(progress+"%");
                builder.setProgress(100,progress,false);
                Notification noti = builder.build();
                manager.notify(10,noti);
                // 중요한건 첫번째 파라매터에의 10값이다. 같은번호에다가 주는게 핵심이다.
                progress++;

                //메세지를 다시 띄운다.

            }
            if(progress >=100){
                builder.setContentTitle("작업이 진행중입니다.");
                builder.setProgress(100,100,true);
                Notification noti= builder.build();
                manager.notify(10,noti);
                SystemClock.sleep(2000);
                builder.setContentTitle("작업이 완료되었습니다.");
                builder.setProgress(100, 100, false);
                builder.setAutoCancel(true);
                builder.setOnlyAlertOnce(true);
                builder.setOngoing(false);

                noti = builder.build();
                manager.notify(10, noti);
                //메세지를 터치하면 실행될 익티비티를 선택해줄것이다.
                Intent intent = new Intent(getBaseContext(),TestActivity.class);
                PendingIntent pending = PendingIntent.getActivity(getBaseContext(),0,intent,0);
                builder.setContentIntent(pending);
                noti = builder.build();
                manager.notify(10,noti);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
