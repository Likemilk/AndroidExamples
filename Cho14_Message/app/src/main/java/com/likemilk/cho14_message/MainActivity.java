package com.likemilk.cho14_message;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Timer;


public class MainActivity extends ActionBarActivity {

    ProgressDialog pro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void basic_toast(View view){
        Toast.makeText(getBaseContext(),"안냥안냥안냥", Toast.LENGTH_SHORT).show();

    }
    //커스텀 토스트
    public void custom_toast(View view){
    //버튼같은거 붙이지마랑... ㅂㄷㅂㄷ? 토스트에 버튼 붙이는 버르장머리없는녀ㅑ석이 어딨노. 노티피케이션에다가 넣어랑.
    // 토스트는 뷰 객체를 세팅해줄수있다.
    // 필요한건 토스트 뷰에 세팅을하고 선택시에 토스트뷰를 띄운다!
    //
        Toast t = new Toast(this);
        //this=>getBaseContext() 어짜피 둘다 같은 컨텍스트나 뷰이다.
        //보여줄 View를 만들어준다.
        //엑티비티는 직접 얻어오면된다. 그러나 나머지는
        //엑티비티는 인플레이터를 추출할수있다.
        //프레그먼트는 엑티비티에 붙을때 붙은 엑티비티의 인플레이터를 추출할수있다.
        //Toast는 액티비티를 가지고있다.
        LayoutInflater inflater = getLayoutInflater();
        View v1 = inflater.inflate(R.layout.taost,null);
        //두번째 파라메터는 null 값. ViewGroup 을 파라메터로 받는 메서드인다
        // 토스트는 그룹이 따로 없으므로 지정하지않는다.
        //도대체 inflate 란 무엇인가?

        // 값을 세팅하겟노라
        ImageView iv = (ImageView)v1.findViewById(R.id.imageView);
        TextView tv = (TextView)v1.findViewById(R.id.textView);
        //View 배경 지정
        v1.setBackgroundResource(android.R.drawable.toast_frame);
        tv.setTextColor(Color.parseColor("#ffffff"));
        tv.setTextColor(Color.WHITE);
        tv.setTextColor(Color.argb(255,255,255,255));
        //Color 객체를 이용한 다양한 방식으로 색상을 지정할 수 있다 .
        tv.setText("안냥커스텀토스트맛있엉");
        //toast에 View 세팅
        t.setView(v1);
        t.setDuration(Toast.LENGTH_SHORT);
        t.setGravity(Gravity.CENTER,0,300);
        //Gravity.CENTER 는 정가운대를기준으로 2번째 3번재 xy 좌표 파라매터를 0 0  만큼 움직인 위치에 말하는것이다.
        //Gravity.CENTER 는 정가운대를기준으로 2번째 3번재 xy 좌표 파라매터를 0 300만큼 움직인 위치에 말하는것이다.
        t.show();
    }
    //-----------------------------------end-------------------------------------------------------------
    public void basic_dialog(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("title");
        builder.setMessage("이거슨 기본 다이얼로구");
        builder.setIcon(R.drawable.ic_launcher);                     //5.0       //4.4    int 값
        builder.setPositiveButton("Positive",new DialogListner()); //right       right     -1 // ok 용도
        //null 값에다가 리스너를 넣으면  된다.
        builder.setNeutralButton("Neutural",new DialogListner());  //left        center    -3 // `
        builder.setNegativeButton("Negative",new DialogListner()); //center      left      -2 // 취소 용도
        //취소버튼에는 아무것도 붙이지 않아도된다. 그러니까 리스너를 안넣어도 된다는것이다 . 그냥 다이얼로그를 닫겟다는 소리니까 말이다.

        builder.show();
    }
    class DialogListner implements Dialog.OnClickListener{
        @Override
        public void onClick(DialogInterface dialog, int which) {
            //which 어 떤 버튼을 눌렀는지 알수있는것. index ? 처럼?
            // View view 를 받듯이~ 그렇듯이~ 늘~~...ㅅㅂㄹ..
            switch(which){
            //버튼으로 분기함.
                case DialogInterface.BUTTON_NEGATIVE:
                    //여기서는this 를 사용하면안된다. 그이유는 DialogListener 를 말하기때문 ... 기본중으 ㅣ기본.
                    Toast.makeText(MainActivity.this,"누른 버튼은 네가티브 which num:"+which,Toast.LENGTH_SHORT).show();
                    break;
                case DialogInterface.BUTTON_NEUTRAL:
                    Toast.makeText(MainActivity.this,"누른 버튼은 뉴츄렬 which num:"+which,Toast.LENGTH_SHORT).show();
                    break;
                case DialogInterface.BUTTON_POSITIVE:
                    Toast.makeText(MainActivity.this,"누른 버튼은 포지티브 which num:"+which,Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
    //-----------------------------------end-------------------------------------------------------------
    public void date_dialog(View view){
    //w자바에서는 날짜가 0월에서 11월 까지이다.  그것ㄷ 월만... month 뿐..
    //그이유는 month는 문자로 표시하기때문에  배열에다가 month 배열에다가 저장해서 불러오는 방법을 사용하기때문에 0월부터 11월 까지이다.
    //또 추가로 요일로 한다면 요일도 monthday tuesday 이런 문자열 이름이 따로 존재하므로 배열로 해야하기때문에 0요일 부터 6요일 까지 있다.
    //시간은 얻어올때 밀리세컨드ㅡ로 얻어온다. 숫자로 얻어온다. 1970년 1월 1일 0시 0분 0초 를 0으로 해서 구해야한다.
        Calendar cal = Calendar.getInstance();
        // Calender 객체로 년/월/일/시/분 을 얻을 수 있다 .
        // 년월일은 DatePickerDialog // 시 분ㅇㄴ TimePickerDialog 로 .
        int year= cal.get(Calendar.YEAR);
        int month= cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog picker = new DatePickerDialog(MainActivity.this,new DateListener(),year,month,day);
        picker.show();

    }
    class DateListener implements DatePickerDialog.OnDateSetListener{
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            Toast.makeText(MainActivity.this,"msg"+year+"년"+(monthOfYear+1)+"월"+dayOfMonth+"일",Toast.LENGTH_SHORT).show();
            time_dialog(view);
        }
    }
//-----------------------------------end-------------------------------------------------------------

    //프로그래스 다이얼로그는 백버튼을 눌러도 종료가안된다. 보통방법으로 종료가안된다. 즉 메세지를 띄우고 잠시
    //일시적으로 어플을 이용하지 못하게끔 만든다.
    //
    public void progress_show(View view){
        //ProgressDialog pro = ProgressDialog.show(MainActivity.this,"타이틀","메시지");
        pro = ProgressDialog.show(MainActivity.this,"타이틀","메시지");
    }

    @Override
    public boolean moveTaskToBack(boolean nonRoot) {
        pro.cancel();
        return super.moveTaskToBack(nonRoot);
    }
//-----------------------------------end-------------------------------------------------------------
    public void time_dialog(View view){
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR);
        int minutes = cal.get(Calendar.MINUTE);
        TimePickerDialog picker = new TimePickerDialog(MainActivity.this,new TimeListener(),hour,minutes,true);
        //첫번째는 Context
        // 두번째는 리스너를 확인버튼에만 적용된다 취소버튼은 아무것도없이 그저 닫히기만한다.
        // 3번째는 시간을 얻은 객체, 4번째는 분을 얻은객체
        //5 번째는 12 시간짜리 am pm 나뉜걸로 하면 false  24시간짜리로하면 true
        picker.show();
    }
    class TimeListener implements TimePickerDialog.OnTimeSetListener{

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String str = hourOfDay+"시"+minute+"분";
            Toast.makeText(MainActivity.this,str,Toast.LENGTH_SHORT).show();
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
