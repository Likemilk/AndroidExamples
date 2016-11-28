package com.likemilk.cho27_contentprovider;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class MainActivity extends ActionBarActivity {
    TextView text1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text1=(TextView) findViewById(R.id.textView);
    }
    public void insert_data(View view){
        ContentResolver resolver = getContentResolver();
        Uri uri = Uri.parse("content://com.test.provider");

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM--dd",Locale.getDefault());
        String date = format.format(new Date());

        ContentValues c1 = new ContentValues();
        c1.put("textData","문자열1000");
        c1.put("intData",10000);
        c1.put("floatData",88.88);
        c1.put("dateData",date);



        ContentValues c2 = new ContentValues();
        c2.put("textData","문자열200");
        c2.put("intData",20000);
        c2.put("floatData",99.33);
        c2.put("dateData",date);

        resolver.insert(uri,c1);
        resolver.insert(uri,c2);

        text1.setText("insert 완료");
    }
    public void select_data(View view){
        ContentResolver resolver = getContentResolver();
        Uri uri = Uri.parse("content://com.test.provider"); // Cho27_SQLite 에 선언되어있는 ContentProvider 를 불러오기위해 선언한것이다.
/*
        *
             public final Cursor query(Uri uri, String[] projection,String selection, String[] selectionArgs, String sortOrder) {
                return query(uri, projection, selection, selectionArgs, sortOrder, null);
              }
        * */
        Cursor c = resolver.query(uri,null,null,null,null);  //이부분이 컨탠트 프로바이더가 해주는 역할이다.
                //컨탠트 프로바이더에 접속하기 위한 uri
                //테이블명 컬럼 이름 배열
                //조건절
                // 조건절 값의 배열
                // order by
                //해빙이나 그룹바이가 들어가는매개변수가 없다.
        text1.setText("");
        while(c.moveToNext()){
            //컬럼이름을 모르면 값을 못불러옵니다 ,
            int idx_pos = c.getColumnIndex("idx");
            int textData_pos = c.getColumnIndex("textData");
            int intData_pos = c.getColumnIndex("intData");
            int floatData_pos = c.getColumnIndex("floatData");
            int dateData_pos = c.getColumnIndex("dateData");
            //칼럼의 인덱스값들을 얻어와서
            //크 칼럼에서 데이터를 원하는 형식으로 데이터를 뽑아내야한다.
            //c. moveToNext! 커서를 이동시킨다!
            int idx = c.getInt(idx_pos);
            String textData = c.getString(textData_pos);
            int intData = c.getInt(intData_pos);
            double floatData = c.getDouble(floatData_pos);

            String dateData = c.getString(dateData_pos);

            try {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                // 얻어온 날짜를 format 에 찾춰서!
                Date date = (Date) format.parse(dateData);
                //java.utill.date 를 선택한다.
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                dateData= cal.get(Calendar.YEAR)+"년" + cal.get(Calendar.MONTH)+"월"+cal.get(Calendar.DAY_OF_MONTH)+"일";


            }catch(Exception e){
                e.printStackTrace();
            }
            text1.append("idx :"+idx+"\n");
            text1.append("textData :"+textData+"\n");
            text1.append("intData :"+intData+"\n");
            text1.append("floatData :"+floatData+"\n");
            text1.append("dateData :"+dateData+"\n\n");
        }

    }
    public void update_data(View view){
        ContentResolver resolver = getContentResolver();
        Uri uri = Uri.parse("content://com.test.provider");
        ContentValues cv = new ContentValues();
        cv.put("textData","문자열5000");
        String where = "idx=?";
        String arg[] ={"8"};
        resolver.update(uri,cv,where,arg);


        text1.setText("update가 완료되었습니다.");
    }
    public void delete_data(View view){
        ContentResolver resolver = getContentResolver();
        Uri uri = Uri.parse("content://com.test.provider");
        String where ="idx=?";
        String []arg = {"8"};
        resolver.delete(uri,where,arg);
        text1.setText("delete가 완료 되었습니다.");
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
미디어파일에대해 또는 연락처에대해 ContentProvider 를 이용하여 얻어올수있다.
여러분이 만들어주는  연락처정보에 이메일을 저장하는앱을 만들겠다. 만들지마라

명함을 사진으로 찍으면 이름, 이메일,주소,전화번호,사진을 를 자동으로 전화번호에 저장을 할 수 있게 하는거
그리고 그 명함을 사용자의 신원정보나 신뢰성을위해 즉  함부로 명함을 사용하지못하도록 하기위한거.
나중에 크게는 서버에다 데이터를 저장하여 그 명함에대한 정보를 제공하기위한역할로 사용한다.


플래그 값에 저장이되는거
어떤 값들이 들어가면 o 안들어가면x 이것이 flag 이다. 이것을 적용하고 나서 쿼리검색의 속도가 상당히 빨라졌다.
------------------------------------------
ㅇ|  ㅇㅇㅇ  |       ㅇㅇ | ㅇㅇㅇㅇㅇ|x
==========================================
이렇게 되어있는대 튜플에 값이 있나 없나의 여부로 파악할 수 있다.

컨텐트 프로바이더 안에다가 어떤코드를 기술하겠다고 작성이 되어있다

아이디를 뽑아내는이유는 id 값을 뽑아내고 이름뽑아내고 플래그값을 뽑아낼것이다.
플래그값이 1 인경우에만 (없으면 0 으로된다,)
안드로이드 os 가 연락처를 관리하기위한 테이블의 구조가 이렇게되어있다
테이블 과 플래그 이렇게 플래그는 튜플당 값이존재하면 1 없으면 0

보통 ContentProvider 접근을 한다. 이걸로 보통 미디어검색을 하는데 사용하기도한다.
GCM 할때 단말기가 구분될 수 있는 값을 넘겨준다.
어떤단말에 어떤어플에 값을 보내겠다.

구글이 단말기의 고유정보를 얻어올 수 있다.
3시 10분부터 연락처를

*/