package com.likemilk.cho31_media;

import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.VideoView;

//new-> android resource directory -> type:raw //raw에 미디어 파일을 붙임.

public class MainActivity extends ActionBarActivity {

    MediaPlayer mp;
    VideoView vv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vv=(VideoView)findViewById(R.id.videoView);
    }

    public void sound(View view){
        if(mp==null){//재생 어플내부에서 재생하는거임.
            mp=MediaPlayer.create(this,R.raw.song);
            mp.start();
        }else{//중지
            mp.stop();
            mp=null;//비우고
        }
    }
    public void video(View view){
        //VideoView 는 R.raw.video 로 가져오지못한다 Uri 로 가져와야한다.
        //application 패키지를 가져오겠다
        if(vv.isPlaying()==false) {
            String pkg = getPackageName();
            Uri uri = Uri.parse("android.resource://" + pkg + "/raw/video");
            vv.setVideoURI(uri);
            vv.start();

        }else{
            vv.stopPlayback();//정지후 다시재생
            //vv.resume(); //일시정지.
        }

    }
    public void web_sound(View view){
        if(mp==null){//재생 어플내부에서 재생하는거임.
            Uri uri = Uri.parse("http://edu2.softcampus.co.kr/song.mp3");
            mp=MediaPlayer.create(this,uri);
            mp.start();
        }else{//중지
            mp.stop();
            mp=null;//비우고
        }
    }
    public void web_video(View view){
        //VideoView 는 R.raw.video 로 가져오지못한다 Uri 로 가져와야한다.
        //application 패키지를 가져오겠다
        if(vv.isPlaying()==false) {
            String pkg = getPackageName();
            Uri uri = Uri.parse("http://edu2.softcampus.co.kr/video.mp4");
            vv.setVideoURI(uri);
            vv.start();

        }else{
            vv.stopPlayback();//정지후 다시재생
            //vv.resume(); //일시정지.
        }

    }

/*
웹서버에서 영상이나 오디오를 웹이 띄우는방법
웹서버에다가 영상이나 오디오를 복사해서 넣으면댄당
*/

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

여러단말기에 대응할 수 있는 법.
언어별
해상도별


*/