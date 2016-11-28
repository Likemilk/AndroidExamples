package com.likemilk.cho27_sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by skplanet on 2015-02-10.
 */
public class DBHelper extends SQLiteOpenHelper{

    public DBHelper(Context context, String db_name,SQLiteDatabase.CursorFactory factory,int version){
        super(context,db_name,factory,version);
        //꼭 이렇게 써야한다. 인식할수 있게끔. 생성자는 오버라이딩이 아님.

    }



    @Override
    public void onCreate(SQLiteDatabase db) {
    //onCreate에서는테이블을 잡아주는 쿼리문을 보내주면된다. 최신버전의 쿼리문을 만들어 주면된다.
    //데이터 베이스파일이 만들어지면최초에 딱 한번만 호출한다. , 최신 구조의 테이블을 만들어줘야한다.
        //if not exist // sql 테이블이 없다면 만들어라 ㅋㅋ이건똑같네
                 //autoincrement 시퀀스라고 하며 1부터 자연스럽게 2 3 4 5 순차적으로 증가한다 즉 유일성을 유지하기위해.. 이것은 id 값이다.
                                                                //증가된 값이 자동으로 적용되게끔한다. 이 시퀀스라고하는건 sql 툴마다 다르다.




        String sql = "create table TestTable ("
                +"idx integer primary key autoincrement not null,"
                +"textData text not null,"
                +"intData integer not null,"
                +"floatData real not null,"
                +"dateData date not null)";


        db.execSQL(sql);  //두개들어가는것은 내일 오늘은 하나짜리만 써보자~

        //sqlite 는 [] 표시로 하지않는다. 그저 전부다 가변형이다

        //text,varchar 문자열
        //smallint 는 2바이트 integer 는 4바이트
        //real, float,double 부동소수 4바이트 또는 8바이트
        //blob,binary 2진데이터 안씀 바이너리를 뜻함 직접 이진데이터를 사용하는 일은 하지않는다.
        // 파일로 저장하거나 그렇게 해서 사용하는거 말고는말이다.
        //sqlite 에서 잘 사용하진않지만 지원은 한다.

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    // 옛날버전에 대한 최신화를 위한 명령어를 작성하면된다.
    //

    }
}
