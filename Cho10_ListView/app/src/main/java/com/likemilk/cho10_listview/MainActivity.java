package com.likemilk.cho10_listview;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
//엑티비티에서 리스트 엑티비티를 상속받아서 쉽게 구현하는방법= 프레그먼트를 상속
// 프레그먼트에 리스트프레그먼트를 상속시켜주면된다.
// not 프레그먼트 ok 리스트프레그먼트
// 엑티비티에서 리스트뷰를 상속받는것과 프레그먼트리스트를 사용하는건 서로 똑같다.
// 그러나 두개가 서로다른것은 식별이 서로 다르다

public class MainActivity extends ActionBarActivity {
    //onClick 은 엑티비티에 정의된것이기때문에 fragment에 적용되지않는다.
    private TextView text1;
    private ListView list1;
    String data[]={
            "항목1","항목2","항목3","항목4","항목5","항목6",
            "항목7","항목8","항목9","항목10","항목11","항목12",
            "항목13","항목4","항목15","항목16","항목17","항목18",
            "항목19","항목20","항목21","항목22","항목23","항목24",
            "항목25","항목26","항목27","항목28","항목29","항목30",
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text1 = (TextView) findViewById(R.id.textView);
        list1 = (ListView) findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data);//한줄짜리 리스트 뷰를 뽑는다.
        list1.setAdapter(adapter);//리스트는 어뎁터를 만들어줘서 넣어주어야한다.
        list1.setOnItemClickListener(new ListListener());
    }
    //리스트뷰의 어뎁터 생성
    class ListListener implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            text1.setText(data[position]);
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
