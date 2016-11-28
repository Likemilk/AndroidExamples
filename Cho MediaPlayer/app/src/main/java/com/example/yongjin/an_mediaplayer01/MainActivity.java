package com.example.yongjin.an_mediaplayer01;

import  android.support.v7.app.ActionBarActivity;
import android.os.Bundle;


/*

동영상은 영상과 음원이 같이 나오게 되는것이다.

밑에 아래부분에는 미리보기가 나오게끔만들것이다.
파일의 위치같은것은 정적인 상수로 선언한다.

사용할것. MediaPlayer / MediaRecoder / SurfaceHolder
SurfaceHolder 는 서페이스 뷰를 이용해서 미디어를 보여질것이다 이것은 클래스 자체에 가속계가 붙어있어서좋은 퍼포먼스를 자아낸다.

Environment.getExternalStorageState(); 이것은 각자 스마트폰 및 단말기에 존재하는 저장 장소의 Path 를 받아올 수 있다.
SufaceView 를 다룰수있는것은 SurfaceHoler 클래스이다. 이것을 이용해서 SurfaceView 를 컨트롤 할 수 있다.
미리보기할때 렌더링을 가장 빠르게 할 수 있는것이 바로 SurfaceView 이다.

녹음버튼 클릭시

동영상 촬영후 엘범에 넣어주고 싶을때.
ContentValues 클래스를 이용한다 그리고 insert 를 이용해서 넣어준다.











*/

import android.content.ContentValues;
import android.content.Intent;
import android.hardware.Camera;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

//동영상 녹화하기
public class MainActivity extends ActionBarActivity {

    public static final String TAG = "AndroidVideoRecorder01";

    private static String EXTERNAL_STORAGE_PATH = "";
    private static String RECORDED_FILE = "video_recorded";
    private static int fileIndex = 0;
    private static String filename = "";

    //오디오/비디오 재생 플레이어
    MediaPlayer player;
    //비디오 녹화 플레이어
    MediaRecorder recorder;
    //사진을 찍거나 동영상을 촬영에 사용
    private Camera camera = null;
    //서피스뷰를 제어하는 홀더 객체
    SurfaceHolder holder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // check external storage
        String state = Environment.getExternalStorageState();

        if (!state.equals(Environment.MEDIA_MOUNTED)) {
            Log.d(TAG, "External Storage Media is not mounted.");
        } else {
            EXTERNAL_STORAGE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
        }

        /* 동영상을 녹화할 때 미리보기를 구현하기 위해 서피스뷰 객체 생성
           - SurfaceView : 하드웨어 가속기능을 이용해 랜더링을 빠르게 할 수 있도록 지원
                           (다만, 젤라빈 버전 이후부터는 일반뷰에도 하드웨어 가속기능을 사용가능)
           - 서피스홀더 : 세피스뷰를 제어하는 객체
         */
        //서피스뷰 객체 생성
        SurfaceView surface = new SurfaceView(this);

        //서피스홀더 객체 참조
        holder = surface.getHolder();

        //카메라 미리보기 타입(SURFACE_TYPE_PUSH_BUFFERS)을 홀더에 지정
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        //카메라 미리보기를 보여줄 FrameLayout 참조
        FrameLayout frame = (FrameLayout) findViewById(R.id.videoLayout);

        //서피스뷰를 FrameLayout 에 Add
        frame.addView(surface);

        //버튼 객체 참조
        Button recordBtn = (Button) findViewById(R.id.recordBtn);//녹음버튼
        Button recordStopBtn = (Button) findViewById(R.id.recordStopBtn);//녹음중지버튼
        Button playBtn = (Button) findViewById(R.id.playBtn);//재생버튼
        Button playStopBtn = (Button) findViewById(R.id.playStopBtn);//재생중지버튼

