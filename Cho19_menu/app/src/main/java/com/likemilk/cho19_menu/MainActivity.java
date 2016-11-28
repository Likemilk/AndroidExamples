package com.likemilk.cho19_menu;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
/*
이메일에 관련된 ..
툴바란 엑션화면에 작은화면을 넣고 거기에다가 매뉴바를 넣고 또 사이드 메뉴도 넣는것을 말한다.
액티비티아니면 프래그먼트 아니면 안된다. 이것때문에 안드로이드에서는툴바라는걸 제공하며 하위버전에서 다 재공한다
사이드 메뉴는 위에다가 툴바를 붙여놓고 사이드로 나오는 툴바를 직접 만들어야한다. 매년 6월 구굴 IO 행사를 한다.
화면 UI에 대한 가이드를 언급한적이있다. [한번참가해볼까.ㅋㄷ?] 안드로이드에서 사용했던 구글라이브러리를 통째로 받을수있다늉
그러나 그런것들은 직접 구현해야한다능.

안드로이드의 단말기가 스스로 가로 길이를 측정해서 액션바를할지 옵션매뉴를 할지 스스로 판단하게함.
 가능하면 액션바로 그게 안되면 옵션바로 대체하게 한다. 메뉴버튼이 있는이 없는 단말기는 옵션메뉴 쩜새개 세로 이거 가 나온다.
 즉 옵션메뉴와 액션매뉴는 하나다 . . 탭을 액션바를통해서 탭을 구성해주면 . 여러기능을 제공한다.  탭을 좌우로 스크롤 한다거나 뭐..
 툴바는 액션바를 상속받은 녀석 . 액션바는 활용부가 굉장히 쩌렁!

 화면이 길게 누르면 나오게하겠다.는 엑티비티에 붙이면된다. 어떤메뉴에붙일것인지는 코드로 설정해야한다.

 컨텍스트 메뉴가 뷰를 길게나오면 알고있기때문에 ....팝업메뉴는 코드로 띄우는것. 그러나 버튼을 누르면 텍스트에 띄운다..
 보
 리스트같은경우는 항목을 길게누르면 다른 메뉴가 나온다는 건 화면에 나온다는것을 알고있다.
 화면의 대부분의 뷰가 리스트의 영역을 차지하고 있다 .
 리스트의 같은경우에는 컨텍스트 뷰를 사용하고있다,
 컨텍스트 뷰는 리스트뷰와 같이 쓰고있다. 컨텍스트뷰와 리스트메뉴는 같이 짝짝궁~! ★

 사용자가 몇번째항목을 길게눌러서 나온메뉴인가!  인덱스번호를 우리가 받아낼 수 있다.

 오늘날의 팝업메뉴를 개발자가 직접 다 만들어 주기도 했다.
 메뉴가 나오는 시점은 우리가 자유롭게 구성해주면 딘다.

 컨텍스트매뉴 해보고 팝업매뉴도 해보고 이것을 하나의 화면에서 우리는 해본다 .컨텍스트메뉴는리스트뷰에서 같이 해보겠습니다.
 리스트뷰에서 컨텍스트메뉴를 연동해서 하는거 우리는 곡 알고있어야한다.

 메뉴를 만드는방법 2
 코드를 통해서 / XML 을 통해서

 지금은 XML 로 만들고 컨텍스트는 코드로 , 과정은 똑같아영 ㅋ
 */

public class MainActivity extends ActionBarActivity {

    TextView text1;
    ListView list1;

    String  data1[]={"항목1","항목2","항목3","항목4","항목5","항목6","항목7","항목8","항목9","항목10"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text1= (TextView )findViewById(R.id.textView);
        list1=(ListView) findViewById(R.id.listView);

        ArrayAdapter<String> adapter  = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data1);
        list1.setAdapter(adapter);

