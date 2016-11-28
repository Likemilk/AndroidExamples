package com.likemilk.cho40_googlemap;


import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


// 요즘 나온 gps 칩셋은 무선네트워크,gps , 기지국 3각 측량을 하여서 정보를 출력한다.
// 위도와 경도.를 측량해낸다.
// 187 p getLastKnownLocation 이것은 이전에 저장된 값을 불러오는 메서드이다 LocationManager 객체를 통해 가져올 수 있다.
// 189 p Maanger.requestLocationUpdates 바로 이 메서드가 새롭게 측정하는것 이것을 무조건 3가지 연결을 다 해주어야한다.
// 1안, LocationManager.GPS_PROVIDER   //안드로이드 os 는 스래드가
// 2안, LocationManager.NETWORK_PROVIDER  //네트워크 망을 이용해서 측정하는경우
// 3안, LocationManager.PASSIVE_PROVIDER  //gps 를 이용 passive provider 는 다은 어플리케이션이 가져온 값을 가져오는것
//  이 세가지를 전부 가져와서 3각측량을 해서 정보를 출력및 가공한다.
// 안드로이드 위치정보가 잘 안잡히는경우는 LocationService 의 스레드가 멈춰서 그렇다. 껏다켜야한다.
// 그래서 1안,2안이 다 죽었을때 사용가능한것이 3안이다. 여기서 가장정확한것이 3안이다.

// 현재 구글맵은 V3 까지 나왔다.
// 버전2가 3D를 지원한다.
// 그러나 안드로이드 구글 맵 버전1은 그림만 그릴수있다.
// 그래서 말풍선이나 마커는 직접 다 만들어줌..
// 버전2를 통해서 구글 코드를 띄우면 가장 간단해진다.
// 이것은 안드로이드 내부에 기본적으로 들어가져있는것이아니라 Google Play Service에 설치되어있는것이다. 따로 설치안받아도
// 이미 설치되어있을것이다. 블랙베리나 이런것이 아니라면...
// 구글맵에 플레이 서비스가 포함되어있다. 시뮬레이터에는 설치가 안되어있다 직접 설치해야하지만 차라리 디바이스를 통해 테스트 하는게 좋다.
// Gmail 계정이 필요하다 .  주소는 바뀌었다. http://console.developers.google.com/ 안드로이드만 안드로이드 닷컴으로만 분리되어있고
// 나머지서비스는 다 여기 홈페이지로 통합되어있다. 사용하려면 api 키를 발급받아야하며 api 키는 사용자와 어플리케이션을 구분하기도위한 것이다.
// open API를 사용하려면 0달라 인증을 하기도한다. //////////////////


