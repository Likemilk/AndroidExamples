package com.likemilk.cho33_resource2;

import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    TextView text1,text2,text3,text4,text5,text6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text1= (TextView)findViewById(R.id.textView);
        text2= (TextView)findViewById(R.id.textView2);
        text3= (TextView)findViewById(R.id.textView3);
        text4= (TextView)findViewById(R.id.textView4);
        text5= (TextView)findViewById(R.id.textView5);
        text6= (TextView)findViewById(R.id.textView6);

        //int color1= Color.TRANSPARENT;//이것은 뒷배경이 보이는 배경 즉 투명색~~ 안드로이드는 알파 래드 그린 블루
        int color1=Color.parseColor("#ffff0000"); //#AARRGGBB
        text1.setTextColor(color1);
        int color2=Color.argb(255,0,255,0);//AAA,RRR,GGG,BBB
        text2.setTextColor(color2);

        // res->resource file -> color finish!

        Resources res = getResources();

        int color4 = res.getColor(R.color.color1);
        int color5 = res.getColor(R.color.color2);
        int color6 = res.getColor(R.color.color3);
        int color7 = res.getColor(R.color.color4);

        text3.setTextColor(color4);
        text4.setTextColor(color5);
        text5.setTextColor(color6);
        text6.setTextColor(color7);

        float px = res.getDimension(R.dimen.px);        float dp = res.getDimension(R.dimen.dp);
        //dp: 가변형 , 160dpi => 1dp = 1px
        float sp = res.getDimension(R.dimen.sp);
        //sp: 폰트에 따라 가변형
        float in = res.getDimension(R.dimen.in);
        //inch
        float mm = res.getDimension(R.dimen.mm);
        //밀리미터
        float pt = res.getDimension(R.dimen.pt);
        //point 1/72인치, 출판용 문서작업시 사용하는 글자크기를 말한다.

        text1.setText("px : "+px+"\n");
        text2.setText("dp : " + dp + "\n");
        text3.setText("sp : " + sp + "\n");
        text4.setText("in : " + in + "\n");
        text5.setText("mm : " + mm + "\n");
        text6.setText("pt : " + pt + "\n");

        float px2 = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,10,res.getDisplayMetrics());
         // COMPLEX_UNIT_DIP 나는 dip 값을 환산하고 싶다!
        TextView text7 = (TextView)findViewById(R.id.textView7);
        text7.setText("10dp : "+ px2);
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
폴더에  - 하이픈으로 속성및 설정을 주면
각각다른폴더에 같은이름으로 원하는 환경마다 설정해줄 수 있다.

안드로이드로 이미지를 만드는것은 잘 안한다.
배경이미지같은것은 타일링을 하여서 배치하는것이 좋다. 그이유는 디바이스마다 크기가 다르기때문이고
버튼을 눌렀을때 누른 감각이 있게 버튼을 눌렀을대와 안눌렀을때의 이미지를 따로 준비하는것이 좋다.




*/