        registerForContextMenu(text1);
        registerForContextMenu(list1);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 이미 default Templete 로 작성되어있음.
        // option menu를 구성하는 메서드이며 true 를 리턴하면 메뉴가 나타난다.
        //getMenuInflater().inflate(R.menu.menu1, menu);
        //MenuInflater() 추출
        MenuInflater inflater = getMenuInflater();
        //메뉴를 구성한다
        inflater.inflate(R.menu.menu1,menu);
        return true; //false 로 하면 메뉴가 안나오고 true 하면 나온다.

    }

    //옵션 을 눌렀을때 option menu 항목을 선택했을때 호출되는 메서드 아예 객체가 넘어온다. 이때 아이디를 추출해서아이디가 뭔지 식별하여서 리스너를 지정한다.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        TextView text1 = (TextView) findViewById(R.id.textView);
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        switch(id){
            case R.id.item1:
                text1.setText("항목1 선택");
                break;
            case R.id.item2:
                text1.setText("항목2 선택");
                break;
            case R.id.item3:
                text1.setText("항목3 선택");
                break;
            case R.id.item4:
                text1.setText("항목4 선택");
                break;
            case R.id.item5:
                text1.setText("항목5 선택");
                break;
            case R.id.item51:
                text1.setText("항목5-1 선택");
                break;
            case R.id.item52:
                text1.setText("항목5-2 선택");
                break;
            case R.id.item53:
                text1.setText("항목5-3 선택");
                break;
            case R.id.item54:
                text1.setText("항목5-4 선택");
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    //주~로 그렇게 많이 사용 검색받기 위한 용도로
    //액티비티 액션바를보면 이미지 |  액티비티입니다            ::  이렇게더ㅚ어있는대 이미지는 android.R.id.home 이란 값을 가지고 있다. 그러나 5.0 이상은 보여주지않는다.
    // 그러나 좌측상단에 < 표시의 아이콘을 넣지않는한 홈버튼은 만들어지지않는다. 안드로이드 5.0 이후에는  < 표시를 활성화 해야한다.



    // ContextMenu 값 설정 특정 위젯이나 뷰를 누르면 나오는 새엇ㅇ되는 매뉴 -------시작 start
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        //길게누른 메뉴가 여기로 넘어오게된다.
        super.onCreateContextMenu(menu, v, menuInfo);

        // View 의 아이디를 추출.
        int id = v.getId();
        if(id==R.id.textView){
            menu.setHeaderTitle("TextView 의 메뉴");
            menu.setHeaderIcon(R.drawable.ic_launcher);
            menu.add(Menu.NONE,Menu.FIRST+1,Menu.NONE,"항목1");
            menu.add(Menu.NONE,Menu.FIRST+2,Menu.NONE,"항목2");

            SubMenu sub = menu.addSubMenu("항목3");
            sub.add(Menu.NONE,Menu.FIRST+3,Menu.NONE,"항목3-1");
            sub.add(Menu.NONE,Menu.FIRST+4,Menu.NONE,"항목3-2");
            sub.add(Menu.NONE,Menu.FIRST+5,Menu.NONE,"항목3-3");
            //1 번째 파라매터는 우선순위
            //2 번째파라메터는 아이디값 같은 녀섣ㄱ이다. id 의 용도로 사용함 static final 같이.
            //애초에 id 란것은 숫자 정수값이기 때문에 ok
            // 보통 Menu.FIRST+1 이렇게 안하고 미리 id 값을 정해서 변수명으로 지정한다.
        }else if(id==R.id.listView){
            //리스트의 몇번째를 길게 눌럿는지 아이디값을 알고싶으면 여기서
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            int position = info.position+1;
            menu.setHeaderTitle(position+"번째의 항목의 메뉴");
            menu.add(Menu.NONE,Menu.FIRST+6,Menu.NONE,"리스트메뉴 항목1");
            menu.add(Menu.NONE,Menu.FIRST+6,Menu.NONE,"리스트메뉴 항목2");
            menu.add(Menu.NONE,Menu.FIRST+6,Menu.NONE,"리스트메뉴 항목3");

        }
    }

        //컨텍스트 메뉴 를 눌렀을대 발생되는 이벤트를 정의
    @Override
    public boolean onContextItemSelected(MenuItem item) {
    //여기에서 MenuItem 객체의 item 변수로부터 id 값을 뽑아 낼수 있다 .

        int id = item.getItemId();
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int list_id = info.position+1;
        // 리스트뷰의 몇번째 항목을 길게 눌럿는지
        //메뉴 info 를 추출하겠습니다.

        if(id == Menu.FIRST+1){
            text1.setText("TextView의 항목1");

        }else if(id == Menu.FIRST+2){
            text1.setText("TextView의 항목2");

        }else if(id == Menu.FIRST+3){
            text1.setText("TextView의 항목3");

        }else if(id == Menu.FIRST+4){
            text1.setText("TextView의 항목4");

        }else if(id == Menu.FIRST+5){
            text1.setText("TextView의 항목5");

        }else if(id == Menu.FIRST+6){
            text1.setText(list_id+"번째 항목1");
        }else if(id == Menu.FIRST+7){

            text1.setText(list_id+"번째 항목2");
        }else if(id == Menu.FIRST+8){

            text1.setText(list_id+"번째 항목3");
        }

        return super.onContextItemSelected(item);

    }

    // ContextMenu 값 설정 특정 위젯이나 뷰를 누르면 나오는 새엇ㅇ되는 매뉴 -------시작 end



    //시험문제에 나올만한거 안드로이드 버전별 특징에 대하여 . 2.2빨라지고 3.0 타블릿 4.0통합 5.0 안드로이드 스튜디오와 메터리얼 디자인 중에서 아닌것을 찾으시오
    /*
    *   2.2 2배에서 5배가 딸라지고 3.0 은 홀로디자인
    *   3.3 테블릿용
    *   4.0 스마트 통합 디바이스 os
    *   5.0 안드로이드 스튜디오와 메테리얼 디자인사용
    *
    *
    *   2, 파일을 양식은 무엇이냐 ? xml..................... /////////////////
    *
    *   3, View 가 이미지 에딧 텍스트뷰 리스트뷰 버튼
    *
    *   4, 가로길이세팅 문자열세팅 이미지셋팅 머 그런 .. 속성
    *
    *   5, 리니어 레이아웃 가중치는 weight  , 그리고 배치 속성 orientation
    *
    *   6, 레이아웃이 머냥!? 방향성 리니어레이아웃
    *                       관계  릴레이티브
    *                          겹침 프레임
    *                         표     테이블
    *                     리스트뷰
    *
    *   7, 엑티비티의 단점보완 프레그먼트
    *
    *   8, 4.4 이하버전과 5.0 이상버전
    *
    *   차이점: 5.0은 Ticker 메세지가 안나온다.
    *           이미지가 흑백이다
    *   안드로이드의 단위 px ,dp [돗] ,xp 가변형으로 변함,
    *
    * */
    public void show_popup(View view){
        PopupMenu popup = new PopupMenu(this,text1);
        //파라메터1 어던 컨텍스트에 띄울까 ?
        //파라메터2 메뉴를 띄울 View
        Menu menu = popup.getMenu();
        //다른것은 매개변수로 넘어왔지만 이것은 추출해주어야한다 팝업메뉴는..

        menu.add(Menu.NONE,Menu.FIRST+1,Menu.NONE,"항목1");
        menu.add(Menu.NONE,Menu.FIRST+2,Menu.NONE,"항목2");

        popup.setOnMenuItemClickListener(new PopupListener());
        popup.show();

        //다른녀석들은 리스너가 기본으로 제공되지만 이건 직접 리스너를 만들어주어야한다.
    }
    class PopupListener implements PopupMenu.OnMenuItemClickListener{

        //호ㅓ환을위헤 v7 Popup Listener 를 선택해준다.
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            int id = menuItem.getItemId();
            if(id == Menu.FIRST+1){
                text1.setText("pop항목1");
            }else if(id==Menu.FIRST+2){
                text1.setText("pop항목2");
            }
            return false;
        }
    }

}

