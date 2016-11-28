package com.likemilk.cho25_httpnetwork;

import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;


public class InputActivity extends ActionBarActivity {
    EditText edit1,edit2;
    DisplayHandler handler;
    ProgressDialog pro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        edit1 = (EditText)findViewById(R.id.editText);
        edit2 = (EditText)findViewById(R.id.editText2);

        handler = new DisplayHandler();


    }
    public void send_btn(View view){
        NetThread t = new NetThread();
        t.start();
        pro = ProgressDialog.show(this,null,"서버로 데이터를 전달중입니다.");
    }

    class NetThread extends Thread{
        @Override
        public void run() {
            try{
                String str_data = edit1.getText().toString();
                String int_data = edit2.getText().toString();

                str_data = URLEncoder.encode(str_data,"UTF-8");
                int_data = URLEncoder.encode(int_data,"UTF-8");

                String param = "str_data="+str_data+"&int_data="+int_data;

                URL url = new URL("http://www.softcampus.co.kr:8080/data_in.jsp");
                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                //post 방식으로 설정 그 이유는 데이터길이의 제한이 없기떄문 get방식은 255 자씩 정해져있다.
                OutputStream os = conn.getOutputStream();
                OutputStreamWriter osw = new OutputStreamWriter(os);
                osw.write(param);
                osw.flush();


                InputStream is = conn.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);

                String str = "";
                StringBuffer buf = new StringBuffer();

                while((str= br.readLine())!=null){
                    buf.append(str);
                }
                String recData=buf.toString();


                osw.close();
                br.close();

                handler.sendEmptyMessage(0);

                //서버로 데이터를 보낼때 이런양식으로 되어있다.
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    class DisplayHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            pro.cancel();
            finish();
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
/*
DOM
1, 파싱작업을 라이브러리가 한다
2, 작업이 용이하다
3, 속도가 느리다
4, 메모리를 많이 사용한다.
5, Dom 보다 SAX가 더 빠르다

SAX
1, 파싱작업을 개발자가 한다.
2, 작업이 dom 에 비해 용이하지않다
3, 그러나 코드가 어려운편이 아니다. 태그의 시작점 또는 끝점에 따른
4, 메모리를 적게 먹는다
5, 한번에 쭉! 간다.
6, xml 의 데이터를 일부만 사용할 때. [시스템의 부담을 덜함.]


XML PULL 파싱
1, SAX 와 동일하다
2, 중간에 파싱작업을 중지할 수 있다.
3, 태그를 분석을할때 다음걸로 이동하라고 호출을 한다. 수동적인 파싱방법이다.



*/