//여러 라이브러리를 쓸예정이므로 api 14  4.0 으로 프로젝트를 생성하였다.
// constant-rig-855 구글 map
// http://console.developers.google.com/ 여기들어가있는다.
// Google API 인증 -> API -> google maps androidv2 하고Google Cloud Messaging for Android // Places API/ 이렇게 3개를 추가해준다.
// 그다음 사용할수 있는 api 키값인증해야되는대 사용자 인증정보를 들어간다. -> 공개 API Access 를 들어간다 -> 안드로이드 키를 누른다.
// SHA1 값은 234p 보면 이야기가 나와있다. 이값을 알아내야한다 바탕화면->계정폴더->.android->debug.keystore 파일이있다. 이것을 쓸수도 만들수도 있다. 우리는 만든다.
// 폴더의 빈공간에다가 shift+우클릭 -> 여기서명령창 열기-> cmd : path %JAVA_HOME%bin ->cmd :   keytool -list -v -keystore mystore.keystore[이 명령어를 치지말고 수정한다]
// -> cmd :   keytool -list -v -keystore debug.keystore  //만약 에러가난다면 자바 경로를 제대로 잘 잡아주길바란다. 잘안된다면 //passwd : android
// 커맨드 창에 인증서지문에서 : SHA1 값만 복사한다. 그 복사한값과 내 패키지명을 같이 써넣는다
//
// 60:B4:0D:AE:E1:D4:6B:D7:84:0C:0A:1E:E9:8E:F5:EC:EF:2E:8B:07;com.likemilk.cho40_googlemap
// 이렇게 구글에다가  인증키를 요청한다. 만들기버튼을 누르면된다.
/*
*      이렇게 키값을 받는다./
 *  Android 애플리케이션용 키

API 키 AIzaSyDspNTp0jSTaRHimvPi0pkxmj_PrnDsks0 ANDROID 애플리케이션
60:B4:0D:AE:E1:D4:6B:D7:84:0C:0A:1E:E9:8E:F5:EC:EF:2E:8B:07;com.likemilk.cho40_googlemap
 활성화 날짜 2015. 2. 13. 오전 8:21:00 활성화 기준 dydwls121200@gmail.com (나)

* */
//  그다음에 SDK Manager 에서 구글 플레이스토어를 다운받는다.
// 그다음 이제 미칠듯한 코딩이다. 안드로이드 메니페스트에서 권한추가 마니마니한당.
// /
// 라이브러리 추가.
// file -> project Structure -> Modules 의 app 클릭해준다 ->dependencies 이것이 라이브러리추가. + 하기 버튼을 눌러서 Library Dependency 클릭함-> 클릭하고보면 play-services 가 나온다. 그걸 골라주고 ok 눌른다.
//

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getMyLocation();
    }

    public void setMyLocation(Location location){
        //위도 경도를 추출 이것은 구글멥 피시버전에도 똑같고 자바버전도똑같고 자바스크립트~! 마저도 똑같다  이것은 구글에서 맞춘것이다

        double lat = location.getLatitude(); //위도 라티튜드~ latitude
        double lot = location.getLongitude(); //경도 롱티튜드~ longTitude

        LatLng position = new LatLng(lat, lot);
        // 위도,경도,셋팅 확대해주어야된다
        CameraUpdate update1 = CameraUpdateFactory.newLatLng(position);
        CameraUpdate update2 = CameraUpdateFactory.zoomTo(15);

        FragmentManager fmng = getSupportFragmentManager();
        //support.v4 로 해야한다.
        SupportMapFragment sFag = (SupportMapFragment) fmng.findFragmentById(R.id.fragment);
        GoogleMap map = sFag.getMap();

        map.moveCamera(update1);
        map.animateCamera(update2);


        // 마커~
        MarkerOptions option = new MarkerOptions();
        option.position(position);
        option.title("현재위치");
        option.snippet("티 아카데미");
        map.addMarker(option);



    }

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

    // 위치정보가 수신되면 반응하는 메서드를 만든다뉸 리슈녀
    class GetMyLocationListener implements LocationListener {
        @Override
        public void onLocationChanged(Location location) {
            setMyLocation(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
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

/*
http://edu2.softcampus.co.kr/t.html
스레드의 특징
메인스레드 5초이상 ㄴㄴ
        화면처리 ㅇㅇ

        화면처리는 헨들러를통해
        화면은 ASYNCTask

        일반스레드 화면처리 ㄴㄴ
        5초이상 작업 가능 ㅇㅇ
        메인스레드 화면처리 ㅇㅇ
        5초이상 작업 ㄴㄴ
        -----------------------
        중간중간 화면처리가 필요하면 화면처리 씀.
        -----
        그걸 위해 제공되는 클래스는AsyncTask
        -------
        단말기를 길게눌렀을때나오는 메뉴 ContextMenu

        OptionMenu 단말기의 메뉴
        -----------
        ActionBar의 메뉴
        ---------------
        서비스 백그라운드를이용
        --------
        브로드 캐스트 리시버
        시스템 감시를 위해
        -----------
        데이터베이스를 다른엡에서 제공가능하게만든것
        ContentProvider
        ---------
        엑티브 화면을 가지는 서비스단위
        ----
        서비스 화면이 없는 서비스단위
        ------
        네트워크 통신시 서버쪽에서 가장먼저 생성하는거
        ServerSocket(포트)
        SoverSocket.accept();////성공시 서버와 클라이언트양쪽에 동시에생김
        -----------
        소켓에서 중요한것은
        인풋스트림과 아웃풋스트림으로 데이터를 주고받고가가능하고
        데이터가공은 인풋스트림과 아웃풋스트림을 이용해가공함
        -0---------
        안드로이드의 db 이름 SQLite
        ------------
        쿼리문으로날리는방법 1
        컬럼읭 ㅣ름과 집어넣는 값을 셋팅한 클래스이름은 ContentsValues 2
        -----------
        select query 를 가져올때 사용하는 도구 Cursor
        --------------
        환경설정 구현할때 사용햇던거
        Preference 객체를 사용함
        -----------
        미디어 파일의 처리
        raw 폴더와 asset의 차이
        -----------
        미디어 플레이어로 음원을 재생
        미디어 서버 구축 ㄴㄴ 해
        -------------
        이미지 했을때 xml로 이미지를 만들었음
        비트맵에다가 리핏에다가 트루를 넣으면 tile 기법으로 배치함
        여러이미지를 겹치게 나타낼때는 layer
        버튼처럼 이미지 상태를 나타낼때 selector
        리핏속성에다가 true 를 넣음 레이아웃태그 내부에다가 bitmap 태그를 넣으면된다.
        셀렉터태그에다가 상태에 따른 액션을 지정
        --------------------
        단위에대한 문제가나옴
        px픽셀은 픽셀단위
        dp 는 가변형[중요]
        xp 폰트에따라 가변형
        pt 는 출판업계
        in 인치
        mm 미리미터
        ---------------------------------------
        폴더를 언어별로 나눠보고도 해상도별로도 orientation 별로도 나누고..
        문자열이 들어갈 수 있는것들은 전부 나누어주면된다. 이미지에 글씨가들어가있다면 언어별로 나눠주는것이 좋다. Values 폴더안에 들어있는것들을 무조건 나눠준다.
        ------------------------------------------------------------------------------------------------------
*/

//  푸시메세지 해당 단말기에만 할당되는 값. 이 값을 p304 GCM push 이
// 1, 값을 서버컴퓨터에 값을 저장시켜놔야한다.
// 2, 어떤 단말기에 메세지를 보내달라고 인식을 해야한다.
// 3, 그값이란건 해당 단말기에만 얻을수있다.
// 4, 그 어플은 다른단말기로 가지않는이상 계속 유지가된다.
// 5, 삭제를 하고 다시설치하면 그 값은 바뀐다. 즉 서버가 클라이언트를 구분하기위한 값으로 사용되기도한다.
// 6, 이값으로 서버에 저장한다.
// 7, 그 다음에 보낼 메세지를 해당 키갑[등록아이디]과 내용[메세지 정보]과 함께 구글에게 전달한다,.
// 8, 그런다음 구글에서 푸시메세지를 보내주기도 한다.
// [][][][][][][][][][][][][[]
// 이때 또 한번 다시 키값을 발급받아야한다.
// 저기 맵에서 키받으면서 했던곳에서 새 키만들기 를 누른다.-> 그다음 서버키를 누른다.
// 저기에 자기 아이피를 입력안하게 될시에 자기ㅁ컴퓨터에다가 가면된다.
// 무튼 무튼 허락받은 키값을 API 키값을 jsp 파일에서 요청하고자 하는곳에서 JSONArray 에 등록을 한다. .add("키값")
// 그런다음에 put 하면 푸시메세지가 날라간다.  다운받은 파일 gcm_server.jsp 파일을 확인해보면된다.
// 브로드케스트 리시버로 값을 받아서 푸시를 날리거나 좋을대로 처리하면된다.
// 서버키를 conn.setRequestProperty("","key"+키값) 여기다가 넣으면된다.
// 이런걸 채팅서버처럼 처리해도된다 이것으로 채팅프로그램 처럼 만들 수 있다 . 이것을 브로드 캐스트로 리시버로 구현할 수 있다.
// 앱에서는 또 따로 GCM 을 쓰기위한 권한을 추가해야한다.

// 안드로이드 분야만 아니더라도  DB / noSQL / 빅데 / 안드로이드/
// 수업은 여기까쥐///////////////////////////