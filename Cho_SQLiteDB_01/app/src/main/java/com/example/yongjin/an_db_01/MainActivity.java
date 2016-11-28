package com.example.yongjin.an_db_01;
//DB 위치
//안드로이드 디바이스 모니터
// mnt -> shell -> emulator -> 0 ->  [+버튼을 눌러서 SQLiteDB 폴터를 생성한다.

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

//데이터베이스 생성 방법-1(openOrCreateDatabase() 메소드 사용)
public class MainActivity extends ActionBarActivity {

    String dbName, tableName;
    TextView status;
    EditText dbNameInput, tableNameInput;
    Button createDbButton, createTableButton;

    boolean dbCreated = false;
    boolean tableCreate = false;

    SQLiteDatabase db;//SQLiteDatabas


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbNameInput = (EditText) findViewById(R.id.databaseNameInput);
        tableNameInput = (EditText) findViewById(R.id.tableNameInput);
        status = (TextView) findViewById(R.id.status);

        createDbButton = (Button) findViewById(R.id.createDatabaseBtn);

        //데이터베이스 생성 버튼 리스너 등록
        createDbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbName = dbNameInput.getText().toString();
                //데이터베이스 생성 메소드 호출
                createDatabase(dbName);
            }
        });

        createTableButton = (Button) findViewById(R.id.createTableBtn);

        //테이블 생성 버튼 리스너 등록
        createTableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tableName =  tableNameInput.getText().toString();

                //데이블 생성 및 레코드 추가
                createTable(tableName);
                int count = insertRecord(tableName);

                println(count + "Records inserted! ");
            }
        });
    }

    //데이터베이스 생성
    private void createDatabase(String dbName) {
        println("Creating Database : " + dbName);

        //데이터베이스 저장 경로(SD카드 경로) + 데이터베이스 이름
        String databaseName = "/sdcard/drugDB/" + dbName;

        try {
            //데이터베이스 생성(or 오픈)-(DB이름, mode, CursorFactory)
            db = openOrCreateDatabase(databaseName, Activity.MODE_PRIVATE, null);

            dbCreated = true;

            println("Database is created.");
        }catch (Exception e) {
            e.printStackTrace();
            println("Database is not created.");
        }
    }

    //테이블 생성
    private void createTable(String tableName) {
        println("Creating table : " + tableName);
        try {
            if (db != null) {
                //execSQL() 메소드를 이용하여 테이블 생성 sql문을 만들어 실행
                db.execSQL("create table if not exists " + tableName + "("
                        + " _id integer PRIMARY KEY autoincrement, "//내부적으로 사용하는 컬럼(선택사항)
                        + " name text, "
                        + " age integer, "
                        + " phone text);");

                println("Table is created.");

                tableCreate = true;
            } else {
                println("Database is not created.");
            }
        }catch (Exception e) {
            e.printStackTrace();
            println("Table is not created.");
        }
    }

    //레코드 추가
    private int insertRecord(String tableName) {
        int count = 3;

        try {
            if (tableName != null) {
                //execSQL() 메소드를 이용하여 레코드 추가 sql문을 만들어 실행
                db.execSQL("insert into " + tableName + "(name, age, phone) values ('소녀시대', 20, '010-1234-1234');");
                db.execSQL("insert into " + tableName + "(name, age, phone) values ('걸스데이', 22, '010-1111-1111');");
                db.execSQL("insert into " + tableName + "(name, age, phone) values ('빅뱅', 25, '010-2222-2222');");

                println("Inserting records into table : " + tableName);
            }
        }catch (Exception e) {
            e.printStackTrace();
            println("Record is not inserted.");
        }
        return count;
    }

    //진행상태 메시지 append
    private void println(String msg) {
        Log.d("SampleDatabase", msg);
        status.append("\n" + msg);
    }
}
