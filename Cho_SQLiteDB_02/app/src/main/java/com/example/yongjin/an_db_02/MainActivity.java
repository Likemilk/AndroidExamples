package com.example.yongjin.an_db_02;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {
    DBHelper dbHelper;
    EditText productName, productPrice;
    TextView textViewResult;
    Button btnInsert, btnUpdate, btnDelete, btnSelect, btnSelectAll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // SQLite DB를 사용하기 위해 dbHelper 생성
        dbHelper = new DBHelper(this);

        // 레이아웃에 정의한 위젯들을 참조
        productName = (EditText) findViewById(R.id.productName);//제품명
        productPrice = (EditText) findViewById(R.id.productPrice);//가격
        textViewResult = (TextView) findViewById(R.id.result);//결과

        // 입력(Insert) 이벤트 - 입력버튼
        btnInsert = (Button) findViewById(R.id.btn_insert);
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String name = productName.getText().toString();
                    String price = productPrice.getText().toString();

                    /* 테이블에 레코드(행) 추가
                       - 입력한 제품명과 가격을 파라미터로 dbHelper.insert() 메소드 호출
                     */
                    dbHelper.insert(name, price);
                    //추가한 레코드를 화면에 보여주기 위해 dbHelper.printData() 호출
                    textViewResult.setText(dbHelper.printData() );
                }catch (Exception e) {
                    Toast.makeText(getApplication(), "추가할 제품명과 가격을 입력하세요!!",
                            Toast.LENGTH_LONG).show();
                    textViewResult.setText(e+"");
                }
            }
        });//end of Insert

        // 수정(Update) 이벤트 - 수정버튼
        btnUpdate = (Button) findViewById(R.id.btn_update);
        btnUpdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    String name = productName.getText().toString();
                    String price = productPrice.getText().toString();

                    /* 테이블의 레코드를 갱신(컬럼 값 수정)
                       - 입력한 제품명과 가격을 파라미터로 dbHelper.update() 메소드 호출
                     */
                    dbHelper.update(name, price);
                    //수정한 레코드를 화면에 보여주기 위해 dbHelper.printData() 호출
                    textViewResult.setText(dbHelper.printData() );
                }catch (Exception e) {
                    Toast.makeText(getApplication(), "수정할 제품명과 가격을 입력하세요!!",
                            Toast.LENGTH_LONG).show();
                    textViewResult.setText(e+"");
                }
            }
        });//end of update

        // 삭제(Delete) 이벤트 - 삭제버튼
        btnDelete = (Button) findViewById(R.id.btn_delete);
        btnDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String name = productName.getText().toString();

                /* 조건에 맞는 레코드를 삭제
                   - 삭제할 제품명을 입력했는지 check 한 후
                   - 입력한 제품명을 파라미터로 dbHelper.delete() 메소드 호출
                */
                if (name.getBytes().length <= 0) {
                    Toast.makeText(getApplication(), "삭제할 제품명을 입력하세요!!",
                            Toast.LENGTH_LONG).show();
                } else {
                    dbHelper.delete(name);
                    //삭제후 테이븛의 내용을 화면에 보여주기 위해 dbHelper.printData() 호출
                    textViewResult.setText( dbHelper.printData() );
                }
            }
        });//end of delete

        // 검색(Select) 이벤트 - 검색버튼
        btnSelect = (Button) findViewById(R.id.btn_select);
        btnSelect.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String name = productName.getText().toString();

                /* 조건에 맞는 레코드를 검색
                   - 검색할 제품명을 입력했는지 check 한 후
                   - 입력한 제품명을 파라미터로 dbHelper.select() 메소드 호출
                */
                if (name.getBytes().length <= 0) {
                    Toast.makeText(getApplication(), "검색할 제품명을 입력하세요!!",
                            Toast.LENGTH_LONG).show();
                } else {
                    textViewResult.setText(dbHelper.select(name) );
                }
            }
        });//end of select

        // 전체검색(electAll) 이벤트 - 전체검색버튼
        btnSelectAll = (Button) findViewById(R.id.btn_select_all);
        btnSelectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //테이블의 모든 레코드를 검색하기 위해 dbHelper.printData() 메소드 호출
                textViewResult.setText(dbHelper.printData() );
            }
        });//end of selectAll
    }//end of onCreate()
}//end of file
