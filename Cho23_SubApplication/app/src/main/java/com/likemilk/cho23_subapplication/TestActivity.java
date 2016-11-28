package com.likemilk.cho23_subapplication;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class TestActivity extends ActionBarActivity {
    TextView text1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        text1 = (TextView) findViewById(R.id.textView);

        Intent intent = getIntent();
        //intent 추출하기
        int data1 = intent.getIntExtra("data1",0);
        double data2 = intent.getDoubleExtra("data2",0.0);
        String data3 = intent.getStringExtra("data3");
        //데이터 추출하기

        text1.setText("data1" + data1 + "\n");
        text1.append("data2" + data2 + "\n");
        text1.append("data3"+data3+"\n");

        //추력하기
    }
    public void finishActivity(View view){
        Intent intent= new Intent();
        intent.putExtra("value1",500);
        intent.putExtra("value2",55.55);
        intent.putExtra("value3","반갑습니다");
        setResult(RESULT_OK,intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_test, menu);
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
