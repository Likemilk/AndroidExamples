package com.example.yongjin.localaca;

import java.util.List;
import java.util.Locale;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    TextView text1,text2;
    LocationManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text1 = (TextView) findViewById(R.id.textView);
        text2 = (TextView) findViewById(R.id.textView2);
        text2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // LocationManager 객체 초기화 , LocationListener 리스너 설정
                getMyLocation();
            }
        });
    }

    // LocationManager 객체 초기화 , LocationListener 리스너 설정
    public void getMyLocation(){
        //여기서는 일단 요청부터 할게영
        LocationManager manager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER); //여기서 네트워크프로바이더를 쓰던 상관없다.
        if(location != null){
            setMyLocation(location);
        }
        GetMyLocationListener listener = new GetMyLocationListener();
        if(manager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,10,listener);
            //1,매개변수 어떤거 쓸래?
            // 1000mms 마다
            // 10m 움직일대마다
            // 리스너 작동
        }
        if(manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,5000,10,listener);
            //1,매개변수 어떤거 쓸래?
            // 1000mms 마다
            // 10m 움직일대마다
            // 리스너 작동
        }
        if(manager.isProviderEnabled(LocationManager.PASSIVE_PROVIDER)){
            manager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER,5000,10,listener);
            //1,매개변수 어떤거 쓸래?
            // 1000mms 마다
            // 10m 움직일대마다
            // 리스너 작동
        }
        //측정중단 메서드
        //manager.removeUpdates(listener);  //예제를위하여 주석처리라능~
    }
    class MyLocationListener implements LocationListener {

        // 위치정보는 아래 메서드를 통해서 전달된다.
        @Override
        public void onLocationChanged(Location location) {
            text1.append("\nonLocationChanged()가 호출되었습니다");

            double latitude = location.getLatitude();
            double longitude = location.getLongitude();

            text1.append("\n현재 위치:" + latitude + "," + longitude);
        }

        @Override
        public void onProviderDisabled(String provider) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

    }
}