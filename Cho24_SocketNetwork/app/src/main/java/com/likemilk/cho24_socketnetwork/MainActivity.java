package com.likemilk.cho24_socketnetwork;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.*;
import android.view.*;

import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;
import java.util.HashMap;

// 모든ㄴ트워크는 스레드를이용 핸들러와 스레드 구조를 사용해도된다 또는 AsyncTask 를 이용해도 된다.
/*
오픈API를 사용할것이다. 경로찾아가는 서비스를 이용하는것은 불법
직접 다 입력해야되고 위치사업자 에 등록해야됨.  길찾아주는 기능은 자기네 회원들만 사용할 수있게함.
경로데이터를 알려주는것은 불법임. 앱들 처리를 할때는 시작과 종료지점은 네이버나 다음이나 구글맵 API 롤 찍어서
서비스를 연결해서 보여줘야한다. OpenAPI 를 연결해서... 현지 가이드들이 가보거나 맛보고  서울시 Open API 가 존재한다.
서울시 Open API 를 이용하면 공공화장실이 어디있는지 알려주기도한다, 길찾기 알고리즘 A-Star  가 있다.
수집된 데이터를 제 3 자에게 양도하는것이 불가능하다.

네트워크의 안드로이드 소켓 통신 = 자바의 소켓통신 거의 같다
시뮬레이션으로 할대는 127.0.0.1 이나 localhost 따위로 하지말자...
세션이네 쿠키네 다 필요없다 DB에서 출력하는정도만 충분하...할까?

네트워크란~?!

컴퓨터 과학기술은 군사무기와 관련이 많다.
소련이 미국으로 탄도미사일 좌표를 계산하기위해서 애니악 을 만듬.
ENIAC 최초컴퓨터
ARPANET : 최초의 네트워크
네트워크 발전의 주역.  스타그래프트 PC 방

IP100개
핀란드... IT 의 볼모지의 노키아... 를 만든나라
네트워크가 없는 지역에 PC 방이 세워지면서 기업에서 망을 끌어옴 그망을 이용해서
네트워크를 퍼트림. CDMA 특허를 가져오면서/..
GDMA 방식 = 전화음질 좋다. but e대용량방식으론 후짐
CDMA 방식 = 전화음질은 나쁘다 데이터를 쪼개서 전송하는 방식.
  ㄴ>기반은 HSDPA 3G 의 네트워크 통신방식

  빠른 네트워크란?
네트워크는 같은시간에 많은 데이터를 보내는것

        개발
----------------------------------
      MS       ||      Oracle(SUN)
---------인터넷이 퍼지게되면서----
 헹.ㅅㅂ 누가   || 야 쩐다 함 제대로 해보자.
막대한 돈을들여 || 인터넷 개쩐대.
인터넷을 깔거써?||
---------------------------------

스트리밍이란?  같은시간에 많은 데이터를 받아서 읽는 그런 기법.

우리나라 : 호주 사막을 + 십자모양으로 땅을 파서 전 지역에 인터넷을 퍼트림

서버와 클라이언트 개발자는 다르다.
  A사는 서버    ||    B사는 클라이언트
=======================================
A사와 B사는 서로 지원되는 체계로 만들어야한다.
그래서 흔히 표준을 만드는데 프로토콜이란걸 만든다. 3프로토콜은 결국 소켓아닌가유 ㅋ HTTP/FTP/
프로토콜은 어떤순서를 데이터를 받을지? 어떤의미를 같는 데이터를 주고 받을지?

서버 : 서비스 제공자
클라이언트 : 서비스 수신자.

서버가 클라이언트 한태 보내는정보는 응답 를 보내주고
응답 결과 또는 요청 정보를 받는다.
클라이언트는 요청을 하고 응답결과를 보낸다.

http: 태그들은 브라우저에 띄우는대 .zip 이나 .exe 같은 것들은 띄우거나 읽지 못하는 파일이므로 클라이언트에 다운로드를 시킨다.

우리가 사용하는 대부분의 서비스는
클라이언트가 먼저 서비스에게 요청하는 방식은 웹방식을 이용하면된다. [개발의 부담감이 적다.]

 서버호스팅 : http://naver.com : 80 / 파일명
             ------------------ ---   --------------  열고자하는 파일
         ip 주소 = 컴퓨터       프로토콜: 프로그램

앞서말했듯 서버와 클라이언트의 소캣은 안드로이드 프로그래밍이아니라 말그대로 자바프로그래밍이다.많이 유사하다.
소켓만 잡고 스트림을받고 출력해주면 된다.

안드로이드에서 네트워크는 전부다 스래드로 빼야한다.
안드로이드에서 네트워크 사용을위한 퍼미션 선언
어플과 어플이외의 요소를 위해 Permission 을 매니페스트에 작성해야한다.
*/

