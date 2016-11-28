package com.example.yongjin.an_db_04;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;


        public class MainActivity extends ActionBarActivity {

            private DBHelper dbHelper;
            private EditText inputName, inputAge, inputPhone;
            private TextView textViewStatus;
            private Button btnInsert, btnUpdate, btnDelete, btnSelect, btnSelectAll;
            private ListView listView;
            private Cursor cursor;
            private SimpleCursorAdapter mAdapter;
            private Context context = this;

    //SimpleCursorAdapter 객체 생성시 사용할 columns(표시할 컬럼 배열)
    private String[] columns;
    //SimpleCursorAdapter 객체 생성시 사용할 to(컬럼들을 표시할 텍스트 뷰 배열)
    private int[] to;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // SQLite DB를 사용하기 위해 dbHelper 생성
        dbHelper = new DBHelper(this);

        // 레이아웃에 정의한 위젯들을 참조
        inputName = (EditText) findViewById(R.id.studentName);//이름
        inputAge = (EditText) findViewById(R.id.studentAge);//나이
        inputPhone = (EditText) findViewById(R.id.studentPhone);//전화
        listView = (ListView) findViewById(R.id.listView);//리스트 뷰

        /* -----------------------------------------------------------
                    데이터베이스 입력, 수정, 삭제, 검색
           ----------------------------------------------------------- */

        // 입력(Insert) 이벤트 - 입력버튼
        btnInsert = (Button) findViewById(R.id.btn_insert);
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    String name = inputName.getText().toString();
                    String age = inputAge.getText().toString();
                    String phone = inputPhone.getText().toString();

                    /* 테이블에 레코드(행) 추가
                       - 입력한 값을 파라미터로 dbHelper.insert() 메소드 호출
                    */
                    dbHelper.insert(name, age, phone);

                    //테이블의 모든 레코드를 쿼리하기 위해 dbHelper.CursorQuery() 호출
                    cursor = dbHelper.CursorQuery();

                    // SimpleCursorAdapter 생성 및 리스트뷰에 어댑터 등록
                    listViewSetAdapter(cursor);

                }catch (Exception e) {
                    Toast.makeText(getApplication(), "추가할 이름, 나이, 전화번호를 확인하세요!!" + e,
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        // 수정(Update) 이벤트 - 수정버튼
        btnUpdate = (Button) findViewById(R.id.btn_update);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String name = inputName.getText().toString();
                    String age = inputAge.getText().toString();
                    String phone = inputPhone.getText().toString();

                    /* 테이블의 레코드를 갱신(컬럼 값 수정)
                       - 입력한 제품명과 가격을 파라미터로 dbHelper.update() 메소드 호출
                     */
                    dbHelper.update(name, age, phone);

                    //테이블의 모든 레코드를 쿼리하기 위해 dbHelper.CursorQuery() 호출
                    cursor = dbHelper.CursorQuery();

                    // SimpleCursorAdapter 생성 및 리스트뷰에 어댑터 등록
                    listViewSetAdapter(cursor);

                }catch (Exception e) {
                    Toast.makeText(getApplication(), "수정할 이름, 나이, 전화번호를 확인하세요!!!!" + e,
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        // 삭제(Delete) 이벤트 - 삭제버튼
        btnDelete = (Button) findViewById(R.id.btn_delete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = inputName.getText().toString();

                /* 조건에 맞는 레코드를 삭제
                   - 삭제할 제품명을 입력했는지 check 한 후
                   - 입력한 제품명을 파라미터로 dbHelper.delete() 메소드 호출
                */
                if (name.getBytes().length <= 0) {
                    Toast.makeText(getApplication(), "삭제할 이름을 입력하세요!!",
                            Toast.LENGTH_LONG).show();
                } else {
                    dbHelper.delete(name);

                    //테이블의 모든 레코드를 쿼리하기 위해 dbHelper.CursorQuery() 호출
                    cursor = dbHelper.CursorQuery();

                    // SimpleCursorAdapter 생성 및 리스트뷰에 어댑터 등록
                    listViewSetAdapter(cursor);

                }
            }
        });

        // 검색(Select) 이벤트 - 검색버튼
        btnSelect = (Button) findViewById(R.id.btn_select);
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = inputName.getText().toString();

                /* 조건에 맞는 레코드를 검색
                   - 검색할 제품명을 입력했는지 check 한 후
                   - 입력한 제품명을 파라미터로 dbHelper.select() 메소드 호출
                */
                if (name.getBytes().length <= 0) {
                    Toast.makeText(getApplication(), "검색할 이름을 입력하세요!!",
                            Toast.LENGTH_LONG).show();
                } else {
                    //테이블에서 조건에 맞는 레코드를 쿼리하기 위해 dbHelper.CursorQuery() 호출
                    cursor = dbHelper.select(name);

                    // SimpleCursorAdapter 생성 및 리스트뷰에 어댑터 등록
                    listViewSetAdapter(cursor);
                }
            }
        });

        // 전체검색(Select) 이벤트 - 전체검색버튼
        btnSelectAll = (Button) findViewById(R.id.btn_select_all);
        btnSelectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //테이블의 모든 레코드를 쿼리하기 위해 dbHelper.CursorQuery() 호출
                cursor = dbHelper.CursorQuery();

                // SimpleCursorAdapter 생성 및 리스트뷰에 어댑터 등록
                listViewSetAdapter(cursor);
            }
        });
    }

    //SimpleCursorAdapter 객체 생성 및 리스트뷰에 어댑터 설정
    public void listViewSetAdapter(Cursor cursor) {
        // 커서의 관리를 액티비티의 수명주기에 맞추어 관리하도록 지정
        startManagingCursor(cursor);

        //SimpleCursorAdapter 객체 생성시 사용할 columns(표시할 컬럼 배열)
        columns = new String[] {"name", "age", "phone"};

        //SimpleCursorAdapter 객체 생성시 사용할 to(컬럼들을 표시할 텍스트 뷰 배열)
        to = new int[] { R.id.name_entry, R.id.age_entry, R.id.phone_entry };

        /* SimpleCursorAdapter 객체는 데이터베이스와 화면을 연결하는 객체로
           데이터베이스에서 데이터를 읽어서 정해진 레이아웃으로 화면에 표시
           - SimpleCursorAdapter(Context context, int layout, Cursor cursor,
                                 String[] from, int[] to)
             . context: context
             . layout: 데이터를 표시할 레이아웃 리소스
             . cursor: 커서 객체
             . from: 화면에 표시할 컬럼이름(문자열 배열)
             . to: 화면에 표시할 from 안의 컬럼이 표시되는 뷰 리스트(텍스트 뷰)
         */
        mAdapter = new SimpleCursorAdapter(context,R.layout.listitem, cursor, columns, to);

        //리스트뷰에 어댑터 설정
        listView.setAdapter(mAdapter);


        /* ------------------------------------------------
                    리스트뷰 친구 아이템 클릭시 전화 걸기
           ------------------------------------------------ */

        //리스트뷰에 setOnItemClickListener 등록
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // position을 이용하여 선택한 아이템을 가져와서 커서 객체 item 에 저장
                Cursor item = (Cursor) mAdapter.getItem(position);

                //item에서 전화번호를 가져와서 number에 저장(item.getString(컬럼 index)
                String number = item.getString(3);

                //커서의 컬럼에 해당하는 데이터를 가져옴
                //String number = item.getString(item.getColumnIndex("phone"));

                Toast.makeText(getApplication(), "phoneNo : " + number,
                        Toast.LENGTH_LONG).show();

                //전화번호 Uri 생성
                Uri phoneNo = Uri.parse("tel:" + number);

                //전화걸기 인텐트 생성
                Intent intent = new Intent(Intent.ACTION_CALL, phoneNo);
                //전화걸기
                startActivity(intent);

            }
        });
    }
}
