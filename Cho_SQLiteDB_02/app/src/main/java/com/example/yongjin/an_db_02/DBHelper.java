package com.example.yongjin.an_db_02;
/*
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DBHelper extends SQLiteOpenHelper{
    Context context;
    SQLiteDatabase db;
    Cursor cursor;

    private static final String DATABASE_NAME="/sdcard/SQLiteDB/productDB.db";
    private static final int DATABASE_VERSION=1;
    private static final String TABLE_NAME = "productTable";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //데이터베이스가 없거나 데이터베이스가 생성이 안될때 이녀석이 생성된다
        db=getWritableDatabase();
        String sql = "create table "+TABLE_NAME+"("
                        +"_id integer primary key autoincreament,"
                        +" productName text, price Integer"
                        +" ); ";
        db.execSQL(sql);


        Toast.makeText(context,"DBHelper 의 oncreate 호출",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Toast.makeText(context,"DBHelper 의 onUpgrade 호출", Toast.LENGTH_SHORT).show();
        //버전이 다르면
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public void insert(String name, String price){
        db=getWritableDatabase();
        db.execSQL("insert into "+TABLE_NAME+" values(null,'"+name+"',"+price+");");
        db.close();
    }
    public void update(String name, String price){
        db=getWritableDatabase();
        db.execSQL("update "+TABLE_NAME+" set price="+price+" where productName like '"+name+"');");
        db.close();
    }
    public void delete(String name){
        db=getWritableDatabase();
        db.execSQL("delete from "+TABLE_NAME+" where productName like '%"+name+"%');");
        db.close();
    }
    public String select(String name){
        db=getReadableDatabase();
        String str="";

        cursor = db.rawQuery("select _id, productName from "+TABLE_NAME+" where productName like '%"+name+"%');",null);

        while(cursor.moveToNext()){
            str+=cursor.getInt(0)+
                    "\n]재품양:"+cursor.getString(1)+
                    "\n가격:"+cursor.getInt(2)+
                    "\n";
        }
        db.close();
        return str;
    }
    public String printData(){
        db=getReadableDatabase();
        String str="";
        cursor = db.rawQuery("select * from "+TABLE_NAME+";",null);

        while(cursor.moveToNext()){
            str+=cursor.getInt(0)+
                    "\n]재품양:"+cursor.getString(1)+
                    "\n가격:"+cursor.getInt(2)+
                    "\n";
        }
        db.close();
        return str;
    }
}


*/


        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;
        import android.widget.Toast;

/**
 * Created by samsungpc on 2015-06-01.
 */

/*
    SQLite 사용할 수 있도록 SQLiteOpenHealer 클래스를 상속받아 DBHelper 생성
 */
public class DBHelper extends SQLiteOpenHelper{

    Context context;
    SQLiteDatabase db;
    Cursor cursor;

    //DATABASE name
    private static final String DATABASE_NAME = "/sdcard/SQLiteDB/productDB.db";
    //DATABASE version
    private static  final int DATABASE_VERSION = 1;
    //TABLE name
    private static final String TABLE_NAME = "productTable";

    //DBHelper 생성자(Context, DB name, cursor, DB version)
    public DBHelper(Context context) {
        //데이터베이스 이름과 버젼 정보를 이용하여 상위 생성자를 호출
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    /* 데이터베이스 Create
        -생성자에서 넘겨받은 이름의 DB와 버젼의 DB가 존재하지 않을 때 호출됨
        -새로운 데이터베이스를 생성할 때 사용
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        /*
            테이블을 생성하기 위해 SQL문을 작성하여 execSQL문 실행
            - CREATE TABLE 테이블명 (컬럼명 타입 옵션);
         */
        db.execSQL("CREATE TABLE " + TABLE_NAME + "( _id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + " productName TEXT, price INTEGER);");
        Toast.makeText(context, "onCreate() 메소드 호출", Toast.LENGTH_LONG).show();
    }

