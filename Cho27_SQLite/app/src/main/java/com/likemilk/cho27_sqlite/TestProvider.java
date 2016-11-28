package com.likemilk.cho27_sqlite;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class TestProvider extends ContentProvider {
    //컨탠트 프로바이더때문에 SQLite 명령문을 직접ㅈ넣는법과 라이브러리를 이용해서 넣는법 직접
    public TestProvider() {
    }


    //데이터베이스 오픈
    public SQLiteDatabase getDatabase(){
        DBHelper helper  = new DBHelper(getContext(), "Test.db", null, 2); //Android Device Monitor -> data -> data -> 패키지명 -> database가 생성되었는지 확인
        //컨택스트가 매개변수가 들어가는부분에 컨택스트를 얻어올수없다면 getContext로 받아올수있다.
        //데이터베이스 매인에서 복사해온것임.
        SQLiteDatabase db = helper.getWritableDatabase(); //getReadableDatabase()도 읽기 쓰기기 된다.
        return db;
    }






    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        //throw new UnsupportedOperationException("Not yet implemented");

        SQLiteDatabase db = getDatabase();
        return db.delete("TestTable",selection,selectionArgs);
        //데이터베이스에서 삭제된게 몇개가 삭제됬는지 숫자를 반환해주는역할을 한다.

    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        //어떤 값에 어떤값을 넣어주세요라고 ContentValues 가 들어온다 단 테이블이름이 들어오지않는다.
        //Content Provider에 어떻게 값이 들어오는가에 따라 다르다. 내부를 어떻게 구현했느냐에 따라 달라진다.
        SQLiteDatabase db = getDatabase();
        db.insert("TestTable",null,values);


        return uri;// 그냥 리턴만 해주면된다 . 명령어만수행하고 반환형이 URI 길래 상관없다는 식으로 uri 를 그냥 반환해버렷다.
    }

    @Override
    public boolean onCreate() {
        //컨텐트 프로바이더가 호출되면 무조건 호출되는 메서드이다.
        // TODO: Implement this to initialize your content provider on startup.
        return false;

    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        //이것은 select 문이다. 쿼리문을 제공하는라이브러리를 하는법과 String 으로 쿼리문을 날리는법이있다.
        //나는 이 select나 insert 를 요청을할대 내가 내가 이 Cursor query를 안써도 쿼리제공라이브러리를 사용하거나 직접 String 으로 넣어서 빼는 방법을 사용하면된다
        //여기에는 group by 나 having 절이 들어오지않는다 . 그것들을 세팅할 수 없다. group by 나 having 절에..
        // TODO: Implement this to handle query requests from clients.
        SQLiteDatabase db = getDatabase();
        Cursor c = db.query("TestTable", projection,selection,selectionArgs,null,null,sortOrder);
        //첫번재는 테이블의 이름
        //
        //세번재는 셀렉션을 사용
        //4번쨰 셀랙션이용
        //5번째 그룹바이 ㄴㄴ
        //6번째 해빙 ㄴㄴ


        return c; //커서를 리턴해준다 //커서를 반환하고 디비를 닫으면안된다 그러면 cursor 도 같이 닫힌다.
        //컨탠트 프로바이더의 수행이 다 끝나면 알아서 자동적으로 닫히니까 강제로 닫지 않는다.
        //
    }
    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        // 이것은 반드시 구현해주어야하기때문에 implements 로 되어있다. abstract 추상메서드이다.
        //throw new UnsupportedOperationException("Not yet implemented");
        //만약이것을 삭제안하면 강제로 예외를 발생시킨다. 꼭 지워준다.

        SQLiteDatabase db = getDatabase();
        return db.update("TestTable",values, selection,selectionArgs);
        //변형된 값을 리턴시켜준다.
    }
}
