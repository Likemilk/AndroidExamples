package com.likemilk.cho11_customlistview;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends ActionBarActivity {
    ListView list;
    int img_data[] = {
            R.drawable.imgflag1,R.drawable.imgflag2,R.drawable.imgflag3,R.drawable.imgflag4,
            R.drawable.imgflag5,R.drawable.imgflag6,R.drawable.imgflag7,R.drawable.imgflag8,
    };
    //이미지 R들은 전부 해쉬 정수값이기 때문에. 숫자 자료형 배열로 선언받는다.

    String str_data1[] = {
            "토고","프랑스","스위스","스페인","일본","독일","브라질","대한민국"
    };
    String str_data2[] = {
            "togo","frans","swiss","spain","japan","dochiland","brazil","Korea"
    };
    //전에 cho10 에서 구성했던 어레이 어뎁터 이미 는 아이디가 정해져있다.
    //그러나 이것은 아이디도 마음대로 모양도 마음대로 사진도 마음대로 정한것.
    //어떤데이터를 어디다가 정의한다. 이것도 다 정의해주어야한다.

    //안드로이드를 개발할때에는 arrayList와 HashMap 만 사용하세요
    //인덱스 번호로 관리하는 arrayList 는 번호로 이름을 가지고 관리하는  HashMap 은 문자열로 구분한다.
    //안드로이드는 arrayList라고 생각하고 데이터를 받으려하기때문에 vector 컬랙션으로 형변환하려하면 fuck됨.


    //ArrayList를 하나 받는다. 단 ArrayList에는 HashMap 을 담아주어야한다. 그리고 HashMap 에는 항목을 담아주어야한다.
    //이렇게 ArrayList는 리스트뷰의 인덱스를표현하고 HashMap 에는 항목을 담는 다.
    //그런다음 ArrayList는 Adapter 에 담는다.
    //이것이 바로 Simple 어뎁터 하나하나 다 정의해주어야하기때문에 즉 재ㅔ공하는것이 정말 심플하기때문이다.
    // adaptor <-  ArrayList(n)-< HashMap(data1);
    //                            HashMap(data2);
    //                            HashMap(data3);/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = (ListView) findViewById(R.id.listView);
        //배열의 갯수만큼 데이터를 담는다
        ArrayList<HashMap<String,Object>> data_list =
                new ArrayList<HashMap<String,Object>>();
        //몽키테스트 인터넷 유틸리티인데 초당 아무대나 눌러서애러를 잡는것이다.
        //안드로이드 어플을 만들면 air_plain 모드를 한번 켜본다.

        for(int i =0; i<img_data.length;i++){
           //HashMap에 들어가는 데이터는 정확하지않기때문에 제네릭을 Object 로 주겟다.
            HashMap<String,Object> map = new HashMap<String,Object>();
            map.put("data1",img_data[i]);
            map.put("data2",str_data1[i]);
            map.put("data3",str_data2[i]);//해쉬맵에 넣는다!
            //3개넣고 그걸하나로 넣고! 3개넣고! 그걸 하나로 넣고!
            data_list.add(map);//어레이리스트 data_list.add(HashMap) 에 넣는다!
        }
        // 하나의 HashMap 이 완성이 되었다.
        // 자바프로그래밍의 컴파일과정은 기계어로 변환하는것이아니라 JRE 읽을수있는 값으로 변환하는것을 말한다.
        // 마지막날에 안드로이드 보안에 관한 자바코드를 추출하는것을 해보겠다.
        // 난독툴을 쓰는이유 코드의 용량을 줄이기위해 남이 보기 어렵게하기위해[개발에 관련없는사람]
        // 안드로이드는 자동으로 난독화해줌. ... 정확히말하면 이 IDE 툴에서

        int ids[]  = {R.id.imageView,R.id.textView,R.id.textView2};
        //해쉬맵의 갯수와 int 형 ids 의 배열의 갯수가 같아야한다.
        String key[] = {"data1","data2","data3"};
        //해쉬맵에 집어넣을때 사용한 이름을 사용 "data1" "data2" " data3" 이런거 이용해서 구분한다.
        // 여기 ids와 key 의 순서에 맞춰서 데이터를 넣는다

        SimpleAdapter adapter = new SimpleAdapter(this, data_list, R.layout.row, key, ids);
        //this 는 Context
        //data_list 는 ArrayList
        //R.layout.row
        //컨텍스트란? 엑티비티는 컨텍스트를 상속밭고있기때문에 this 로 context는 안드로이드 OS 가 셋팅한다.
        //컨텍스트란게 매개변수라면 엑티비티를 넣어주면된다는소리이다.
        //만약 그게 아니라면 컨텍스트를 추출해서 넣어야한다.
        list.setAdapter(adapter);
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