    /* 데이터베이스 Version Upgrade
        -DB가 존재하지만 버젼이 다르면 호출됨
        -DB를 변경하고, 버젼을 변경할 때 여러가지 업그레이드 작업 수행 가능
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Toast.makeText(context, "onUpgrade() 메소드 호출", Toast.LENGTH_LONG).show();
        /*테이블을 업그레이드 하기 위해 SQL문을 작성하여 execSQL문 실행
            -DROP TABLE IF EXISTS 테이블 명;
            -기존 테이블을 삭제한 후 테이블 재 생성
         */
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //테이블의 레코드(행) insert
    public void insert(String productName, String price) {
        db = getWritableDatabase();     //DB를 read/write모드로 open

        /* 래코드를 추가하기 위해 SQL문을 작성하여 execSQL문 실행
            -INSERT INTO 테이블 명 VALUES(컬럼값, 컬럼값 ,,,,, 컬럼명, 컬럼명...);
            -테이블에 레코드를 추가할 때 사용
         */
        db.execSQL("INSERT INTO " + TABLE_NAME + " VALUES(null, '" + productName
                + "', " + price + ");");
        db.close();     //DB close
    }

    //테이블의 레코드 Update
    public void update(String productName, String price) {
        db = getWritableDatabase();     //DB를 read/write모드로 open

        /* 레코드를 갱신하기 위해 SQL문을 작성하여 execSQL문 실행
            -UPDATE 테이블명 SET 변경할 컬럼 WHERE 조건;
            -조건에 맞는 레코드를 갱신할 때 사용
         */
        db.execSQL("UPDATE " + TABLE_NAME + " SET price = " + price + " WHERE productName = '"
                + productName + "';");
        db.close();
    }

    //테이블의 레코드 Delete
    public void delete(String productName) {
        db = getWritableDatabase();     //DB를 write/read모드로 open

        /* 레코드를 삭제하기 위해 SQL문을 작성하여 execSQL문 실행
            -DELETE FROM 테이블명 WHERE 조건;
            -조건에 맞는 레코드를 삭제할 때 사용
         */
        db.execSQL("DELETE FROM " + TABLE_NAME  + " WHERE productName = '" + productName + "';");
        db.close();
    }

    //조건에 맞는 레코드 Select
    public String select(String productName) {
        db = getReadableDatabase();     //DB를 read 전용 모드로 open
        String str = "";

        /* 레코드를 검색하기 위해 SELECT 문을 작성하여 rawQuery문 실행
            -SELECT 검색할 컬럼 FROM 테이블명 WHERE 조건;
            -조건에 맞는 레코드를 검색할 때 사용
            -테이블에서 조건에 맞는 레코드를 검색하여 cursor객체에 저장
            (rawQuery()는 검색 결과를 cursor객체에 반환)
         */
        cursor = db.rawQuery("SELECT _id, productName, price FROM " + TABLE_NAME
                + " WHERE productName LIKE '"
                + productName + "%'", null);

        /*반복문을 사용하여 cursor객체에 있는 레코드의 컬럼값을 추출
            -moveToNext()는 커서를 다음 레코드로 이동시키는 메소드로, 만약 레코드가 없으면 false반환
            -레코드의 컬럼값을 추출할 때는 컬럼의 타입에 따라 getInt(컬럼index), getString(컬럼index)
            등의 메소드 사용
         */

        while(cursor.moveToNext()) {
            str += cursor.getInt(0)     //id
                    + " : 제품명: "
                    + cursor.getString(1)   //productName
                    + ", 가격: "
                    + cursor.getInt(2)      //price
                    + "\n";
        }
        cursor.close();
        return str;
    }

    //테이블에 있는 전체 레코드 Select - 전체검색
    public String printData() {
        db = getReadableDatabase();
        String str = "";

        //SELECT * FROM 테이블명 null'
        cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        while(cursor.moveToNext()) {
            str += cursor.getInt(0)     //id
                    + " : 제품명: "
                    + cursor.getString(1)   //productName
                    + ", 가격: "
                    + cursor.getInt(2)      //price
                    + "\n";
        }
        cursor.close();
        return str;
    }
}