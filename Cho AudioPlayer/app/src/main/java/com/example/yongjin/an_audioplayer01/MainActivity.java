package com.example.yongjin.an_audioplayer01;
/*

AudioPlayer01
이기능을 사용하기위해 인터넷 옵션을 추가하여야한다.
동영상 재생하기.
간단한 방법= Video 위젯을 사용한다.

.prepare 일부 프레임에 대해서 읽어와서 음원에대한 정보를 출력하는 부분
.seekTo // 중지한 시점으로 이동하여 오디오를 재생한다. 만약 0 으로 주면 처음부터 다시시작하겠다는 이야기이다.
볼륨조절 getSystemService 에서 불러오면된다.

//
MicRecord 오디오 녹음과 재생하기.
녹음 사용한 미디어 리코더 객체를 생성할때.
퍼미션
저장데이터 읽기
저장데이터 쓰기
그리고 RECORD_AUDIO


주의 :
Recoder 의 설정을 할때 설정 위치가바뀌면안된다.
1) ㅇ오디오 입력 소스 설정
2) 오디오 출력 포맷 설정
3) 오디오 인코더(코덱) 설정  //Default 는 스마트폰 자체에서 사용하고 있는 코덱을 설정하겠다.
4) 녹음된 파일 저장 경로 지정. 경로는 SQL lite 의 경로에 저장하겠다.

4)// 경로의 지정은 단말기의 외장 메모리 경로를 가져와야한다 경로를 하드코딩으로 일일히 다 정하게될경우 시스템의 환경이 각각다른
안드로이드의 경우에는 배포용으로 사용할 수 만은 없다.

*    코딩부분에서는 온클릭부분에 파일이 오류가 난 부분은 파일이 저장이 되어있지 않거나 파일의 경로가 잘못 설정되있다거나
그런경우가 빈번하니 이부분 잘 유의하길 바란다.      *
그리고 플레이어에 멈추고 릴리즈 하는것을 유의한다






*/

        import android.media.MediaPlayer;
        import android.media.MediaRecorder;
        import android.os.Bundle;
        import android.support.v7.app.ActionBarActivity;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.Toast;

//오디오 녹음 및 재생
public class MainActivity extends ActionBarActivity {
    //녹음 파일 경로
    final private static String RECORDED_FILE = "/sdcard/SQLiteDB/recorded.mp4";
    //파일명을 저장할 변수
    private String record_file_name;

    //재생을 위한 미디어플레이어 객체
    MediaPlayer player;

    //녹음을 위한 미디어리코드 객체
    MediaRecorder recorder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button recordBtn = (Button) findViewById(R.id.recordBtn);
        Button recordStopBtn = (Button) findViewById(R.id.recordStopBtn);
        Button playBtn = (Button) findViewById(R.id.playBtn);
        Button playStopBtn = (Button) findViewById(R.id.playStopBtn);

        //녹음 버튼 클릭시
        recordBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //이미 리코더를 사용중에 있으면 초기화
                if (recorder != null) {
                    recorder.stop();//리코더 중지
                    recorder.release();//리코더 자원 해제
                    recorder = null;
                }

                //녹음시 사용할 미디어리코더 객체 생성
                recorder = new MediaRecorder();

                /* 리코드 속성 설정 하는것은 순서가 가장 중요하다 이 순서를 어기지 않도록 한다.
                   - 속성 설정 순서(순서가 바뀌면 실행 오류 발생)
                     1) 오디오 입력 소스 설정
                     2) 오디오 출력 포멧 설정
                     3) 오디오 인코더(코덱) 설정
                 */

                recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
                recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);

                //녹음된 파일 저장 경로 지정
                recorder.setOutputFile(RECORDED_FILE);

                /* 실제 배표용 앱을 만들 때 파일 저장 경로 지정은
                   안드로이드 기기의 외장 메모리에서 루트 폴더의 절대경로를 가져와 저장 해야함

                  . 단말기의 외장 메모리 경로를 가져온다.
                  String path = Environment.getExternalStorageDirectory().getAbsolutePath();

                  . 파일명을 저장할 변수 = path 와 파일명을 결합
                  m_file_name = path + "/recorded.mp4";

                  . 녹음된 데이터가 저장될 파일 경로를 설정
                  recorder.setOutputFile(m_file_name);
                 */

                try {
                    recorder.prepare();//녹을 위한 플레이어 준비
                    recorder.start();//녹음 시작

                    Toast.makeText(getApplicationContext(), "녹음을 시작합니다.",
                            Toast.LENGTH_LONG).show();
                } catch (Exception ex) {
                    Log.e("SampleAudioRecorder", "Exception : ", ex);
                }
            }
        });

        //녹음중지 버튼 클릭시
        recordStopBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (recorder != null) {
                    try {
                        /* 녹음이 안된 상태(파일 저장이 안된경우)에서 stop()메소드를 호출하면
                            IllegalStateException 예외가 발생.
                         */
                        recorder.stop();//녹음 중지
                        recorder.release();//미디어플레이어와 연결된 리소스 해제
                        recorder = null;

                        Toast.makeText(getApplicationContext(), "녹음이 중지되었습니다.",
                                Toast.LENGTH_LONG).show();
                    }catch (IllegalStateException e) {
                        Toast.makeText(getApplicationContext(),
                                "IllegalStateException",Toast.LENGTH_LONG).show();
                    }

                }
            }
        });

        //재생 버튼 클릭시
        playBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (player != null) {
                   /* 녹음이 안된 상태(파일 저장이 안된경우)에서 stop()메소드를 호출하면
                      IllegalStateException 예외가 발생.
                   */
                    player.stop();
                    player.release();
                    player = null;
                }

                try {
                    //녹음 재생을 위한 미디어플레이어 생성
                    player = new MediaPlayer ();

                    //파일 저장 경로 지정
                    player.setDataSource(RECORDED_FILE);
                    player.prepare();//재생 준비
                    player.start();//재생

                    Toast.makeText(getApplicationContext(), "녹음된 파일을 재생합니다.",
                            Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Log.e("SampleAudioRecorder", "Audio play failed.", e);
                }
            }
        });

        //재생중지 버튼 클릭시
        playStopBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (player != null) {
                    /* 녹음이 안된 상태(파일 저장이 안된경우)에서 stop()메소드를 호출하면
                      IllegalStateException 예외가 발생.
                   */
                    player.stop();
                    player.release();
                    player = null;

                    Toast.makeText(getApplicationContext(), "재생을 중지합니다.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /* 액티비티가 onPause 상태일때 호출되는 메소드
       - onPause 에서 할당받은 자원들을 해제하길 권장
    */
    @Override
    protected void onPause() {
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
