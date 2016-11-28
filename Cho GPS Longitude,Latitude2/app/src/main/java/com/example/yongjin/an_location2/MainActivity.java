package com.example.yongjin.an_location2;

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
import android.widget.Toast;


/*
1 파일
2, 프로젝트 트르럭쳐
3, app 에서 디펜던스
4, + 버튼을 눌러서 play-service 라이브러리를 추가한다.
5, 그다음 api 를 맞춰ㅜㄴ다 그런대 API 가 21 이 아니면안되!! 왜안되 엉어어어ㅓ어어 ㅠㅠㅠㅠㅠㅠㅠ
6, 구글 API 키를 발급해야한다
-사용자디렉토리 yongjin 안에있는 .android 디렉토리를 찾아간다.
그래서  debug.keystore 라는 폴더를 찾으면 그 해당 디렉토리에서 이 명령어를 친다

input : keytool -list -v -keystore debug.keystore -storepass android -keypass android
output : R.drawable.keytool-output.png
만약 안된다면 JAVA_HOME/bin/keytool.exe 를 실행하는건대 이것은 기존에 환경설정을 잡아주지않으면 안된다
아니면 그냥 무식하게 자바홈을 직접 다 입력하든가.

7, http://code.google.com/apis/console 로 들어가서 googleMapAPI 에 들어간다.[Google Maps Android API 이걸로 들어가야댐]
8, API 사용설정으로 들어간다. 그다음 사용자 인증정보에 들어간다
9, 새키만들기를 누른다.
10 안드로이드 키 를 누른다.





7, 키를 발급받았으면  application 태그안에서 meta-data 태그로 만들어서 키를 만들어 넣는다.

*/


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

/**
 * 현재 위치의 지도를 보여주는 방법
 *
 * Google Play Services 라이브러리를 링크하여 사용
 * 구글맵 v2를 사용하기 위한 여러 가지 권한이 있어야 함
 * 매니페스트 파일 안에 있는 키 값을 PC에 맞는 것으로 새로 발급
 */
public class MainActivity extends ActionBarActivity {
    //GoogleMap 객체
    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* 지도 객체 참조(GoogleMap 객체)
           - 지도를 보여주기 위해 XML 레이아웃에 추가한 프래그먼트(<fragment>)에는
             class 속성으로 "SupportMapFragment" 클래스가 할당되어 있음.
             이 객체는 레이아웃에 정의한 "SupportMapFragment" 객체를 자바 소스에서
             참조한 후, getMap() 메소드를 호출하면 GoogleMap(지도객체) 객체를 참조할 수 있음.

           - 프래그먼트(액티비티 위에 부분화면을 표시하는 뷰)는 일반 뷰가 아니기 때문에
             프래그먼트를 참조할 때 프래그먼트 매니저(액티비티 안에서 프래그먼트를 관리)를 사용
        */
        SupportMapFragment fragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.map);
        //구글 맵 객체 참조
        map = fragment.getMap();

        //map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

        // 위치 정보 확인을 위해 정의한 메소드 호출
        startLocationService(); //이때야 바로 구글 맵을 불러오는 녀석이다 ! 그렇다 ! 그렇당! 뀨아 꾸앙
        //내가 필요한건 지구의 지도가아니라 내 위치에 근접한 지도만 필요해! 그러니까 우린 내 위치에 근접한 지도만 가져오겠어 !

    }

    /**
     * 위치 정보 확인(수신)을 위해 정의한 메소드
     */
    private void startLocationService() {
        // 위치 관리자 객체 참조
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //내 위치는 이녀석으로 참조를 할꺼야 ! LocationManager 로 리스너를 불러와서 참조할 수 있다구 !
        // 이것을 참조하기위해서는 리스너를 불러왕되!
        // 위치 정보를 받을 위치 리스너 객체 생성
        GPSListener gpsListener = new GPSListener();
        long minTime = 10000;//GPS정보 전달 시간 지정 - 10초마다 위치정보 전달
        // 여기다가 시간을 줬는대 mms 이니까 1000 = 1 초 .
        float minDistance = 0;//이동거리 지정

        // GPS 기반 위치 요청
        manager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,//이녀석은 위치를 불러올때 두가지 방법이 있다고 했다 .이녀석은 GPS 센서를 이용해서 불러옴
                minTime, //10초 마다 GPS 에서 위치정보를 가져온다.
                minDistance, // 최소거리 아마 이동거리인가? 무튼 이동했을때에 따라서도 이 녀ㅕ석을 호출해온다.
                gpsListener); //리스너는 gpsListener 로 수신하는 리스너를 정하겠다 .! 그렇다.!

        // 네트워크 기반 위치 요청
        manager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER, // 이녀석은 기지국을 이용하겠다 는것이다.
                minTime,
                minDistance,
                gpsListener);

        // 위치 확인이 안되는 경우에도 최근에 확인된 위치 정보 먼저 확인
        //만약 내가 Gps 기능도 이용못하고 기지국에 있는 정보도 이용하지못할때에는
        // 이전에 받아 놓았던 위도 경도 값을 띄워놓겠다라는 소리이다.
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
    //이녀석이 꼐속해서 받아왔던 위치 관련 리스너이다!
    private class GPSListener implements LocationListener {
        /*
         * 위치 정보가 확인(수신)될 때마다 자동 호출되는 메소드
        */
        public void onLocationChanged(Location location) {
            Double latitude = location.getLatitude();//위도
            Double longitude = location.getLongitude();//경도

            String msg = "Latitude : "+ latitude + "\nLongitude:"+ longitude;
            Log.i("GPSLocationService", msg);

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
    //아까는 위도 경도 값만뽑아서 요청을 했지만 이것은 맵에다가 자신의 위치를 띄우겠다는 소리이다.
    // latitude 와 longtitude  는 위도와 경도
    private void showCurrentLocation(Double latitude, Double longitude) {
        /* 현재 위치를 이용해 LatLng 객체 생성 - 위도와 경도로 좌표를 표시
           - 전달받은 위도와 경도 값으로 LatLng 객체를 만들면 지도 위에 표시될 수 있는
             새로운 포인트가 만들어짐(포인트 객체 생성)
         */
        LatLng curPoint = new LatLng(latitude, longitude);
        //이녀석은 위도와 경도의 위치를 나타내는 좌표객체이다. 이녀석을 통해서

        /* 구글맵 객체의 animateCamera() 메소드를 이용하여 그 위치(curPoint)를 중심으로 지도를 보여줌
            @param curPoint
            @param level(배율)
         */
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(curPoint, 15));
        //위도 경도 값을 전해주고 뒤에있는 param 15는 배율을 지정해준것이다.
        // 그 배율이란것은 지도의 확도 레벨은 15로 주어라 라는 ㅅ리 인거 같음.
        /* 지도 유형 설정
           - 일반지도인 경우 : GoogleMap.MAP_TYPE_NORMAL
           - 지형도인 경우 : GoogleMap.MAP_TYPE_TERRAIN,
           - 위성 지도인 경우 : GoogleMap.MAP_TYPE_SATELLITE
         */
        map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
    }
}
