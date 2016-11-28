package com.example.yongjin.an_location;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/*

매니패스트에는 ACESS_FINE_LOCATION 이 존재하는대 이것은 GPS 의 기능을 이용하겠다이다.
ACCESS_LCOARSE_LOCATION gps 기능을 이용하느것이 아닌 기지국을 이용해서

1,단계 위치 관리자 객체 참조하기 로케이션 메니저한태 GPS 모듈의 값을 참조한다. 그 값을 위치리스너
에게넘겨준다. [무언가 넘겨준다는 행위는 위치 리스너가 감지를 하게된다.]
2, 로케이션의 값을 받고 무언 가 처리를 하고싶다면 로케이션 리스너의 onLocationChanged 를 알려준다.
3,




*/
//GPS를 이용해 나의 위치 알아내기
public class MainActivity extends ActionBarActivity {
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);

        // 버튼 이벤트 처리
        Button button01 = (Button) findViewById(R.id.button01);
        button01.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // 위치 정보 확인을 위해 정의한 메소드 호출
                startLocationService();
            }
        });
    }

    /**
     * 위치 정보 확인(수신)을 위해 정의한 메소드
     */
    private void startLocationService() {
        // 위치 관리자 객체 참조
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // 위치 정보를 받을 위치 리스너 객체 생성
        GPSListener gpsListener = new GPSListener();
        long minTime = 10000;//GPS정보 전달 시간 지정 - 10초마다 위치정보 전달
        float minDistance = 0;//이동거리 지정

        // GPS를 이용한 위치 요청
        manager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                minTime,
                minDistance,
                gpsListener);

        // 네트워크(기지국)를 이용한 위치 요청
        manager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                minTime,
                minDistance,
                gpsListener);

        // 위치 확인이 안되는 경우에도 최근에 확인된 위치 정보 먼저 확인
        try {
            Location lastLocation = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (lastLocation != null) {
                Double latitude = lastLocation.getLatitude();
                Double longitude = lastLocation.getLongitude();

                Toast.makeText(getApplicationContext(), "Last Known Location : \n" + "Latitude : " + latitude +
                        "\nLongitude:" + longitude, Toast.LENGTH_LONG).show();
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        Toast.makeText(getApplicationContext(), "위치 확인이 시작되었습니다. 로그를 확인하세요.",
                Toast.LENGTH_SHORT).show();
    }

    /**
     * 위치 리스너 클래스 정의
     */
    private class GPSListener implements LocationListener {
        /**
         * 위치 정보가 확인(수신)될 때마다 자동 호출되는 메소드
         */
        public void onLocationChanged(Location location) {
            Double latitude = location.getLatitude();//위도
            Double longitude = location.getLongitude();//경도

            String msg = "Latitude : "+ latitude + "\nLongitude:"+ longitude;

            textView.setText(msg);

            Log.i("GPSListener", msg);

            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    }
}
