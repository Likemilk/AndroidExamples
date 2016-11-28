package com.likemilk.cho38_sensoruse;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends Activity {
    TextView text1;
    SensorListener listener;
    SensorManager manager;
    //센서작동을 멈추겠다 하면 리스너를 꺼야한다
    //센서는 멈추는게아니라 참조를 멈추는것이다 센서는 휴대폰 작동과동시에 같이 작동한다.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text1 = (TextView)findViewById(R.id.textView2);

        listener = new SensorListener();
        manager = (SensorManager)getSystemService(SENSOR_SERVICE);
    }


    public void get_magnetic(View view){
        // 자기장 센서 객체를 추출
        Sensor sensor = manager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        //센서에 리스너 연결
        boolean chk = manager.registerListener(listener, sensor,SensorManager.SENSOR_DELAY_UI);
        //센서측정 주기를 의미함 SensorDelayUi  //액정을 화면하는 구성하는 속도. SENSOR_DELAY !!
        //등록에 성공하면 지원// 실패하면 이 센서는 존재하지않는다
        if(chk==false){
            text1.setText("이 단말기는 자기장 센서를 지원하지않습니다.");
        }

    }


    public void get_accelerometer(View view){
        Sensor sensor = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        boolean chk = manager.registerListener(listener, sensor,SensorManager.SENSOR_DELAY_UI);
        if(chk==false){
            text1.setText("이 단말기는 가속도 센서를 지원하지 않습니다");
        }
    }

    public void get_orientation(View view){
        MultiListener listener = new MultiListener();
        //센서객체 추출
        Sensor sensor1 = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor sensor2 = manager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        // 리스너 등록
        manager.registerListener(listener,sensor1,SensorManager.SENSOR_DELAY_UI);
        manager.registerListener(listener,sensor2,SensorManager.SENSOR_DELAY_UI);
    }

    class MultiListener implements SensorEventListener{
        //가속도 센서로 측정된 값 배열
        float[] acc_data = null;
        //자기장 센서로 측정된 값 배열
        float[] mng_data = null;
        //메서가 호출될때마다 계속 사용되어져야 하기때문
        @Override
        public void onSensorChanged(SensorEvent event) {
            int type = event.sensor.getType();
            if(type==Sensor.TYPE_ACCELEROMETER){
                acc_data=event.values.clone();
            } else if(type == Sensor.TYPE_MAGNETIC_FIELD){
                mng_data = event.values.clone();
                //값들을 복제한다.
            }
            //자기장센서값과 가속도 센서값을 모두 가져왔을 경우
            if(acc_data!=null && mng_data !=null){
                //행렬 연산을 위한 임시배열
                float rot_data[] = new float[9]; //3*3 행렬을 만들어서 값을 만들어준다. 우리가 만들것은 result
                //최종 결과를 받아낼 배열
                float result[] = new float[3];
                //기울기 행렬을 연산한다.
                SensorManager.getRotationMatrix(rot_data,null,acc_data,mng_data);//이 두개의 배열행렬을 가지고 최종값을 뽑아낸다 rot_data를 가지고데이터를 뽑안낸다.!
                SensorManager.getOrientation(rot_data, result);// 여기에 나온값은 radian 값이나온다.  우리는 도수체계를 쓴다 0부터 360
                //그래서 우리는 라디안 체계로즉 파이체계로 되어있는 값을 도수체계로 뽑을것이다. 그리고 우리는 나침반을 구현할때 반대로 회전시켜주면 나침반이 된다!
                //라디안값을 각도값으로 환산
                result[0] = (float)Math.toDegrees(result[0]);
                result[1] = (float)Math.toDegrees(result[1]);
                result[2] = (float)Math.toDegrees(result[2]);
                //만약 방위값이 음수로 나온다면 360을 더해준다.
                if(result[0]<0){
                    result[0]+=360;
                }
                text1.setText("방위값 :"+result[0]+"\n");
                text1.append("앞뒤 기울기 :"+result[1]+"\n");
                text1.append("좌우 기울기 :"+result[2]+"\n");
                //진북과 자북
                // 나침반을 이용한것은 자북.
                // 실제 절대적인 동서남북측정은 진북
                // 자북의 정확한 측정을위해 이런 측정을 사용한다.
                // 스마트폰에는 진북센서가 없다.
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }

    //센서 리스너를 만들어주어야 한다
    class SensorListener implements SensorEventListener{

        //센서 값이 바뀔때마다 호출되는 메서드
        @Override
        public void onSensorChanged(SensorEvent event) {
            //센서의 타입값을 얻어오겠다.
            int type= event.sensor.getType();
            if(type==Sensor.TYPE_MAGNETIC_FIELD){
                text1.setText("x축 자기장: "+event.values[0]+"\n");
                text1.append("y축 자기장: " + event.values[1] + "\n");
                text1.append("z축 자기장: " + event.values[2] + "\n");

            }else if(type== Sensor.TYPE_ACCELEROMETER){
                text1.setText("x축 기울기: "+event.values[0]+"\n" );
                text1.append("y축 기울기: " + event.values[1] + "\n");
                text1.append("z축 기울기: " + event.values[2] + "\n");
            }
        }
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }
}
// 안드로이드는 카메라를 구현하기가 쉽지않다.
// 기존에 안드로이드의 카메라는 각도마다 다 다르게 찍힌다. 가끔 어느각도에서 찍어도 같은 화면을 제공하는것은
// 자신이 기운값을 계산에서 사진을 돌려준것이다. 실제로 이미지 안에는 회전 각도 값이 포함되어있다.
// 카메라는 기본적으로 이미지를 가로모드로 카메라를 찍게끔되어있다. 그렇지만 어느 기울기에서 똑같이 보이게 되는것은
// 어플 자체에서 이 돌아가는 값을 계산해서 돌려준것이다. 그래서 카메라를 구현을 할대 우리가 직접 구현해주어야 한다.
// 더군다나.. 요즘 나오는 카메라가 성능이 겁나 좋아서 용량이 장난아니다 그런것들이 서버로 가게되면
// 1, 트레픽 쩖 2, 서버용량 과부하 쩖 3,로딩및 업로딩 시간 쩖, 4서비스 불편 쩖
// 그래서 실제로 서버로 업로드를 할때 이미지를 리사이징 하거나 질을 낮춰서 올린다.
//
// 보통 카메라를 찍으면 내부 메모리에 저장이 되는것을 저장하는것과
// 화면에 보이는 이미지를 저장하는 방법 서로 다르다. [썸네일]
// 이미지에는 카메라 조리게값 위도값 각도 경도 셔터 빛의 노출 값 기타등등의 값들이 들어가는대 우리는 카메라를 찍은 각도값을
// exif 인터페이스를 통해 뽑아낼 수있다 . 우리는 그것을 봅아낼것이다.
// 탭탭탭 탭도 써보자~ 최근들어서 쓰기 시작한 탭~구현 -ㅁ`//////////////////