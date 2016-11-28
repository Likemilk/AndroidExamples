package com.likemilk.cho26_datastorage;

import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/*
간단하게 저장할때는 preference를 사용 //많이 저장할대는 database 를 사용한다.
서로 상호보완적으로 사용하는것이 좋다 단말기에서는 내부 파일시스템을 볼수없다. 그러나 실제로 단말기에서는 똑같이 저장된다.
아이폰은 내부저장소밖엥벗고 안드로이드는 내부저장소와 외부저장소가 따로 있다

내부 = 임시저장소  : 사진이나 머 그런것들이 저장되는것들. 언젠가는 os 가 삭제한다. , 언젠가는 삭제될 파일들이 저장되어있는곳들.

     = 내부저장소  :  임시저장소에 자정하기 좀 큰파일들.
              ㄴ> 앱설치
              ㄴ> 데이터 저장(25mb 미만)만 읽을수있다 그러나 4.01부터는 4기가 까지 읽을수있다. 파일형식은 fat32  애초에 내부저장소는 용량이 작다
              ㄴ> 게임이 깔리는 방법 용량을 적게 코드만 어플에 넣어놓고 나머지는 네트워크를 통해 외부저장소에 추가하는방법을 이용한다.
              ㄴ> 내부저장소는 해킹을 해야만 접근이 가능함.
              ㄴ> 데이터베이스나 프리퍼런스 둘다 전부는 내부저장소에 저장이된다.
              ㄴ> 보안상의 규칙을잘 지키고있다  해킹을해야만 접근 할 수 있으므로.

     = 외부저장소 : 대용량 파일 과 미디어 파일을 저장할수있다.
                    4.0부터는 앱설치를 가능하다.[가능한 앱설치는 하지않는다 보안상 여러문제가 있다.]
                    데이터 파일을 저장할 수 있다.
                    안드로이드어플에 관련된 데이터는 android/apk 에 외부저장된 apk 를 볼 수 있다.

//사용자가 정보를 검색을한다 그다음에 어떤사이트를 들어가는지도 데이터로 저장한다 업계들은...
// 추천시스템은 amazon 이란 페이지가 가장 잘 되어있다.

*/
public class MainActivity extends ActionBarActivity {
    TextView text1;
    //외부 출력때 사용할 스트링변수
    String sd_path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text1 = (TextView)findViewById(R.id.textView);
        sd_path = Environment.getExternalStorageDirectory().getAbsolutePath();
        //절대경로명
        sd_path = sd_path +"/android/data/" + getPackageName(); // 안드로이드 경로명을 구하는 방법
                            //끝에 꼭  / 를 넣어야한다 왜냐하면 안드로읻 os 에서 패키징되어있던 어플을 삭제할대 외부저장소의 android 의 data 폴더에서 패키지명을 삭제하는대
        // 삭제를 못하게된다 그 이유는!  / 를 빼고 저장하게된다면
        // android/datacom. 이렇게되는대 수정하면
        // android/data/com 이렇게 되기때문이다.



        //폴더의 존재 여부를 확인한다.
        File file = new File(sd_path);
        if(file.exists()==false){
            file.mkdir(); //폴더가 안만들어지고 파일형태가 만들어지게 될것이다. 만약 사용자가 볼수있게 하겠다면 권한 설정해줄게 많다..
                        //리눅스의 파일시스템에따라 만들어지는것이다. 그러므로 당황 ㄴㄴ해 /
                        //폴더가 없으면~ 만들고 있으면 안만들고 ~
        }
        //미디어파일들을 단말기 내부의 데이터베이스에 저장해놓기때문에 음악파일이나 미디어파일을 빠르게 찾을 수 있다.
        //만약 그렇게 안되있다면실제로 일일히 디렉토리 들어가서~ 미디어를 보여주고~ 그다음에 크릭하고... 꽤나 번거롭다.

    }

    //확인하기 Device simulator 관리자 들어가서 파일에서 data->data->[내 프로젝트]->files 에 보면 저장되어있는걸 확인 할 수있다. ]
                                                   // 외부저장소는 data-data-app-프로잭트->files 에 추가되어있다.

    //내부저장소에 만들어지는 데이터는 어플삭제와 동시에 같이 삭제됨.
    public void writeInternal(View view){
        try{
            //자바에서 파일 인풋스트림과 아웃풋 스트림을 사용하는데 똑같이 사용하면된다
            FileOutputStream fos = openFileOutput("myFile.txt",MODE_PRIVATE); //openFileOutput 은 내부저장소에 쓰기위함
            //모드 Private 하면 덮어씌고 append 하면 이어서 쓴다. MODE_PRIVATE , MODE_APPEND
            DataOutputStream dos = new DataOutputStream(fos);
            //데이터를 쓰기 편하게 하기위해!
            dos.writeInt(100);
            dos.writeDouble(123.456);
            dos.writeUTF("안녕하세요");
            dos.flush();//
            dos.close();
            text1.setText("파일 쓰기 완료(내부)");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void readInternal(View view){
        try{
            FileInputStream fis = openFileInput("myFile.txt"); //openFileOutput 은 내부저장소에 쓰기위함
            DataInputStream dis = new DataInputStream(fis);

            int data1 = dis.readInt();
            double data2 = dis.readDouble();
            String data3 = dis.readUTF();

            text1.setText("data1 : "+data1+"\n");
            text1.append("data2 : "+data2+"\n");
            text1.append("data3 : "+data3+"\n");

            dis.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

//-----------외부 저장소에 쓰기 [권한필요]
//
/*
     uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE>
    uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"
*/
    public void writeExternal(View view){
        try{
            FileOutputStream fos = new FileOutputStream(sd_path+"/sdFile.dat");
             //만약 이렇게 경로를 안적어주면 내부저장소에 저장이되고  이렇게 할경우에는 외부 저장소에 저장이되게한다
            DataOutputStream dos = new DataOutputStream(fos);
            //자바에서는 기본 스트림만 얻어오면 어떤코드든 비슷비슷합니다~
            dos.writeInt(200);
            dos.writeDouble(1234.5667);
            dos.writeUTF("밥먹엇니?");
            dos.flush();

            fos.close();
            dos.close();
            text1.setText("저장완료[내부]");

        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void readExternal(View view){
        //확인 방법 android device tool -> sdstorage-> sdcard -> android->  [패키지명] -> 뙇
        try{
            FileInputStream fis = new FileInputStream(sd_path + "/sdFile.dat"); //openFileOutput 은 내부저장소에 쓰기위함
            DataInputStream dis = new DataInputStream(fis);

            int data1 = dis.readInt();
            double data2 = dis.readDouble();
            String data3 = dis.readUTF();

            text1.setText("data1 : "+data1+"\n");
            text1.append("data2 : "+data2+"\n");
            text1.append("data3 : "+data3+"\n");

            dis.close();
        }catch(Exception e){
            e.printStackTrace();
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
