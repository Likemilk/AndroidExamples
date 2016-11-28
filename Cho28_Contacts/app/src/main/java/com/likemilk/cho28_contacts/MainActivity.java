package com.likemilk.cho28_contacts;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends ActionBarActivity {
    ListView list;
    ArrayList<HashMap<String,String>> data_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = (ListView)findViewById(R.id.listView);
        data_list = new ArrayList<HashMap<String,String>>();

        ContentResolver resolver = getContentResolver();
        //개인정보를 provider 를 이용해 모든정보를 가지고 온다.
        Cursor c1 = resolver.query(ContactsContract.Contacts.CONTENT_URI,null,null,null,null);
        //널값을 넣는이유는 조건따지지않고 일단 데이터를 다 불러모으기 위해서이다.

        while(c1.moveToNext()){
            int name_pos = c1.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
            int id_pos = c1.getColumnIndex(ContactsContract.Contacts._ID);//개인정보테이블의 아이디값
            int chk_pos = c1.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER);

            String name = c1.getString(name_pos);
            long id = c1.getLong(id_pos);
            int chk = c1.getInt(chk_pos);

            if(chk==1){ //하나라도 숫자가 저장되어있으면 1이 들어간다.
                //String where = ContactsContract.CommonDataKinds.Phone._ID+"=?"; //이게 실제 컬럼의 아이디이 가아니고 시퀀서 ID 의 이름이다.
                String where = ContactsContract.CommonDataKinds.Phone.CONTACT_ID+"=?"; //이거다. 그리고 오른쪽에 = 표시는 자기가 수정해도된다.

                String []arg = {id+""};

                Cursor c2 = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,where,arg,null);
                                                                                           //여기다가 널값 넣는이유는 데이터를 가리지말고 일단 많이 불러오게하려고
                while(c2.moveToNext()){
                    //전화번호
                    int number_pos=c2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                    String number = c2.getString(number_pos);

                    HashMap<String,String> map = new HashMap<String,String>();

                    map.put("name",name);
                    map.put("number",number);

                    data_list.add(map);
                }
            }
        }
        String key[] = {"name","number"};
        int id[] = {android.R.id.text1,android.R.id.text2};      //지금부터 사용할 것들이 text1 , text2 에 정의되어져있다.
        SimpleAdapter adapter = new SimpleAdapter(this,data_list,android.R.layout.simple_list_item_2,key,id);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new ListListener());
    }

    class ListListener implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            HashMap<String, String > map = data_list.get(position);//우선 데이터를 뽑아보자
            String number = map.get("number");
            //전화를 건다
            Uri uri = Uri.parse("tel:" + number);
            //Intent intent = new Intent(Intent.ACTION_DIAL,uri);
            Intent intent = new Intent(Intent.ACTION_CALL,uri);
            startActivity(intent);
        }
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
