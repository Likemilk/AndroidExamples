package com.example.yongjin.an_location3;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * 현재 위치의 지도를 보여주고 그 위에 오버레이를 추가하는 방법
 * 내 위치 표시
 * 방향 센서를 이용해 나침반을 화면에 표시
 */
public class MainActivity extends ActionBarActivity {
    private RelativeLayout mainLayout;
    private GoogleMap map;

    private CompassView mCompassView;
    private SensorManager mSensorManager;
    private boolean mCompassEnabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 메인 레이아웃 객체 참조
        mainLayout = (RelativeLayout) findViewById(R.id.mainLayout);

        // 지도 객체 참조
        map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

        // 센서 관리자 객체 참조 - 센서 매니저 참조
        mSensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);

        // 나침반을 표시할 뷰 생성
        boolean sideBottom = true;
        mCompassView = new CompassView(this);
        mCompassView.setVisibility(View.VISIBLE);

        //나침반 뷰 레이아웃 생성 및 속성 설정(크기, 표시 위치)
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        params.addRule(sideBottom ? RelativeLayout.ALIGN_PARENT_BOTTOM : RelativeLayout.ALIGN_PARENT_TOP);

        //메인 레이아웃에 나침판 뷰 추가
        mainLayout.addView(mCompassView, params);

        mCompassEnabled = true;

        Toast.makeText(getApplicationContext(),"onCreate", Toast.LENGTH_LONG).show();

        // 위치 확인하여 위치 표시 시작
        startLocationService();
    }
    // 화면이 보이는 시점에 호출
    @Override
    public void onResume() {
        super.onResume();

        Toast.makeText(getApplicationContext(),"onResume", Toast.LENGTH_LONG).show();
        // 위치가 확인되었을 때 내 위치를 자동 표시 enable
        map.setMyLocationEnabled(true);

        //화면에 액티비티가 보일 시점에서 센서 매니저에 센서 리스너 등록
        if(mCompassEnabled) {
            mSensorManager.registerListener(mListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                    SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        Toast.makeText(getApplicationContext(),"onPause", Toast.LENGTH_LONG).show();
        // 내 위치 자동 표시 disable
        map.setMyLocationEnabled(false);
        //화면의 액티비티가 중지될 시점에서 센서 매니저의 센서 리스너 해제
        if(mCompassEnabled) {
            mSensorManager.unregisterListener(mListener);
        }
    }

    /**
     * 현재 위치 확인을 위해 정의한 메소드
     */
    private void startLocationService() {
        // 위치 관리자 객체 참조
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        Toast.makeText(getApplicationContext(),"startLocationService", Toast.LENGTH_LONG).show();

        // 위치 정보를 받을 위치 리스너 객체 생성
        GPSListener gpsListener = new GPSListener();
        long minTime = 10000;//GPS정보 전달 시간 지정 - 10초마다 위치정보 전달
        float minDistance = 0;//이동거리 지정

        // GPS 기반 위치 요청
        manager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                minTime,
                minDistance,
                gpsListener);

        // 네트워크 기반 위치 요청
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

                Toast.makeText(getApplicationContext(), "Last Known Location : "
                        + "Latitude : " + latitude + "\nLongitude:"
                        + longitude, Toast.LENGTH_LONG).show();
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        Toast.makeText(getApplicationContext(), "위치 확인 시작함. 로그를 확인하세요.",
                Toast.LENGTH_SHORT).show();
    }

    /**
     * 위치 리스너 클래스 정의
     */
    private class GPSListener implements LocationListener {
        /**
         * 위치 정보가 확인되었을 때 호출되는 메소드
         */
        public void onLocationChanged(Location location) {
            Double latitude = location.getLatitude();
            Double longitude = location.getLongitude();

            String msg = "Latitude : "+ latitude + "\nLongitude:"+ longitude;
            Log.i("GPSLocationService", msg);

            Toast.makeText(getApplicationContext(), "onLocationChanged", Toast.LENGTH_SHORT).show();

            // 현재 위치의 지도를 보여주기 위해 정의한 메소드 호출
            showCurrentLocation(latitude, longitude);
        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    }

    /**
     * 현재 위치의 지도를 보여주기 위해 정의한 메소드
     *
     * @param latitude
     * @param longitude
     */
    private void showCurrentLocation(Double latitude, Double longitude) {
        // 현재 위치를 이용해 LatLon 객체 생성
        LatLng curPoint = new LatLng(latitude, longitude);

        map.animateCamera(CameraUpdateFactory.newLatLngZoom(curPoint, 15));

        // 지도 유형 설정. 지형도인 경우에는 GoogleMap.MAP_TYPE_TERRAIN,
        // 위성 지도인 경우에는 GoogleMap.MAP_TYPE_SATELLITE
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        Toast.makeText(getApplicationContext(), "showCurrentLocation", Toast.LENGTH_SHORT).show();

        // 현재 위치 주위에 아이콘을 표시하기 위해 정의한 메소드 호출
        showPositionIcon(latitude, longitude);
    }

    /**
     * 아이콘을 표시하기 위해 정의한 메소드
     */
    private void showPositionIcon(Double latitude, Double longitude) {
        //마커 생성
        MarkerOptions marker = new MarkerOptions();
        //마커 표시 위치 지정
        marker.position(new LatLng(latitude, longitude));

        marker.title("● 대학명 : 가천대학교(글로벌캠퍼스)\n");//마커 타이틀
        marker.snippet("● 주소 : 경기도 성남시 수정구");//설명
        marker.draggable(true);

        //마커에 아이콘 표시
        marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_spot));
        //지도에 마커 등록 및 마커 title/snippet 표시
        map.addMarker(marker).showInfoWindow(); //이것을 안하면 마커가 설명글이 나오지 않게 된다.
    }

    /**
     * 센서의 정보를 받기 위한 센서 리스너 객체 생성
     */
    private final SensorEventListener mListener = new SensorEventListener() {
        private int iOrientation = -1;

        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }

        // 센서의 값을 받을 수 있도록 호출되는 메소드 - 센서값이 바뀔 때 마다 호출
        public void onSensorChanged(SensorEvent event) {
            if (iOrientation < 0) {
                iOrientation = ((WindowManager)
                        getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getRotation();
            }

            // 센서정보가 바뀔 때마다 나침판 뷰의 방향 설정
            mCompassView.setAzimuth(event.values[0] + 90 * iOrientation);
            mCompassView.invalidate();
        }
    };
}