        //녹음 버튼 클릭시
        recordBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    if (recorder == null) {
                        //녹음을 위한 미디어리코더 객체 생성
                        recorder = new MediaRecorder();
                    }
                    //오디오/비디오 속성 설정
                    recorder.setAudioSource(MediaRecorder.AudioSource.MIC);//오디오 입력소스
                    recorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);//비디오 입력소스
                    recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);//미디어 출력 포멧
                    recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);//오디오 인코더
                    recorder.setVideoEncoder(MediaRecorder.VideoEncoder.DEFAULT);//비디오 인코더

                    //동영상 파일명 생성을 위해 createFilename() 메소드 호출
                    filename = createFilename();
                    Log.d(TAG, "current filename : " + filename);

                    //저장 경로 설정
                    recorder.setOutputFile(filename);

                    /* 카메라 미리보기를 미디어리코더에서 사용할 수 있도록 미리보기 화면 설정
                       - 카메라 미리보기 정보는 getSurface()를 이용해 얻을 수 있음
                     */
                    recorder.setPreviewDisplay(holder.getSurface());
                    recorder.prepare();//동영상 녹화 준비
                    recorder.start();//동영상 녹화 시작

                } catch (Exception ex) {
                    Log.e(TAG, "Exception : ", ex);

                    recorder.release();//리코더 해제
                    recorder = null;
                }
                Toast.makeText(getApplicationContext(),"동영상 녹화가 시작되었습니다.",
                        Toast.LENGTH_LONG).show();
            }
        });

        //동영상 녹화 정지 버튼 클릭시
        recordStopBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (recorder == null)
                    return;

                recorder.stop();
                recorder.reset();
                recorder.release();
                recorder = null;

                Toast.makeText(getApplicationContext(),"동영상 녹화가 중지되었습니다.",
                        Toast.LENGTH_LONG).show();

                //미디어앨범에 저장하기 위해 ContentValues 객체 생성
                ContentValues values = new ContentValues(10);

                //동영상에 관한 각종 정보를 ContentValues 에 저장
                values.put(MediaStore.MediaColumns.TITLE, "RecordedVideo");
                values.put(MediaStore.Audio.Media.ALBUM, "Video Album");
                values.put(MediaStore.Audio.Media.ARTIST, "Mike");
                values.put(MediaStore.Audio.Media.DISPLAY_NAME, "Recorded Video");
                values.put(MediaStore.MediaColumns.DATE_ADDED, System.currentTimeMillis() / 1000);
                values.put(MediaStore.MediaColumns.MIME_TYPE, "video/mp4");
                values.put(MediaStore.Audio.Media.DATA, filename);

                //녹화된 파일을 내용제공자를 이용해 동영상 목록으로 저장
                Uri videoUri = getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values);

                if (videoUri == null) {
                    Log.d("SampleVideoRecorder", "Video insert failed.");
                    return;
                }

                // 동영상 목록을 사진 갤러리에 추가
                // 영상 액션을 이용해 uri 객체를 브로드캐스팅하면 다른 앱에서 앨범 정보를 스캔할 수 있음
                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, videoUri));
            }
        });

        //동영상 재생 버튼 클릭시
        playBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (player == null) {
                    //미디어플레이어 객체 생성
                    player = new MediaPlayer();
                }

                try {
                    //파일 저장 경로 지정
                    player.setDataSource(filename);

                    //미디어플레이어에 사용할 서피스뷰 홀더 설정
                    player.setDisplay(holder);

                    player.prepare();
                    player.start();
                } catch (Exception e) {
                    Log.e(TAG, "Video play failed.", e);
                }
            }
        });

        //동영상 재생 중지 버튼 클릭시
        playStopBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (player == null)
                    return;

                player.stop();
                player.release();
                player = null;
            }
        });
    }

    //파일이름 생성
    private String createFilename() {
        fileIndex++;

        String newFilename = "";
        if (EXTERNAL_STORAGE_PATH == null || EXTERNAL_STORAGE_PATH.equals("")) {
            // use internal memory
            newFilename = RECORDED_FILE + fileIndex + ".mp4";
        } else {
            newFilename = EXTERNAL_STORAGE_PATH + "/" + RECORDED_FILE + fileIndex + ".mp4";
        }

        return newFilename;
    }

    @Override
    protected void onPause() {
        if (camera != null) {
            camera.release();
            camera = null;
        }

        if (recorder != null) {
            recorder.release();
            recorder = null;
        }

        if (player != null) {
            player.release();
            player = null;
        }

        super.onPause();
    }
}
