package com.likemilk.cho37_sensorlist;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.List;
// 증강 현실 3년 4년 요즘에는 냉장고에 제품을 넣어서 바코드로 찍으면 해당 제품에 대한 레시피가 인터넷으로 검색됨.
// 사이드 미러가 사라지고 카메라로 대체됨 그리고 창문에 디스플레이가 나옴
// 차간통신이 가능.이번년도 또는 내년에 표준프로토콜을 정한다. 사물인터넷의 핵심은 센서!
// 센서는 정보수집! 정보를 가공해주는것이 프로그래머의 역할
// //////////////////////////////////////////////


public class MainActivity extends ActionBarActivity {
    TextView text1;
    SensorManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text1 = (TextView) findViewById(R.id.textView);
        manager = (SensorManager) getSystemService(SENSOR_SERVICE);
    }
    public void getSensorInfo(View view){
        //센서 목록을 가져온다
        List<Sensor> list = manager.getSensorList(Sensor.TYPE_ALL);
        //text1.setText("List<Sensor> list.toString() : "+list.toString());
        text1.setText("");
        for(Sensor s : list){
            text1.append("센서이름: "+s.getName()+"\n");
            text1.append("센서타입: "+s.getType()+"\n\n");
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
