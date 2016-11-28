package com.likemilk.cho27_sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

/*
안드로이드에서 포함하고 있는 것은 SQLite 라는 데이터베이스로 작은 임베디드 데이터베이스로 개발된 경량급 관계형 데이터베이스 이다.
표준 SQL문 다 사용가능하다. 내부에 database 가 설치되어 들아가있다. ex 파이어폭스가 사용하고있다. 작은 경량의 데이터베이스~
이것은 database 는 안드로이드 os가 가지고 있다. 안드로이드 os가 직접 사용한다. 원래는 c언어의 라이브러리로 되어있다.
그러나 안드로이는 따로 라이브러리로 만들어져있다. c로 되어있는것을 java 언어로 사용해도된다. 안드로이드 에서 제공하고있는 sql lite 를 이용하면된다.
android studio 에서 제공하는것이 아니다. r그리고 대부분의 모바일이 이 데이터베이스 sqllite 를 채택하고 있다.



public abstract void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion);
1

2 번째 올드버전을 땡겨와봤더니 1번. 지금 만들어져있는 버전은 g 이다. 2번째 매개변수는 최신에 맞춰서 최신의 버전에 맞춰주기위해
각 버전별로 분기해서 각 역할을 나눠야한다.  그 이유는
나는 버전1부터 15 까지 만들었다 그러나 어떤사용자는 버전 3일대 다운을 받고 동기화도 꺼놓고 전혀 사용하지않았다.
그다음 시작을 하게될경우 onUpgrade 메서드를 이용해서 3->4 의 변경사항적용 후 4->5일대 변경사항 적용후 5->6 변경사항적용 ...........15번까지 다 다운을 받아야한다
그렇기때문에 버전별로 관리를  할수있게 되어야하기때문에 각 버전별로 분기를 해서 사용해야한다.

3

*/

public class MainActivity extends ActionBarActivity {
    TextView text1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text1 = (TextView)findViewById(R.id.textView);
    }

    //데이터베이스 오픈
    public SQLiteDatabase getDatabase(){
        DBHelper helper  = new DBHelper(this, "Test.db", null, 1); //Android Device Monitor -> data -> data -> 패키지명 -> database가 생성되었는지 확인

        //데이터베이스 오픈
        SQLiteDatabase db = helper.getWritableDatabase(); //getReadableDatabase()도 읽기 쓰기기 된다.
        return db;
    }
    public void insert_data(View view){
        SQLiteDatabase db = getDatabase();

        //날짜 데이터를 문자열로 준비한다.
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()); //Locale.getDefault() ; 해당 국가 시간에 맞춘다.
        String date = format.format(new Date()); //SQLite의 date값은 실제로 문자열을 저장하는 것이다.

        ContentValues cv1 = new ContentValues();
        cv1.put("textData", "문자열10");
        cv1.put("intData", 1000);
        cv1.put("floatData", 55.55);
        cv1.put("dateData", date);

        ContentValues cv2 = new ContentValues();
        cv2.put("textData", "문자열20");
        cv2.put("intData", 2000);
        cv2.put("floatData", 66.66);
        cv2.put("dateData", date);

        db.insert("TestTable", null, cv1);
        db.insert("TestTable", null, cv2);

        /*
        직접 쿼리를 작성하고 쿼리를 실행한다.

        //쿼리문 작성
        String sql = "insert into TestTable(textData, intData, floatData, dateData) values (?, ?, ?, ?)";  //값이 들어가는 부분은 ?로 처리하고 binding한다.

        //바인딩될 값 (컬럼의 타입에 관계없이 문자열로 준비하면 된다.)
        String[] arg1 = {"문자열1", "100", "11.11", date}; //?의 순서에 맞춘다.
        String[] arg2 = {"문자열2", "200", "22.22", date};

        //문자열 이어붙일 때는 StringBuffer를 사용해서 메모리를 절약한다.

        //쿼리 실행
        db.execSQL(sql, arg1);
        db.execSQL(sql, arg2);
        */

        db.close();

        text1.setText("insert 완료");
    }

    public void select_data(View view){
        //database를 안정성을 위해서 필요한 데이터를 collection에 담고 close를 한 이후에 원하는 작업을 한다.

        SQLiteDatabase db = getDatabase();

        //db.query() 매개변수의 순서
        //테이블 이름, 가져올 테이블 목록(배열), 조건절, 조건걸 ?에 바인될 값, group by, having, order by
        Cursor c = db.query("TestTable", null, null, null, null, null, null);

        /*
        String sql = "select * from TestTable"; // * 를 쓰면 컬럼 순서대로 가져온다.

        //쿼리 실행
        Cursor c = db.rawQuery(sql, null); //현재 커서 객체는 아무것도 가리키지 않는다. next()를 써서 다음 데이터를 가리킨다. 데이터가 하나만 있어도 next() 사용
        */


        text1.setText("");

        //끝까지 반복한다.
        while(c.moveToNext()){
            //각 컬럼의 index 번호 추출
            int idx_pos = c.getColumnIndex("idx");
            int textData_pos = c.getColumnIndex("textData");
            int intData_pos = c.getColumnIndex("intData");
            int floatData_pos = c.getColumnIndex("floatData");
            int dateData_pos = c.getColumnIndex("dateData");

            //데이터 추출
            int idx = c.getInt(idx_pos);
            String textData = c.getString(textData_pos);
            int intData = c.getInt(intData_pos);
            double floatData = c.getDouble(floatData_pos);
            String dateData = c.getString(dateData_pos);

            try{
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                Date date = (Date)format.parse(dateData);
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                dateData = cal.get(Calendar.YEAR) + "년 " +  (cal.get(Calendar.MONTH) + 1) + "월 " + cal.get(Calendar.DAY_OF_MONTH) + "일";
            }catch(Exception e){
                e.printStackTrace();
            }
            text1.append("idx : " + idx + "\n");
            text1.append("textData : " + textData + "\n");
            text1.append("intData : " + intData + "\n");
            text1.append("floatData : " + floatData + "\n");
            text1.append("dateData : " + dateData + "\n\n");

        }
        db.close();
    }

    public void update_data(View view){
        SQLiteDatabase db = getDatabase();

        ContentValues cv = new ContentValues();
        cv.put("textData", "업뎃완료");
        String where = "idx = ?";
        String[] arg = {""};

        db.update("TestTable", cv, null, null);

        /*
        String sql = "update TestTable set textData = ? where idx = ?";
        String[] arg = {"문자열3", "1"};

        db.execSQL(sql, arg);
        */

        db.close();
        text1.setText("update 완료");
    }

    public void delete_data(View view){
        SQLiteDatabase db = getDatabase();

        /*
        String sql = "delete from TestTable where idx = ?"; //한 쿼리가 실행되는 동안 다른 쿼리는 실행되지 않는다.
        String[] arg = {"1"};
        db.execSQL(sql, arg);
        */

        String where = null;
        String[] arg =null;
        db.delete("TestTable",where, arg);

        db.close();
        text1.setText("delete 완료");
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
컨텐츠 프로바이더는 다른어플이 데이터를 받아 사용하고 싶을때 사용할 수 있게 만드는것 무조건 이름을 사용하여야한다
매니페스트에 퍼미션이 추가 되어있어야합니다.

new -> other -> contentProvider
URI authurity 가 이것이 이름이다 com.test.provider 라고 지정해주었다.
나머지는 체크 ok ok ~!  a매니패스트를 보면 provider 라는 태그가 추가되었다.

*/