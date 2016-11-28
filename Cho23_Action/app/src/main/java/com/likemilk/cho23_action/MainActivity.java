package com.likemilk.cho23_action;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

// 패키지 네임 클래스 네임으로 다른엑티비티를 여는것이 가능했지만 5.0부터는 지원하지않는다 이것이 명시적 인텐트
//  안드로이드 OS 에서 요구하는 이름과 사용자가 직접 정의하여 사용하는 이름 으로 는 할수있다.
// 예를들어 사진파일을 찍으면 안드로이드 OS 에서 ㅇ떤 어플리케이션으로 열래? 라는 창이뜨는데 이것은 안드로이 OS 에서 제공하는 이름을 사용하였을때
// 이때 불러오는것은 어플을 불러오는게 아니라 해당하는 어플의 특정 액티비티를 불러오는것이다.
// 즉 앱이 실행단위가 아니라 앱의 액티비티가 실행단위가 되는것이다.


// 패키지 이름과 클래스 이름 != 이름 하고는 다른것이다.

//암시적 인텐트 메니페스트의 name 이 지정되어있는거
//명시적 인텐트  AJAJaj.class 이렇게 불러오는것.
public class MainActivity extends ActionBarActivity {
    TextView text1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text1 = (TextView) findViewById(R.id.textView);
    }
    public void start_sub(View view){
        //메니페스트의 네임에서
        Intent intent = new Intent("com.test.sub");
        // 암시적 인텐트로 다른 액티비티의 서비스나 액티비티를 불러온다.
        // 만약 다른앱에서
        intent.putExtra("data1",100);
        intent.putExtra("data2",12.34);
        intent.putExtra("data3","안녕하세요");

        startActivityForResult(intent,100);
    }
    public void showGoogleMap(View view){
        Uri uri = Uri.parse("geo:37.243243,131.861601"); // 위도와 경도 값이고 geo 는 좌표를 표시할 기호.
        Intent intent =new Intent(Intent.ACTION_VIEW,uri);
        startActivity(intent);

    }
    public void showWebSite(View view){
        Uri uri = Uri.parse("http://www.google.com");
        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
        startActivity(intent);
    }
    public void playVideo(View view){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        path = "file:///"+path +"/video.mp4";//  "file:///" ///는 리눅스 체계가 원래 이렇다
        //외부 저장소까지의 경로
        Uri uri = Uri.parse(path);
        intent.setDataAndType(uri,"video/mp4");
        startActivity(intent);

    }
    public void playMusic(View view){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        path ="file:///"+ path +"/song.mp3";
        //외부 저장소까지의 경로
        Uri uri = Uri.parse(path);
        intent.setDataAndType(uri,"audio/mp3");
        startActivity(intent);
    }

//미디어 컨탠츠파일을 읽어올껀대 보통은 경로명을 직접 작성하는대 우리는 음악이나 미디어컨텐츠파일들을
//메서드로 불러올것이다. 이것이 안정성에 최선의 방책이 될수도있다. 메서드를 통해 외부저장소의 경로를 구할것이다.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //코드가 다른액티비티를 갔다가 다시 되돌아올때를 말한다.
        //다른액티비티로 갔다가 되돌아올대 불려지는 콜백함수
        int value1=data.getIntExtra("value1",0);
        double value2=data.getDoubleExtra("value2",0);
        String value3=data.getStringExtra("value3");
        text1.setText("value1"+value1+"\n");
        text1.append("value2"+value2+"\n");
        text1.append("value3"+value3+"\n");
        finish();

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
