package com.likemilk.cho32_resource;

import android.content.res.Resources;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

//Values 태그는 파일이 중요한것이 아니다. name 이 중요하다.
public class MainActivity extends ActionBarActivity {
    TextView text1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text1=(TextView) findViewById(R.id.textView);
        text1.append("\n");
        //문자열 얻어오기
        Resources res = getResources();//res폴더안에 무엇인가 얻어올때는 항상 Resource 객체를 사용하고 getResource()를 해야함
        String str2= res.getString(R.string.str2);
        text1.append(str2+"\n");
        String str3 = getString(R.string.str2);
        text1.append(str3+"\n");

        //위 두가지는 String 객체로 뽑아 내는경우.

        //text1.setText(R.string.str2);// append에는 int 형으로 넣는것이 없다. setText()에는 있다.

        //출력서식이 있는 문자열!
        String str4=getString(R.string.str3);
        String str5=String.format(str4,"홍길동",100);
        text1.append(str4+"\n`");
        text1.append(str5+"\n");

        // html이 섞여 있는 문자열
        String str6 = getString(R.string.str4);
        Spanned str7 = Html.fromHtml(str6);
        text1.append(str7);
        text1.append("\n");
        //문자열 배열 추출 string-array
        String str_array[] = res.getStringArray(R.array.str_arr);
        //바로 이렇게 출력하면 ID 숫자값이 나온다. text1.append(str_array+"\n");

        for(String str : str_array){
            text1.append(str+"\n");
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