public class MainActivity extends ActionBarActivity {
    TextView text1;
    DisplayHandler handler;
    //?? 응 응 ?
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text1= (TextView) findViewById(R.id.textView);
        handler = new DisplayHandler();

    }

    // 버튼핸들러
    public void connectServer(View view){
        NetThread t = new NetThread();
        t.start();
    }

    // 스레드 지정
    class NetThread extends Thread{
        @Override
        public void run() {
            try{
                //소켓에 해쉬맵으로 값을 넘기기로한다.
                Socket socket = new Socket("192.168.201.216",25555);
                // 스트림 출력
                InputStream is= socket.getInputStream();
                OutputStream os=socket.getOutputStream();
                DataInputStream dis= new DataInputStream(is);
                DataOutputStream dos= new DataOutputStream(os);

                int data1=dis.readInt();
                double data2=dis.readDouble();
                String data3=dis.readUTF();

                dos.writeInt(500);
                dos.writeDouble(500.55);
                dos.writeUTF("반갑다능");

                is.close();
                os.close();
                dis.close();
                dos.close();
                socket.close();

                HashMap<String,Object> map = new HashMap<String,Object>();
                map.put("data1",data1);
                map.put("data2",data2);
                map.put("data3",data3);

                Message msg = new Message();
                msg.what= 0;
                msg.obj =map;
                handler.sendMessage(msg);

            }catch(IOException ie){
                ie.printStackTrace();
            }
        }
    }


    //화면 처리담당 핸들러
    class DisplayHandler extends Handler {
        // android.os 에 관련뢴 패캐지를 선택해야 한다.
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            //하나밖에없으므로 what값으로 분기하지않겠습니다.
            HashMap<String,Object> map = (HashMap<String,Object>) msg.obj;

            int data1 = (Integer) map.get("data1");
            double data2 = (Double) map.get("data2");
            String data3 = (String) map.get("data3");

            text1.setText("data1 : "+data1+"\n");
            text1.append("data2 : "+data2+"\n");
            text1.append("data3 : "+data3+"\n");
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
피쳐폰 시절때는 웹서버를 사용 x 소켓을 사용했다.
웹페이지는 서버로부터 연결을 하고 웹페이지를 받는다. 그런다음에 연결을 끈는다. 그리고 받은 웹페이지를 브라우저가 펼쳐준다.
이때문에 비연결형 통신이라고 한다. 지금의 HTML5 기반의 서비스는 소캣통신같은것까지 지원한다.
심지어 개발도구마저도 웹버전

과제: 날씨정보 파싱하기
@http://api.openweathermap.org/
서버에 데이터를 저장하기 위한 페이지
@  http://www.softcampus.co.kr:8080/show.jsp 서버 데이터 확인용 페이지
웹서버에 다운로드 업로드를 구현하겠다
@  http://www.softcampus.co.kr:8080/data_in.jsp?str_data=aaa&int_data=123
android cos.jar 파일 업로드를 구현할때 사용하는 대표 라이브러리

데이터 출력(xml)
http://www.softcampus.co.kr:8080/data_out_xml.jsp

데이터 출력 json
http://www.softcampus.co.kr:8080/data_out_json.jsp

데이터 초기화
http://www.softcampus.co.kr:8080/data_init.jsp


우리는 제이슨JSON 으로 데이터를 추출할것이다 .parsing 할것이다 .트래픽이 적게발생하고 데이터도 적어서 더 빠르고 가볍게 받아질수 있다.
파싱!파싱! 파싱!파싱~!파아아아아아ㅏ시이이ㅣㅇ이이파싱!
 파싱은 저의 특기죠 ;.
 */