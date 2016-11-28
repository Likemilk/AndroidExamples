package com.likemilk.cho35_device;

import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    TextView text1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text1 = (TextView)findViewById(R.id.textView);

        TelephonyManager manager =(TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        text1.setText("전화번호 : "+manager.getLine1Number()+"\n");
        text1.append("장치 ID : " + manager.getDeviceId() + "\n");
        text1.append("SIM 국가코드 : " + manager.getSimCountryIso() + "\n");
        text1.append("모바일 국가코드 : " + manager.getSimOperator() + "\n");
        text1.append("서비스 이름 : " + manager.getSimOperatorName() + "\n");
        text1.append("SIM 일련번호 : " + manager.getSimSerialNumber() + "\n");
        text1.append("SIM 상태 : " +manager.getSimState()+"\n");
        text1.append("음성 메일 번호 : " +manager.getVoiceMailNumber()+"\n");
        //안드로이드는 전화번호를 추출하는것이 자유롭지만 아이폰은 그렇지 않다.
        text1.append("------------------\n");
        text1.append("Device Serial Number : "+ Build.SERIAL+"\n");
        text1.append("안드로이드 버전 : "+ Build.VERSION.RELEASE+"\n");
        //미개통이나 타블릿하면 전화번호나 이런것들은 전부 null값이나온다.
        //이러한정보들은 iphone 에서도 뽑아낼수있다. 그러나 전화번호나 그런건 안된다. 
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
