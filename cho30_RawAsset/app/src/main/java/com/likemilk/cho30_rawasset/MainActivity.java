package com.likemilk.cho30_rawasset;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/*
*
프로그램 실행중에 만들어진 파일들은 전부 읽고쓰기가 가능합니다.
어플리케이션을 만들때 들어간 파일들은 전부 읽기 전용이다 res 들을 말하는것들 이미지파일들이나 여러가지들을 말한다.
폰트라던가 여러가지들. 주로 안드로이드의 어플들의 값들은 xml에 저장합니다.

값들은 res/values 폴더 안에 들어간다
파일들은 그이외 등등등 .

파일들은 우리마음대로 정할 수 있다 .
values 폴더에 들어가있는파일들은 values폴더안에 xml파일들은 파일명으로 접근하는게아니라 값의 이름name 으로 접근한다.
drawable 과 layout 은 R.id 똔든 R.drawable. 으로 접근한다.


파일자체가 중요한것인가, 아니면 값들이 중요한가에따라 어디에 위치할지 달라지며 res 안에있는모든파일들은 읽기 전용입니다.
특히 폴더 res 내부에 있는 내용이 아주 중요하다.


안드로이드에서는 raw 에는 동영상이나 파일,데이터파일, 개발을 하는 어플리케이션에 따라서 추가디는파일. r
raw랑 asset 이란 폴더는 서로 다르다.


 raw라는 폴더는 이미지를 제외한 미디어파일이 들어가는곳 mp3나 mp4 같은것이아니라  장비를 관리하기위한 이미지나 그런파일들
 자 회사에서 해당제품을 효율적으로 관리하기위해 사용하는 파일들을 raw 라고 한다 근대 이미지같은것은 drawable 에다가 저장한다.
 그래서 안드로이드는 이미지를 제외한 raw 파일들을 raw 폴더에다가 집어넣는다 동영상,사운드,텍스트파일,기타 등등. raw에서 직접 인풋스트림으로
 뽑아낼 수 있다. 사용할려면 직접뽑아서 분석을해야한다.

 raw 폴더는 res폴더안에 만드는 폴더이다. R.ooo.oooo 로 접근할 수 있다. 직접 지칭가능하다. 그파일을읽어올수있는 InputStream 을 얻어올수있다그러나
 OutputStream 은 안된다.

 asset 이란 폴더가있다 이것은 어플밖에다가 만드는것이다. 여기에는 모든파일을 집어 넣을 수 있다.
 그파일에 들어가는 내용을 가져와서 다 처리할 수 있다.

raw 폴더와 assets 폴더는 전부 다 알고있어야한다.  몇몇파일들은 반드시 이 폻더에 저장한다.
assets => 폰트파일 저장.그이외를 저장하는게 일반적
raw  => 텍스트 ,동영상,사운드파일을 일반적으로 저장하고

안드로이드는 새로운버전이 나오면 개발자가 그거에맞춰 다 패치를해주어야한다
애플은 새로운버전이나오면 애플에서 화면크기를 확대해주거나 키워주거나 지원해준다.

 안드로이드에서는 하나의 어플리케이션이 다양한어플리케이션에 대응하여야한다.



1, 안드로이드 프로젝트를 패키지로 보기로한다.

2, 안드로이드를 만드는 회사들이 구글이외에 아주 다양해서 관리를 따로 할 수 가없다. 그래서 구글에서는 그것에 다 대응해주겠다고했다.
2,
3, 이미지를 언어별로 나누어주어야한다.
4, 어떤폴더를 어디까지 추가를 해야하는가. 언어별폴더,해상도별폴더,언어별로 추가,
5, 이제부터는 버전이달라도 모두!! 똑같은 모습을 보여주기위해 라이브러리로 다 제공을 한다.
6, 안드로이드에 개발할때 필수적으로 해야할 작업을해야한다 그이유는 서비스의 질을 높 일 수있다.
7, 단말기마다 대응하는 작업을 할 것이다.



,,
*/
//res -> directory -> resouce type ->raw  로 생성한다 폴더명은 마음대로 하면안된다.
//raw-> file-> text.txt
//res에 들어가는 파일들은 전부 소문자로 .

//-----------
//안드로이드에서 지정해주는 폴더이름은 절대 바꾸지말것.
//app->Folder->assetsFolder->finish(설정 건들지말고.)
//R.java에 assets 가 들어가는것이아니라 파일 이름이 자유로워도된다.
//assets->new->file->TEST.txt[대문자로 해도 상관없음을 과.시.한.다]


//폰트는 절대 무료 폰트를 쓴다.
public class MainActivity extends ActionBarActivity {
    TextView text1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text1= (TextView) findViewById(R.id.textView);
    }
    public void read_raw(View view){
        try{
            //res폴더의 리소스에 접근할수 있는 객체 생성
            Resources res= getResources();
            //text.txt 파일과 연결되어있는 스트림 호출
            InputStream is = res.openRawResource(R.raw.text);
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            //한줄씩 읽어올것이기때문에 BufferedReader 를 호출했다.
            String str="";
            StringBuffer buf = new StringBuffer();

            while((str=br.readLine())!=null){
                buf.append(str);
            }
            br.close();
            text1.setText(buf.toString());
            //Raw 폴더에다가  넣었다 하면 res.openRawResource 로 얻어올수있다.
            //Resources res= getResources();
            //text.txt 파일과 연결되어있는 스트림 호출
            //InputStream is = res.openRawResource(R.raw.text);
            //assets 에다가넣으면 일일히 처리해야되는대
            //raw 에다가 넣으면 간편하게.
            //어플 내부에서 재생을 하는것이다.
            //이 어플내부에다가
        }catch(Exception ie){
            ie.printStackTrace();
        }
    }
    public void read_assets(View view){
        try{
            AssetManager manager = getAssets();
            //InputStream is = manager.open("test.txt");//파일명을 찾을때에는 대소문자 구분을한다 . 본래 파일ㅇ명 TEST.txt
            //InputStream is = manager.open("TEST.TXT");//확장자명도 대소문자 구분을 해야한다.  기본적인 파일을 찾을수없는 java.io.exception file not found
            InputStream is = manager.open("TEST.txt");
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            //한줄씩 읽어올것이기때문에 BufferedReader 를 호출했다.
            String str="";
            StringBuffer buf = new StringBuffer();

            while((str=br.readLine())!=null){
                buf.append(str);
            }
            br.close();
            text1.setText(buf.toString());

        }catch(Exception e){
            e.printStackTrace();
        }


    }
//폰드는 AssetManager 로 받는다.
//AssetManager 를 사용한다면 assets 폴더를 만들어야한다.
//Resource 는 res 폴더안에있는 자원들을 읽어오는 역할을한다.
// InputStream을 주고받는것이라면 Assets 이든 res 든 상관없다.
// 폰트는 AssetManger 파일로 받을 수 있다
// 폰트파일은 두가지종류가있다 ttf 를 묶음으로 가지고있는녀석과 ttf 만 있는녀석이있는대 묶음으로 가지고있는녀석은 사용하면안된다.
// 그리고 폰트가 한글이 지원되지않는 폰트는 한글이 사용되지않는다. C:/windows/fonts 에 다양한 폰트파일이 있다.

    public void set_font(View view){
        AssetManager manager = getAssets();
        Typeface face = Typeface.createFromAsset(manager, "font.ttf");
        //첫번째 파라메터는 AssetManager 를 받기때문에 manager 를 만들어주어야한다.
        //파일의 이름을 넣는다


        text1.setTypeface(face);

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