/*
안드로이드의 4대 구성요소

컨텐츠 프로바이더
서비스
브로드 캐스터
액티비티

화면이 없는 실행단위를 서비스라고한다.메모리청소 발생작업이 일어나면 그 때 제거가된다.
서비스가 메모리상에서 제거되면 onDestroy() 메서드가 실행되어서 다시 실행시킨다.

4.0 이하의 버전은 개발자가 직접 GC 처리를 해주어야하는데 안해주고 끝낸다.
그러나 4.1부터는 안드로이드 OS 가 직접 메모리 청소를 시켜주는 기능을 넣었다.

GC를 소멸시키고 싶으면 null값을 넣거나 지역변수는 그냥 내비두면 알아서 소멸시킨다.

브로드캐스트 리시버 : 어떠한 사건이 발생하면 그것에대해 반응하는것.
 껏다 켜도 서비스는 영원히 돌아간다 왠만한 서비스들 은 죄다 이렇게 만들어놓는다. 부팅이 완료되면
서비스를 바로 돌린다.

서비스는 화면이 없기떄문에 화면에 관련된 작업을 할 수 없다.

보통 서비스는 스래드를 돌리는 용도로 사용을한다.

그리고 여기서 IPC 를 같이 볼것이다. 액티비티에서 서비스에서 쓰는 요소를 가져다주고싶다
 액티비티와 서비스는 서로의 영역을 침범할수 없다 그러나 IPC 를 이용해서 서로 상호적인 관계를 만들어 줄 수 있다.

메모리청소 앱 아무~~~~~~~~~~~~~효과 없당. ㅋㄷㅋㄷ ..... ㅅㅂ!?
*/