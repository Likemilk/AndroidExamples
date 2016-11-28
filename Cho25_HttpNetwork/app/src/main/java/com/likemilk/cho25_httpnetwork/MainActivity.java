package com.likemilk.cho25_httpnetwork;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


public class MainActivity extends ActionBarActivity {

    DisplayHandler handler;
    // 데이터를 담아 놓을 컬랙션
    ArrayList<String> idx_xml_list, str_xml_list,int_xml_list;
    ArrayList<String> str_json_list;
    ArrayList<Integer> idx_json_list, int_json_list;

    TextView text1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text1 = (TextView)findViewById(R.id.textView3);

        idx_xml_list = new ArrayList<String>();
        str_xml_list = new ArrayList<String>();
        int_xml_list = new ArrayList<String>();

        handler =new DisplayHandler();

    }
    public void show_input(View view){
        Intent intent = new Intent(this,InputActivity.class);
        startActivity(intent);
    }
    //-------DOM-----
    public void dom(View view){
        DomThread thread = new DomThread();
        thread.start();
    }
    class DomThread extends Thread {
        public void run(){
            try{
                //컬랙션 초기화
                idx_xml_list.clear();
                str_xml_list.clear();
                int_xml_list.clear();



                URL url = new URL("http://www.softcampus.co.kr:8080/data_out_xml.jsp");
                URLConnection conn = url.openConnection();
                InputStream is = conn.getInputStream();
                // 도큐먼트 객체 추출
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.parse(is);
                // 이것이 파싱~ㄴ
                //org.w3c.dom 을 불러와야한다.
                //이것이 메모리를 많이먹음 Document 왜냐하면 태그의 모든정보가 여기에 다 들어가있기떄문에.  XML 에 있는 문서들을 전부 메모리에 올려두고 시작하기때문이다.
                Element root = doc.getDocumentElement(); //이제 여기서 필요한 정보를 뽑아내면된다. 필요한 태그 객체들을 출력하면된다.
                NodeList idx_node_list = root.getElementsByTagName("idx");
                NodeList str_node_list = root.getElementsByTagName("str_data");
                NodeList int_node_list = root.getElementsByTagName("int_data");

                for(int i=0; i < idx_node_list.getLength();i++){
                    Element idx_el = (Element)idx_node_list.item(i);
                    Element str_el = (Element)str_node_list.item(i);
                    Element int_el = (Element)int_node_list.item(i);

                    String idx_data = idx_el.getTextContent();
                    String str_data = str_el.getTextContent();
                    String int_data = int_el.getTextContent();
                    //이렇게하면 문자열을 얻을수 있다.

                    idx_xml_list.add(idx_data);
                    str_xml_list.add(str_data);
                    int_xml_list.add(int_data);

                }
                // 핸들러 요청
                handler.sendEmptyMessage(0);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    //-------DOM-----

    //-------SAX-----
    public void sax(View view){
        SaxThread thread = new SaxThread();
        thread.start();
    }
    class SaxThread extends Thread{
        @Override
        public void run() {
            try{
                // 이것 역시 d인풋스트림을 필요로 한다
                URL url = new URL("http://www.softcampus.co.kr:8080/data_out_xml.jsp");
                URLConnection conn = url.openConnection();
                InputStream is = conn.getInputStream();
                //실제 파싱작업을 담당할 객체를 생성하겠다
                SaxHandler sax = new SaxHandler();
                //파싱작업 시작

                //파서를 생성한다.
                SAXParserFactory factory = SAXParserFactory.newInstance();
                SAXParser parser = factory.newSAXParser();
                //태그를 읽어 오는 도구
                XMLReader reader = parser.getXMLReader();
                //실제 파싱 작업을 담당할 객체를 생성한다.
                reader.setContentHandler(sax);

                InputSource source = new InputSource(is);
                //리더가 인풋스트림을 통해서 다른요소를 읽어오고 처리하고 날리고 읽어오고 처리하고 날리고를 반복한다.
                //
                //파싱 시작!
                reader.parse(source);
            }catch(Exception e){
                e.printStackTrace();
            }

        }
    }
    //파싱작업을 담당하는 클래스
    class SaxHandler extends DefaultHandler {

        //데이터 문자열을 넣어 놓을 문자열 변수
        String tempStr;

        @Override
        public void startDocument() throws SAXException {
            super.startDocument();
            idx_xml_list.clear();
            str_xml_list.clear();
            int_xml_list.clear();

        }
        //우리가 파싱하는 dom 과 sax 는 표준방법이다.
        //원래 쓰고있는 표준라이브러리이다
        //우리는 이걸로 태그이름으로 구분할 것이다.
        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        //시작태그를 만났을때 있는 속성값을 추출하는 작업이 실행된다
            //안드로이드는 로컬내임이 태그이름
            //attrivbute 는 객체이름이 속성의 이름을 넣어주면 속성에 세팅되어있는 값이 나온다.
            //어떤태그에 문자열을 집어넣어야하는지 알수가없다.
            //이부분은 개발자가 직접구현해야한다.
            //종료태그까지저장을한다.
            //시작태그가오면 임시태그에넣고

            // 다른사람은 시작태그가 실행되면 boolean true 종료태그가 만났으면되면 boolean false
            // 이런방식으로 구현하는 사람도 있다.
            super.startElement(uri, localName, qName, attributes);
            if(localName.equals("태그명")){
                String attr = attributes.getValue("속성명");
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            super.characters(ch, start, length);
            //1매개변수 :3글자라곳하면 배열하나를 만들어놓고 3글자를 담아놓을 수 있는 배열문자열변수를 만들어서 넘겨주는 역할을 한다. 새로만드는것이 아니다.
            //2매개변수 :start부터 length의 갯수만큼 읽어와라.
            //3매개변수 : 문자열을 계속 생성하는것이 아니라 캐릭터메서드를 만들어서 계속 넘겨주는 방식을 사용한다. 메모리를 아끼기 위한수단이다.
            //태그가 아닌 문자열을 만났을때
            //문자열을 임시문자열변수에 담을때

            tempStr = new String(ch,start,length);
            //종료태그를 만났을때 호출되는 메서드이다.
            //임시 문자열 변수에 있는 문자열 값을 컬랙션에 달아준다.
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            //종료태그를 만났을때 호출되는 메서드
            //임시문자열 변수에있는문자열 값을 컬렉션에 담아두는 작업을 한다.
            super.endElement(uri, localName, qName);
            if(localName.equals("idx")){
                idx_xml_list.add(tempStr);

            }else if(localName.equals("str_data")){
                str_xml_list.add(tempStr);

            }else if(localName.equals("int_data")){
                int_xml_list.add(tempStr);
            }
        }


        @Override
        public void endDocument() throws SAXException {
        //문서의 마지막을 만났을때(파싱완료)
        //추출한 데이터를 이용해 필요한 작업을한다.
            super.endDocument();
            handler.sendEmptyMessage(0);
        }
    }

    //-------SAX-----

    //-------PULL-----
    //풀파싱은 심플파싱이라고도하는대 개발자가 하나하나 다 지정해주어야 한다. 개발자에게 제공되는것이 심플하기떄문에 ㅠㅠ..
    //문서의 마지막을 만났을때 브레크문을 써서 만들것이다 풀파싱은
    //꼭 그렇게 안해도된다 어떠한 상황일대 중단을 하겠다는 문장을 넣어서 break 를 넣어도된다.
    //다음요소로 이동해라 뭐 그런요소들을 개발자가 직접 입력하는것이다.
    public void pull(View view){
        PullThread thread = new PullThread();
        thread.start();
    }
    class PullThread extends Thread{
        @Override
        public void run() {
            try{
                // 이것 역시 d인풋스트림을 필요로 한다
                URL url = new URL("http://www.softcampus.co.kr:8080/data_out_xml.jsp");
                URLConnection conn = url.openConnection();
                InputStream is = conn.getInputStream();
                //접속  pull 파서
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance().newInstance();
                XmlPullParser parser = factory.newPullParser();

                parser.setInput(is,"UTF-8");
                //파싱작업 시작
                String tempStr=null;// 지역변수는 초기화 하는것이 좋다

                while(true){
                    // 현재 요소를 타입값으로 얻어오면됩니다
                    int type = parser.getEventType();
                    if(type== XmlPullParser.START_DOCUMENT){
                        //문서의 시작
                        idx_xml_list.clear();
                        str_xml_list.clear();
                        int_xml_list.clear();
                        //컬랙션 초기화
                    }else if(type == XmlPullParser.START_TAG){
                        //시작 태그  // android:layout_width 로 xml 을 예로들면   : 는 namespace 고 그 오른쪽에있는것이 속성이름이다.
                        String  tag_name = parser.getName();
                        if(tag_name.equals("태그명")){
                            String attr = parser.getAttributeValue("네임스페이스","속성이름");
                        }
                    }else if(type == XmlPullParser.TEXT){
                        //문자열 값
                        tempStr = parser.getText();
                    }else if(type == XmlPullParser.END_TAG){
                        //종료 태그
                        String tag_name = parser.getName();
                        if(tag_name.equals("idx")){
                            idx_xml_list.add(tempStr);
                        } else if (tag_name.equals("str_data")){
                            str_xml_list.add(tempStr);
                        } else if(tag_name.equals("int_data")){
                            int_xml_list.add(tempStr);
                        }

                    }else if(type == XmlPullParser.END_DOCUMENT){
                        //문서의 마지막
                        handler.sendEmptyMessage(0);
                        break;
                    }
                    // 다음 요소로 이동한다
                    parser.next();
                }

            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    //-------PULL-----


    //-------JSON-----
    public void json(View view){
        JsonThread thread =new JsonThread();
        thread.start();
    }
    class JsonThread extends Thread{
        @Override
        public void run() {
            try{
                idx_json_list.clear();
                str_json_list.clear();
                int_json_list.clear();
                //JSON 은 일단 문자열이 필요하므로 문자열을 얻어오는것을 하겠다.
                URL url = new URL("http://www.softcampus.co.kr:8080/data_out_json.jsp");
                URLConnection conn = url.openConnection();
                InputStream is = conn.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String str;
                StringBuffer buf = new StringBuffer();

                while((str=br.readLine())!=null){
                    buf.append(str);
                }
                String rec_data=buf.toString();

                //JSON Array 생성
                JSONArray json_array = new JSONArray(rec_data);
                //JSON Array 로 한이유는 처음 문장이 시작할때 배열로 시작한것이라 그렇다 만약에 오브젝트로 시작 {} 되면
                //Array가 아니라 JSONObject 로 시작하면된다. 읽기 시작하려는것이 배열이냐 ! 오브젝트냐! 그것이 문제로다.
                // 문서의 첫 시작점이 시작이아니라 읽고 싶은곳 시작을 말하는것이다!
                //과제는 sys로 뽑아내고 sunrise 와 sunset 을 뽑아내면된다.
                // 데이터의 구조에따라서 데이터를 뽑아내는것만달라지고


                //이 객체는 저장되어있는객체를 다 읽어서 JSON형식에 맞게 parsing 을 한다.
                for(int i=0;i<json_array.length();i++){
                    JSONObject obj = json_array.getJSONObject(i);

                    int idx=obj.getInt("idx");
                    String str_data = obj.getString("str_data");
                    int int_data = obj.getInt("int_data");

                    idx_json_list.add(idx);
                    str_json_list.add(str_data);
                    int_json_list.add(int_data);

                }
                handler.sendEmptyMessage(1);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    //-------JSON-----

    class DisplayHandler extends Handler {
        //문서의 시작을 만났을때 호출
        //XML 파싱 작업 준비 단계
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what ==0){
            //xml
                text1.setText("");
                for(int i =0;i<idx_xml_list.size();i++){
                    String idx = idx_xml_list.get(i);
                    String str_data = str_xml_list.get(i);
                    String int_data = int_xml_list.get(i);

                    text1.append("idx :    "+idx+"\n");
                    text1.append("str_data:    "+str_data+"\n");
                    text1.append("int_data :    "+int_data+"\n\n");

                }
            }else if(msg.what==1){
                text1.setText("");
                for(int i=0; i<idx_json_list.size();i++){
                    int idx = idx_json_list.get(i);
                    String str_data = str_json_list.get(i);
                    int int_data = int_json_list.get(i);

                    text1.append("idx :    "+idx+"\n");
                    text1.append("str_data:    "+str_data+"\n");
                    text1.append("int_data :    "+int_data+"\n\n");

                }
            }
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
JSON 같은경우에는 스트림을 생성하는것이아니라 문자열 데이터가 필요하다,
코드상에서 JSCN 형식에 맞게 작성을하거나 ...
우리가 파일에서 문자열을 읽어오거나 네트워크에서 문자열 데이터를 읽어 오거나한줄한줄씩
그렇게 스트림에 담아준다. 그다음에 거기서 데이터를 추출해서 담아준다.

JSON문서의 데이터 양식.
배열 : []   type : JSON Array     : 이것은 표준라이브러리가아니라 이것을 분석할수 있는 몇가지 라이브러리가있다.
                                우리가 사용한것은 스마트폰 어플리케이션 내부에 저장되어있는 라이브러리이다.
객체 : {}   type : JSON Object 로 추출 :

문자열  : 따옴표 ("")  String
정수값 100 , 120 , int
실수  10.12 , 13.3253 double

----------------
{
    [
        {
            "idx" : 161,
            "str_data" : "안녕",
            "int_data" : "100"
        },
        {
            "idx" : 161,
            "str_data" : "안녕",
            "int_data" : "100"
        }
    ]
}
해석 객체가 하나 있고
객체안에 배열이 하나가 있다
배열 안에는 객체 두개가있다
첫번째 객체는 3가지의 변수를 갖고있고
두번째 객체 역시 3가지의 변수를 갖고 있다.

JSON 애서 배열과 객체는

배열은 같은 의미를 갖는 객체를 끼리 묶는 단위로 사용하고
객체는 서로다른 특성을 갖는 개체를 지정하는것이다.
알아서 인덱스번호로 지정해서 처리하기때문에 가급적이면 for 문을 넣어서 관리하는 것이 좋다.

학생자체는 객체로 만들어서 데이터를 ㅅ ㅔ팅하는편이 좋다.

전부다 의미ㄱ바 같은것인지 의미가 다른것인기 구분해서 데이터를 넣어주면될것이다.



거기서 타입이 json 이기때문에 코드가 편이라고 가볍다 . 둘중에 편한걸 사용하면된다./
--------------Json 기본 양식

*/

