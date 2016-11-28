package com.example.yongjin.an_location4;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;



import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/*
*
1, 인텐트의 액션정보를 정의한다
2, 그다음 인텐트와 펜딩인텐트를 이용한 목표지점을 추가한다.
3, 브로드캐스트 수신자의 정의를 등록한다.

*/

//위치정보를 활용한 근접경보 기능 구현
public class MainActivity extends ActionBarActivity {
    private static final String TAG = "MainActivity";

    private LocationManager mLocationManager;
    private CoffeeIntentReceiver mIntentReceiver;

    ArrayList mPendingIntentList;

    String intentKey = "positionProximity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 위치 관리자 객체 참조
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //펜딩인텐트 객체를 담을 ArrayList
        mPendingIntentList = new ArrayList();

        // 버튼 이벤트 처리
        Button startBtn = (Button) findViewById(R.id.startBtn);
        startBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                /* 근접경보 위치(범위) 좌표 등록을 위해 register() 메소드 호출
                 * @param id
                 * @param latitude
                 * @param longitude
                 * @param radius - 반경
                 * @param expiration - 반경을 벗어나면 동작하지 않도록 하기 위한 인자:-1이면 적용되지 않음
                */
                int countTargets = 2;
                register(1001, 37.4508483, 127.1274086, 200, -1);

                //학교 위치
                //37.4508483   lat
                //127.1274086 long

                //37.4708198  lat
                //127.1274265  long
                register(1002, 37.4708198, 127.1274265, 200, -1);
                //액티비티의 id 값 , 위도 , 경도 , 범위 , 오차
                // 근접 경보의 위치를 현재 위치로 잡아주어야한다.
                TextView textView01 = (TextView) findViewById(R.id.textView01);
                textView01.setText("등록지점-1(id:1001): " + "37.3868537, 126.6690862");

                TextView textView02 = (TextView) findViewById(R.id.textView02);
                textView02.setText("등록지점-2(id:1002): " + "37.3866193, 126.6691741");

                // 브로드캐스트 리시버(수신자) 객체 생성 및 등록
                mIntentReceiver = new CoffeeIntentReceiver(intentKey);
                registerReceiver(mIntentReceiver, mIntentReceiver.getFilter());

                Toast.makeText(getApplicationContext(), countTargets + "개 지점에 대한 근접 리스너 등록",
                        Toast.LENGTH_LONG).show();
            }
        });

        Button stopBtn = (Button) findViewById(R.id.stopBtn);
        stopBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                unregister();
                Toast.makeText(getApplicationContext(), "근접 리스너 해제", Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * 근접경보 위치 등록 메소드
     * @param id
     * @param latitude
     * @param longitude
     * @param radius - 반경
     * @param expiration - 반경을 벗어나면 동작하지 않도록 하기 위한 인자     *
     */
    private void register(int id, double latitude, double longitude, float radius, long expiration) {
        //근접경보를 위한 위치 정보 등록 - 인텐트 이용
        Intent proximityIntent = new Intent(intentKey);//(근접경보 구분자)
        proximityIntent.putExtra("id", id);//아이디
        proximityIntent.putExtra("latitude", latitude);//위도
        proximityIntent.putExtra("longitude", longitude);//경도

        /* 펜딩인테트 생성 - 조건이 맞을 때 실행하는 인텐트
           - 근접경보 범위안에 들어왔을 때 브로드캐스트 수신자에게 방송한다.
           - PendingIntent.FLAG_CANCEL_CURRENT : 지금 등록된것이 있으면 해제하고 현재 것을 등록
         */
        PendingIntent intent = PendingIntent.getBroadcast(this, id, proximityIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        //근접경보 대상 추가
        mLocationManager.addProximityAlert(latitude, longitude, radius, expiration, intent);

        //펜딩인텐트 어레이리스트에 인텐트 등록
        mPendingIntentList.add(intent);
    }

    public void onStop() {
        super.onStop();

        unregister();
    }

    /**
     * 등록한 정보 해제
     */
    private void unregister() {
        //근접경보 대상 삭제
        if (mPendingIntentList != null) {
            for (int i = 0; i < mPendingIntentList.size(); i++) {
                PendingIntent curIntent = (PendingIntent) mPendingIntentList.get(i);
                mLocationManager.removeProximityAlert(curIntent);
                mPendingIntentList.remove(i);
            }
        }

        if (mIntentReceiver != null) {
            unregisterReceiver(mIntentReceiver);
            mIntentReceiver = null;
        }
    }

    /**
     * 근접경보 범위에 들어 왔을 때 브로드캐스팅 메시지를 받을 브로드캐스팅 수신자 정의
     */
    private class CoffeeIntentReceiver extends BroadcastReceiver {

        private String mExpectedAction;
        private Intent mLastReceivedIntent;

        public CoffeeIntentReceiver(String expectedAction) {
            mExpectedAction = expectedAction;
            mLastReceivedIntent = null;
        }

        public IntentFilter getFilter() {
            IntentFilter filter = new IntentFilter(mExpectedAction);
            return filter;
        }

        /**
         * 근접경보 범위에 들어갔을 때(방송을 수신) 호출되는 메소드
         * 방송(메시지)을 수신했을 때 처리할 코드를 작성
         * @param context
         * @param intent
         */
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                mLastReceivedIntent = intent;

                int id = intent.getIntExtra("id", 0);
                double latitude = intent.getDoubleExtra("latitude", 0.0D);
                double longitude = intent.getDoubleExtra("longitude", 0.0D);

                Toast.makeText(context, "당신은 근접경보 범위 안에 있습니다.\n당신의 위치 : " + id + ", "
                        + latitude + ", " + longitude, Toast.LENGTH_LONG).show();
            }
        }

        public Intent getLastReceivedIntent() {
            return mLastReceivedIntent;
        }

        public void clearReceivedIntents() {
            mLastReceivedIntent = null;
        }
    }
}
